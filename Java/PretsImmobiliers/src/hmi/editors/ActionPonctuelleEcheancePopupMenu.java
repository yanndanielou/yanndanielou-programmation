/*
 * Controlguide
 * Copyright (c) Siemens AG 2016, All Rights Reserved, Confidential
 */
package hmi.editors;

import javax.swing.JMenu;

import model.Echeance;

public class ActionPonctuelleEcheancePopupMenu extends JMenu {

  private static final long serialVersionUID = 980990651567443340L;
  private Echeance echeance;

  private ChoixDoublementPonctuelEcheanceMenuItem doublementPonctuelEcheanceMenuItem;

  public ActionPonctuelleEcheancePopupMenu(Echeance echeance) {
    super("Action ponctuelle");
    this.echeance = echeance;
    addActionListener(new EcheancePopupMenuListener());

    doublementPonctuelEcheanceMenuItem = new ChoixDoublementPonctuelEcheanceMenuItem(echeance);
    add(doublementPonctuelEcheanceMenuItem);
  }
}
