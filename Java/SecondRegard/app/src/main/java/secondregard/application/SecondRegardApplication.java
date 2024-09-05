package secondregard.application;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;

import common.collection.CollectionUtils;
import common.duration.CodeDurationCounter;
import common.duration.FormatterUtils;
import common.filesanddirectories.DirectoryHelper;
import pdfmodification.helpers.PDFModificationHelpers;
import secondregard.data.inputpdfdocument.builders.ActionsToPerformDataModel;
import secondregard.data.inputpdfdocument.builders.InputPDFAndActionsToPerformDataModel;
import secondregard.data.inputpdfdocument.builders.InputPDFsDataModel;
import secondregard.data.inputpdfdocument.builders.SecondRegardActionsToPerformModelBuilder;
import secondregard.data.users.SecondRegardAllowedUser;
import secondregard.data.users.SecondRegardAllowedUsersFromCsvLoader;
import secondregard.helpers.SecondRegardConstants;
import secondregard.helpers.SecondRegardHelpers;
import secondregard.helpers.SecondRegardParams;
import zip4j.Zip4JZipManager;

/**
 * https://github.com/topobyte/pdfbox-tools/blob/master/src/main/java/org/apache/pdfbox/tools/OverlayPDF.java
 * 
 */

public class SecondRegardApplication {
	protected static final Logger LOGGER = LogManager.getLogger(SecondRegardApplication.class);

	// public static final MultiThreadStrategy MULTITHREAD_STRATEGY =
	// MultiThreadStrategy.ONE_THREAD_PER_INPUT_PDF;
	public static final MultiThreadStrategy MULTITHREAD_STRATEGY = MultiThreadStrategy.MONOTHREAD;

	public static void main(String[] args) throws Exception {

		CodeDurationCounter applicationDurationCounter = new CodeDurationCounter();

		LOGGER.info(() -> "Application started");

		List<SecondRegardAllowedUser> pdfAllowedUsers = SecondRegardAllowedUsersFromCsvLoader
				.getPDFAllowedUsersFromCsvFile("Input/Password à garder en INTERNE.csv").stream()
				.filter(SecondRegardAllowedUser::isAllowedToAccessPDF)
				.filter(e -> SecondRegardParams.ALLOWED_USER_PRENOMS.isEmpty()
						|| SecondRegardParams.ALLOWED_USER_PRENOMS.contains(e.getPrenom()))
				.filter(e -> SecondRegardParams.ALLOWED_USER_ENTITES.isEmpty()
						|| SecondRegardParams.ALLOWED_USER_ENTITES.contains(e.getEntite()))
				.toList();

		LOGGER.info(() -> "Number of allowed users:" + pdfAllowedUsers.size());

		SecondRegardActionsToPerformModelBuilder secondRegardActionsToPerformModelBuilder = new SecondRegardActionsToPerformModelBuilder(
				"Input/SecondRegardActionsToPerformDataModel.json");
		ActionsToPerformDataModel actionsToPerformDataModel = secondRegardActionsToPerformModelBuilder
				.getActionsToPerformDataModel();

		if (SecondRegardParams.PROCESS_ZIPS) {
			SecondRegardHelpers.processZipsToCreate(pdfAllowedUsers, actionsToPerformDataModel.getZipBatchs());
		}

		if (SecondRegardParams.PROCESS_PDFS) {
			List<Thread> pDFProcessorThreads = addWatermarkAndEncryptButWatermarkIsJustTextAdded(
					actionsToPerformDataModel.getPdfBatchs(), pdfAllowedUsers);

			if (!pDFProcessorThreads.isEmpty()) {
				LOGGER.info("Start threads");
				pDFProcessorThreads.forEach(e -> e.start());
				LOGGER.info("Wait for threads end");
				pDFProcessorThreads.forEach(e -> {
					try {
						e.join();
					} catch (InterruptedException e1) {
						LOGGER.fatal(() -> "Could not join thread" + e);
						e1.printStackTrace();
					}
				});
			}

		}
		LOGGER.info(() -> "Application end. Duration:"
				+ FormatterUtils.GetDurationAsString(applicationDurationCounter.getDuration()));

	}

	/**
	 * https://stackoverflow.com/questions/32844926/using-overlay-in-pdfbox-2-0
	 * 
	 * @param pdfAllowedUser
	 * 
	 * @throws IOException
	 */
	private static List<Thread> addWatermarkAndEncryptButWatermarkIsJustTextAdded(
			List<InputPDFAndActionsToPerformDataModel> pdfBatchs, List<SecondRegardAllowedUser> pdfAllowedUsers)
			throws IOException {

		List<Thread> pdfProcessorThreads = new ArrayList<>();

		for (InputPDFAndActionsToPerformDataModel pdfBatch : pdfBatchs) {

			for (InputPDFsDataModel inputPdf : pdfBatch.getInputPdfs()) {
				List<File> inputPDFFiles = inputPdf.getInputPDFFiles();

				for (File inputPDFFile : inputPDFFiles) {
					pdfProcessorThreads.addAll(handlePdf(pdfAllowedUsers, pdfBatch, inputPdf, inputPDFFile));
					// pdfProcessorThreads.add(new PDFProcessorThread(pdfAllowedUsers, pdfBatch,
					// inputPdf, inputPDFFile));

				}
			}
		}
		return pdfProcessorThreads;
	}

	private static List<Thread> handlePdf(List<SecondRegardAllowedUser> pdfAllowedUsers,
			InputPDFAndActionsToPerformDataModel pdfBatch, InputPDFsDataModel inputPdf, File inputPDFFile)
			throws IOException {

		LOGGER.info(() -> "Handle input PDF file:" + inputPDFFile.getAbsolutePath());

		if (MULTITHREAD_STRATEGY == MultiThreadStrategy.ONE_THREAD_PER_INPUT_PDF) {
			return CollectionUtils.asList(new PDFProcessorThread(pdfAllowedUsers, pdfBatch, inputPdf, inputPDFFile));
		}

		SecondRegardHelpers.generateUnprotectedPdfForNoUserIfNeeded(inputPdf, inputPDFFile, pdfBatch);
		SecondRegardHelpers.processPdf(pdfAllowedUsers, pdfBatch, inputPdf, inputPDFFile);

		return new ArrayList<>();
	}

}