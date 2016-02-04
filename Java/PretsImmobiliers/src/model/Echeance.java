package model;

import Core.ModificationAction.ModificationEcheanceAction;
import Core.ModificationAction.NoOperationAction;

public class Echeance {
  private Emprunt emprunt;
  private double capitalRestantAEmprunter;
  private double montantCapital;
  private double montantInteret;
  private double montantAssurance;
  private ModificationEcheanceAction modificationEcheanceAction;

  public Echeance(Emprunt emprunt, double capitalRestantAEmprunter, double montantCapital, double montantInteret) {
    this.emprunt = emprunt;
    this.capitalRestantAEmprunter = capitalRestantAEmprunter;
    this.montantCapital = montantCapital;
    this.montantInteret = montantInteret;
    modificationEcheanceAction = new NoOperationAction();
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

  public ModificationEcheanceAction getModificationEcheanceAction() {
    return modificationEcheanceAction;
  }

  public void setMontantAssurance(double montantAssurance) {
    this.montantAssurance = montantAssurance;
  }

  public double getMontantAssurance() {
    return montantAssurance;
  }
}
