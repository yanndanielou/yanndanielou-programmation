package pdfmodification.application;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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

@Deprecated
public class PDFProcessorForOneUserThread extends PDFProcessorGenericThread {

	PDFAllowedUser pdfAllowedUser;
	InputPDFAndActionsToPerformDataModel pdfBatch;
	InputPDFsDataModel inputPdf;
	File inputPDFFile;

	protected static final Logger LOGGER = LogManager.getLogger(PDFProcessorForOneUserThread.class);

	public PDFProcessorForOneUserThread(PDFAllowedUser pdfAllowedUser,
			InputPDFAndActionsToPerformDataModel pdfBatch, InputPDFsDataModel inputPdf, File inputPDFFile) {

		this.pdfAllowedUser = pdfAllowedUser;
		this.pdfBatch = pdfBatch;
		this.inputPdf = inputPdf;
		this.inputPDFFile = inputPDFFile;

	}

	@Override
	public void run() {
		LOGGER.info(() -> "Handle input PDF file:" + inputPDFFile.getAbsolutePath());
		
		List<File> outputPdfFiles = new ArrayList<>();

		try {
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

			String outputPDFFileFullPath = getOutputPDFFileNameWithFullPath(inputPdf, inputPDFFile, originalDoc, pdfAllowedUser);
			File outputPdfFile = new File(outputPDFFileFullPath);
			outputPdfFiles.add(outputPdfFile);
			
			LOGGER.info(() -> "Save output PDF");
			saveOutputPDF(inputPdf, inputPDFFile, originalDoc, pdfAllowedUser);
			

			originalDoc.close();

			LOGGER.info(() -> "Thread has handled all PDFs");
		} catch (IOException e) {
			e.printStackTrace();
			LOGGER.fatal(() -> "Could not treat PDF: " + this);
		}
	}

}
