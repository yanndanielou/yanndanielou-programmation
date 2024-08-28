package secondregard.helpers;

import java.io.File;
import java.io.IOException;

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
import common.duration.CodeDurationCounter;
import common.duration.FormatterUtils;
import common.filesanddirectories.FileHelper;
import common.filesanddirectories.FileNameExtensionAndPathHelper;
import pdfmodification.helpers.PDFModificationHelpers;
import secondregard.data.inputpdfdocument.builders.InputPDFAndActionsToPerformDataModel;
import secondregard.data.inputpdfdocument.builders.InputPDFsDataModel;
import secondregard.data.inputpdfdocument.builders.PDFFontDataModel;
import secondregard.data.inputpdfdocument.builders.TextLineToDisplayDataModel;
import secondregard.data.users.PDFAllowedUser;

public class SecondRegardHelpers {

	protected static final Logger LOGGER = LogManager.getLogger(SecondRegardHelpers.class);

	public static void addWatermarkOnEachPage(PDDocument originalDoc,
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

	public static void saveOutputPDF(InputPDFsDataModel inputPdf, File inputPDFFile, PDDocument originalDoc,
			PDFAllowedUser pdfAllowedUser) throws IOException {

		String generatedPersonnalizedProtectedPDFFullPath = getOutputPDFFileNameWithFullPath(inputPdf, inputPDFFile,
				originalDoc, pdfAllowedUser);

		LOGGER.info(() -> "Save generated protected PDF:" + generatedPersonnalizedProtectedPDFFullPath + " for "
				+ pdfAllowedUser);
		PDFModificationHelpers.saveOutputPDF(inputPDFFile, originalDoc, generatedPersonnalizedProtectedPDFFullPath);
	}

	public static String getOutputPDFFileName(InputPDFsDataModel inputPdf, File inputPDFFile,
			PDFAllowedUser pdfAllowedUser) {
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
			PDDocument originalDoc, PDFAllowedUser pdfAllowedUser) {

		String generatedPersonnalizedProtectedPDFFullPath = SecondRegardConstants.OUTPUT_DIRECTORY_NAME
				+ "/" + getOutputPDFFileName(inputPdf, inputPDFFile, pdfAllowedUser);

		return generatedPersonnalizedProtectedPDFFullPath;

	}

	public static void protectPDF(PDDocument documentToProtect, PDFAllowedUser pdfAllowedUser) throws IOException {

		String ownerPasswordToPrintPDF = pdfAllowedUser.getMotDePasseImpression();
		String userPasswordToOpenPDF = pdfAllowedUser.getMotDePasseOuverture();
		PDFModificationHelpers.protectPDF(documentToProtect, ownerPasswordToPrintPDF, userPasswordToOpenPDF);
	}

}
