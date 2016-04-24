package model;

import hmi.LoanViewsMediator;

import java.util.ArrayList;
import java.util.List;

import Core.EcheancesCalculateur;
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

  private void createEcheances() {
    // A simplifier. calculer ici l'échéance constante si le nombre de mensualité a été entré par l'utilisateur
    Double mensualiteHorsAssuranceFixed = isMensualiteHorsAssuranceFilledByUser ? mensualiteHorsAssurance : null;
    Integer nombreEcheancesFixed = isNombreEcheancesFilledByUser ? nombreEcheancesDesire : null;
    echeances = EcheancesCalculateur.computeEcheances(this, capitalEmprunte, mensualiteHorsAssuranceFixed, nombreEcheancesFixed, getTauxPeriodique());

    if (!isMensualiteHorsAssuranceFilledByUser) {
      if (!echeances.isEmpty()) {
        mensualiteHorsAssurance = echeances.get(0).getEcheanceInitiale().getMensualiteHorsAssurance();
      }
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

  public double getMontantTotalMensualitesHorsAssuranceInitial() {
    double montantTotalMensualitesHorsAssurance = 0;
    for (Echeance echeance : echeances) {
      if (echeance.hasEcheanceInitiale()) {
        montantTotalMensualitesHorsAssurance += echeance.getEcheanceInitiale().getMensualiteHorsAssurance();
      }
    }
    return montantTotalMensualitesHorsAssurance;
  }

  public double getMontantTotalMensualitesHorsAssuranceRecale() {
    double montantTotalMensualitesHorsAssurance = 0;
    for (Echeance echeance : echeances) {
      if (echeance.hasEcheanceRecalee()) {
        montantTotalMensualitesHorsAssurance += echeance.getEcheanceRecalee().getMensualiteHorsAssurance();
      }
    }
    return montantTotalMensualitesHorsAssurance;
  }

  public double getMontantTotalAssuranceInitial() {
    double montantTotalAssurance = 0;
    for (Echeance echeance : echeances) {
      montantTotalAssurance += echeance.getEcheanceInitiale().getMontantAssurance();
    }
    return montantTotalAssurance;
  }

  public boolean hasEcheanceRecalee() {
    for (Echeance echeance : echeances) {
      if (echeance.hasEcheanceRecalee()) {
        return true;
      }
    }
    return false;
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
    modificationEcheanceAction.updateSubsequentEcheancesRecalees(modificationEcheanceAction, echeanceWithActionApplied);
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
        echeanceRecalee.setCapitalRestantARembourserAvantEcheance(echeanceInitiale.getCapitalRestantARembourserAvantEcheance());
        echeanceRecalee.setMontantCapital(echeanceInitiale.getMontantCapital());
        echeanceRecalee.setMontantAssurance(echeanceInitiale.getMontantAssurance());
        echeance.setEcheanceRecalee(echeanceRecalee);
      }
    }

  }

}
