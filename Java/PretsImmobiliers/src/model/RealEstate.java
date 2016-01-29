package model;

public class RealEstate {

  private static final double TAUX_GROSSIER_FRAIS_NOTAIRE = 0.0775;

  private double prixNetAcheteur;
  private double fraisAgence;
  private double apportPersonnel;
  private boolean entreParticuliers;
  private boolean valeurMobilier;
  private boolean fraisNotaireFilledByUser = false;
  private int fraisNotaire;

  public double getFraisAgence() {
    return fraisAgence;
  }

  public void setFraisAgence(double fraisAgence) {
    this.fraisAgence = fraisAgence;
  }

  public double getApportPersonnel() {
    return apportPersonnel;
  }

  public void setApportPersonnel(double apportPersonnel) {
    this.apportPersonnel = apportPersonnel;
  }

  public void setValeurMobilier(boolean valeurMobilier) {
    this.valeurMobilier = valeurMobilier;
  }

  public void setEntreParticuliers(boolean entreParticuliers) {
    this.entreParticuliers = entreParticuliers;
  }

  public void setPrixNetAcheteur(double prixNetAcheteur) {
    this.prixNetAcheteur = prixNetAcheteur;
  }

  public double getPrixNetAcheteur() {
    return prixNetAcheteur;
  }

  public void setFraisNotaire(int fraisNotaire) {
    this.fraisNotaire = fraisNotaire;
    fraisNotaireFilledByUser = true;
  }

  public double getFraisNotaire() {
    if (fraisNotaireFilledByUser) {
      return fraisNotaire;
    }
    double fraisNotaireDouble = (prixNetAcheteur - fraisAgence) * TAUX_GROSSIER_FRAIS_NOTAIRE;
    int fraisNotaireInt = (int) fraisNotaireDouble;
    return fraisNotaireInt;
  }

}
