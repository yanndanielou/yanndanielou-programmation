/*
 * Controlguide
 * Copyright (c) Siemens AG 2016, All Rights Reserved, Confidential
 */
package Core.ModificationAction;

import model.Echeance;

public abstract class ModificationEcheanceAction {
  protected Echeance echeance;

  public void setEcheance(Echeance echeance) {
    this.echeance = echeance;
  }

  public Echeance getEcheance() {
    return echeance;
  }
}
