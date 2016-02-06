/*
 * Controlguide
 * Copyright (c) Siemens AG 2016, All Rights Reserved, Confidential
 */
package hmi.editors;

import hmi.EcheancesTableModel;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

import model.Echeance;
import model.ProjetImmobilier;

public class EcheancesTablePopupMenuHandler implements PopupMenuListener {

  private ProjetImmobilier projetImmobilier;
  private JTable echeancesTable;
  private EcheancesTableModel echeancesTableModel;
  private EcheancesTablePopupMenu popupMenu;
  private List<EcheancePopupMenuItem> popupMenuItems = new ArrayList<EcheancePopupMenuItem>();

  public EcheancesTablePopupMenuHandler(JTable echeancesTable, EcheancesTableModel echeancesTableModel, EcheancesTablePopupMenu popupMenu) {
    this.echeancesTable = echeancesTable;
    this.echeancesTableModel = echeancesTableModel;
    this.popupMenu = popupMenu;
    this.projetImmobilier = ProjetImmobilier.getInstance();
  }

  private void deleteMenuItems() {
    for (EcheancePopupMenuItem popupMenuItem : popupMenuItems) {
      popupMenu.remove(popupMenuItem);
    }
    popupMenuItems.clear();
  }

  @Override
  public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        int rowAtPoint = echeancesTable.rowAtPoint(SwingUtilities.convertPoint(popupMenu, new Point(0, 0), echeancesTable));
        for (Echeance echeance : echeancesTableModel.getDisplayedEcheancesAtRow(rowAtPoint)) {
          EcheancePopupMenuItem popupMenuItem = new EcheancePopupMenuItem(echeance);
          popupMenuItems.add(popupMenuItem);
          popupMenu.add(popupMenuItem);
          //   popupMenu.repaint();
        }

        if (rowAtPoint > -1) {
          echeancesTable.setRowSelectionInterval(rowAtPoint, rowAtPoint);
        }
      }
    });
  }

  @Override
  public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
    //    deleteMenuItems();
  }

  @Override
  public void popupMenuCanceled(PopupMenuEvent e) {
    //    deleteMenuItems();
  }

}
