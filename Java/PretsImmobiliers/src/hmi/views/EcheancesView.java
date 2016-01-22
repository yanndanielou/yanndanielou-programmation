package hmi.views;

import hmi.EcheancesTableModel;

import java.awt.BorderLayout;

import javax.swing.JScrollPane;
import javax.swing.JTable;

public class EcheancesView extends ProjetImmobilierBaseView {
  private static final long serialVersionUID = -3015965288824537079L;

  private static final EcheancesView INSTANCE = new EcheancesView();

  private JTable echeancesTable;
  private EcheancesTableModel echeancesTableModel;

  private EcheancesView() {
    loanViewsMediator.setEcheancesView(this);
  }

  public static EcheancesView getInstance() {
    return INSTANCE;
  }

  @Override
  protected void createWidgets() {
    echeancesTableModel = new EcheancesTableModel();
    echeancesTable = new JTable(echeancesTableModel);
  }

  @Override
  protected void placeWidgets() {
    add(new JScrollPane(echeancesTable), BorderLayout.CENTER);
  }

  public void afterEmpruntCreated() {
    //   echeancesTable.repaint();
    echeancesTableModel.fireTableStructureChanged();
    //    echeancesTableModel.fireTableDataChanged();

  }
}
