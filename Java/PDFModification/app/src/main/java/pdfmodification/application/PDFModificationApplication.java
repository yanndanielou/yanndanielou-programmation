package pdfmodification.application;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.multipdf.Overlay;
import org.apache.pdfbox.multipdf.Overlay.Position;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageContentStream.AppendMode;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.apache.pdfbox.pdmodel.encryption.StandardProtectionPolicy;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.apache.pdfbox.util.Matrix;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import common.directories.DirectoryHelper;
import pdfmodification.data.PDFAllowedUser;
import pdfmodification.data.PDFAllowedUsersFromCsvLoader;
import pdfmodification.helpers.PDFModificationHelpers;

/**
 * https://github.com/topobyte/pdfbox-tools/blob/master/src/main/java/org/apache/pdfbox/tools/OverlayPDF.java
 * 
 */

public class PDFModificationApplication {
	protected static final Logger LOGGER = LogManager.getLogger(PDFModificationApplication.class);

	public static void main(String[] args) throws Exception {

		LOGGER.info(() -> "Application started");

		// List<PDFAllowedUser> pdfAllowedUsers = PDFAllowedUsersFromCsvLoader
		// .getPDFAllowedUsersFromCsvFile("Input/Documentation Produit -
		// Passwords.csv");
		List<PDFAllowedUser> pdfAllowedUsers = PDFAllowedUsersFromCsvLoader
				.getPDFAllowedUsersFromCsvFile("Input/test_passwords.csv");

		LOGGER.info(() -> "Number of pdfAllowedUsers:" + pdfAllowedUsers.size());

		for (PDFAllowedUser pdfAllowedUser : pdfAllowedUsers) {
			addWatermarkAndEncryptButWatermarkIsJustTextAdded(pdfAllowedUser);
		}
//		allStepsInOneMethodWithIntermediateFiles();

		LOGGER.info(() -> "Application end");

	}

	public static void allStepsInOneMethodWithIntermediateFiles() throws IOException {

		PDDocument newWatermarkOnlyDocument = new PDDocument();

		PDRectangle rectangle = PDRectangle.A4;
		PDPage watermarkOnlyPage = new PDPage(rectangle);

		newWatermarkOnlyDocument.addPage(watermarkOnlyPage);

		PDPageContentStream watermarkOnlyDocumentPageContentStream = new PDPageContentStream(newWatermarkOnlyDocument,
				watermarkOnlyPage, AppendMode.APPEND, true);

		watermarkOnlyDocumentPageContentStream.beginText();
		watermarkOnlyDocumentPageContentStream.newLineAtOffset(rectangle.getWidth() / 3, rectangle.getHeight() / 2);

		watermarkOnlyDocumentPageContentStream.setStrokingColor(Color.blue);
		watermarkOnlyDocumentPageContentStream.setNonStrokingColor(Color.gray);
		watermarkOnlyDocumentPageContentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 20);
		watermarkOnlyDocumentPageContentStream.showText("My watermark");
		watermarkOnlyDocumentPageContentStream.setTextMatrix(new Matrix(1, 0, 0, 1.5f, 7, 30));
		watermarkOnlyDocumentPageContentStream.showText("Stretched text (size 12, factor 1.5)");
		watermarkOnlyDocumentPageContentStream.setTextMatrix(new Matrix(1, 0, 0, 2f, 7, 5));
		watermarkOnlyDocumentPageContentStream.showText("Stretched text (size 12, factor 2)");
		watermarkOnlyDocumentPageContentStream.endText();

		watermarkOnlyDocumentPageContentStream.close();

		File myObj = new File(PDFModificationHelpers.watermarkOnlyPDFFileName);
		if (myObj.delete()) {
			System.out.println("Delete previous version of file file: " + myObj.getName());
		} else {
			System.out.println("Failed to delete the file.");
		}

		LOGGER.info(() -> "Save output pdf");
		newWatermarkOnlyDocument.save(PDFModificationHelpers.watermarkOnlyPDFFileName);

		// OverlayPDF
		UUID randomUUID = java.util.UUID.randomUUID();
		String fileName = PDFModificationHelpers.outputDirectoryName + randomUUID.toString() + ".pdf";
		FileUtils.copyFile(new File(PDFModificationHelpers.originalPDFDocumentBeforeAnyModificationFullPath),
				new File(fileName));

		File file = new File(fileName);
		PDDocument originalDoc = Loader.loadPDF(file);

		Overlay overlayer = new Overlay();
		overlayer.setInputPDF(originalDoc);
		overlayer.setAllPagesOverlayFile(PDFModificationHelpers.watermarkOnlyPDFFileName);
		overlayer.setOverlayPosition(Position.BACKGROUND);

