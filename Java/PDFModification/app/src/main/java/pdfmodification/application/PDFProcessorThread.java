package pdfmodification.application;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;

import common.compress.ZipFileManager;
import common.filesanddirectories.DirectoryHelper;
import pdfmodification.data.inputpdfdocument.builders.InputPDFAndActionsToPerformDataModel;
import pdfmodification.data.inputpdfdocument.builders.InputPDFsDataModel;
import pdfmodification.data.users.PDFAllowedUser;
import pdfmodification.helpers.PDFModificationConstants;
import pdfmodification.helpers.PDFModificationHelpers;

public class PDFProcessorThread extends PDFProcessorGenericThread {

	List<PDFAllowedUser> pdfAllowedUsers;
	InputPDFAndActionsToPerformDataModel pdfBatch;
	InputPDFsDataModel inputPdf;
	File inputPDFFile;

	protected static final Logger LOGGER = LogManager.getLogger(PDFProcessorThread.class);

	public PDFProcessorThread(List<PDFAllowedUser> pdfAllowedUsers, InputPDFAndActionsToPerformDataModel pdfBatch,
			InputPDFsDataModel inputPdf, File inputPDFFile) {

		this.pdfAllowedUsers = pdfAllowedUsers;
		this.pdfBatch = pdfBatch;
		this.inputPdf = inputPdf;
		this.inputPDFFile = inputPDFFile;

	}

	@Override
	public void run() {
		LOGGER.info(() -> "Handle input PDF file:" + inputPDFFile.getAbsolutePath());

		try {
			handlePdf();
			LOGGER.info(() -> "Thread has handled all PDFs");
		} catch (IOException e) {
			e.printStackTrace();
			LOGGER.fatal(() -> "Could not treat PDF: " + this);
		}
	}

	private void handlePdf() throws IOException {
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
			PDFModificationHelpers.saveOutputPDF(inputPdf, inputPDFFile, originalDoc, null);

			originalDoc.close();
		}

		List<String> outputPdfFilesFullPaths = new ArrayList<>();

		for (PDFAllowedUser pdfAllowedUser : pdfAllowedUsers) {
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

			LOGGER.info(() -> "Save output PDF");
			PDFModificationHelpers.saveOutputPDF(inputPdf, inputPDFFile, originalDoc, pdfAllowedUser);

			String outputPDFFileFullPath = getOutputPDFFileNameWithFullPath(inputPdf, inputPDFFile, pdfAllowedUser);
			outputPdfFilesFullPaths.add(outputPDFFileFullPath);

			originalDoc.close();
		}

		ZipFileManager zipFileManager = new ZipFileManager();
		String zipFileName = PDFModificationConstants.OUTPUT_DIRECTORY_NAME + "/"
				+ PDFModificationHelpers.getOutputPDFFileName(inputPdf, inputPDFFile, null) + ".zip";
		zipFileManager.createZipFileWithFilesFullPaths(zipFileName, outputPdfFilesFullPaths);
	}
}
