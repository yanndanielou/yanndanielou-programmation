/*
 * Controlguide
 * Copyright (c) Siemens AG 2016, All Rights Reserved, Confidential
 */
package Core.ModificationAction;

import model.Echeance;
import model.EcheanceProperties;

public class DoublementPonctuelEcheanceAction extends ModificationEcheanceAction {

  public DoublementPonctuelEcheanceAction(Echeance echeance) {
    super(true, echeance);
  }

  @Override
  public String toString() {
    return "Double";
  }

  @Override
  public EcheanceProperties createEcheanceRecalee() {
    EcheanceProperties echeanceReference = getEcheanceReference();
    EcheanceProperties echeanceRecalee = new EcheanceProperties(echeance);
    echeanceRecalee.setMontantAssurance(echeanceReference.getMontantAssurance() * 2);
    echeanceRecalee.setMontantCapital(echeanceReference.getMontantCapital() * 2);
    //TODO: à vérifier
    echeanceRecalee.setCapitalRestantARembourser(echeanceReference.getCapitalRestantARembourser());
    return echeanceRecalee;
  }
}
