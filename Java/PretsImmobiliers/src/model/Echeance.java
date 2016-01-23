package model;

public class Echeance {
  private Emprunt emprunt;
  private double capitalRestantAEmprunter;
  private double montantCapital;
  private double montantInteret;

  public Echeance(Emprunt emprunt, double capitalRestantAEmprunter, double montantCapital, double montantInteret) {
    this.emprunt = emprunt;
    this.capitalRestantAEmprunter = capitalRestantAEmprunter;
    this.montantCapital = montantCapital;
    this.montantInteret = montantInteret;
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
}
