/*
 * Controlguide
 * Copyright (c) Siemens AG 2016, All Rights Reserved, Confidential
 */
package hmi.editors;

import hmi.EcheancesTableModel;

import javax.swing.JPopupMenu;
import javax.swing.JTable;

public class EcheancesTablePopupMenu extends JPopupMenu {

  private static final long serialVersionUID = -386683607360574896L;

  public EcheancesTablePopupMenu(JTable echeancesTable, EcheancesTableModel echeancesTableModel) {
    addPopupMenuListener(new EcheancesTablePopupMenuHandler(echeancesTable, echeancesTableModel, this));
  }

}
