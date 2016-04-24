package model;

import Core.ModificationAction.ModificationEcheanceAction;
import Core.ModificationAction.NoOperationAction;

public class Echeance {
  private Emprunt emprunt;

  private EcheanceProperties echeanceInitiale;
  private EcheanceProperties echeanceRecalee;

  private ModificationEcheanceAction modificationEcheanceAction = null;

  public Echeance(Emprunt emprunt, double capitalRestantARembourser, double montantCapital) {
    this.emprunt = emprunt;
    echeanceInitiale = new EcheanceProperties(this);
    echeanceInitiale.setMontantCapital(montantCapital);
    echeanceInitiale.setCapitalRestantARembourserAvantEcheance(capitalRestantARembourser);
  }

  public Echeance(Emprunt emprunt, EcheanceProperties echeanceInitiale) {
    this.emprunt = emprunt;
    this.echeanceInitiale = echeanceInitiale;
    echeanceInitiale.setEcheance(this);
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

  public boolean hasEcheanceInitiale() {
    return echeanceInitiale != null;
  }

  public EcheanceProperties getEcheanceRecalee() {
    return echeanceRecalee;
  }

  public EcheanceProperties getEcheanceReferenceBeforeRecalage() {
    return hasEcheanceRecalee() ? getEcheanceRecalee() : getEcheanceInitiale();
  }

  public void setEcheanceRecalee(EcheanceProperties echeanceRecalee) {
    this.echeanceRecalee = echeanceRecalee;
  }

  public void deleteEcheanceRecalee() {
    setEcheanceRecalee(null);
    modificationEcheanceAction = new NoOperationAction(this);
  }

  public Emprunt getEmprunt() {
    return emprunt;
  }
}
