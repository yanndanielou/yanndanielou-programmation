package pdfmodification.helpers;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
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

import common.filesanddirectories.DirectoryHelper;

/**
 * https://github.com/topobyte/pdfbox-tools/blob/master/src/main/java/org/apache/pdfbox/tools/OverlayPDF.java
 * 
 */

public class PDFModificationHelpers {
	protected static final Logger LOGGER = LogManager.getLogger(PDFModificationHelpers.class);

	public static final String outputDirectoryName = "output";
	public static final String inputDirectoryName = "input";
	public static final String PDF_EXTENSION_WITH_POINT = ".pdf";
	public static final String originalPDFDocumentBeforeAnyModificationFileNameWithoutExtension = "SSC3_2_AdminTools_GUMPS_SyReqSpec 02 00";
	public static final String originalPDFDocumentBeforeAnyModificationFileNameWithExtension = originalPDFDocumentBeforeAnyModificationFileNameWithoutExtension + PDF_EXTENSION_WITH_POINT;
	public static final String originalPDFDocumentBeforeAnyModificationFullPath = inputDirectoryName + "\\"
			+ originalPDFDocumentBeforeAnyModificationFileNameWithExtension;
	public static final String watermarkOnlyPDFFileName = outputDirectoryName + "\\Watermark xx.pdf";
	public static final String documentWithWatermark = outputDirectoryName + "\\documentWithWatermark.pdf";

	public static PDDocument createWatermarkOnlyDocument() throws IOException {

		PDDocument watermarkOnlyDocument = new PDDocument();

		PDRectangle rectangle = PDRectangle.A4;
		PDPage watermarkPage = new PDPage(rectangle);

		watermarkOnlyDocument.addPage(watermarkPage);

		PDPageContentStream watermarkPageContentStream = new PDPageContentStream(watermarkOnlyDocument, watermarkPage,
				AppendMode.APPEND, true);

		watermarkPageContentStream.beginText();
		watermarkPageContentStream.newLineAtOffset(rectangle.getWidth() / 3, rectangle.getHeight() / 2);

		watermarkPageContentStream.setStrokingColor(Color.blue);
		watermarkPageContentStream.setNonStrokingColor(Color.gray);
		watermarkPageContentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 20);
		watermarkPageContentStream.showText("My watermark");
		watermarkPageContentStream.setTextMatrix(new Matrix(1, 0, 0, 1.5f, 7, 30));
		watermarkPageContentStream.showText("Stretched text (size 12, factor 1.5)");
		watermarkPageContentStream.setTextMatrix(new Matrix(1, 0, 0, 2f, 7, 5));
		watermarkPageContentStream.showText("Stretched text (size 12, factor 2)");
		watermarkPageContentStream.endText();

		watermarkPageContentStream.close();

		return watermarkOnlyDocument;
	}

	public static void allStepsInOneMethodWithoutIntermediateFile() throws IOException {

		PDDocument watermarkOnlyDocument = createWatermarkOnlyDocument();

		// OverlayPDF
		Overlay overlayer = new Overlay();
		overlayer.setInputFile(originalPDFDocumentBeforeAnyModificationFileNameWithExtension);
		overlayer.setAllPagesOverlayFile(watermarkOnlyPDFFileName);
		overlayer.setOverlayPosition(Position.BACKGROUND);

		overlayer.close();

		DirectoryHelper.createFolderIfNotExists(outputDirectoryName);
		try (PDDocument result = overlayer.overlay(new HashMap<>())) {
			result.save(outputDirectoryName + "/" + documentWithWatermark);
		}
	}

	/**
	 * https://stackoverflow.com/questions/32844926/using-overlay-in-pdfbox-2-0
	 * 
	 * @throws IOException
	 */
	public static void method3() throws IOException {
		UUID randomUUID = java.util.UUID.randomUUID();
		String fileName = randomUUID.toString() + ".pdf";
		FileUtils.copyFile(new File(originalPDFDocumentBeforeAnyModificationFileNameWithExtension), new File(fileName));

		PDRectangle rectangle = PDRectangle.A4;

		File file = new File(fileName);
		PDDocument originalDoc = Loader.loadPDF(file);
		for (PDPage page1 : originalDoc.getPages()) {

			PDPageContentStream contentStream = new PDPageContentStream(originalDoc, page1, AppendMode.APPEND, true);

			// PDColor nonStrokingColor = PDColor.

			contentStream.setNonStrokingColor(Color.gray);
			contentStream.beginText();
			contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 99);
			contentStream.newLineAtOffset(rectangle.getWidth() / 3, rectangle.getHeight() / 2);
			contentStream.showText("My watermark");
			contentStream.endText();
			contentStream.close();
		}

		DirectoryHelper.createFolderIfNotExists(outputDirectoryName);

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
		String ownerPasswordToPrintPDF = "12345";
		String userPasswordToOpenPDF = "abdde";
		StandardProtectionPolicy spp = new StandardProtectionPolicy(ownerPasswordToPrintPDF, userPasswordToOpenPDF, ap);
		spp.setEncryptionKeyLength(keyLength);

		// Apply protection
		originalDoc.protect(spp);

		/*
		 * PDEncryption pdEncryption = new PDEncryption();
		 * pdEncryption.setOwnerEncryptionKey("OK".getBytes());
		 * originalDoc.setEncryptionDictionary(pdEncryption);
		 */
		originalDoc.save(outputDirectoryName + "/" + "result " + fileName);
		// Files.createDirectories(Paths.get(outputDirectoryName));
		// Files.createDirectory(Paths.get(outputDirectoryName), null)

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