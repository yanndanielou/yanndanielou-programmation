package pdfmodification.application;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.pdfbox.Loader;
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
import common.filesanddirectories.DirectoryHelper;
import common.filesanddirectories.FileHelper;
import common.filesanddirectories.FileNameExtensionAndPathHelper;
import common.string.utils.StringUtils;
import pdfmodification.data.inputpdfdocument.builders.InputPDFAndActionsToPerformDataModel;
import pdfmodification.data.inputpdfdocument.builders.InputPDFAndActionsToPerformModelBuilder;
import pdfmodification.data.inputpdfdocument.builders.InputPDFsDataModel;
import pdfmodification.data.inputpdfdocument.builders.PDFFontDataModel;
import pdfmodification.data.inputpdfdocument.builders.TextLineToDisplayDataModel;
import pdfmodification.data.users.PDFAllowedUser;
import pdfmodification.data.users.PDFAllowedUsersFromCsvLoader;
import pdfmodification.helpers.PDFModificationHelpers;

/**
 * https://github.com/topobyte/pdfbox-tools/blob/master/src/main/java/org/apache/pdfbox/tools/OverlayPDF.java
 * 
 */

public class PDFModificationApplication {
	protected static final Logger LOGGER = LogManager.getLogger(PDFModificationApplication.class);

	public static void main(String[] args) throws Exception {

		LOGGER.info(() -> "Application started");

		List<PDFAllowedUser> pdfAllowedUsers = PDFAllowedUsersFromCsvLoader
				.getPDFAllowedUsersFromCsvFile("Input/Password à garder en INTERNE.csv").stream()
				.filter(PDFAllowedUser::isAllowedToAccessPDF).toList();

		LOGGER.info(() -> "Number of pdfAllowedUsers:" + pdfAllowedUsers.size());

		InputPDFAndActionsToPerformModelBuilder inputPDFAndActionsToPerformModelBuilder = new InputPDFAndActionsToPerformModelBuilder(
				"Input/InputPDFAndActionsToPerformDataModel.json");
		InputPDFAndActionsToPerformDataModel inputPDFAndActionsToPerformDataModel = inputPDFAndActionsToPerformModelBuilder
				.getInputPDFAndActionsToPerformDataModel();

		addWatermarkAndEncryptButWatermarkIsJustTextAdded(inputPDFAndActionsToPerformDataModel, pdfAllowedUsers);

		LOGGER.info(() -> "Application end");

	}

	/**
	 * https://stackoverflow.com/questions/32844926/using-overlay-in-pdfbox-2-0
	 * 
	 * @param pdfAllowedUser
	 * 
	 * @throws IOException
	 */
	private static void addWatermarkAndEncryptButWatermarkIsJustTextAdded(
			InputPDFAndActionsToPerformDataModel inputPDFAndActionsToPerformDataModel,
			List<PDFAllowedUser> pdfAllowedUsers) throws IOException {

		for (PDFAllowedUser pdfAllowedUser : pdfAllowedUsers) {
			LOGGER.info(() -> "Handle pdf user:" + pdfAllowedUser.getPrenom() + " " + pdfAllowedUser.getNom());

			for (InputPDFsDataModel inputPdf : inputPDFAndActionsToPerformDataModel.getInputPdfs()) {
				List<File> inputPDFFiles = inputPdf.getInputPDFFiles();

				for (File inputPDFFile : inputPDFFiles) {

					LOGGER.info(() -> "Handle input PDF file:" + inputPDFFile.getAbsolutePath());

					LOGGER.info(() -> "Load PDF");
					PDDocument originalDoc = Loader.loadPDF(inputPDFFile);

					List<Integer> allPageNumberToDelete = inputPdf.getAllPageNumberToDelete();
					LOGGER.info(() -> "Delete " + allPageNumberToDelete.size() + " pages");
					deletePages(originalDoc, allPageNumberToDelete);

					LOGGER.info(() -> "Add watermark on each page");
					addWatermarkOnEachPage(originalDoc, inputPDFAndActionsToPerformDataModel, pdfAllowedUser);

					DirectoryHelper.createFolderIfNotExists(PDFModificationHelpers.outputDirectoryName);

					LOGGER.info(() -> "Protect PDF");
					protectPDF(originalDoc, pdfAllowedUser);

					LOGGER.info(() -> "Save output PDF");
					saveOutputPDF(inputPdf, inputPDFFile, originalDoc, pdfAllowedUser);

					originalDoc.close();
				}
			}
		}
	}

	private static void deletePages(PDDocument originalDoc, List<Integer> allPageNumberToDelete) {
		int numberOfPagesAlreadyDeleted = 0;
		int offset = 1;
		for (Integer pageNumberToDelete : allPageNumberToDelete) {
			originalDoc.removePage(pageNumberToDelete - offset - numberOfPagesAlreadyDeleted);
			numberOfPagesAlreadyDeleted++;
		}
	}

	private static void addWatermarkOnEachPage(PDDocument originalDoc,
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

	private static void saveOutputPDF(InputPDFsDataModel inputPdf, File inputPDFFile, PDDocument originalDoc,
			PDFAllowedUser pdfAllowedUser) throws IOException {

		String fileNameWithoutExtension = FileNameExtensionAndPathHelper
				.getFileNameWithoutExtension(inputPDFFile.getName());
		String generatedPersonnalizedProtectedPDFFileNameWithExtension = fileNameWithoutExtension + " "
				+ pdfAllowedUser.getPrenom() + " " + pdfAllowedUser.getNom()
				+ PDFModificationHelpers.PDF_EXTENSION_WITH_POINT;

		String generatedPersonnalizedProtectedPDFFullPath = PDFModificationHelpers.outputDirectoryName + "/"
				+ Strings.nullToEmpty(inputPdf.getOutputFilePrefixToAdd())
				+ generatedPersonnalizedProtectedPDFFileNameWithExtension;

		FileHelper.removeFileIfExists(generatedPersonnalizedProtectedPDFFullPath);

		LOGGER.info(() -> "Save generated protected PDF:" + generatedPersonnalizedProtectedPDFFullPath + " for "
				+ pdfAllowedUser);

		originalDoc.save(generatedPersonnalizedProtectedPDFFullPath);
	}

	public static void protectPDF(PDDocument documentToProtect, PDFAllowedUser pdfAllowedUser) throws IOException {

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