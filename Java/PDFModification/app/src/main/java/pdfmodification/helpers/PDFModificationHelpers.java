package pdfmodification.helpers;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.apache.pdfbox.pdmodel.encryption.StandardProtectionPolicy;

import common.duration.CodeDurationCounter;
import common.duration.FormatterUtils;
import common.filesanddirectories.FileHelper;

public class PDFModificationHelpers {

	protected static final Logger LOGGER = LogManager.getLogger(PDFModificationHelpers.class);

	public static void deletePages(PDDocument originalDoc, List<Integer> allPageNumberToDelete) {
		int numberOfPagesAlreadyDeleted = 0;
		int offset = 1;
		for (Integer pageNumberToDelete : allPageNumberToDelete) {
			originalDoc.removePage(pageNumberToDelete - offset - numberOfPagesAlreadyDeleted);
			numberOfPagesAlreadyDeleted++;
		}
	}

	public static void saveOutputPDF(File inputPDFFile, PDDocument originalDoc, String outputPdfFileNameWithFullPath)
			throws IOException {

		while (FileHelper.removeFileIfExists(outputPdfFileNameWithFullPath)) {

		}
		CodeDurationCounter saveTimeDurationCounter = new CodeDurationCounter();
		originalDoc.save(outputPdfFileNameWithFullPath);

		LOGGER.info(() -> "Saved in " + FormatterUtils.GetDurationAsString(saveTimeDurationCounter.getDuration()));
	}

	public static void protectPDF(PDDocument documentToProtect, String ownerPasswordToPrintPDF,
			String userPasswordToOpenPDF) throws IOException {

		// Define the length of the encryption key.
		// Possible values are 40, 128 or 256.
		int keyLength = 256;

		AccessPermission ap = new AccessPermission();

		// disable printing,
		ap.setCanPrint(false);
		// disable copying
		ap.setCanExtractContent(false);
		// Disable other things if needed...

		// Owner password (to open the file with all permissions) is "12345"
		// User password (to open the file but with restricted permissions, is empty
		// here)
		StandardProtectionPolicy spp = new StandardProtectionPolicy(ownerPasswordToPrintPDF, userPasswordToOpenPDF, ap);
		spp.setEncryptionKeyLength(keyLength);

		// Apply protection
		documentToProtect.protect(spp);
	}

}
