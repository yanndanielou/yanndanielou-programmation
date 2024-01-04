package pdfmodification.data.inputpdfdocument.builders;

import org.apache.pdfbox.pdmodel.font.Standard14Fonts.FontName;

public class PDFFontDataModel {

	private FontName fontName;
	private Integer fontSize;

	public FontName getFontName() {
		return fontName;
	}

	public Integer getFontSize() {
		return fontSize;
	}

}
