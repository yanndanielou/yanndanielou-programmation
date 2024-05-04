package common.excel;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

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
