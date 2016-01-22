package model;

public class RealEstate {

  private static final double TAUX_GROSSIER_FRAIS_NOTAIRE = 0.075;

  private double prixNetAcheteur;
  private double apportPersonnel;
  private boolean entreParticuliers;
  private boolean valeurMobilier;

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

  public double getFraisNotaire() {
    double fraisNotaire = prixNetAcheteur * TAUX_GROSSIER_FRAIS_NOTAIRE;
    return fraisNotaire;
  }
}
