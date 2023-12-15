package pdfmodification.application;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.apache.pdfbox.util.Matrix;

/**
 * https://github.com/topobyte/pdfbox-tools/blob/master/src/main/java/org/apache/pdfbox/tools/OverlayPDF.java
 * 
 */

public class PDFModificationApplication {
	protected static final Logger LOGGER = LogManager.getLogger(PDFModificationApplication.class);

	static final String originalPDFDocumentBeforeAnyModification = "SSC3_2_AdminTools_GUMPS_SyReqSpec 02 00.pdf";
	static final String overlayPDFFileName = "Watermark xx.pdf";
	static final String documentWithWatermark = "documentWithWatermark.pdf";
	static final String outputDirectoryName = "output";

	public static void main(String[] args) throws Exception {

		LOGGER.info(() -> "Application started");
		method3();

		LOGGER.info(() -> "Application end");

	}

	public static void method1YDA() throws IOException {

		PDDocument document = new PDDocument();

		PDRectangle rectangle = PDRectangle.A4;
		PDPage page = new PDPage(rectangle);

		document.addPage(page);

		PDPageContentStream contentStream = new PDPageContentStream(document, page, AppendMode.APPEND, true);

		contentStream.beginText();
		contentStream.newLineAtOffset(rectangle.getWidth() / 3, rectangle.getHeight() / 2);

		contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 20);
		contentStream.showText("My watermark");
		contentStream.setTextMatrix(new Matrix(1, 0, 0, 1.5f, 7, 30));
		contentStream.showText("Stretched text (size 12, factor 1.5)");
		contentStream.setTextMatrix(new Matrix(1, 0, 0, 2f, 7, 5));
		contentStream.showText("Stretched text (size 12, factor 2)");
		contentStream.endText();

		contentStream.close();

		File myObj = new File(overlayPDFFileName);
		if (myObj.delete()) {
			System.out.println("Delete previous version of file file: " + myObj.getName());
		} else {
			System.out.println("Failed to delete the file.");
		}

		LOGGER.info(() -> "Save output pdf");
		document.save(overlayPDFFileName);

		// OverlayPDF
		Overlay overlayer = new Overlay();
		overlayer.setInputFile(overlayPDFFileName);
		overlayer.setAllPagesOverlayFile(originalPDFDocumentBeforeAnyModification);
		overlayer.setOverlayPosition(Position.FOREGROUND);
		overlayer.close();

		PDDocument watermarkDocument = new PDDocument();
		overlayer.setAllPagesOverlayPDF(watermarkDocument);
		watermarkDocument.save(documentWithWatermark);

	}

	/**
	 * https://stackoverflow.com/questions/32844926/using-overlay-in-pdfbox-2-0
	 * 
	 * @throws IOException
	 */
	public static void method3() throws IOException {
		UUID randomUUID = java.util.UUID.randomUUID();
		String fileName = randomUUID.toString() + ".pdf";
		FileUtils.copyFile(new File(originalPDFDocumentBeforeAnyModification), new File(fileName));

		PDRectangle rectangle = PDRectangle.A4;

		File file = new File(fileName);
		PDDocument originalDoc = Loader.loadPDF(file);
		for (PDPage page1 : originalDoc.getPages()) {

			PDPageContentStream contentStream = new PDPageContentStream(originalDoc, page1, AppendMode.APPEND, true);

			// PDColor nonStrokingColor = PDColor.

			contentStream.setNonStrokingColor(Color.gray);
			contentStream.beginText();
			contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 30);
			contentStream.newLineAtOffset(rectangle.getWidth() / 3, rectangle.getHeight() / 2);
			contentStream.showText("My watermark");
			contentStream.endText();
			contentStream.close();
		}

		File dir = new File(outputDirectoryName);
		if (!dir.exists())
			dir.mkdirs();

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