package hmi;

import hmi.editors.ActionEditor;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;

import model.Echeance;
import model.Emprunt;
import model.ProjetImmobilier;
import Core.util.DisplayUtils;

public class EcheancesTableModel extends AbstractTableModel {
  private static final long serialVersionUID = 508691973838270560L;

  private ProjetImmobilier projetImmobilier;

  private JTable table;

  // Columns
  private final int ECHEANCE_NUMBER_COLUMN_INDEX = 0;
  private final int ECHEANCE_DATE_COLUMN_INDEX = ECHEANCE_NUMBER_COLUMN_INDEX + 1;
  private final int NOMBRE_COLONNES_FIXES = ECHEANCE_DATE_COLUMN_INDEX + 1;

  private final int PAR_EMPRUNT_ACTION_COLUMN_INDEX = 0;
  private final int PAR_EMPRUNT_MENSUALITE_HORS_ASSURANCE_COLUMN_INDEX = PAR_EMPRUNT_ACTION_COLUMN_INDEX + 1;
  private final int PAR_EMPRUNT_MONTANT_CAPITAL_COLUMN_INDEX = PAR_EMPRUNT_MENSUALITE_HORS_ASSURANCE_COLUMN_INDEX + 1;
  private final int PAR_EMPRUNT_MONTANT_INTERETS_COLUMN_INDEX = PAR_EMPRUNT_MONTANT_CAPITAL_COLUMN_INDEX + 1;
  private final int PAR_EMPRUNT_MONTANT_CAPITAL_RESTANT_A_REMBOURSER_COLUMN_INDEX = PAR_EMPRUNT_MONTANT_INTERETS_COLUMN_INDEX + 1;
  private final int PAR_EMPRUNT_SEPARATION_ENTRE_EMPRUNT_COLUMN_INDEX = PAR_EMPRUNT_MONTANT_CAPITAL_RESTANT_A_REMBOURSER_COLUMN_INDEX + 1;
  private final int PAR_EMPRUNT_NOMBRE_COLUMNS = PAR_EMPRUNT_SEPARATION_ENTRE_EMPRUNT_COLUMN_INDEX + 1;

  private final static String BAD_LOGIC = "Bad logic";

  public EcheancesTableModel() {
    projetImmobilier = ProjetImmobilier.getInstance();
  }

  private boolean isEmpruntColumn(int column) {
    return column >= NOMBRE_COLONNES_FIXES;
  }

  public Echeance getEcheance(int row, int column) {
    Emprunt emprunt = getEmprunt(column);

    if (emprunt.getEcheances().size() > row) {
      Echeance echeance = emprunt.getEcheances().get(row);
      return echeance;
    }
    return null;
  }

  public Emprunt getEmprunt(int column) {
    if (!isEmpruntColumn(column)) {
      return null;
    }
    int empruntNumber = (column - NOMBRE_COLONNES_FIXES) / PAR_EMPRUNT_NOMBRE_COLUMNS;
    List<Emprunt> emprunts = projetImmobilier.getEmprunts();
    return emprunts.get(empruntNumber);
  }

  private boolean isActionColumn(int column) {
    return isColumnParEmprunt(column, PAR_EMPRUNT_ACTION_COLUMN_INDEX);
  }

  private boolean isMensualiteHorsAssuranceColumn(int column) {
    return isColumnParEmprunt(column, PAR_EMPRUNT_MENSUALITE_HORS_ASSURANCE_COLUMN_INDEX);
  }

  private boolean isMontantCapitalColumn(int column) {
    return isColumnParEmprunt(column, PAR_EMPRUNT_MONTANT_CAPITAL_COLUMN_INDEX);
  }

  private boolean isMontantInteretsColumn(int column) {
    return isColumnParEmprunt(column, PAR_EMPRUNT_MONTANT_INTERETS_COLUMN_INDEX);
  }

  private boolean isCapitalMontantAEmprunterColumn(int column) {
    return isColumnParEmprunt(column, PAR_EMPRUNT_MONTANT_CAPITAL_RESTANT_A_REMBOURSER_COLUMN_INDEX);
  }

  private boolean isSeparationEntreEmpruntsColumn(int column) {
    return isColumnParEmprunt(column, PAR_EMPRUNT_SEPARATION_ENTRE_EMPRUNT_COLUMN_INDEX);
  }

