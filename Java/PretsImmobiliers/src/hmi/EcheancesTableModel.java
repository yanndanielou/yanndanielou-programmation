package hmi;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import model.Echeance;
import model.Emprunt;
import model.ProjetImmobilier;
import Core.DisplayUtils;

public class EcheancesTableModel extends AbstractTableModel {
  private static final long serialVersionUID = 508691973838270560L;

  private ProjetImmobilier projetImmobilier;

  // Columns
  private final int ECHEANCE_NUMBER_COLUMN_INDEX = 0;
  private final int ECHEANCE_DATE_COLUMN_INDEX = ECHEANCE_NUMBER_COLUMN_INDEX + 1;
  private final int NOMBRE_COLONNES_FIXES = ECHEANCE_DATE_COLUMN_INDEX + 1;

  private final int PAR_EMPRUNT_MENSUALITE_HORS_ASSURANCE_COLUMN_INDEX = 0;
  private final int PAR_EMPRUNT_MONTANT_CAPITAL_COLUMN_INDEX = PAR_EMPRUNT_MENSUALITE_HORS_ASSURANCE_COLUMN_INDEX + 1;
  private final int PAR_EMPRUNT_MONTANT_INTERETS_COLUMN_INDEX = PAR_EMPRUNT_MONTANT_CAPITAL_COLUMN_INDEX + 1;
  private final int PAR_EMPRUNT_MONTANT_CAPITAL_RESTANT_A_REMBOURSER_COLUMN_INDEX = PAR_EMPRUNT_MONTANT_INTERETS_COLUMN_INDEX + 1;
  private final int PAR_EMPRUNT_NOMBRE_COLUMNS = PAR_EMPRUNT_MONTANT_CAPITAL_RESTANT_A_REMBOURSER_COLUMN_INDEX + 1;

  private final static String BAD_LOGIC = "Bad logic";

  public EcheancesTableModel() {
    projetImmobilier = ProjetImmobilier.getInstance();
  }

  private boolean isEmpruntColumn(int column) {
    return column >= NOMBRE_COLONNES_FIXES;
  }

  private Emprunt getEmprunt(int column) {
    if (!isEmpruntColumn(column)) {
      return null;
    }
    int empruntNumber = 0;
    List<Emprunt> emprunts = projetImmobilier.getEmprunts();
    return emprunts.get(empruntNumber);
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
    return BAD_LOGIC;
  }

  @Override
  public int getRowCount() {
    List<Emprunt> emprunts = projetImmobilier.getEmprunts();
    if (emprunts.isEmpty()) {
      return 0;
    }
    return emprunts.get(0).getEcheances().size();
  }

  @Override
  public int getColumnCount() {
    List<Emprunt> emprunts = projetImmobilier.getEmprunts();
    return NOMBRE_COLONNES_FIXES + emprunts.size() * PAR_EMPRUNT_NOMBRE_COLUMNS;
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
    Emprunt emprunt = getEmprunt(columnIndex);
    Echeance echeance = emprunt.getEcheances().get(rowIndex);
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
    return BAD_LOGIC;
  }

}
