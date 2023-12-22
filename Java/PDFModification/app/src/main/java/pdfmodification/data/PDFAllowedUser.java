package pdfmodification.data;

import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvBindByName;

public class PDFAllowedUser {

	@CsvBindByName(column = "nom")
	private String nom;

	@CsvBindByName(column = "prenom")
	private String prenom;
}
