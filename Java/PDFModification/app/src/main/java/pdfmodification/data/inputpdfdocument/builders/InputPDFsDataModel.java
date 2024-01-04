package pdfmodification.data.inputpdfdocument.builders;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import common.builders.FileByMaskDataModel;
import common.builders.IntegersDataModel;

public class InputPDFsDataModel {
	private FileByMaskDataModel inputPdfWithFileMaskForMultiplePDF;
	private List<PageNumberToDeleteDataModel> pageNumberToDelete;

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
}
