package hmi.views;

import hmi.EcheancesTableModel;
import hmi.editors.EcheancesTablePopupMenuHandler;

import java.awt.BorderLayout;
import java.awt.event.ComponentEvent;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class EcheancesView extends ProjetImmobilierBaseView {
  private static final long serialVersionUID = -3015965288824537079L;

  private static final EcheancesView INSTANCE = new EcheancesView();

  private JLabel title;
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
    title = new JLabel("Echeances");
    echeancesTableModel = new EcheancesTableModel();
    echeancesTable = new JTable(echeancesTableModel);
    echeancesTableModel.setTable(echeancesTable);
    echeancesTable.addMouseListener(new EcheancesTablePopupMenuHandler(echeancesTable, echeancesTableModel));
    //echeancesTable.setComponentPopupMenu(new EcheancesTablePopupMenu(echeancesTable, echeancesTableModel));
  }

  @Override
  protected void placeWidgetsAtInit() {
    setLayout(new BorderLayout());
    add(title, BorderLayout.NORTH);
    add(new JScrollPane(echeancesTable), BorderLayout.CENTER);
  }

  public void afterEmpruntCreated() {
  }

  public void afterEmpruntDeleted() {
    echeancesTableModel.fireTableStructureChanged();
  }

  public void afterEmpruntModified() {
    echeancesTableModel.fireTableStructureChanged();
  }

  public void afterAssurancesMensuellesModified() {
    echeancesTableModel.fireTableStructureChanged();
  }

  @Override
  public void componentResized(ComponentEvent event) {
    if (event.getComponent() == this) {
      replaceWidgets();
    }
  }

}
