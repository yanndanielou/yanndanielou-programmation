package model;

import java.util.ArrayList;
import java.util.List;

public class Emprunt {

  public static final int NOMBRE_ECHEANCES_PAR_AN = 12;

  private double tauxAnnuel;
  private double capitalEmprunte;

  private boolean isMensualiteHorsAssuranceFilledByUser = false;
  private Double mensualiteHorsAssurance;
  private Double assurancesMensuelles;
  private boolean isNombreEcheancesFilledByUser = false;
  private Integer nombreEcheancesDesire;
  private List<Echeance> echeances = new ArrayList<Echeance>();

  public Emprunt() {
    resetComputedValues();
  }

  /**
  * Formule du calcul de l'échéance:   {@link http://www.cbanque.com/credit/principe.php}} 
  */
  private double computeEcheanceConstante() {
    final double tauxPeriodique = getTauxPeriodique();
    double echeance = (capitalEmprunte * tauxPeriodique * Math.pow(1 + tauxPeriodique, nombreEcheancesDesire)) / (Math.pow(1 + tauxPeriodique, nombreEcheancesDesire) - 1);
    return echeance;
  }

  private void createEcheances() {
    double capitalRestantARembourser = capitalEmprunte;
    final double tauxPeriodique = getTauxPeriodique();
    if (isMensualiteHorsAssuranceFilledByUser) {
      while (capitalRestantARembourser > 0) {
        double montantInteret = capitalRestantARembourser * tauxPeriodique;
        double montantCapital = Math.min(mensualiteHorsAssurance - montantInteret, capitalRestantARembourser);
        if (montantCapital < 0) {
          return;
        }
        Echeance echeance = new Echeance(this, capitalRestantARembourser, montantCapital, montantInteret);
        capitalRestantARembourser -= montantCapital;
        echeances.add(echeance);
      }
    } else if (isNombreEcheancesFilledByUser) {
      if (mensualiteHorsAssurance == null) {
        mensualiteHorsAssurance = computeEcheanceConstante();
      }
      for (int i = 0; i < nombreEcheancesDesire; i++) {
        double montantInteret = capitalRestantARembourser * tauxPeriodique;
        double montantCapital = mensualiteHorsAssurance - montantInteret;
        Echeance echeance = new Echeance(this, capitalRestantARembourser, montantCapital, montantInteret);
        capitalRestantARembourser -= montantCapital;
        echeances.add(echeance);
      }
    } else {
      // Bad logic
    }
  }

  public double getMontantTotalInterets() {
    double montantTotalInterets = 0;
    for (Echeance echeance : echeances) {
      montantTotalInterets += echeance.getMontantInteret();
    }
    return montantTotalInterets;
  }

  public double getMontantTotalAssurance() {
    double montantTotalAssurance = 0;
    for (Echeance echeance : echeances) {
      montantTotalAssurance += echeance.getMontantAssurance();
    }
    return montantTotalAssurance;
  }

  public double getCapitalEmprunte() {
    return capitalEmprunte;
  }

  public double getTauxAnnuel() {
    return tauxAnnuel;
  }

  public double getTauxPeriodique() {
    return tauxAnnuel / NOMBRE_ECHEANCES_PAR_AN;
  }

  public List<Echeance> getEcheances() {
    return echeances;
  }

  public void modifyTauxAnnuel(double tauxAnnuel) {
    this.tauxAnnuel = tauxAnnuel;
    resetComputedValues();
    createEcheances();
  }

  public void modifyCapitalEmprunte(double capitalEmprunte) {
    this.capitalEmprunte = capitalEmprunte;
    resetComputedValues();
    createEcheances();
  }

  public void modifyMensualiteHorsAssurance(double mensualiteHorsAssurance) {
    resetComputedValues();
    this.mensualiteHorsAssurance = mensualiteHorsAssurance;
    isMensualiteHorsAssuranceFilledByUser = true;
    isNombreEcheancesFilledByUser = false;
    createEcheances();
  }

  public void modifyAssurancesMensuelles(double assurancesMensuelles) {
    this.assurancesMensuelles = assurancesMensuelles;
    updateAssurancesInEcheances();
  }

  private void updateAssurancesInEcheances() {
    for (Echeance echeance : echeances) {
      echeance.setMontantAssurance(assurancesMensuelles);
    }
  }

  public void modifyNombreEcheancesDesire(int nombreEcheancesDesire) {
    resetComputedValues();
    this.nombreEcheancesDesire = nombreEcheancesDesire;
    isMensualiteHorsAssuranceFilledByUser = false;
    isNombreEcheancesFilledByUser = true;
    createEcheances();
  }

  public boolean isNombreEcheancesFilled() {
    return isNombreEcheancesFilledByUser;
  }

  public boolean isMensualiteHorsAssuranceFilled() {
    return isMensualiteHorsAssuranceFilledByUser;
  }

  public Double getMensualiteHorsAssurance() {
    return mensualiteHorsAssurance;
  }

  public int getActualNombreEcheances() {
    return echeances.size();
  }

  private void resetComputedValues() {
    echeances.clear();
    if (!isMensualiteHorsAssuranceFilledByUser) {
      mensualiteHorsAssurance = null;
    }
    if (!isNombreEcheancesFilledByUser) {
      nombreEcheancesDesire = null;
    }
  }

}
