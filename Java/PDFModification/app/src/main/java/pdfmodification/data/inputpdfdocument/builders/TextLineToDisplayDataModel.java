package pdfmodification.data.inputpdfdocument.builders;

import org.apache.pdfbox.pdmodel.font.Standard14Fonts.FontName;

import common.builders.PointDataModel;
import pdfmodification.data.TextToDisplayType;
import pdfmodification.data.users.PDFAllowedUser;

public class TextLineToDisplayDataModel {

	private String freeText;
	private TextToDisplayType textType;
	private PDFFontDataModel font;
	private PointDataModel newLineAtOffset;

	public String computeText(PDFAllowedUser pdfAllowedUser) {
		switch (textType) {
		case FIRST_NAME_SPACE_AND_LAST_NAME:
			return pdfAllowedUser.getPrenom() + " " + pdfAllowedUser.getNom();
		case FREE_TEXT:
			return freeText;
		default:
			return null;
		}
	}

	public PointDataModel getNewLineAtOffset() {
		return newLineAtOffset;
	}

	public PDFFontDataModel getFont() {
		return font;
	}

}
