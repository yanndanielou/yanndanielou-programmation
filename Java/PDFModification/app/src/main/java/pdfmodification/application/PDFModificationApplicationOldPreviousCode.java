package pdfmodification.application;

/**
 * https://github.com/topobyte/pdfbox-tools/blob/master/src/main/java/org/apache/pdfbox/tools/OverlayPDF.java
 * 
 */

public class PDFModificationApplicationOldPreviousCode {
	
	/*
	 * @Deprecated public static void allStepsInOneMethodWithIntermediateFiles()
	 * throws IOException {
	 * 
	 * PDDocument newWatermarkOnlyDocument = new PDDocument();
	 * 
	 * PDRectangle rectangle = PDRectangle.A4; PDPage watermarkOnlyPage = new
	 * PDPage(rectangle);
	 * 
	 * newWatermarkOnlyDocument.addPage(watermarkOnlyPage);
	 * 
	 * PDPageContentStream watermarkOnlyDocumentPageContentStream = new
	 * PDPageContentStream(newWatermarkOnlyDocument, watermarkOnlyPage,
	 * AppendMode.APPEND, true);
	 * 
	 * watermarkOnlyDocumentPageContentStream.beginText();
	 * watermarkOnlyDocumentPageContentStream.newLineAtOffset(rectangle.getWidth() /
	 * 3, rectangle.getHeight() / 2);
	 * 
	 * watermarkOnlyDocumentPageContentStream.setStrokingColor(Color.blue);
	 * watermarkOnlyDocumentPageContentStream.setNonStrokingColor(Color.gray);
	 * watermarkOnlyDocumentPageContentStream.setFont(new
	 * PDType1Font(Standard14Fonts.FontName.HELVETICA), 20);
	 * watermarkOnlyDocumentPageContentStream.showText("My watermark");
	 * watermarkOnlyDocumentPageContentStream.setTextMatrix(new Matrix(1, 0, 0,
	 * 1.5f, 7, 30)); watermarkOnlyDocumentPageContentStream.
	 * showText("Stretched text (size 12, factor 1.5)");
	 * watermarkOnlyDocumentPageContentStream.setTextMatrix(new Matrix(1, 0, 0, 2f,
	 * 7, 5)); watermarkOnlyDocumentPageContentStream.
	 * showText("Stretched text (size 12, factor 2)");
	 * watermarkOnlyDocumentPageContentStream.endText();
	 * 
	 * watermarkOnlyDocumentPageContentStream.close();
	 * 
	 * File myObj = new File(PDFModificationHelpers.watermarkOnlyPDFFileName); if
	 * (myObj.delete()) {
	 * System.out.println("Delete previous version of file file: " +
	 * myObj.getName()); } else { System.out.println("Failed to delete the file.");
	 * }
	 * 
	 * LOGGER.info(() -> "Save output pdf");
	 * newWatermarkOnlyDocument.save(PDFModificationHelpers.watermarkOnlyPDFFileName
	 * );
	 * 
	 * // OverlayPDF UUID randomUUID = java.util.UUID.randomUUID(); String fileName
	 * = PDFModificationHelpers.outputDirectoryName + randomUUID.toString() +
	 * ".pdf"; FileUtils.copyFile(new
	 * File(PDFModificationHelpers.originalPDFDocumentBeforeAnyModificationFullPath)
	 * , new File(fileName));
	 * 
	 * File file = new File(fileName); PDDocument originalDoc =
	 * Loader.loadPDF(file);
	 * 
	 * Overlay overlayer = new Overlay(); overlayer.setInputPDF(originalDoc);
	 * overlayer.setAllPagesOverlayFile(PDFModificationHelpers.
	 * watermarkOnlyPDFFileName); overlayer.setOverlayPosition(Position.BACKGROUND);
	 * 
	 * overlayer.close();
	 * 
	 * PDDocument watermarkDocument = new PDDocument();
	 * overlayer.setAllPagesOverlayPDF(watermarkDocument);
	 * 
	 * try (PDDocument result = overlayer.overlay(new HashMap<>())) {
	 * result.save(PDFModificationHelpers.documentWithWatermark); } //
	 * watermarkDocument.save(documentWithWatermark);
	 * 
	 * }
	 */

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