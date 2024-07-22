package pdfmodification.data.users;

import com.opencsv.bean.CsvBindByName;

import pdfmodification.data.ValiditeAcces;

public class PDFAllowedUser {

	@CsvBindByName(column = "nom")
	private String nom;

	@CsvBindByName(column = "prenom")
	private String prenom;

	@CsvBindByName(column = "accesHautementConfidentiel")
	private String accesHautementConfidentiel;

	@CsvBindByName(column = "validiteAcces")
	private ValiditeAcces validiteAcces;

	@CsvBindByName(column = "motDePasseOuverture")
	private String motDePasseOuverture;

	@CsvBindByName(column = "motDePasseImpression")
	private String motDePasseImpression;

	public String getNom() {
		return nom.trim();
	}

	public String getPrenom() {
		return prenom.trim();
	}

	public String getAccesHautementConfidentiel() {
		return accesHautementConfidentiel.trim();
	}

	public String getMotDePasseOuverture() {
		return motDePasseOuverture.trim();
	}

	public String getMotDePasseImpression() {
		return motDePasseImpression.trim();
	}

	public boolean isAllowedToAccessPDF() {
		return ValiditeAcces.Oui.equals(validiteAcces);
	}

	@Override
	public String toString() {
		return "PDFAllowedUser [nom=" + nom + ", prenom=" + prenom + ", accesHautementConfidentiel="
				+ accesHautementConfidentiel + ", validiteAcces=" + validiteAcces + ", motDePasseOuverture="
				+ motDePasseOuverture + ", motDePasseImpression=" + motDePasseImpression + "]";
	}

}