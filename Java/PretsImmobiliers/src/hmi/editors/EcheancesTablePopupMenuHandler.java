/*
 * Controlguide
 * Copyright (c) Siemens AG 2016, All Rights Reserved, Confidential
 */
package hmi.editors;

import hmi.EcheancesTableModel;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;

import model.Echeance;
import model.ProjetImmobilier;

public class EcheancesTablePopupMenuHandler extends MouseAdapter {

  private ProjetImmobilier projetImmobilier;
  private JTable echeancesTable;
  private EcheancesTableModel echeancesTableModel;
  private EcheancesTablePopupMenu popupMenu;
  private List<EcheancePopupMenu> popupMenuItems = new ArrayList<EcheancePopupMenu>();

  public EcheancesTablePopupMenuHandler(JTable echeancesTable, EcheancesTableModel echeancesTableModel) {
    this.echeancesTable = echeancesTable;
    this.echeancesTableModel = echeancesTableModel;
    this.projetImmobilier = ProjetImmobilier.getInstance();
    popupMenu = new EcheancesTablePopupMenu(echeancesTable, echeancesTableModel);
  }

  private void deleteMenuItems() {
    for (EcheancePopupMenu popupMenuItem : popupMenuItems) {
      popupMenu.remove(popupMenuItem);
    }
    popupMenuItems.clear();
  }

  @Override
  public void mouseReleased(MouseEvent mouseEvent) {
    super.mouseReleased(mouseEvent);
    //Seulement s'il s'agit d'un clic droit
    if (mouseEvent.isPopupTrigger()) {
      int eventX = mouseEvent.getX();
      int eventY = mouseEvent.getY();
      deleteMenuItems();
      int rowAtPoint = echeancesTable.rowAtPoint(new Point(eventX, eventY));
      if (rowAtPoint > 0) {
        for (Echeance echeance : echeancesTableModel.getDisplayedEcheancesAtRow(rowAtPoint)) {
          EcheancePopupMenu popupMenuItem = new EcheancePopupMenu(echeance);
          popupMenuItems.add(popupMenuItem);
          popupMenu.add(popupMenuItem);
        }
      }
      popupMenu.show(echeancesTable, eventX, eventY);
    }
  }
  /*
    @Override
    public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
      int selectedRow = echeancesTable.getSelectedRow();
      if (selectedRow > 0) {
        for (Echeance echeance : echeancesTableModel.getDisplayedEcheancesAtRow(selectedRow)) {
          EcheancePopupMenuItem popupMenuItem = new EcheancePopupMenuItem(echeance);
          popupMenuItems.add(popupMenuItem);
          popupMenu.add(popupMenuItem);
        }
      }
      SwingUtilities.invokeLater(new Runnable() {
        @Override
        public void run() {
          int rowAtPoint = echeancesTable.rowAtPoint(SwingUtilities.convertPoint(popupMenu, new Point(0, 0), echeancesTable));
          if (rowAtPoint > -1) {
            //      echeancesTable.setRowSelectionInterval(rowAtPoint, rowAtPoint);
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
    }*/

}
