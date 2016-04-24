package Core;

import java.util.ArrayList;
import java.util.List;

import Core.ModificationAction.ModificationEcheanceAction;
import model.Echeance;
import model.EcheanceProperties;
import model.Emprunt;

public class EcheancesCalculateur {

  private EcheancesCalculateur() {
  }

  public static void deleteNextEcheanceRecaleesBecauseEverythingIsPaidBack(EcheanceProperties lastEcheanceRecalee) {
    Echeance echeance = lastEcheanceRecalee.getEcheance();
    Emprunt emprunt = echeance.getEmprunt();
    List<Echeance> echeances = emprunt.getEcheances();

    Echeance lastEcheanceAvecRecalage = echeance;
    int indexEcheanceAvecAction = echeances.indexOf(lastEcheanceAvecRecalage);
    for (int i = indexEcheanceAvecAction + 1; i < echeances.size(); i++) {
      Echeance echeanceASupprimerLeRecalage = echeances.get(i);
      echeanceASupprimerLeRecalage.deleteEcheanceRecalee();
    }
  }

  public static List<Echeance> computeEcheances(Emprunt emprunt, double capitalEmprunte, Double mensualiteHorsAssuranceFixed, Integer nombreEcheancesFixed, double tauxPeriodique) {
    List<Echeance> echeances = new ArrayList<>();
    List<EcheanceProperties> echeancesProperties = computeEcheancesProperties(capitalEmprunte, mensualiteHorsAssuranceFixed, nombreEcheancesFixed, tauxPeriodique);
    if (echeancesProperties != null) {
      for (EcheanceProperties echeanceProperties : echeancesProperties) {
        Echeance echeance = new Echeance(emprunt, echeanceProperties);
        echeances.add(echeance);
      }
    }
    return echeances;
  }

  public static List<EcheanceProperties> computeEcheancesProperties(double capitalEmprunte, Double mensualiteHorsAssuranceFixed, Integer nombreEcheancesFixed, double tauxPeriodique) {
    List<EcheanceProperties> echeancesProperties = new ArrayList<>();

    double capitalRestantARembourser = capitalEmprunte;
    if (mensualiteHorsAssuranceFixed != null) {
      double mensualiteHorsAssurance = mensualiteHorsAssuranceFixed;
      while (capitalRestantARembourser > 0) {
        double montantInteret = capitalRestantARembourser * tauxPeriodique;
        double montantCapital = Math.min(mensualiteHorsAssurance - montantInteret, capitalRestantARembourser);
        if (montantCapital < 0) {
          return null;
        }
        EcheanceProperties echeanceProperties = new EcheanceProperties();
        echeanceProperties.setCapitalRestantARembourserAvantEcheance(capitalRestantARembourser);
        echeanceProperties.setMontantCapital(montantCapital);
        capitalRestantARembourser -= montantCapital;
        echeancesProperties.add(echeanceProperties);
      }
    } else if (nombreEcheancesFixed != null) {
      int nombreEcheances = nombreEcheancesFixed;
      double mensualiteHorsAssurance = computeEcheanceConstante(capitalEmprunte, nombreEcheances, tauxPeriodique);
      for (int i = 0; i < nombreEcheances; i++) {
        double montantInteret = capitalRestantARembourser * tauxPeriodique;
        double montantCapital = mensualiteHorsAssurance - montantInteret;
        EcheanceProperties echeanceProperties = new EcheanceProperties();
        echeanceProperties.setCapitalRestantARembourserAvantEcheance(capitalRestantARembourser);
        echeanceProperties.setMontantCapital(montantCapital);
        capitalRestantARembourser -= montantCapital;
        echeancesProperties.add(echeanceProperties);
      }
    } else {
      // Bad logic
    }

    return echeancesProperties;
  }

  /**
  * Formule du calcul de l'échéance:   {@link http://www.cbanque.com/credit/principe.php}} 
  */
  public static double computeEcheanceConstante(double capitalEmprunte, int nombreEcheances, double tauxPeriodique) {
    double echeance = (capitalEmprunte * tauxPeriodique * Math.pow(1 + tauxPeriodique, nombreEcheances)) / (Math.pow(1 + tauxPeriodique, nombreEcheances) - 1);
    return echeance;
  }
}
