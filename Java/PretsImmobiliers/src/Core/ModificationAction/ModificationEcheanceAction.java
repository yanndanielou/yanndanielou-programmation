package Core.ModificationAction;

import java.util.List;

import Core.EcheancesCalculateur;
import model.Echeance;
import model.EcheanceProperties;
import model.Emprunt;

public abstract class ModificationEcheanceAction {
  protected Echeance echeance;
  private boolean ponctuel;
  private boolean gardeNombreEcheances;
  private boolean gardeMensualite;

  public ModificationEcheanceAction(boolean ponctuel, Echeance echeance, boolean gardeNombreEcheances, boolean gardeMensualite) {
    this.ponctuel = ponctuel;
    this.echeance = echeance;
    this.gardeNombreEcheances = gardeNombreEcheances;
    this.gardeMensualite = gardeMensualite;
  }

  public Echeance getEcheance() {
    return echeance;
  }

  public boolean isPonctuel() {
    return ponctuel;
  }

  protected EcheanceProperties getEcheanceReference() {
    EcheanceProperties echeanceReference = echeance.getEcheanceReferenceBeforeRecalage();
    return echeanceReference;
  }

  public void updateSubsequentEcheancesRecalees(ModificationEcheanceAction modificationEcheanceAction, Echeance echeanceWithActionApplied) {

    EcheanceProperties echeanceReferenceBeforeRecalage = echeance.getEcheanceReferenceBeforeRecalage();
    echeance.applyAction(modificationEcheanceAction);

    Emprunt emprunt = echeanceWithActionApplied.getEmprunt();
    List<Echeance> echeances = emprunt.getEcheances();

    int indexEcheanceAvecAction = echeances.indexOf(echeanceWithActionApplied);
    EcheanceProperties echeanceRecaleeWithActionApplied = echeanceWithActionApplied.getEcheanceRecalee();

    Echeance previousEcheance = echeanceWithActionApplied;

    double nouveauCapitalRestantARembourser = echeance.getEcheanceRecalee().getCapitalRestantARembourserApresEcheance();

    if (gardeNombreEcheances) {
      List<EcheanceProperties> echeanceRecalees = EcheancesCalculateur.computeEcheancesProperties(nouveauCapitalRestantARembourser, null, emprunt.getActualNombreEcheances() - indexEcheanceAvecAction, emprunt.getTauxPeriodique());
      int indexEcheance = indexEcheanceAvecAction;
      for (EcheanceProperties echeanceRecalee : echeanceRecalees) {
        Echeance echeanceARecaler = echeances.get(indexEcheance);
        echeanceARecaler.setEcheanceRecalee(echeanceRecalee);
        echeanceRecalee.setEcheance(echeanceARecaler);
        indexEcheance++;
      }
    } else {
      if (gardeMensualite) {
        int indexEcheance = indexEcheanceAvecAction;
        List<EcheanceProperties> echeanceRecalees = EcheancesCalculateur.computeEcheancesProperties(nouveauCapitalRestantARembourser, echeanceReferenceBeforeRecalage.getMensualiteHorsAssurance(), null, emprunt.getTauxPeriodique());
        for (EcheanceProperties echeanceRecalee : echeanceRecalees) {
          indexEcheance++;
          Echeance echeanceARecaler = echeances.get(indexEcheance);
          echeanceARecaler.setEcheanceRecalee(echeanceRecalee);
          echeanceRecalee.setEcheance(echeanceARecaler);
        }
        EcheancesCalculateur.deleteNextEcheanceRecaleesBecauseEverythingIsPaidBack(emprunt.getEcheances().get(indexEcheance).getEcheanceRecalee());
      }
      else {
        for (int i = indexEcheanceAvecAction + 1; i < echeances.size(); i++) {
          Echeance echeanceARecaler = echeances.get(i);
          EcheanceProperties echeanceRecalee = new EcheanceProperties(echeanceARecaler);
          EcheanceProperties previousEcheanceRecalee = previousEcheance.getEcheanceRecalee();
          double capitalRestantARembourser = previousEcheanceRecalee.getCapitalRestantARembourserAvantEcheance() - previousEcheanceRecalee.getMontantCapital();
          if (capitalRestantARembourser >= previousEcheanceRecalee.getMontantCapital()) {
            echeanceRecalee.setCapitalRestantARembourserAvantEcheance(capitalRestantARembourser);
            double mensualiteHorsAssurance = modificationEcheanceAction.isPonctuel() ? echeanceARecaler.getEcheanceReferenceBeforeRecalage().getMensualiteHorsAssurance() : echeanceRecaleeWithActionApplied.getMensualiteHorsAssurance();
            echeanceRecalee.setMontantCapital(mensualiteHorsAssurance - echeanceRecalee.getMontantInteret());
            echeanceRecalee.setMontantAssurance(echeanceRecaleeWithActionApplied.getMontantAssurance());
            echeanceARecaler.setEcheanceRecalee(echeanceRecalee);
            previousEcheance = echeanceARecaler;
          } else {
            echeanceRecalee.setCapitalRestantARembourserAvantEcheance(capitalRestantARembourser);
            echeanceRecalee.setMontantCapital(capitalRestantARembourser - echeanceRecalee.getMontantInteret());
            echeanceRecalee.setMontantAssurance(echeanceRecaleeWithActionApplied.getMontantAssurance());
            echeanceARecaler.setEcheanceRecalee(echeanceRecalee);

            EcheancesCalculateur.deleteNextEcheanceRecaleesBecauseEverythingIsPaidBack(echeanceRecalee);
            return;
          }
        }
      }
    }

  }

  public abstract EcheanceProperties createEcheanceRecalee();
}
