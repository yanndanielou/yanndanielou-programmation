package secondregard.data.users;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

public class SecondRegardAllowedUsersFromCsvLoader {

	public static List<SecondRegardAllowedUser> getPDFAllowedUsersFromCsvFile(String pathAsString) {
		Path inputCsvFile = Paths.get(pathAsString);


		try (Reader reader = Files.newBufferedReader(inputCsvFile)) {
			CsvToBean<SecondRegardAllowedUser> cb = new CsvToBeanBuilder<SecondRegardAllowedUser>(reader).withType(SecondRegardAllowedUser.class).withSeparator(';')
					.build();

			return cb.parse();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ArrayList<SecondRegardAllowedUser>();
	}

}
