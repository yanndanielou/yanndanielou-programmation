import java.io.File;
import java.util.HashMap;
import org.apache.pdfbox.multipdf.Overlay;
import org.apache.pdfbox.pdmodel.PDDocument;

public class PDFModificationApplication {

    public static void main(String[] args) throws Exception{  
        PDDocument realDoc = PDDocument.load(new File("originaldocument.pdf"));
        //the above is the document you want to watermark
        //for all the pages, you can add overlay guide, indicating watermark the original pages with the watermark document.

        HashMap<Integer, String> overlayGuide = new HashMap<Integer, String>();
        for(int i=0; i<realDoc.getNumberOfPages(); i++){
            overlayGuide.put(i+1, "watermark.pdf");
            //watermark.pdf is the document which is a one page PDF with your watermark image in it. 
            //Notice here, you can skip pages from being watermarked.
        }
        Overlay overlay = new Overlay();
        overlay.setInputPDF(realDoc);
        overlay.setOutputFile("final.pdf");
        overlay.setOverlayPosition(Overlay.Position.BACKGROUND);
        overlay.overlay(overlayGuide);      
    }
}