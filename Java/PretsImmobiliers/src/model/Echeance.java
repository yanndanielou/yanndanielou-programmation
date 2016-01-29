package model;

public class Echeance {
  private Emprunt emprunt;
  private double capitalRestantAEmprunter;
  private double montantCapital;
  private double montantInteret;
  private double montantAssurance;

  public Echeance(Emprunt emprunt, double capitalRestantAEmprunter, double montantCapital, double montantInteret) {
    this.emprunt = emprunt;
    this.capitalRestantAEmprunter = capitalRestantAEmprunter;
    this.montantCapital = montantCapital;
    this.montantInteret = montantInteret;
    montantAssurance = 0;
  }

  public double getMensualiteHorsAssurance() {
    return montantCapital + montantInteret;
  }

  public double getCapitalRestantARembourser() {
    return capitalRestantAEmprunter;
  }

  public double getMontantCapital() {
    return montantCapital;
  }

  public double getMontantInteret() {
    return montantInteret;
  }

  public double getMontantAssurance() {
    return montantAssurance;
  }
}
