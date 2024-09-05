package secondregard.data.inputpdfdocument.builders;

import common.builders.ColorDataModel;
import common.builders.PointDataModel;
import secondregard.data.TextToDisplayType;
import secondregard.data.users.SecondRegardAllowedUser;

public class TextLineToDisplayDataModel {

	private String freeText;
	private TextToDisplayType textType;
	private PDFFontDataModel font;
	private PointDataModel newLineAtOffset;
	private ColorDataModel nonStrokingColor;
	
	private boolean onlyForPersonalizedByAllowedUser = false;
	private boolean onlyForNotPersonalized = false;

	public boolean isToValidFor(SecondRegardAllowedUser pdfAllowedUser) {
		if (isUserDependant()) {
			return pdfAllowedUser != null;
		}
		if (onlyForPersonalizedByAllowedUser) {
			return pdfAllowedUser != null;
		}
		if (onlyForNotPersonalized) {
			return pdfAllowedUser == null;
		}
		return true;
	}

	private boolean isUserDependant() {
		return TextToDisplayType.FIRST_NAME_SPACE_AND_LAST_NAME.equals(textType);
	}

	public String computeText(SecondRegardAllowedUser pdfAllowedUser) {
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

	public ColorDataModel getNonStrokingColor() {
		return nonStrokingColor;
	}
}
