package secondregard.data.inputpdfdocument.builders;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.google.gson.Gson;

public class SecondRegardActionsToPerformModelBuilder {
	private Gson gson = new Gson();

	private ActionsToPerformDataModel actionsDataModel;

	public SecondRegardActionsToPerformModelBuilder(String jsonFilePath) {
		BufferedReader br = null;

		try {
			br = new BufferedReader(new FileReader(jsonFilePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		actionsDataModel = gson.fromJson(br, ActionsToPerformDataModel.class);
	}

	public ActionsToPerformDataModel getActionsToPerformDataModel() {
		return actionsDataModel;
	}

}
