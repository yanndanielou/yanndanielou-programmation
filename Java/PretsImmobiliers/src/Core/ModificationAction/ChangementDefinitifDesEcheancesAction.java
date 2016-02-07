/*
 * Controlguide
 * Copyright (c) Siemens AG 2016, All Rights Reserved, Confidential
 */
package Core.ModificationAction;

import model.Echeance;

public abstract class ChangementDefinitifDesEcheancesAction extends ModificationEcheanceAction {

  public ChangementDefinitifDesEcheancesAction(Echeance echeance) {
    super(false, echeance);
  }

}
