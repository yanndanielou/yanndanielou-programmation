package model;

public class EcheanceProperties {
  private Echeance echeance;
  private double montantCapital;
  private double montantAssurance;
  private double capitalRestantARembourserAvantEcheance;

  public EcheanceProperties(Echeance echeance) {
    this.echeance = echeance;
  }

  public EcheanceProperties() {
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
    return capitalRestantARembourserAvantEcheance * echeance.getEmprunt().getTauxPeriodique();
  }

  public double getMensualiteHorsAssurance() {
    return montantCapital + getMontantInteret();
  }

  public double getCapitalRestantARembourserAvantEcheance() {
    return capitalRestantARembourserAvantEcheance;
  }

  public void setCapitalRestantARembourserAvantEcheance(double capitalRestantAEmprunterAvantEcheance) {
    this.capitalRestantARembourserAvantEcheance = capitalRestantAEmprunterAvantEcheance;
  }

  public double getCapitalRestantARembourserApresEcheance() {
    return capitalRestantARembourserAvantEcheance - montantCapital;
  }

  public Echeance getEcheance() {
    return echeance;
  }

  public void setEcheance(Echeance echeance) {
    this.echeance = echeance;
  }
}
