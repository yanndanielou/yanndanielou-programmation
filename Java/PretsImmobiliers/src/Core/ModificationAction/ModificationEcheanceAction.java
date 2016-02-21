package Core.ModificationAction;

import java.util.List;

import model.Echeance;
import model.EcheanceProperties;
import model.Emprunt;

public abstract class ModificationEcheanceAction {
  protected Echeance echeance;
  private boolean ponctuel;

  public ModificationEcheanceAction(boolean ponctuel, Echeance echeance) {
    this.ponctuel = ponctuel;
    this.echeance = echeance;
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
    Emprunt emprunt = echeanceWithActionApplied.getEmprunt();
    List<Echeance> echeances = emprunt.getEcheances();

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

  protected void deleteNextEcheanceRecaleesBecauseEverythingIsPaidBack(EcheanceProperties lastEcheanceRecalee) {
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

  public abstract EcheanceProperties createEcheanceRecalee();
}
