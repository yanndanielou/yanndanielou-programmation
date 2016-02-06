package model;

public class EcheanceProperties {
  private double montantCapital;
  private double montantInteret;
  private double montantAssurance;
  private double capitalRestantARembourser;

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
    return montantInteret;
  }

  public void setMontantInteret(double montantInteret) {
    this.montantInteret = montantInteret;
  }

  public double getMensualiteHorsAssurance() {
    return montantCapital + montantInteret;
  }

  public double getCapitalRestantARembourser() {
    return capitalRestantARembourser;
  }

  public void setCapitalRestantARembourser(double capitalRestantAEmprunter) {
    this.capitalRestantARembourser = capitalRestantAEmprunter;
  }
}
