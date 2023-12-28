package common.excel;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import common.filesanddirectories.FileNameExtensionAndPathHelper;

public class ExcelToCSVConverter {

	private static final Logger LOGGER = LogManager.getLogger(ExcelToCSVConverter.class);

	public static void convertAllWorksheetFromExcelFileToCSV(String excelFilePathWithExtension) {
		String excelFileExtension = FileNameExtensionAndPathHelper
				.getExtensionWithoutPointOfFile(excelFilePathWithExtension);
		String excelFileNameWithoutExtension = FileNameExtensionAndPathHelper
				.getFileNameWithoutExtension(excelFilePathWithExtension);
		InputStream inp = null;
		try {
			inp = new FileInputStream(excelFilePathWithExtension);
			Workbook wb = WorkbookFactory.create(inp);

			LOGGER.info(() -> "Excel document " + excelFilePathWithExtension + " has " + wb.getNumberOfSheets()
					+ " work sheets");

			for (int i = 0; i < wb.getNumberOfSheets(); i++) {
				String sheetName = wb.getSheetAt(i).getSheetName();

				System.out.println(sheetName);
			}
		} catch (java.io.FileNotFoundException ex) {
			LOGGER.error(ex);
		} catch (Exception ex) {
			LOGGER.error(ex);
		} finally {
			try {
				inp.close();
			} catch (IOException ex) {
			}
		}
		/*
		 * // Create an instance of Workbook class Workbook workbook = new Workbook();
		 * // Load an Excel file workbook.loadFromFile(excelFilePathWithExtension);
		 * 
		 * // Get the first worksheet WorksheetsCollection worksheets =
		 * workbook.getWorksheets(); for (int workSheetNumber = 0; workSheetNumber <
		 * worksheets.size(); workSheetNumber++) { Worksheet sheet =
		 * worksheets.get(workSheetNumber); String sheetName = sheet.getName();
		 * LOGGER.info(() -> "Treat sheet: " + sheetName); // Save the worksheet as CSV
		 * sheet.saveToFile(excelFileNameWithoutExtension + "-" + sheetName, ","); }
		 */
	}

}
