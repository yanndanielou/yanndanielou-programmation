package builders.scenariolevel;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.google.gson.Gson;

public class ScenarioLevelDataModelBuilder {
	
	//private static final Logger LOGGER = LogManager.getLogger(ScenarioLevelDataModelBuilder.class);

	
	private Gson gson = new Gson();

	private ScenarioLevelDataModel scenario_level_data_model;

	public ScenarioLevelDataModel getScenario_level_data_model() {
		return scenario_level_data_model;
	}

	public ScenarioLevelDataModelBuilder(String generic_objects_data_model_json_file) {
		BufferedReader br = null;

		try {
			br = new BufferedReader(new FileReader(generic_objects_data_model_json_file));

		} catch (IOException e) {
			e.printStackTrace();
		}
		scenario_level_data_model = gson.fromJson(br, ScenarioLevelDataModel.class);
	}

}
