package pdfmodification.data.users;

import com.opencsv.bean.CsvBindByName;

import common.string.utils.StringUtils;
import pdfmodification.data.ValiditeAcces;

public class PDFAllowedUser {

	@CsvBindByName(column = "nom")
	private String nom;

	@CsvBindByName(column = "prenom")
	private String prenom;

	@CsvBindByName(column = "entite")
	private String entite;

	@CsvBindByName(column = "accesHautementConfidentiel")
	private String accesHautementConfidentiel;

	@CsvBindByName(column = "validiteAcces")
	private ValiditeAcces validiteAcces;

	@CsvBindByName(column = "motDePasseOuverture")
	private String motDePasseOuverture;

	@CsvBindByName(column = "motDePasseImpression")
	private String motDePasseImpression;

	public String getNom() {
		return org.apache.commons.lang3.StringUtils.trim(nom);
	}

	public String getEntite() {
		return org.apache.commons.lang3.StringUtils.trim(entite);
	}

	public String getPrenom() {
		return org.apache.commons.lang3.StringUtils.trim(prenom);
	}

	public String getAccesHautementConfidentiel() {
		return org.apache.commons.lang3.StringUtils.trim(accesHautementConfidentiel);
	}

	public String getMotDePasseOuverture() {
		return org.apache.commons.lang3.StringUtils.trim(motDePasseOuverture);
	}

	public String getMotDePasseImpression() {
		return org.apache.commons.lang3.StringUtils.trim(motDePasseImpression);
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