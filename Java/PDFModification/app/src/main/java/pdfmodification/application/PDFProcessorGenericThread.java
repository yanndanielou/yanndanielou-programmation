package pdfmodification.application;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import pdfmodification.data.inputpdfdocument.builders.InputPDFsDataModel;
import pdfmodification.data.users.PDFAllowedUser;
import pdfmodification.helpers.PDFModificationConstants;
import pdfmodification.helpers.PDFModificationHelpers;

public class PDFProcessorGenericThread extends Thread {

	protected static final Logger LOGGER = LogManager.getLogger(PDFProcessorGenericThread.class);

	public PDFProcessorGenericThread() {

	}

	protected static String getOutputPDFFileNameWithFullPath(InputPDFsDataModel inputPdf, File inputPDFFile,
			PDFAllowedUser pdfAllowedUser) {

		String generatedPersonnalizedProtectedPDFFullPath = PDFModificationConstants.OUTPUT_DIRECTORY_NAME + "/"
				+ PDFModificationHelpers.getOutputPDFFileName(inputPdf, inputPDFFile, pdfAllowedUser);

		return generatedPersonnalizedProtectedPDFFullPath;

	}
}
