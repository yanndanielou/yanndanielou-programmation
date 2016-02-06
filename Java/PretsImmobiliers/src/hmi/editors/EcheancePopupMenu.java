/*
 * Controlguide
 * Copyright (c) Siemens AG 2016, All Rights Reserved, Confidential
 */
package hmi.editors;

import javax.swing.JMenu;

import model.Echeance;

public class EcheancePopupMenu extends JMenu {

  private static final long serialVersionUID = 980990651567443340L;
  private Echeance echeance;

  private ChoixAugmentationMenuItem augmenter10Pourcents;
  private ChoixDoublementPonctuelEcheanceMenuItem doublementPonctuelEcheanceMenuItem;

  public EcheancePopupMenu(Echeance echeance) {
    super(echeance.getEmprunt().getName());
    this.echeance = echeance;
    addActionListener(new EcheancePopupMenuListener());

    augmenter10Pourcents = new ChoixAugmentationMenuItem(10, echeance);
    add(augmenter10Pourcents);
    
    doublementPonctuelEcheanceMenuItem = new ChoixDoublementPonctuelEcheanceMenuItem( echeance);
    add(doublementPonctuelEcheanceMenuItem);
  }
}
