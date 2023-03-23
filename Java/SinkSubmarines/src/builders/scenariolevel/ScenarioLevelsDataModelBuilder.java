package builders.scenariolevel;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.google.gson.Gson;

public class ScenarioLevelsDataModelBuilder {
	private Gson gson = new Gson();

	private ScenarioLevelsDataModel scenario_leve_data_model;

	public ScenarioLevelsDataModel getGeneric_objects_data_model() {
		return scenario_leve_data_model;
	}

	public ScenarioLevelsDataModelBuilder(String generic_objects_data_model_json_file) {
		BufferedReader br = null;

		try {
			
			br = new BufferedReader(new FileReader(generic_objects_data_model_json_file));

		} catch (IOException e) {
			e.printStackTrace();
		}
		scenario_leve_data_model = gson.fromJson(br, ScenarioLevelsDataModel.class);
	}

}
