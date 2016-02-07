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
    double mensualiteHorsAssuranceAvantAction = echeanceReference.getMensualiteHorsAssurance();
    EcheanceProperties echeanceRecalee = new EcheanceProperties(echeance);
    echeanceRecalee.setCapitalRestantARembourser(echeanceReference.getCapitalRestantARembourser());
    echeanceRecalee.setMontantCapital(mensualiteHorsAssuranceAvantAction * 2 - echeanceRecalee.getMontantInteret());
    // TODO: à vérifier
    echeanceRecalee.setMontantAssurance(echeanceReference.getMontantAssurance() * 2);
    return echeanceRecalee;
  }
}
