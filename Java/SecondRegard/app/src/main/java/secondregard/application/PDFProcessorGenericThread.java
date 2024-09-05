package secondregard.application;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import secondregard.data.inputpdfdocument.builders.InputPDFsDataModel;
import secondregard.data.users.SecondRegardAllowedUser;
import secondregard.helpers.SecondRegardConstants;
import secondregard.helpers.SecondRegardHelpers;

public class PDFProcessorGenericThread extends Thread {

	protected static final Logger LOGGER = LogManager.getLogger(PDFProcessorGenericThread.class);

	public PDFProcessorGenericThread() {

	}
}