  private boolean isColumnParEmprunt(int column, int columnToCheck) {
    boolean empruntColumn = isEmpruntColumn(column);
    if (empruntColumn) {
      int columnByEmpruntIndex = (column - NOMBRE_COLONNES_FIXES) % PAR_EMPRUNT_NOMBRE_COLUMNS;
      return columnByEmpruntIndex == columnToCheck;
    } else {
      return false;
    }
  }

  private boolean isEcheanceNumberColumn(int column) {
    return column == ECHEANCE_NUMBER_COLUMN_INDEX;
  }

  private boolean isEcheanceDateColumn(int column) {
    return column == ECHEANCE_DATE_COLUMN_INDEX;
  }

  @Override
  public String getColumnName(int column) {
    if (isEcheanceNumberColumn(column)) {
      return "Num";
    }
    if (isEcheanceDateColumn(column)) {
      return "Date";
    }
    if (isActionColumn(column)) {
      return "Action";
    }
    if (isMensualiteHorsAssuranceColumn(column)) {
      return "Mensualite hors assurance";
    }
    if (isMontantCapitalColumn(column)) {
      return "Montant capital";
    }
    if (isMontantInteretsColumn(column)) {
      return "Montant interets";
    }
    if (isCapitalMontantAEmprunterColumn(column)) {
      return "Capital restant à rembourser";
    }
    if (isSeparationEntreEmpruntsColumn(column)) {
      return "  ";
    }
    return BAD_LOGIC;
  }

  @Override
  public int getRowCount() {
    return projetImmobilier.getNombreEcheances();
  }

  @Override
  public int getColumnCount() {
    List<Emprunt> emprunts = projetImmobilier.getEmprunts();
    int noSeparatorForLastEmprunt = 1;
    return NOMBRE_COLONNES_FIXES + emprunts.size() * PAR_EMPRUNT_NOMBRE_COLUMNS - noSeparatorForLastEmprunt;
  }

  @Override
  public Object getValueAt(int rowIndex, int columnIndex) {
    if (isEcheanceNumberColumn(columnIndex)) {
      return rowIndex + 1;
    }
    if (isEcheanceDateColumn(columnIndex)) {
      Date now = new Date();
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(now);
      calendar.add(Calendar.MONTH, rowIndex + 1);
      Date time = calendar.getTime();
      int echeanceYear = time.getYear() + 1900;
      int echeanceMonth = time.getMonth() + 1;
      String echeanceDate = "" + echeanceYear + "/" + echeanceMonth;
      return echeanceDate;
    }

    Echeance echeance = getEcheance(rowIndex, columnIndex);
    if (echeance != null) {
      if (isActionColumn(columnIndex)) {
        return echeance.getModificationEcheanceAction();
      }
      if (isMensualiteHorsAssuranceColumn(columnIndex)) {
        return DisplayUtils.getRoundedValueForDisplay(echeance.getMensualiteHorsAssurance());
      }
      if (isMontantCapitalColumn(columnIndex)) {
        return DisplayUtils.getRoundedValueForDisplay(echeance.getMontantCapital());
      }
      if (isMontantInteretsColumn(columnIndex)) {
        return DisplayUtils.getRoundedValueForDisplay(echeance.getMontantInteret());
      }
      if (isCapitalMontantAEmprunterColumn(columnIndex)) {
        return DisplayUtils.getRoundedValueForDisplay(echeance.getCapitalRestantARembourser());
      }
      if (isSeparationEntreEmpruntsColumn(columnIndex)) {
        return "";
      }
    }
    else {
      return "";
    }
    return BAD_LOGIC;
  }

  @Override
  public void fireTableStructureChanged() {
    super.fireTableStructureChanged();
    recreateEditors();
  }

  @Override
  public boolean isCellEditable(int row, int col) {
    if (isActionColumn(col)) {
      return true;
    }
    return false;
  }

  private void recreateEditors() {
    for (int columnNumber = 0; columnNumber < table.getColumnCount(); columnNumber++) {
      TableColumn tableColumn = table.getColumnModel().getColumn(columnNumber);
      if (isActionColumn(columnNumber)) {
        tableColumn.setCellEditor(new ActionEditor(this));
      }
    }
  }

  public void setTable(JTable table) {
    this.table = table;
  }
}
