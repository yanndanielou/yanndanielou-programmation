package secondregard.helpers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.ListUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageContentStream.AppendMode;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts.FontName;

import com.google.common.base.Strings;

import common.builders.ColorDataModel;
import common.builders.PointDataModel;
import common.filesanddirectories.DirectoryHelper;
import common.filesanddirectories.FileHelper;
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

				if (textLinesToDisplay.isToValidFor(pdfAllowedUser)) {

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

	public static String getOutputPDFFileNameWithoutExtension(InputPDFsDataModel inputPdf, File inputPDFFile,
			SecondRegardAllowedUser pdfAllowedUser) {

		String fileNameWithoutExtension = inputPdf.getGenericOutputFileName();
		if (fileNameWithoutExtension == null) {
			fileNameWithoutExtension = Strings.nullToEmpty(inputPdf.getOutputFilePrefixToAdd())
					+ FileNameExtensionAndPathHelper.getFileNameWithoutExtension(inputPDFFile.getName());
		}

		String generatedPersonnalizedProtectedPDFFileNameWithoutExtension = fileNameWithoutExtension;

		if (pdfAllowedUser != null) {
			generatedPersonnalizedProtectedPDFFileNameWithoutExtension += " " + pdfAllowedUser.getPrenom() + " "
					+ pdfAllowedUser.getNom();
		}

		return generatedPersonnalizedProtectedPDFFileNameWithoutExtension;

	}

	public static String getOutputPDFFileName(InputPDFsDataModel inputPdf, File inputPDFFile,
			SecondRegardAllowedUser pdfAllowedUser) {

		return getOutputPDFFileNameWithoutExtension(inputPdf, inputPDFFile, pdfAllowedUser)
				+ SecondRegardConstants.PDF_EXTENSION_WITH_POINT;

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

				boolean zipCreationSuccess = new Zip4JZipManager.ZipCreationBuilder(outputZipFullPath)
						.addDirectories(foldersToAdd).addFiles(filesToAdd)
						.encrypt(secondRegardAllowedUser.getMotDePasseOuverture()).build().createZip();

				LOGGER.info(outputZipFileName + " processed. Result:" + zipCreationSuccess);

			}
		}
		LOGGER.info("Zips processed");

	}

	public static void generateUnprotectedPdfForNoUserIfNeeded(InputPDFsDataModel inputPdf, File inputPDFFile,
			InputPDFAndActionsToPerformDataModel pdfBatch) throws IOException {
		if (inputPdf.isEncrypted() && SecondRegardParams.GENERATE_ALSO_UNPROTECTED_PDF_FOR_NO_USER) {
			LOGGER.info(() -> "Load PDF");
			PDDocument originalDoc = Loader.loadPDF(inputPDFFile);

			List<Integer> allPageNumberToDelete = inputPdf.getAllPageNumberToDelete();
			LOGGER.info(() -> "Delete " + allPageNumberToDelete.size() + " pages");
			PDFModificationHelpers.deletePages(originalDoc, allPageNumberToDelete);

			LOGGER.info(() -> "Add watermark on each page");
			SecondRegardHelpers.addWatermarkOnEachPage(originalDoc, pdfBatch, new SecondRegardAllowedUser());

			DirectoryHelper.createFolderIfNotExists(SecondRegardConstants.OUTPUT_DIRECTORY_NAME);

			LOGGER.info(() -> "Save output PDF");
			SecondRegardHelpers.saveOutputPDF(inputPdf, inputPDFFile, originalDoc, new SecondRegardAllowedUser());

			originalDoc.close();
		}
	}

	public static void processPdf(List<SecondRegardAllowedUser> pdfAllowedUsers,
			InputPDFAndActionsToPerformDataModel pdfBatch, InputPDFsDataModel inputPdf, File inputPDFFile)
			throws IOException {
		List<String> outputPdfFilesFullPaths = new ArrayList<>();

		if (inputPdf.isPersonalizeByAllowedUsed()) {

			for (SecondRegardAllowedUser pdfAllowedUser : pdfAllowedUsers) {
				LOGGER.info(() -> "Handle pdf user:" + pdfAllowedUser.getPrenom() + " " + pdfAllowedUser.getNom());

				LOGGER.debug(() -> "Load PDF");
				PDDocument originalDoc = Loader.loadPDF(inputPDFFile);

				List<Integer> allPageNumberToDelete = inputPdf.getAllPageNumberToDelete();
				LOGGER.debug(() -> "Delete " + allPageNumberToDelete.size() + " pages");
				PDFModificationHelpers.deletePages(originalDoc, allPageNumberToDelete);

				LOGGER.debug(() -> "Add watermark on each page");
				SecondRegardHelpers.addWatermarkOnEachPage(originalDoc, pdfBatch, pdfAllowedUser);

				DirectoryHelper.createFolderIfNotExists(SecondRegardConstants.OUTPUT_DIRECTORY_NAME);

				if (inputPdf.isEncrypted()) {

					LOGGER.debug(() -> "Protect PDF");
					SecondRegardHelpers.protectPDF(originalDoc, pdfAllowedUser);
				}

				String outputPDFFileFullPath = SecondRegardHelpers.getOutputPDFFileNameWithFullPath(inputPdf,
						inputPDFFile, originalDoc, pdfAllowedUser);
				outputPdfFilesFullPaths.add(outputPDFFileFullPath);

				LOGGER.info(() -> "Save output PDF");
				SecondRegardHelpers.saveOutputPDF(inputPdf, inputPDFFile, originalDoc, pdfAllowedUser);

				originalDoc.close();

			}

			if (SecondRegardParams.GENERATE_ALSO_ZIP_FILES) {
				String zipFileName = SecondRegardConstants.OUTPUT_DIRECTORY_NAME + "/"
						+ SecondRegardHelpers.getOutputPDFFileNameWithoutExtension(inputPdf, inputPDFFile, null)
						+ ".zip";

				FileHelper.removeFileIfExists(zipFileName);
				boolean zipSuccess = new Zip4JZipManager.ZipCreationBuilder(zipFileName)
						.addFilesByFullPaths(outputPdfFilesFullPaths).build().createZip();

				LOGGER.info(() -> "Zip creation result:" + zipSuccess);
			}
		} else {

			LOGGER.info(() -> "Load PDF");
			PDDocument originalDoc = Loader.loadPDF(inputPDFFile);

			List<Integer> allPageNumberToDelete = inputPdf.getAllPageNumberToDelete();
			LOGGER.info(() -> "Delete " + allPageNumberToDelete.size() + " pages");
			PDFModificationHelpers.deletePages(originalDoc, allPageNumberToDelete);

			LOGGER.info(() -> "Add watermark on each page");
			SecondRegardHelpers.addWatermarkOnEachPage(originalDoc, pdfBatch, null);

			DirectoryHelper.createFolderIfNotExists(SecondRegardConstants.OUTPUT_DIRECTORY_NAME);

			String outputPDFFileFullPath = SecondRegardHelpers.getOutputPDFFileNameWithFullPath(inputPdf, inputPDFFile,
					originalDoc, null);
			outputPdfFilesFullPaths.add(outputPDFFileFullPath);

			LOGGER.info(() -> "Save output PDF");
			SecondRegardHelpers.saveOutputPDF(inputPdf, inputPDFFile, originalDoc, null);

			originalDoc.close();
		}

	}

}
