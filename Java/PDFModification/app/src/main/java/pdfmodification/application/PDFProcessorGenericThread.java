package pdfmodification.application;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageContentStream.AppendMode;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.apache.pdfbox.pdmodel.encryption.StandardProtectionPolicy;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts.FontName;

import com.google.common.base.Strings;

import common.builders.ColorDataModel;
import common.builders.PointDataModel;
import common.duration.CodeDurationCounter;
import common.duration.FormatterUtils;
import common.filesanddirectories.FileHelper;
import common.filesanddirectories.FileNameExtensionAndPathHelper;
import pdfmodification.data.inputpdfdocument.builders.InputPDFAndActionsToPerformDataModel;
import pdfmodification.data.inputpdfdocument.builders.InputPDFsDataModel;
import pdfmodification.data.inputpdfdocument.builders.PDFFontDataModel;
import pdfmodification.data.inputpdfdocument.builders.TextLineToDisplayDataModel;
import pdfmodification.data.users.PDFAllowedUser;
import pdfmodification.helpers.PDFModificationHelpers;

public class PDFProcessorGenericThread extends Thread {

	protected static final Logger LOGGER = LogManager.getLogger(PDFProcessorGenericThread.class);

	public PDFProcessorGenericThread() {

	}

	protected static void deletePages(PDDocument originalDoc, List<Integer> allPageNumberToDelete) {
		int numberOfPagesAlreadyDeleted = 0;
		int offset = 1;
		for (Integer pageNumberToDelete : allPageNumberToDelete) {
			originalDoc.removePage(pageNumberToDelete - offset - numberOfPagesAlreadyDeleted);
			numberOfPagesAlreadyDeleted++;
		}
	}

	protected static void addWatermarkOnEachPage(PDDocument originalDoc,
			InputPDFAndActionsToPerformDataModel inputPDFAndActionsToPerformDataModel, PDFAllowedUser pdfAllowedUser)
			throws IOException {

		for (PDPage originalDocPage : originalDoc.getPages()) {

			PDPageContentStream pageOfOriginalDocumentWithMatermarkAsContentStream = new PDPageContentStream(
					originalDoc, originalDocPage, AppendMode.APPEND, true);

			pageOfOriginalDocumentWithMatermarkAsContentStream.beginText();

			for (TextLineToDisplayDataModel textLinesToDisplay : inputPDFAndActionsToPerformDataModel
					.getTextLinesToDisplay()) {

				ColorDataModel nonStrokingColorDataModel = textLinesToDisplay.getNonStrokingColor();
				if (nonStrokingColorDataModel != null) {
					pageOfOriginalDocumentWithMatermarkAsContentStream
							.setNonStrokingColor(nonStrokingColorDataModel.getColorAsAwtColor());
				}

				PDFFontDataModel pdfFont = textLinesToDisplay.getFont();

				if (pdfFont != null) {
					FontName fontName = pdfFont.getFontName();
					int fontSize = pdfFont.getFontSize();
					pageOfOriginalDocumentWithMatermarkAsContentStream.setFont(new PDType1Font(fontName), fontSize);
				}

				PointDataModel newLineAtOffset = textLinesToDisplay.getNewLineAtOffset();
				pageOfOriginalDocumentWithMatermarkAsContentStream.newLineAtOffset(newLineAtOffset.getX(),
						newLineAtOffset.getY());
				String computeText = textLinesToDisplay.computeText(pdfAllowedUser);
				pageOfOriginalDocumentWithMatermarkAsContentStream.showText(computeText);

			}
			pageOfOriginalDocumentWithMatermarkAsContentStream.endText();
			pageOfOriginalDocumentWithMatermarkAsContentStream.close();
		}

	}
	protected static String getOutputPDFFileName(InputPDFsDataModel inputPdf, File inputPDFFile, PDDocument originalDoc) {
		String fileNameWithoutExtension = inputPdf.getGenericOutputFileName();
		if (fileNameWithoutExtension == null) {
			fileNameWithoutExtension = Strings.nullToEmpty(inputPdf.getOutputFilePrefixToAdd())
					+ FileNameExtensionAndPathHelper.getFileNameWithoutExtension(inputPDFFile.getName());
		}

		String generatedPersonnalizedProtectedPDFFileNameWithExtension = fileNameWithoutExtension + " "
				+ PDFModificationHelpers.PDF_EXTENSION_WITH_POINT;

		return generatedPersonnalizedProtectedPDFFileNameWithExtension;

	}

	protected static String getOutputPDFFileName(InputPDFsDataModel inputPdf, File inputPDFFile, PDDocument originalDoc,
			PDFAllowedUser pdfAllowedUser) {
		String fileNameWithoutExtension = inputPdf.getGenericOutputFileName();
		if (fileNameWithoutExtension == null) {
			fileNameWithoutExtension = Strings.nullToEmpty(inputPdf.getOutputFilePrefixToAdd())
					+ FileNameExtensionAndPathHelper.getFileNameWithoutExtension(inputPDFFile.getName());
		}

		String generatedPersonnalizedProtectedPDFFileNameWithExtension = fileNameWithoutExtension + " "
				+ pdfAllowedUser.getPrenom() + " " + pdfAllowedUser.getNom()
				+ PDFModificationHelpers.PDF_EXTENSION_WITH_POINT;

		return generatedPersonnalizedProtectedPDFFileNameWithExtension;

	}
	
	protected static String getOutputPDFFileNameWithFullPath(InputPDFsDataModel inputPdf, File inputPDFFile, PDDocument originalDoc,
			PDFAllowedUser pdfAllowedUser) {

		String generatedPersonnalizedProtectedPDFFullPath = PDFModificationHelpers.outputDirectoryName + "/"
				+ getOutputPDFFileName(inputPdf, inputPDFFile, originalDoc, pdfAllowedUser);


		return generatedPersonnalizedProtectedPDFFullPath;

	}

	protected static void saveOutputPDF(InputPDFsDataModel inputPdf, File inputPDFFile, PDDocument originalDoc,
			PDFAllowedUser pdfAllowedUser) throws IOException {

		String generatedPersonnalizedProtectedPDFFullPath = getOutputPDFFileNameWithFullPath(inputPdf, inputPDFFile, originalDoc, pdfAllowedUser);

		boolean fileRemoved = FileHelper.removeFileIfExists(generatedPersonnalizedProtectedPDFFullPath);

		LOGGER.info(() -> "Save generated protected PDF:" + generatedPersonnalizedProtectedPDFFullPath + " for "
				+ pdfAllowedUser + ". Previous file removed:" + fileRemoved);

		CodeDurationCounter saveTimeDurationCounter = new CodeDurationCounter();
		originalDoc.save(generatedPersonnalizedProtectedPDFFullPath);

		LOGGER.info(() -> "Saved in " + FormatterUtils.GetDurationAsString(saveTimeDurationCounter.getDuration()));
	}

	protected static void protectPDF(PDDocument documentToProtect, PDFAllowedUser pdfAllowedUser) throws IOException {

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
		String ownerPasswordToPrintPDF = pdfAllowedUser.getMotDePasseImpression();
		String userPasswordToOpenPDF = pdfAllowedUser.getMotDePasseOuverture();
		StandardProtectionPolicy spp = new StandardProtectionPolicy(ownerPasswordToPrintPDF, userPasswordToOpenPDF, ap);
		spp.setEncryptionKeyLength(keyLength);

		// Apply protection
		documentToProtect.protect(spp);
	}

}
