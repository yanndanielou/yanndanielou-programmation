package secondregard.data.inputpdfdocument.builders;

import java.util.List;

public class InputPDFAndActionsToPerformDataModel {

	private List<InputPDFsDataModel> inputPdfs;

	private List<TextLineToDisplayDataModel> textLinesToDisplay;
	
	private boolean enabled = true;

	public List<TextLineToDisplayDataModel> getTextLinesToDisplay() {
		return textLinesToDisplay;
	}

	public List<InputPDFsDataModel> getInputPdfs() {
		return inputPdfs;
	}
	
	public boolean isEnabled() {
		return enabled;
	}


}
