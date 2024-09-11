package secondregard.data.inputpdfdocument.builders;

import java.util.List;

public class ActionsToPerformDataModel {

	private List<FilesToZipDataModel> zipBatchs;
	private List<InputPDFAndActionsToPerformDataModel> pdfBatchs;

	public List<InputPDFAndActionsToPerformDataModel> getPdfBatchs() {
		return pdfBatchs.stream().filter(InputPDFAndActionsToPerformDataModel::isEnabled).toList();
	}

	public List<FilesToZipDataModel> getZipBatchs() {
		return zipBatchs;
	}

}
