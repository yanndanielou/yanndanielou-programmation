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
import common.filesanddirectories.FileNameExtensionAndPathHelper;
import pdfmodification.data.inputpdfdocument.builders.InputPDFAndActionsToPerformDataModel;
import pdfmodification.data.inputpdfdocument.builders.InputPDFsDataModel;
import pdfmodification.data.users.PDFAllowedUser;
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
			deletePages(originalDoc, allPageNumberToDelete);

			LOGGER.info(() -> "Add watermark on each page");
			addWatermarkOnEachPage(originalDoc, pdfBatch, new PDFAllowedUser());

			DirectoryHelper.createFolderIfNotExists(PDFModificationHelpers.outputDirectoryName);

			LOGGER.info(() -> "Save output PDF");
			saveOutputPDF(inputPdf, inputPDFFile, originalDoc, new PDFAllowedUser());

			originalDoc.close();
		}

		List<File> outputPdfFiles = new ArrayList<>();

		for (PDFAllowedUser pdfAllowedUser : pdfAllowedUsers) {
			LOGGER.info(() -> "Handle pdf user:" + pdfAllowedUser.getPrenom() + " " + pdfAllowedUser.getNom());

			LOGGER.info(() -> "Load PDF");
			PDDocument originalDoc = Loader.loadPDF(inputPDFFile);

			List<Integer> allPageNumberToDelete = inputPdf.getAllPageNumberToDelete();
			LOGGER.info(() -> "Delete " + allPageNumberToDelete.size() + " pages");
			deletePages(originalDoc, allPageNumberToDelete);

			LOGGER.info(() -> "Add watermark on each page");
			addWatermarkOnEachPage(originalDoc, pdfBatch, pdfAllowedUser);

			DirectoryHelper.createFolderIfNotExists(PDFModificationHelpers.outputDirectoryName);

			LOGGER.info(() -> "Protect PDF");
			protectPDF(originalDoc, pdfAllowedUser);

			LOGGER.info(() -> "Save output PDF");
			saveOutputPDF(inputPdf, inputPDFFile, originalDoc, pdfAllowedUser);

			String outputPDFFileFullPath = getOutputPDFFileNameWithFullPath(inputPdf, inputPDFFile, originalDoc,
					pdfAllowedUser);
			File outputPdfFile = new File(outputPDFFileFullPath);
			outputPdfFiles.add(outputPdfFile);

			originalDoc.close();
		}

		ZipFileManager zipFileManager = new ZipFileManager();
		String zipFileName = PDFModificationHelpers.outputDirectoryName + "/"
				+ FileNameExtensionAndPathHelper.getFileNameWithoutExtension(inputPDFFile) + ".zip";
		zipFileManager.createZipFileWithFiles(zipFileName, outputPdfFiles);
	}
}
