/*
 * Controlguide
 * Copyright (c) Siemens AG 2016, All Rights Reserved, Confidential
 */
package Core.ModificationAction;

import model.Echeance;
import model.EcheanceProperties;

public class AugmentationDesEcheancesAction extends ChangementDefinitifDesEcheancesAction {
  private int augmentationPourcentage;

  public AugmentationDesEcheancesAction(int augmentationPourcentage, Echeance echeance) {
    super(echeance);
    this.augmentationPourcentage = augmentationPourcentage;
  }

  @Override
  public EcheanceProperties createEcheanceRecalee() {
    EcheanceProperties echeanceReference = getEcheanceReference();
    EcheanceProperties echeanceRecalee = new EcheanceProperties(echeance);
    // TODO: à vérifier
    echeanceRecalee.setMontantAssurance(echeanceReference.getMontantAssurance());
    double montantCapitalInitial = echeanceReference.getMontantCapital();
    double montantCapital = montantCapitalInitial + montantCapitalInitial * augmentationPourcentage / 100;
    echeanceRecalee.setMontantCapital(montantCapital);
    //TODO: à vérifier
    echeanceRecalee.setCapitalRestantARembourser(echeanceReference.getCapitalRestantARembourser());

    return echeanceRecalee;
  }

  @Override
  public String toString() {
    return "+ " + augmentationPourcentage + "%";
  }

}
