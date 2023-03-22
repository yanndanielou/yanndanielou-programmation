package builders.genericobjects;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.google.gson.Gson;

public class GenericObjectsDataModelBuilder {
	private Gson gson = new Gson();

	private GenericObjectsDataModel generic_objects_data_model;

	public GenericObjectsDataModel getGeneric_objects_data_model() {
		return generic_objects_data_model;
	}

	public GenericObjectsDataModelBuilder(String generic_objects_data_model_json_file) {
		BufferedReader br = null;

		try {
			
			br = new BufferedReader(new FileReader(generic_objects_data_model_json_file));

		} catch (IOException e) {
			e.printStackTrace();
		}
		generic_objects_data_model = gson.fromJson(br, GenericObjectsDataModel.class);
	}

}
