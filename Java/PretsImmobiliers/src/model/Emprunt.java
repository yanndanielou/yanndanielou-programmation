package model;

import java.util.ArrayList;
import java.util.List;

public class Emprunt {

  public static final int NOMBRE_ECHEANCES_PAR_AN = 12;

  private double tauxAnnuel;
  private double capitalEmprunte;

  private boolean isMensualiteHorsAssuranceFilled = false;
  private Double mensualiteHorsAssurance;
  private boolean isNombreEcheancesFilled = false;
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
    if (isMensualiteHorsAssuranceFilled) {
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
    } else if (isNombreEcheancesFilled) {
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
    isMensualiteHorsAssuranceFilled = true;
    isNombreEcheancesFilled = false;
    createEcheances();
  }

  public void modifyNombreEcheancesDesire(int nombreEcheancesDesire) {
    resetComputedValues();
    this.nombreEcheancesDesire = nombreEcheancesDesire;
    mensualiteHorsAssurance = computeEcheanceConstante();
    isMensualiteHorsAssuranceFilled = false;
    isNombreEcheancesFilled = true;
    createEcheances();
  }

  public boolean isNombreEcheancesFilled() {
    return isNombreEcheancesFilled;
  }

  public boolean isMensualiteHorsAssuranceFilled() {
    return isMensualiteHorsAssuranceFilled;
  }

  public Double getMensualiteHorsAssurance() {
    return mensualiteHorsAssurance;
  }

  public int getActualNombreEcheances() {
    return echeances.size();
  }

  private void resetComputedValues() {
    echeances.clear();
    if (!isMensualiteHorsAssuranceFilled) {
      mensualiteHorsAssurance = null;
    }
    if (!isNombreEcheancesFilled) {
      nombreEcheancesDesire = null;
    }
  }
}
