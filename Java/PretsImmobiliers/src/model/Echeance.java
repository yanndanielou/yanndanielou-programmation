package model;

import Core.ModificationAction.ModificationEcheanceAction;
import Core.ModificationAction.NoOperationAction;

public class Echeance {
  private Emprunt emprunt;

  private EcheanceProperties echeanceInitiale;
  private EcheanceProperties echeanceRecalee;

  private ModificationEcheanceAction modificationEcheanceAction;

  public Echeance(Emprunt emprunt, double capitalRestantARembourser, double montantCapital) {
    this.emprunt = emprunt;
    echeanceInitiale = new EcheanceProperties(this);
    echeanceInitiale.setMontantCapital(montantCapital);
    echeanceInitiale.setCapitalRestantARembourser(capitalRestantARembourser);
    modificationEcheanceAction = new NoOperationAction(this);
  }

  public void applyAction(ModificationEcheanceAction modificationEcheanceAction) {
    this.modificationEcheanceAction = modificationEcheanceAction;
    echeanceRecalee = modificationEcheanceAction.createEcheanceRecalee();
  }

  public ModificationEcheanceAction getModificationEcheanceAction() {
    return modificationEcheanceAction;
  }

  public EcheanceProperties getEcheanceInitiale() {
    return echeanceInitiale;
  }

  public boolean hasEcheanceRecalee() {
    return echeanceRecalee != null;
  }

  public EcheanceProperties getEcheanceRecalee() {
    return echeanceRecalee;
  }

  public void setEcheanceRecalee(EcheanceProperties echeanceRecalee) {
    this.echeanceRecalee = echeanceRecalee;
  }

  public Emprunt getEmprunt() {
    return emprunt;
  }
}
