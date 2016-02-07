package model;

import hmi.LoanViewsMediator;

import java.util.ArrayList;
import java.util.List;

import Core.ModificationAction.ModificationEcheanceAction;

public class Emprunt {

  public static final int NOMBRE_ECHEANCES_PAR_AN = 12;

  private double tauxAnnuel;
  private double capitalEmprunte;

  private String name;
  private boolean isMensualiteHorsAssuranceFilledByUser = false;
  private Double mensualiteHorsAssurance;
  private Double assurancesMensuelles;
  private boolean isNombreEcheancesFilledByUser = false;
  private Integer nombreEcheancesDesire;
  private List<Echeance> echeances = new ArrayList<Echeance>();

  public Emprunt(String name) {
    this.name = name;
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
        Echeance echeance = new Echeance(this, capitalRestantARembourser, montantCapital);
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
        Echeance echeance = new Echeance(this, capitalRestantARembourser, montantCapital);
        capitalRestantARembourser -= montantCapital;
        echeances.add(echeance);
      }
    } else {
      // Bad logic
    }
  }

  public double getMontantTotalInteretsInitial() {
    double montantTotalInterets = 0;
    for (Echeance echeance : echeances) {
      if (echeance.hasEcheanceInitiale()) {
        montantTotalInterets += echeance.getEcheanceInitiale().getMontantInteret();
      }
    }
    return montantTotalInterets;
  }

  public double getMontantTotalInteretsRecale() {
    double montantTotalInterets = 0;
    for (Echeance echeance : echeances) {
      if (echeance.hasEcheanceRecalee()) {
        montantTotalInterets += echeance.getEcheanceRecalee().getMontantInteret();
      }
    }
    return montantTotalInterets;
  }

  public double getMontantTotalCapitalRembourseInitial() {
    double montantTotalCapitalRembourse = 0;
    for (Echeance echeance : echeances) {
      if (echeance.hasEcheanceInitiale()) {
        montantTotalCapitalRembourse += echeance.getEcheanceInitiale().getMontantCapital();
      }
    }
    return montantTotalCapitalRembourse;
  }

  public double getMontantTotalCapitalRembourseRecale() {
    double montantTotalCapitalRembourse = 0;
    for (Echeance echeance : echeances) {
      if (echeance.hasEcheanceRecalee()) {
        montantTotalCapitalRembourse += echeance.getEcheanceRecalee().getMontantCapital();
      }
    }
    return montantTotalCapitalRembourse;
  }

  public double getMontantTotalAssuranceInitial() {
    double montantTotalAssurance = 0;
    for (Echeance echeance : echeances) {
      montantTotalAssurance += echeance.getEcheanceInitiale().getMontantAssurance();
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
      echeance.getEcheanceInitiale().setMontantAssurance(assurancesMensuelles);
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

  public String getName() {
    return name;
  }

  public void modifyName(String name) {
    this.name = name;
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

  public void applyModificationEcheanceAction(ModificationEcheanceAction modificationEcheanceAction, Echeance echeanceWithActionApplied) {
    copyEcheanceInitialeToEcheancesRecaleesForEcheancesPriorToThisAction(echeanceWithActionApplied);
    echeanceWithActionApplied.applyAction(modificationEcheanceAction);
    updateSubsequentEcheancesRecalees(modificationEcheanceAction, echeanceWithActionApplied);
    LoanViewsMediator.getInstance().onModificationEcheanceActionPerformed();
  }

  private void copyEcheanceInitialeToEcheancesRecaleesForEcheancesPriorToThisAction(Echeance echeanceWithActionApplied) {

    for (Echeance echeance : echeances) {
      if (echeance == echeanceWithActionApplied) {
        return;
      }
      if (!echeance.hasEcheanceRecalee()) {
        EcheanceProperties echeanceInitiale = echeance.getEcheanceInitiale();
        EcheanceProperties echeanceRecalee = new EcheanceProperties(echeance);
        echeanceRecalee.setCapitalRestantARembourser(echeanceInitiale.getCapitalRestantARembourser());
        echeanceRecalee.setMontantCapital(echeanceInitiale.getMontantCapital());
        echeanceRecalee.setMontantAssurance(echeanceInitiale.getMontantAssurance());
        echeance.setEcheanceRecalee(echeanceRecalee);
      }
    }

  }

  private void updateSubsequentEcheancesRecalees(ModificationEcheanceAction modificationEcheanceAction, Echeance echeanceWithActionApplied) {
    int indexEcheanceAvecAction = echeances.indexOf(echeanceWithActionApplied);
    EcheanceProperties echeanceRecaleeWithActionApplied = echeanceWithActionApplied.getEcheanceRecalee();

    Echeance previousEcheance = echeanceWithActionApplied;
    for (int i = indexEcheanceAvecAction + 1; i < echeances.size(); i++) {
      Echeance echeanceARecaler = echeances.get(i);
      EcheanceProperties echeanceRecalee = new EcheanceProperties(echeanceARecaler);
      EcheanceProperties previousEcheanceRecalee = previousEcheance.getEcheanceRecalee();
      double capitalRestantARembourser = previousEcheanceRecalee.getCapitalRestantARembourser() - previousEcheanceRecalee.getMontantCapital();
      if (capitalRestantARembourser >= previousEcheanceRecalee.getMontantCapital()) {
        echeanceRecalee.setCapitalRestantARembourser(capitalRestantARembourser);
        double mensualiteHorsAssurance = modificationEcheanceAction.isPonctuel() ? echeanceARecaler.getEcheanceReferenceBeforeRecalage().getMensualiteHorsAssurance() : echeanceRecaleeWithActionApplied.getMensualiteHorsAssurance();
        echeanceRecalee.setMontantCapital(mensualiteHorsAssurance - echeanceRecalee.getMontantInteret());
        echeanceRecalee.setMontantAssurance(echeanceRecaleeWithActionApplied.getMontantAssurance());
        echeanceARecaler.setEcheanceRecalee(echeanceRecalee);
        previousEcheance = echeanceARecaler;
      } else {
        echeanceRecalee.setCapitalRestantARembourser(capitalRestantARembourser);
        echeanceRecalee.setMontantCapital(capitalRestantARembourser - echeanceRecalee.getMontantInteret());
        echeanceRecalee.setMontantAssurance(echeanceRecaleeWithActionApplied.getMontantAssurance());
        echeanceARecaler.setEcheanceRecalee(echeanceRecalee);

        deleteNextEcheanceRecaleesBecauseEverythingIsPaidBack(echeanceRecalee);
        return;
      }
    }
  }

  private void deleteNextEcheanceRecaleesBecauseEverythingIsPaidBack(EcheanceProperties lastEcheanceRecalee) {
    Echeance lastEcheanceAvecRecalage = lastEcheanceRecalee.getEcheance();
    int indexEcheanceAvecAction = echeances.indexOf(lastEcheanceAvecRecalage);
    for (int i = indexEcheanceAvecAction + 1; i < echeances.size(); i++) {
      Echeance echeanceASupprimerLeRecalage = echeances.get(i);
      echeanceASupprimerLeRecalage.deleteEcheanceRecalee();
    }
  }
}
