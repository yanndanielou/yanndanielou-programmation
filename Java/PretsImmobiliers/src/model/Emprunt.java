package model;

import java.util.ArrayList;
import java.util.List;

public class Emprunt {

  public static final int NOMBRE_ECHEANCES_PAR_AN = 12;

  private double tauxAnnuel;
  private double tauxPeriodique;
  private double capitalEmprunte;
  private double mensualiteHorsAssurance;
  private List<Echeance> echeances = new ArrayList<Echeance>();

  public Emprunt(double capitalEmprunte, double tauxAnnuel, double mensualiteHorsAssurance) {
    this(capitalEmprunte, tauxAnnuel);
    this.mensualiteHorsAssurance = mensualiteHorsAssurance;
    createEcheances();
  }

  public Emprunt(double capitalEmprunte, double tauxAnnuel, int nombreEcheances) {
    this(capitalEmprunte, tauxAnnuel);
    mensualiteHorsAssurance = computeEcheanceConstante(nombreEcheances);
    createEcheances(nombreEcheances);
  }

  public Emprunt(Double capitalEmprunte, Double tauxAnnuel) {
    this.capitalEmprunte = capitalEmprunte;
    this.tauxAnnuel = tauxAnnuel;
    tauxPeriodique = tauxAnnuel / NOMBRE_ECHEANCES_PAR_AN;
  }

  /**
  * Formule du calcul de l'échéance:   {@link http://www.cbanque.com/credit/principe.php}} 
  */
  private double computeEcheanceConstante(int nombreEcheances) {
    double echeance = (capitalEmprunte * tauxPeriodique * Math.pow(1 + tauxPeriodique, nombreEcheances)) / (Math.pow(1 + tauxPeriodique, nombreEcheances) - 1);
    return echeance;
  }

  private void createEcheances() {
    double capitalRestantARembourser = capitalEmprunte;
    while (capitalRestantARembourser > 0) {
      double montantInteret = capitalRestantARembourser * tauxPeriodique;
      double montantCapital = Math.min(mensualiteHorsAssurance - montantInteret, capitalRestantARembourser);
      Echeance echeance = new Echeance(this, capitalRestantARembourser, montantCapital, montantInteret);
      capitalRestantARembourser -= montantCapital;
      echeances.add(echeance);
    }

  }

  private void createEcheances(int nombreEcheances) {
    double capitalRestantARembourser = capitalEmprunte;
    for (int i = 0; i < nombreEcheances; i++) {
      double montantInteret = capitalRestantARembourser * tauxPeriodique;
      double montantCapital = mensualiteHorsAssurance - montantInteret;
      Echeance echeance = new Echeance(this, capitalRestantARembourser, montantCapital, montantInteret);
      capitalRestantARembourser -= montantCapital;
      echeances.add(echeance);
    }
  }

  public double getMontantTotalInterets() {
    double montantTotalInterets = 0;
    for (Echeance echeance : echeances) {
      montantTotalInterets += echeance.getMontantInteret();
    }
    return montantTotalInterets;
  }

  public double getCapitalEmprunte() {
    return capitalEmprunte;
  }

  public Double getTauxAnnuel() {
    return tauxAnnuel;
  }

  public List<Echeance> getEcheances() {
    return echeances;
  }
}
