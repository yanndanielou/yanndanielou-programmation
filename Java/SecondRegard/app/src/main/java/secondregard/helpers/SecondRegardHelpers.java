package secondregard.helpers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections4.ListUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageContentStream.AppendMode;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts.FontName;

import com.google.common.base.Strings;

import common.builders.ColorDataModel;
import common.builders.PointDataModel;
import common.filesanddirectories.FileNameExtensionAndPathHelper;
import pdfmodification.helpers.PDFModificationHelpers;
import secondregard.data.inputpdfdocument.builders.FilesToZipDataModel;
import secondregard.data.inputpdfdocument.builders.InputPDFAndActionsToPerformDataModel;
import secondregard.data.inputpdfdocument.builders.InputPDFsDataModel;
import secondregard.data.inputpdfdocument.builders.PDFFontDataModel;
import secondregard.data.inputpdfdocument.builders.TextLineToDisplayDataModel;
import secondregard.data.users.SecondRegardAllowedUser;
import zip4j.Zip4JZipManager;

public class SecondRegardHelpers {

	protected static final Logger LOGGER = LogManager.getLogger(SecondRegardHelpers.class);

	public static void addWatermarkOnEachPage(PDDocument originalDoc,
			InputPDFAndActionsToPerformDataModel inputPDFAndActionsToPerformDataModel,
			SecondRegardAllowedUser pdfAllowedUser) throws IOException {

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

	public static void saveOutputPDF(InputPDFsDataModel inputPdf, File inputPDFFile, PDDocument originalDoc,
			SecondRegardAllowedUser pdfAllowedUser) throws IOException {

		String generatedPersonnalizedProtectedPDFFullPath = getOutputPDFFileNameWithFullPath(inputPdf, inputPDFFile,
				originalDoc, pdfAllowedUser);
		LOGGER.info(() -> "Save generated protected PDF:" + generatedPersonnalizedProtectedPDFFullPath + " for "
				+ pdfAllowedUser);
		PDFModificationHelpers.saveOutputPDF(inputPDFFile, originalDoc, generatedPersonnalizedProtectedPDFFullPath);
	}

	public static String getOutputPDFFileName(InputPDFsDataModel inputPdf, File inputPDFFile,
			SecondRegardAllowedUser pdfAllowedUser) {
		String fileNameWithoutExtension = inputPdf.getGenericOutputFileName();
		if (fileNameWithoutExtension == null) {
			fileNameWithoutExtension = Strings.nullToEmpty(inputPdf.getOutputFilePrefixToAdd())
					+ FileNameExtensionAndPathHelper.getFileNameWithoutExtension(inputPDFFile.getName());
		}

		String generatedPersonnalizedProtectedPDFFileNameWithExtension = fileNameWithoutExtension;

		if (pdfAllowedUser != null) {
			generatedPersonnalizedProtectedPDFFileNameWithExtension += " " + pdfAllowedUser.getPrenom() + " "
					+ pdfAllowedUser.getNom();
		}

		generatedPersonnalizedProtectedPDFFileNameWithExtension += SecondRegardConstants.PDF_EXTENSION_WITH_POINT;

		return generatedPersonnalizedProtectedPDFFileNameWithExtension;

	}

	public static String getOutputPDFFileNameWithFullPath(InputPDFsDataModel inputPdf, File inputPDFFile,
			PDDocument originalDoc, SecondRegardAllowedUser pdfAllowedUser) {

		String generatedPersonnalizedProtectedPDFFullPath = SecondRegardConstants.OUTPUT_DIRECTORY_NAME + "/"
				+ getOutputPDFFileName(inputPdf, inputPDFFile, pdfAllowedUser);

		return generatedPersonnalizedProtectedPDFFullPath;

	}

	public static void protectPDF(PDDocument documentToProtect, SecondRegardAllowedUser pdfAllowedUser)
			throws IOException {

		String ownerPasswordToPrintPDF = pdfAllowedUser.getMotDePasseImpression();
		String userPasswordToOpenPDF = pdfAllowedUser.getMotDePasseOuverture();
		PDFModificationHelpers.protectPDF(documentToProtect, ownerPasswordToPrintPDF, userPasswordToOpenPDF);
	}

	public static void processZipsToCreate(List<SecondRegardAllowedUser> secondRegardAllowedUsers,
			List<FilesToZipDataModel> zipBatchs) {
		LOGGER.info("Process Zips to create");

		for (FilesToZipDataModel filesToZipDataModel : zipBatchs) {

			for (SecondRegardAllowedUser secondRegardAllowedUser : secondRegardAllowedUsers) {

				String outputZipFileName = filesToZipDataModel.getZipToCreatePrefix() + " "
						+ secondRegardAllowedUser.getPrenom() + " " + secondRegardAllowedUser.getNom()
						+ SecondRegardConstants.ZIP_EXTENSION_WITH_POINT;

				String outputZipFullPath = SecondRegardConstants.OUTPUT_DIRECTORY_NAME + "/" + outputZipFileName;

				List<File> foldersToAdd = ListUtils.emptyIfNull(filesToZipDataModel.getFoldersToAddFullPath()).stream()
						.map(e -> {
							return new File(SecondRegardConstants.INPUT_DIRECTORY_NAME + "\\" + e);
						}).toList();

				List<File> filesToAdd = ListUtils.emptyIfNull(filesToZipDataModel.getFilesToAddFullPath()).stream()
						.map(e -> {
							return new File(SecondRegardConstants.INPUT_DIRECTORY_NAME + "\\" + e);
						}).toList();

				boolean zipCreationSuccess = Zip4JZipManager.createEncryptedZipFileWithFilesFolders(outputZipFullPath,
						secondRegardAllowedUser.getMotDePasseOuverture(), foldersToAdd, filesToAdd);

				LOGGER.info(outputZipFileName + " processed. Result:" + zipCreationSuccess);

			}
		}
		LOGGER.info("Zips processed");

	}

}
