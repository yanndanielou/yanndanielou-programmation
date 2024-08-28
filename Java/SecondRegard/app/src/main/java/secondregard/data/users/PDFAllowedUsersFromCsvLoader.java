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

public class PDFAllowedUsersFromCsvLoader {

	public static List<PDFAllowedUser> getPDFAllowedUsersFromCsvFile(String pathAsString) {
		Path inputCsvFile = Paths.get(pathAsString);


		try (Reader reader = Files.newBufferedReader(inputCsvFile)) {
			CsvToBean<PDFAllowedUser> cb = new CsvToBeanBuilder<PDFAllowedUser>(reader).withType(PDFAllowedUser.class).withSeparator(';')
					.build();

			return cb.parse();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ArrayList<PDFAllowedUser>();
	}

}
