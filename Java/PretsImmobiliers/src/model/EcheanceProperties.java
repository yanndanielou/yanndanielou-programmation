package model;

public class EcheanceProperties {
  private Echeance echeance;
  private double montantCapital;
  private double montantAssurance;
  private double capitalRestantARembourser;

  public EcheanceProperties(Echeance echeance) {
    this.echeance = echeance;
  }

  public double getMontantAssurance() {
    return montantAssurance;
  }

  public void setMontantAssurance(double montantAssurance) {
    this.montantAssurance = montantAssurance;
  }

  public double getMontantCapital() {
    return montantCapital;
  }

  public void setMontantCapital(double montantCapital) {
    this.montantCapital = montantCapital;
  }

  public double getMontantInteret() {
    return capitalRestantARembourser * echeance.getEmprunt().getTauxPeriodique();
  }

  public double getMensualiteHorsAssurance() {
    return montantCapital + getMontantInteret();
  }

  public double getCapitalRestantARembourser() {
    return capitalRestantARembourser;
  }

  public void setCapitalRestantARembourser(double capitalRestantAEmprunter) {
    this.capitalRestantARembourser = capitalRestantAEmprunter;
  }

  public Echeance getEcheance() {
    return echeance;
  }
}
