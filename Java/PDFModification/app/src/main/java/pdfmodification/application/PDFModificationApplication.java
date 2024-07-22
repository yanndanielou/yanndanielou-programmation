package pdfmodification.application;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageContentStream.AppendMode;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.apache.pdfbox.pdmodel.encryption.StandardProtectionPolicy;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts.FontName;

import com.google.common.base.Strings;

import common.builders.ColorDataModel;
import common.builders.PointDataModel;
import common.collection.CollectionUtils;
import common.compress.ZipFileManager;
import common.duration.CodeDurationCounter;
import common.duration.FormatterUtils;
import common.filesanddirectories.DirectoryHelper;
import common.filesanddirectories.FileHelper;
import common.filesanddirectories.FileNameExtensionAndPathHelper;
import pdfmodification.data.inputpdfdocument.builders.InputPDFAndActionsToPerformDataModel;
import pdfmodification.data.inputpdfdocument.builders.InputPDFAndActionsToPerformModelBuilder;
import pdfmodification.data.inputpdfdocument.builders.InputPDFsDataModel;
import pdfmodification.data.inputpdfdocument.builders.ListOfPDFBatchesDataModel;
import pdfmodification.data.inputpdfdocument.builders.PDFFontDataModel;
import pdfmodification.data.inputpdfdocument.builders.TextLineToDisplayDataModel;
import pdfmodification.data.users.PDFAllowedUser;
import pdfmodification.data.users.PDFAllowedUsersFromCsvLoader;
import pdfmodification.helpers.PDFModificationConstants;
import pdfmodification.helpers.PDFModificationHelpers;
import pdfmodification.helpers.PDFModificationParams;

/**
 * https://github.com/topobyte/pdfbox-tools/blob/master/src/main/java/org/apache/pdfbox/tools/OverlayPDF.java
 * 
 */

public class PDFModificationApplication {
	protected static final Logger LOGGER = LogManager.getLogger(PDFModificationApplication.class);

	// public static final MultiThreadStrategy MULTITHREAD_STRATEGY =
	// MultiThreadStrategy.ONE_THREAD_PER_INPUT_PDF;
	public static final MultiThreadStrategy MULTITHREAD_STRATEGY = MultiThreadStrategy.MONOTHREAD;

