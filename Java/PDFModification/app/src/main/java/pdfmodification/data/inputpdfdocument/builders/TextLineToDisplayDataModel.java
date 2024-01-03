package pdfmodification.data.inputpdfdocument.builders;

import pdfmodification.data.TextToDisplayType;
import pdfmodification.data.users.PDFAllowedUser;

public class TextLineToDisplayDataModel {

	private String freeText;
	private TextToDisplayType textType;

	public String getText(PDFAllowedUser pdfAllowedUser) {
		switch (textType) {
		case FIRST_NAME_SPACE_AND_LAST_NAME:
			return pdfAllowedUser.getPrenom() + " " + pdfAllowedUser.getNom();
		case FREE_TEXT:
			return freeText;
		default:
			return null;
		}
	}

}
