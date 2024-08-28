package secondregard.application;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import secondregard.data.inputpdfdocument.builders.InputPDFsDataModel;
import secondregard.data.users.SecondRegardAllowedUser;
import secondregard.helpers.SecondRegardHelpers;
import secondregard.helpers.SecondRegardConstants;

public class PDFProcessorGenericThread extends Thread {

	protected static final Logger LOGGER = LogManager.getLogger(PDFProcessorGenericThread.class);

	public PDFProcessorGenericThread() {

	}

	protected static String getOutputPDFFileNameWithFullPath(InputPDFsDataModel inputPdf, File inputPDFFile,
			SecondRegardAllowedUser pdfAllowedUser) {

		String generatedPersonnalizedProtectedPDFFullPath = SecondRegardConstants.OUTPUT_DIRECTORY_NAME + "/"
				+ SecondRegardHelpers.getOutputPDFFileName(inputPdf, inputPDFFile, pdfAllowedUser);

		return generatedPersonnalizedProtectedPDFFullPath;

	}
}
