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

  protected EcheanceProperties getEcheanceReference() {
    EcheanceProperties echeanceReference = echeance.hasEcheanceRecalee() ? echeance.getEcheanceRecalee() : echeance.getEcheanceInitiale();
    return echeanceReference;
  }

  public abstract EcheanceProperties createEcheanceRecalee();
}
