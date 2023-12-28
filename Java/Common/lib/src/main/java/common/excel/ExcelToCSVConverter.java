package common.excel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.spire.xls.Workbook;
import com.spire.xls.Worksheet;
import com.spire.xls.collections.WorksheetsCollection;

import common.filesanddirectories.FileNameExtensionAndPathHelper;

public class ExcelToCSVConverter {

	private static final Logger LOGGER = LogManager.getLogger(ExcelToCSVConverter.class);

	public static void convertAllWorksheetFromExcelFileToCSV(String excelFilePathWithExtension) {
		String excelFileExtension = FileNameExtensionAndPathHelper
				.getExtensionWithoutPointOfFile(excelFilePathWithExtension);
		String excelFileNameWithoutExtension = FileNameExtensionAndPathHelper
				.getFileNameWithoutExtension(excelFilePathWithExtension);

		// Create an instance of Workbook class
		Workbook workbook = new Workbook();
		// Load an Excel file
		workbook.loadFromFile(excelFilePathWithExtension);

		// Get the first worksheet
		WorksheetsCollection worksheets = workbook.getWorksheets();
		LOGGER.info(
				() -> "Excel document " + excelFilePathWithExtension + " has " + worksheets.size() + " work sheets");
		for (int workSheetNumber = 0; workSheetNumber < worksheets.size(); workSheetNumber++) {
			Worksheet sheet = worksheets.get(workSheetNumber);
			String sheetName = sheet.getName();
			LOGGER.info(() -> "Treat sheet: " + sheetName);
			// Save the worksheet as CSV
			sheet.saveToFile(excelFileNameWithoutExtension + "-" + sheetName, ",");
		}

	}

}
