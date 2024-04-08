package pdfmodification.data.inputpdfdocument.builders;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import common.builders.FileByMaskDataModel;

public class InputPDFsDataModel {
	private FileByMaskDataModel inputPdfWithFileMaskForMultiplePDF;
	private List<PageNumberToDeleteDataModel> pageNumberToDelete;
	private String outputFilePrefixToAdd;
	private String genericOutputFileName;

	public List<File> getInputPDFFiles() {
		return inputPdfWithFileMaskForMultiplePDF.getResolvedFiles();
	}

	public List<Integer> getAllPageNumberToDelete() {
		if (pageNumberToDelete == null) {
			return new ArrayList<Integer>();
		}
		List<Integer> allIntegers = new ArrayList<Integer>();
		for (PageNumberToDeleteDataModel integersDataModel : pageNumberToDelete) {
			allIntegers.add(integersDataModel.getPageNumber());
		}
		return allIntegers;
	}

	public String getOutputFilePrefixToAdd() {
		return outputFilePrefixToAdd;
	}

	public String getGenericOutputFileName() {
		return genericOutputFileName;
	}
}
