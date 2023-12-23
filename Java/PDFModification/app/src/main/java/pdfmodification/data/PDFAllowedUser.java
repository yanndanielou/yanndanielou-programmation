package pdfmodification.data;

import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvBindByName;

public class PDFAllowedUser {

	@CsvBindByName(column = "nom")
	private String nom;

	@CsvBindByName(column = "prenom")
	private String prenom;
	
	@CsvBindByName(column = "accesHautementConfidentiel")
	private String accesHautementConfidentiel;
	
	@CsvBindByName(column = "validiteAcces")
	private String validiteAcces;
	
	@CsvBindByName(column = "motDePasseOuverture")
	private String motDePasseOuverture;
	
	@CsvBindByName(column = "motDePasseImpression")
	private String motDePasseImpression;
	
}