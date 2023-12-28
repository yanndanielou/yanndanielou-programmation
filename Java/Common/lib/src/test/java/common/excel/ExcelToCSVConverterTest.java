package common.excel;

import static matcher.BasicMatchers.empty;
import static matcher.BasicMatchers.greaterThanOrEqualTo;
import static matcher.BasicMatchers.is;
import static matcher.BasicMatchers.lessThanOrEqualTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import common.random.RandomIntegerGenerator;

public class ExcelToCSVConverterTest {

	@Nested
	public class ConvertAllWorksheetFromExcelFileToCSV {
		@Nested
		public class BadUsages {

		}

		@Nested
		public class CorrectUsages {

			@Test
			void basicExampleExcelFile() {
				ExcelToCSVConverter.convertAllWorksheetFromExcelFileToCSV("lib/src/test/resources/common/excel/ExcelWorksheetToTestCsvConversion.xlsx");
			}
		}
	}

}
