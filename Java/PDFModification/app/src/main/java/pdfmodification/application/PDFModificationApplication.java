package pdfmodification.application;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.pdfbox.cos.COSDictionary;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageContentStream.AppendMode;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDSimpleFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.apache.pdfbox.util.Matrix;

public class PDFModificationApplication {
	protected static final Logger LOGGER = LogManager.getLogger(PDFModificationApplication.class);

	public static void main(String[] args) throws Exception {
		
		LOGGER.info(() -> "Application started");
		PDDocument document = new PDDocument();

		PDRectangle rectangle = PDRectangle.A4;
		PDPage page = new PDPage(rectangle);

		document.addPage(page);

		PDPageContentStream content = new PDPageContentStream(document, page, AppendMode.APPEND, true);

		content.beginText();
		content.newLineAtOffset(rectangle.getWidth()/3, rectangle.getHeight()/2);

		content.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 20);
		content.showText("My watermark");
		content.setTextMatrix(new Matrix(1, 0, 0, 1.5f, 7, 30));
		content.showText("Stretched text (size 12, factor 1.5)");
		content.setTextMatrix(new Matrix(1, 0, 0, 2f, 7, 5));
		content.showText("Stretched text (size 12, factor 2)");
		content.endText();

		content.close();

		LOGGER.info(() -> "Save output pdf");
		document.save("SimplePdfStretchedText.pdf");

		LOGGER.info(() -> "Application end");

	}
}