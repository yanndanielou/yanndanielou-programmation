package pdfmodification.application;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;

import common.filesanddirectories.DirectoryHelper;
import pdfmodification.data.inputpdfdocument.builders.InputPDFAndActionsToPerformDataModel;
import pdfmodification.data.inputpdfdocument.builders.InputPDFsDataModel;
import pdfmodification.data.users.PDFAllowedUser;
import pdfmodification.helpers.PDFModificationHelpers;

public class PDFProcessorForNoUserThread extends PDFProcessorGenericThread {

	InputPDFAndActionsToPerformDataModel pdfBatch;
	InputPDFsDataModel inputPdf;
	File inputPDFFile;

	protected static final Logger LOGGER = LogManager.getLogger(PDFProcessorForNoUserThread.class);

	public PDFProcessorForNoUserThread(InputPDFAndActionsToPerformDataModel pdfBatch, InputPDFsDataModel inputPdf,
			File inputPDFFile) {

		this.pdfBatch = pdfBatch;
		this.inputPdf = inputPdf;
		this.inputPDFFile = inputPDFFile;

	}

	@Override
	public void run() {
		LOGGER.info(() -> "Handle input PDF file:" + inputPDFFile.getAbsolutePath());

		try {

			LOGGER.info(() -> "Load PDF");
			PDDocument originalDoc = Loader.loadPDF(inputPDFFile);

			List<Integer> allPageNumberToDelete = inputPdf.getAllPageNumberToDelete();
			LOGGER.info(() -> "Delete " + allPageNumberToDelete.size() + " pages");
			deletePages(originalDoc, allPageNumberToDelete);

			LOGGER.info(() -> "Add watermark on each page");
			addWatermarkOnEachPage(originalDoc, pdfBatch, new PDFAllowedUser());

			DirectoryHelper.createFolderIfNotExists(PDFModificationHelpers.outputDirectoryName);

			LOGGER.info(() -> "Save output PDF");
			saveOutputPDF(inputPdf, inputPDFFile, originalDoc, new PDFAllowedUser());

			originalDoc.close();

			LOGGER.info(() -> "Thread has handled PDF");
		} catch (IOException e) {
			e.printStackTrace();
			LOGGER.fatal(() -> "Could not treat PDF: " + this);
		}
	}

}
