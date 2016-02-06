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
    EcheanceProperties echeanceInitiale = echeance.getEcheanceInitiale();
    EcheanceProperties echeanceRecalee = new EcheanceProperties();
    echeanceRecalee.setMontantAssurance(echeanceInitiale.getMontantAssurance() * 2);
    echeanceRecalee.setMontantCapital(echeanceInitiale.getMontantCapital() * 2);
    //TODO: à vérifier
    echeanceRecalee.setMontantInteret(echeanceInitiale.getMontantInteret());

    //FIXME
    echeanceRecalee.setCapitalRestantARembourser(echeanceInitiale.getCapitalRestantARembourser());
    return echeanceRecalee;
  }
}
