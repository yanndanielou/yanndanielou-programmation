/*
 * Controlguide
 * Copyright (c) Siemens AG 2016, All Rights Reserved, Confidential
 */
package hmi.editors;

import javax.swing.JMenuItem;

import model.Echeance;

public class EcheancePopupMenuItem extends JMenuItem {

  private static final long serialVersionUID = 980990651567443340L;
  private Echeance echeance;

  public EcheancePopupMenuItem(Echeance echeance) {
    super(echeance.getEmprunt().getName());
    this.echeance = echeance;
    addActionListener(new EcheancePopupMenuListener());
  }
}
