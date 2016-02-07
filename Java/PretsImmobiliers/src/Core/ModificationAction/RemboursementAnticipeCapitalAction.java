/*
 * Controlguide
 * Copyright (c) Siemens AG 2016, All Rights Reserved, Confidential
 */
package Core.ModificationAction;

import java.text.NumberFormat;

import model.Echeance;
import model.EcheanceProperties;

public class RemboursementAnticipeCapitalAction extends ModificationEcheanceAction {
  private int capitalRembourse;

  public RemboursementAnticipeCapitalAction(int capitalRembourse, Echeance echeance) {
    super(true, echeance);
    this.capitalRembourse = capitalRembourse;
  }

  @Override
  public String toString() {
    return NumberFormat.getIntegerInstance().format(capitalRembourse) + " €";
  }

  @Override
  public EcheanceProperties createEcheanceRecalee() {
    EcheanceProperties echeanceReference = getEcheanceReference();
    double mensualiteHorsAssuranceAvantAction = echeanceReference.getMensualiteHorsAssurance();
    EcheanceProperties echeanceRecalee = new EcheanceProperties(echeance);
    echeanceRecalee.setCapitalRestantARembourser(echeanceReference.getCapitalRestantARembourser());
    echeanceRecalee.setMontantCapital(mensualiteHorsAssuranceAvantAction + capitalRembourse - echeanceRecalee.getMontantInteret());
    // TODO: à vérifier
    echeanceRecalee.setMontantAssurance(echeanceReference.getMontantAssurance() * 2);
    return echeanceRecalee;
  }
}
