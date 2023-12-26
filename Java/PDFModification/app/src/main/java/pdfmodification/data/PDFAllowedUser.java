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

	public String getNom() {
		return nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public String getAccesHautementConfidentiel() {
		return accesHautementConfidentiel;
	}

	public String getValiditeAcces() {
		return validiteAcces;
	}

	public String getMotDePasseOuverture() {
		return motDePasseOuverture;
	}

	public String getMotDePasseImpression() {
		return motDePasseImpression;
	}
	
}