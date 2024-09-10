package secondregard.data.inputpdfdocument.builders;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import common.builders.FileByMaskDataModel;

public class InputPDFsDataModel {
	private FileByMaskDataModel inputPdfWithFileMaskForMultiplePDF;
	private List<PagesToDeleteDataModel> pagesToDelete;
	private String outputFilePrefixToAdd;
	private String genericOutputFileName;
	private boolean personalizeByAllowedUsed;
	private boolean encrypt;

	public List<File> getInputPDFFiles() {
		return inputPdfWithFileMaskForMultiplePDF.getResolvedFiles();
	}

	public List<Integer> getAllPageNumberToDelete() {
		if (pagesToDelete == null) {
			return new ArrayList<Integer>();
		}
		List<Integer> allIntegers = new ArrayList<Integer>();
		for (PagesToDeleteDataModel integersDataModel : pagesToDelete) {
			allIntegers.addAll(integersDataModel.getPageNumbers());
		}
		return allIntegers;
	}

	public String getOutputFilePrefixToAdd() {
		return outputFilePrefixToAdd;
	}

	public String getGenericOutputFileName() {
		return genericOutputFileName;
	}

	public boolean isEncrypted() {
		return encrypt;
	}

	public boolean isPersonalizeByAllowedUsed() {
		return personalizeByAllowedUsed;
	}
}
