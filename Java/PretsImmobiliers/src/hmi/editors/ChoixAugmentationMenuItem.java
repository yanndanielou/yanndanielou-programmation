/*
 * Controlguide
 * Copyright (c) Siemens AG 2016, All Rights Reserved, Confidential
 */
package hmi.editors;

import model.Echeance;
import Core.ModificationAction.AugmentationDesEcheancesAction;

public class ChoixAugmentationMenuItem extends ModificationEcheanceActionMenuItem {

  private static final long serialVersionUID = -978794474604856189L;

  public ChoixAugmentationMenuItem(int pourcentage, Echeance echeance) {
    super("+ " + String.valueOf(pourcentage) + " %", echeance);
    modificationEcheanceAction = new AugmentationDesEcheancesAction(pourcentage, echeance);
  }

}