		overlayer.close();

		PDDocument watermarkDocument = new PDDocument();
		overlayer.setAllPagesOverlayPDF(watermarkDocument);

		try (PDDocument result = overlayer.overlay(new HashMap<>())) {
			result.save(PDFModificationHelpers.documentWithWatermark);
		}
		// watermarkDocument.save(documentWithWatermark);

	}

	/**
	 * https://stackoverflow.com/questions/32844926/using-overlay-in-pdfbox-2-0
	 * 
	 * @param pdfAllowedUser
	 * 
	 * @throws IOException
	 */
	private static void addWatermarkAndEncryptButWatermarkIsJustTextAdded(PDFAllowedUser pdfAllowedUser)
			throws IOException {

		PDRectangle rectangle = PDRectangle.A4;

		String watermarkText = "Confidentiel. Copie réservée à " + pdfAllowedUser.getPrenom() + " "
				+ pdfAllowedUser.getNom();

		File file = new File(PDFModificationHelpers.originalPDFDocumentBeforeAnyModificationFullPath);
		PDDocument originalDoc = Loader.loadPDF(file);
		for (PDPage originalDocPage : originalDoc.getPages()) {

			PDPageContentStream pageOfOriginalDocumentWithMatermarkAsContentStream = new PDPageContentStream(
					originalDoc, originalDocPage, AppendMode.APPEND, true);

			// PDColor nonStrokingColor = PDColor.

			pageOfOriginalDocumentWithMatermarkAsContentStream.setNonStrokingColor(Color.gray);
			pageOfOriginalDocumentWithMatermarkAsContentStream.beginText();
			pageOfOriginalDocumentWithMatermarkAsContentStream
					.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 99);
			pageOfOriginalDocumentWithMatermarkAsContentStream.newLineAtOffset(rectangle.getWidth() / 3,
					rectangle.getHeight() / 2);
			pageOfOriginalDocumentWithMatermarkAsContentStream.showText(watermarkText);
			pageOfOriginalDocumentWithMatermarkAsContentStream.endText();
			pageOfOriginalDocumentWithMatermarkAsContentStream.close();
		}

		DirectoryHelper.createFolderIfNotExists(PDFModificationHelpers.outputDirectoryName);

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
		originalDoc.protect(spp);

		String generatedPersonnalizedProtectedPDFFileNameWithExtension = PDFModificationHelpers.originalPDFDocumentBeforeAnyModificationFileNameWithoutExtension
				+ " " + pdfAllowedUser.getPrenom() + " " + pdfAllowedUser.getNom() + PDFModificationHelpers.PDF_EXTENSION_WITH_POINT;
		String generatedPersonnalizedProtectedPDFFullPath = PDFModificationHelpers.outputDirectoryName + "/"
				+ generatedPersonnalizedProtectedPDFFileNameWithExtension;

		LOGGER.info(() -> "Save generated protected PDF:" + generatedPersonnalizedProtectedPDFFullPath + " for "
				+ pdfAllowedUser);
		originalDoc.save(generatedPersonnalizedProtectedPDFFullPath);

		originalDoc.close();
	}

	/**
	 * https://stackoverflow.com/questions/32844926/using-overlay-in-pdfbox-2-0
	 */
	/*
	 * public void method2() { PDDocument overlayDoc = new PDDocument(); PDPage page
	 * = new PDPage(); overlayDoc.addPage(page); Overlay overlayObj = new Overlay();
	 * PDType1Font font = Standard14Fonts.FontName.COURIER_OBLIQUE;
	 * 
	 * PDPageContentStream contentStream = new PDPageContentStream(overlayDoc,
	 * page); contentStream.setFont(font, 50); contentStream.setNonStrokingColor(0);
	 * contentStream.beginText(); contentStream.moveTextPositionByAmount(200, 200);
	 * contentStream.drawString("deprecated"); // deprecated. Use showText(String
	 * text) contentStream.endText(); contentStream.close();
	 * 
	 * PDDocument originalDoc = PDDocument.load(new File("...inputfile.pdf"));
	 * overlayObj.setOverlayPosition(Overlay.Position.FOREGROUND);
	 * overlayObj.setInputPDF(originalDoc);
	 * overlayObj.setAllPagesOverlayPDF(overlayDoc); Map<Integer, String> ovmap =
	 * new HashMap<Integer, String>(); // empty map is a dummy
	 * overlayObj.setOutputFile("... result-with-overlay.pdf");
	 * overlayObj.overlay(ovmap); overlayDoc.close(); originalDoc.close(); }
	 */
}