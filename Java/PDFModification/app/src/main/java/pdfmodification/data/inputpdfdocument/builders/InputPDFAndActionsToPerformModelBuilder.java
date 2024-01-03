package pdfmodification.data.inputpdfdocument.builders;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.google.gson.Gson;

public class InputPDFAndActionsToPerformModelBuilder {
	private Gson gson = new Gson();

	private InputPDFAndActionsToPerformDataModel inputPDFAndActionsToPerformDataModel;

	public InputPDFAndActionsToPerformDataModel getInputPDFAndActionsToPerformDataModel() {
		return inputPDFAndActionsToPerformDataModel;
	}

	public InputPDFAndActionsToPerformModelBuilder(String jsonFilePath) {
		BufferedReader br = null;

		try {
			br = new BufferedReader(new FileReader(jsonFilePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		inputPDFAndActionsToPerformDataModel = gson.fromJson(br, InputPDFAndActionsToPerformDataModel.class);
	}

}
