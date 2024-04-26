package common.builders;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;

public class JsonDataModelBuilder<T> {

	private static final Logger LOGGER = LogManager.getLogger(JsonDataModelBuilder.class);

	private Gson gson = new Gson();

	public T loadDataModelFromJsonFile(Class<?> classReader, Class<?> dataModelClass, String jsonFile) {

		InputStream resourceAsStream = classReader.getResourceAsStream(jsonFile);
		LOGGER.info(() -> "GameBoardModelBuilder, resourceAsStream:" + resourceAsStream);

		BufferedReader br = null;

		try {
			br = new BufferedReader(new InputStreamReader(resourceAsStream));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return (T) gson.fromJson(br, dataModelClass);
	}

}
