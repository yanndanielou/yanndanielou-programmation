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
import pdfmodification.helpers.PDFModificationHelpers;
import secondregard.data.inputpdfdocument.builders.InputPDFAndActionsToPerformDataModel;
import secondregard.data.inputpdfdocument.builders.InputPDFsDataModel;
import secondregard.data.users.SecondRegardAllowedUser;
import secondregard.helpers.SecondRegardConstants;
import secondregard.helpers.SecondRegardHelpers;
import secondregard.helpers.SecondRegardParams;
import zip4j.Zip4JZipManager;

public class PDFProcessorThread extends PDFProcessorGenericThread {

	List<SecondRegardAllowedUser> pdfAllowedUsers;
	InputPDFAndActionsToPerformDataModel pdfBatch;
	InputPDFsDataModel inputPdf;
	File inputPDFFile;

	protected static final Logger LOGGER = LogManager.getLogger(PDFProcessorThread.class);

	public PDFProcessorThread(List<SecondRegardAllowedUser> pdfAllowedUsers,
			InputPDFAndActionsToPerformDataModel pdfBatch, InputPDFsDataModel inputPdf, File inputPDFFile) {

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
		SecondRegardHelpers.generateUnprotectedPdfForNoUserIfNeeded(inputPdf, inputPDFFile, pdfBatch);
		SecondRegardHelpers.processPdf(pdfAllowedUsers, pdfBatch, inputPdf, inputPDFFile);
	}
}
