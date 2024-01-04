package pdfmodification.data.inputpdfdocument.builders;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import common.builders.FileByMaskDataModel;

public class InputPDFAndActionsToPerformDataModel {

	private FileByMaskDataModel inputPdfWithFileMaskForMultiplePDF;

	private ArrayList<TextLineToDisplayDataModel> textLinesToDisplay;

	public ArrayList<TextLineToDisplayDataModel> getTextLinesToDisplay() {
		return textLinesToDisplay;
	}

	public List<File> getInputPDFFiles() {
		return inputPdfWithFileMaskForMultiplePDF.getResolvedFiles();
	}

}
