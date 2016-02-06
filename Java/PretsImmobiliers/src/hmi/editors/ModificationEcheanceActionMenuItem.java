/*
 * Controlguide
 * Copyright (c) Siemens AG 2016, All Rights Reserved, Confidential
 */
package hmi.editors;

import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

import model.Echeance;

public abstract class ModificationEcheanceActionMenuItem extends JMenuItem implements ActionListener {

  private static final long serialVersionUID = 5750467627135245425L;

  protected Echeance echeance;

  public ModificationEcheanceActionMenuItem(String name, Echeance echeance) {
    super(name);
    this.echeance = echeance;
    addActionListener(this);
  }
}