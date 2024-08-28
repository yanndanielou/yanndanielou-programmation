package secondregard.application;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;

import common.filesanddirectories.DirectoryHelper;
import compressedarchivefiles.standardjavalibrary.StandardJavaLibraryZipFileManager;
import pdfmodification.helpers.PDFModificationHelpers;
import secondregard.data.inputpdfdocument.builders.InputPDFAndActionsToPerformDataModel;
import secondregard.data.inputpdfdocument.builders.InputPDFsDataModel;
import secondregard.data.users.SecondRegardAllowedUser;
import secondregard.helpers.SecondRegardHelpers;
import secondregard.helpers.SecondRegardConstants;
import secondregard.helpers.SecondRegardParams;
import zip4j.Zip4JZipManager;

public class PDFProcessorThread extends PDFProcessorGenericThread {

	List<SecondRegardAllowedUser> pdfAllowedUsers;
	InputPDFAndActionsToPerformDataModel pdfBatch;
	InputPDFsDataModel inputPdf;
	File inputPDFFile;

	protected static final Logger LOGGER = LogManager.getLogger(PDFProcessorThread.class);

	public PDFProcessorThread(List<SecondRegardAllowedUser> pdfAllowedUsers, InputPDFAndActionsToPerformDataModel pdfBatch,
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
		if (SecondRegardParams.GENERATE_ALSO_UNPROTECTED_PDF_FOR_NO_USER) {
			LOGGER.info(() -> "Load PDF");
			PDDocument originalDoc = Loader.loadPDF(inputPDFFile);

			List<Integer> allPageNumberToDelete = inputPdf.getAllPageNumberToDelete();
			LOGGER.info(() -> "Delete " + allPageNumberToDelete.size() + " pages");
			PDFModificationHelpers.deletePages(originalDoc, allPageNumberToDelete);

			LOGGER.info(() -> "Add watermark on each page");
			SecondRegardHelpers.addWatermarkOnEachPage(originalDoc, pdfBatch, new SecondRegardAllowedUser());

			DirectoryHelper.createFolderIfNotExists(SecondRegardConstants.OUTPUT_DIRECTORY_NAME);

			LOGGER.info(() -> "Save output PDF");
			SecondRegardHelpers.saveOutputPDF(inputPdf, inputPDFFile, originalDoc, null);

			originalDoc.close();
		}

		List<String> outputPdfFilesFullPaths = new ArrayList<>();

		for (SecondRegardAllowedUser pdfAllowedUser : pdfAllowedUsers) {
			LOGGER.info(() -> "Handle pdf user:" + pdfAllowedUser.getPrenom() + " " + pdfAllowedUser.getNom());

			LOGGER.info(() -> "Load PDF");
			PDDocument originalDoc = Loader.loadPDF(inputPDFFile);

			List<Integer> allPageNumberToDelete = inputPdf.getAllPageNumberToDelete();
			LOGGER.info(() -> "Delete " + allPageNumberToDelete.size() + " pages");
			PDFModificationHelpers.deletePages(originalDoc, allPageNumberToDelete);

			LOGGER.info(() -> "Add watermark on each page");
			SecondRegardHelpers.addWatermarkOnEachPage(originalDoc, pdfBatch, pdfAllowedUser);

			DirectoryHelper.createFolderIfNotExists(SecondRegardConstants.OUTPUT_DIRECTORY_NAME);

			LOGGER.info(() -> "Protect PDF");
			SecondRegardHelpers.protectPDF(originalDoc, pdfAllowedUser);

			LOGGER.info(() -> "Save output PDF");
			SecondRegardHelpers.saveOutputPDF(inputPdf, inputPDFFile, originalDoc, pdfAllowedUser);

			String outputPDFFileFullPath = getOutputPDFFileNameWithFullPath(inputPdf, inputPDFFile, pdfAllowedUser);
			outputPdfFilesFullPaths.add(outputPDFFileFullPath);

			originalDoc.close();
		}

		if (SecondRegardParams.GENERATE_ALSO_ZIP_FILES) {
			// StandardJavaLibraryZipFileManager zipFileManager = new
			// StandardJavaLibraryZipFileManager();
			// zipFileManager.createZipFileWithFilesFullPaths(zipFileName,
			// outputPdfFilesFullPaths);
			String zipFileName = SecondRegardConstants.OUTPUT_DIRECTORY_NAME + "/"
					+ SecondRegardHelpers.getOutputPDFFileName(inputPdf, inputPDFFile, null) + ".zip";
			boolean zipSuccess = Zip4JZipManager.createZipFileWithFilesFullPaths(zipFileName, outputPdfFilesFullPaths);
			LOGGER.info(() -> "Zip creation result:" + zipSuccess);
		}
	}
}
