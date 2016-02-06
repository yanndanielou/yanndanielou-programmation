/*
 * Controlguide
 * Copyright (c) Siemens AG 2016, All Rights Reserved, Confidential
 */
package hmi.editors;

import java.awt.event.ActionEvent;

import model.Echeance;
import Core.ModificationAction.AugmentationDesEcheancesAction;

public class ChoixAugmentationMenuItem extends ModificationEcheanceActionMenuItem {

  private static final long serialVersionUID = -978794474604856189L;

  private AugmentationDesEcheancesAction augmentationDesEcheancesAction;

  public ChoixAugmentationMenuItem(int pourcentage, Echeance echeance) {
    super("+ " + String.valueOf(pourcentage) + " %", echeance);
    augmentationDesEcheancesAction = new AugmentationDesEcheancesAction(pourcentage);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    echeance.setModificationEcheanceAction(augmentationDesEcheancesAction);
  }
}