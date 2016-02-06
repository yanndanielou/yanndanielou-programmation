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
    EcheanceProperties echeanceInitiale = echeance.getEcheanceInitiale();
    EcheanceProperties echeanceRecalee = new EcheanceProperties();
    // TODO: à vérifier
    echeanceRecalee.setMontantAssurance(echeanceInitiale.getMontantAssurance());
    double montantCapitalInitial = echeanceInitiale.getMontantCapital();
    double montantCapital = montantCapitalInitial + montantCapitalInitial * augmentationPourcentage / 100;
    echeanceRecalee.setMontantCapital(montantCapital);
    //TODO: à vérifier
    echeanceRecalee.setMontantInteret(echeanceInitiale.getMontantInteret());
    //FIXME
    echeanceRecalee.setCapitalRestantARembourser(echeanceInitiale.getCapitalRestantARembourser());

    return echeanceRecalee;
  }

  @Override
  public String toString() {
    return "Augmentation :" + augmentationPourcentage + "%";
  }

}
