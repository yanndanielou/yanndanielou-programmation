package pdfmodification.data.inputpdfdocument.builders;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import common.builders.FileByMaskDataModel;

public class InputPDFAndActionsToPerformDataModel {

	private List<InputPDFsDataModel> inputPdfs;

	private List<TextLineToDisplayDataModel> textLinesToDisplay;

	public List<TextLineToDisplayDataModel> getTextLinesToDisplay() {
		return textLinesToDisplay;
	}

	public List<InputPDFsDataModel> getInputPdfs() {
		return inputPdfs;
	}

}
