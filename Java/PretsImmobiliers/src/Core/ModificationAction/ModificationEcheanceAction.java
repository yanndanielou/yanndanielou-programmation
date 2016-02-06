/*
 * Controlguide
 * Copyright (c) Siemens AG 2016, All Rights Reserved, Confidential
 */
package Core.ModificationAction;

import model.Echeance;
import model.EcheanceProperties;

public abstract class ModificationEcheanceAction {
  protected Echeance echeance;
  private boolean ponctuel;

  public ModificationEcheanceAction(boolean ponctuel, Echeance echeance) {
    this.ponctuel = ponctuel;
    this.echeance = echeance;
  }

  public Echeance getEcheance() {
    return echeance;
  }

  public boolean isPonctuel() {
    return ponctuel;
  }

  public abstract EcheanceProperties createEcheanceRecalee();
}