	public static void main(String[] args) throws Exception {

		CodeDurationCounter applicationDurationCounter = new CodeDurationCounter();

		LOGGER.info(() -> "Application started");

		List<PDFAllowedUser> pdfAllowedUsers = PDFAllowedUsersFromCsvLoader
				.getPDFAllowedUsersFromCsvFile("Input/Password à garder en INTERNE.csv").stream()
				.filter(PDFAllowedUser::isAllowedToAccessPDF)
				.filter(e -> PDFModificationParams.ALLOWED_USER_PRENOMS.isEmpty()
						|| PDFModificationParams.ALLOWED_USER_PRENOMS.contains(e.getPrenom()))
				.toList();
		
		LOGGER.info(() -> "Number of pdfAllowedUsers:" + pdfAllowedUsers.size());

		InputPDFAndActionsToPerformModelBuilder inputPDFAndActionsToPerformModelBuilder = new InputPDFAndActionsToPerformModelBuilder(
				"Input/InputPDFAndActionsToPerformDataModel.json");
		ListOfPDFBatchesDataModel listOfPDFBatchesDataModel = inputPDFAndActionsToPerformModelBuilder
				.getListOfPDFBatchesDataModel();

		List<Thread> pDFProcessorThreads = addWatermarkAndEncryptButWatermarkIsJustTextAdded(listOfPDFBatchesDataModel,
				pdfAllowedUsers);

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
			ListOfPDFBatchesDataModel listOfPDFBatchesDataModel, List<PDFAllowedUser> pdfAllowedUsers)
			throws IOException {

		List<Thread> pdfProcessorThreads = new ArrayList<>();

		for (InputPDFAndActionsToPerformDataModel pdfBatch : listOfPDFBatchesDataModel.getPdfBatchs()) {

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

	private static List<Thread> handlePdf(List<PDFAllowedUser> pdfAllowedUsers,
			InputPDFAndActionsToPerformDataModel pdfBatch, InputPDFsDataModel inputPdf, File inputPDFFile)
			throws IOException {

		LOGGER.info(() -> "Handle input PDF file:" + inputPDFFile.getAbsolutePath());

		List<Thread> threads = new ArrayList<>();

		if (MULTITHREAD_STRATEGY == MultiThreadStrategy.ONE_THREAD_PER_INPUT_PDF) {
			return CollectionUtils.asList(new PDFProcessorThread(pdfAllowedUsers, pdfBatch, inputPdf, inputPDFFile));
		}
		{
			LOGGER.info(() -> "Load PDF");
			PDDocument originalDoc = Loader.loadPDF(inputPDFFile);

			List<Integer> allPageNumberToDelete = inputPdf.getAllPageNumberToDelete();
			LOGGER.info(() -> "Delete " + allPageNumberToDelete.size() + " pages");
			PDFModificationHelpers.deletePages(originalDoc, allPageNumberToDelete);

			LOGGER.info(() -> "Add watermark on each page");
			PDFModificationHelpers.addWatermarkOnEachPage(originalDoc, pdfBatch, new PDFAllowedUser());

			DirectoryHelper.createFolderIfNotExists(PDFModificationConstants.OUTPUT_DIRECTORY_NAME);

			LOGGER.info(() -> "Save output PDF");
			PDFModificationHelpers.saveOutputPDF(inputPdf, inputPDFFile, originalDoc, new PDFAllowedUser());

			originalDoc.close();
		}

		List<String> outputPdfFilesFullPaths = new ArrayList<>();

		for (PDFAllowedUser pdfAllowedUser : pdfAllowedUsers) {
			{
				LOGGER.info(() -> "Handle pdf user:" + pdfAllowedUser.getPrenom() + " " + pdfAllowedUser.getNom());

				LOGGER.info(() -> "Load PDF");
				PDDocument originalDoc = Loader.loadPDF(inputPDFFile);

				List<Integer> allPageNumberToDelete = inputPdf.getAllPageNumberToDelete();
				LOGGER.info(() -> "Delete " + allPageNumberToDelete.size() + " pages");
				PDFModificationHelpers.deletePages(originalDoc, allPageNumberToDelete);

				LOGGER.info(() -> "Add watermark on each page");
				PDFModificationHelpers.addWatermarkOnEachPage(originalDoc, pdfBatch, pdfAllowedUser);

				DirectoryHelper.createFolderIfNotExists(PDFModificationConstants.OUTPUT_DIRECTORY_NAME);

				LOGGER.info(() -> "Protect PDF");
				PDFModificationHelpers.protectPDF(originalDoc, pdfAllowedUser);

				String outputPDFFileFullPath = PDFModificationHelpers.getOutputPDFFileNameWithFullPath(inputPdf,
						inputPDFFile, originalDoc, pdfAllowedUser);
				outputPdfFilesFullPaths.add(outputPDFFileFullPath);

				LOGGER.info(() -> "Save output PDF");
				PDFModificationHelpers.saveOutputPDF(inputPdf, inputPDFFile, originalDoc, pdfAllowedUser);

				originalDoc.close();
			}

		}
		ZipFileManager zipFileManager = new ZipFileManager();
		String zipFileName = PDFModificationConstants.OUTPUT_DIRECTORY_NAME + "/"
				+ PDFModificationHelpers.getOutputPDFFileName(inputPdf, inputPDFFile, null) + ".zip";
		zipFileManager.createZipFileWithFilesFullPaths(zipFileName, outputPdfFilesFullPaths);

		return threads;
	}

}