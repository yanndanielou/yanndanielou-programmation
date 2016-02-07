/*
 * Controlguide
 * Copyright (c) Siemens AG 2016, All Rights Reserved, Confidential
 */
package Core.ModificationAction;

import model.Echeance;
import model.EcheanceProperties;

public class AugmentationDesEcheancesAction extends ModificationEcheanceAction {
  private int augmentationPourcentage;

  public AugmentationDesEcheancesAction(int augmentationPourcentage, Echeance echeance) {
    super(false, echeance);
    this.augmentationPourcentage = augmentationPourcentage;
  }

  @Override
  public EcheanceProperties createEcheanceRecalee() {
    EcheanceProperties echeanceReference = getEcheanceReference();
    EcheanceProperties echeanceRecalee = new EcheanceProperties(echeance);
    echeanceRecalee.setCapitalRestantARembourser(echeanceReference.getCapitalRestantARembourser());
    double mensualiteHorsAssuranceAvantAction = echeanceReference.getMensualiteHorsAssurance();
    double newMensualiteHorsAssurance = mensualiteHorsAssuranceAvantAction + mensualiteHorsAssuranceAvantAction * augmentationPourcentage / 100;
    echeanceRecalee.setMontantCapital(newMensualiteHorsAssurance - echeanceRecalee.getMontantInteret());

    // TODO: à vérifier
    echeanceRecalee.setMontantAssurance(echeanceReference.getMontantAssurance());
    return echeanceRecalee;
  }

  @Override
  public String toString() {
    return "+ " + augmentationPourcentage + "%";
  }

}
