package secondregard.data.inputpdfdocument.builders;

import java.util.List;

public class InputPDFAndActionsToPerformDataModel {

	private List<InputPDFsDataModel> inputPdfs;
	private boolean encrypt;

	private List<TextLineToDisplayDataModel> textLinesToDisplay;

	public List<TextLineToDisplayDataModel> getTextLinesToDisplay() {
		return textLinesToDisplay;
	}

	public List<InputPDFsDataModel> getInputPdfs() {
		return inputPdfs;
	}

	public boolean isEncrypted() {
		return encrypt;
	}

}
