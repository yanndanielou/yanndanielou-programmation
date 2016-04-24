package hmi;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import model.Echeance;
import model.Emprunt;
import model.ProjetImmobilier;
import Core.util.DisplayUtils;

public class EcheancesTableModel extends AbstractTableModel {
  private static final long serialVersionUID = 508691973838270560L;

  private ProjetImmobilier projetImmobilier;

  private JTable table;

  // Columns
  private static final int ECHEANCE_NUMBER_COLUMN_INDEX = 0;
  private static final int ECHEANCE_DATE_COLUMN_INDEX = ECHEANCE_NUMBER_COLUMN_INDEX + 1;
  private static final int NOMBRE_COLONNES_FIXES = ECHEANCE_DATE_COLUMN_INDEX + 1;

  private static final int PAR_EMPRUNT_ACTION_COLUMN_INDEX = 0;

  private static final int PAR_EMPRUNT_ECHEANCE_INITIALE_MENSUALITE_HORS_ASSURANCE_COLUMN_INDEX = PAR_EMPRUNT_ACTION_COLUMN_INDEX + 1;
  private static final int PAR_EMPRUNT_ECHEANCE_INITIALE_MONTANT_CAPITAL_COLUMN_INDEX = PAR_EMPRUNT_ECHEANCE_INITIALE_MENSUALITE_HORS_ASSURANCE_COLUMN_INDEX + 1;
  private static final int PAR_EMPRUNT_ECHEANCE_INITIALE_MONTANT_INTERETS_COLUMN_INDEX = PAR_EMPRUNT_ECHEANCE_INITIALE_MONTANT_CAPITAL_COLUMN_INDEX + 1;
  private static final int PAR_EMPRUNT_ECHEANCE_INITIALE_MONTANT_CAPITAL_RESTANT_A_REMBOURSER_COLUMN_INDEX = PAR_EMPRUNT_ECHEANCE_INITIALE_MONTANT_INTERETS_COLUMN_INDEX + 1;

  private static final int PAR_EMPRUNT_SEPARATION_APRES_EMPRUNT_SANS_ECHEANCE_RECALEE = PAR_EMPRUNT_ECHEANCE_INITIALE_MONTANT_CAPITAL_RESTANT_A_REMBOURSER_COLUMN_INDEX + 1;
  private static final int PAR_EMPRUNT_NOMBRE_COLUMNS_SANS_ECHEANCE_RECALEE = PAR_EMPRUNT_SEPARATION_APRES_EMPRUNT_SANS_ECHEANCE_RECALEE + 1;

  private static final int PAR_EMPRUNT_ECHEANCE_RECALEE_MENSUALITE_HORS_ASSURANCE_COLUMN_INDEX = PAR_EMPRUNT_ECHEANCE_INITIALE_MONTANT_CAPITAL_RESTANT_A_REMBOURSER_COLUMN_INDEX + 1;
  private static final int PAR_EMPRUNT_ECHEANCE_RECALEE_MONTANT_CAPITAL_COLUMN_INDEX = PAR_EMPRUNT_ECHEANCE_RECALEE_MENSUALITE_HORS_ASSURANCE_COLUMN_INDEX + 1;
  private static final int PAR_EMPRUNT_ECHEANCE_RECALEE_MONTANT_INTERETS_COLUMN_INDEX = PAR_EMPRUNT_ECHEANCE_RECALEE_MONTANT_CAPITAL_COLUMN_INDEX + 1;
  private static final int PAR_EMPRUNT_ECHEANCE_RECALEE_MONTANT_CAPITAL_RESTANT_A_REMBOURSER_COLUMN_INDEX = PAR_EMPRUNT_ECHEANCE_RECALEE_MONTANT_INTERETS_COLUMN_INDEX + 1;

  private static final int PAR_EMPRUNT_SEPARATION_APRES_EMPRUNT_AVEC_ECHEANCE_RECALEE = PAR_EMPRUNT_ECHEANCE_RECALEE_MONTANT_CAPITAL_RESTANT_A_REMBOURSER_COLUMN_INDEX + 1;
  private static final int PAR_EMPRUNT_NOMBRE_COLUMNS_AVEC_ECHEANCE_RECALEE = PAR_EMPRUNT_SEPARATION_APRES_EMPRUNT_AVEC_ECHEANCE_RECALEE + 1;

  private static final int EMPTY_ROW_BETWEEN_ECHEANCE_AND_TOTAL = 1;
  private static final int TOTALS_ROW = 1;

  private static final String BAD_LOGIC = "Bad logic";
  private static final String EMPTY_CELL = "";
  private static final String EMPTY_TITLE = " ";

  public EcheancesTableModel() {
    projetImmobilier = ProjetImmobilier.getInstance();
  }

  private boolean isEmpruntColumn(int column) {
    return column >= NOMBRE_COLONNES_FIXES;
  }

  public Echeance getEcheance(int row, int column) {
    Emprunt emprunt = getEmprunt(column);

    if (emprunt != null) {
      if (emprunt.getEcheances().size() > row) {
        Echeance echeance = emprunt.getEcheances().get(row);
        return echeance;
      }
    }
    return null;
  }

  public Emprunt getEmprunt(int column) {
    if (!isEmpruntColumn(column)) {
      return null;
    }
    int i = NOMBRE_COLONNES_FIXES;
    for (Emprunt emprunt : projetImmobilier.getEmprunts()) {
      i += getNombreColonnesPourEmprunt(emprunt);
      if (i > column) {
        return emprunt;
      }
    }
    return null;
  }

  private int getFirstAbsoluteColumnPourEmprunt(Emprunt emprunt) {
    int firstAbsoluteColumn = NOMBRE_COLONNES_FIXES;

    for (Emprunt empruntIt : projetImmobilier.getEmprunts()) {
      if (emprunt == empruntIt) {
        return firstAbsoluteColumn;
      }
      firstAbsoluteColumn += getNombreColonnesPourEmprunt(empruntIt);
    }
    return -1;
  }

  private int getNombreColonnesPourEmprunt(Emprunt emprunt) {
    return emprunt.hasEcheanceRecalee() ? PAR_EMPRUNT_NOMBRE_COLUMNS_AVEC_ECHEANCE_RECALEE : PAR_EMPRUNT_NOMBRE_COLUMNS_SANS_ECHEANCE_RECALEE;
  }

  public Set<Echeance> getDisplayedEcheancesAtRow(int row) {
    Set<Echeance> displayedEcheances = new HashSet<Echeance>();
    for (int column = 0; column < getColumnCount(); column++) {
      Echeance echeance = getEcheance(row, column);
      if (echeance != null) {
        displayedEcheances.add(echeance);
      }
    }
    return displayedEcheances;
  }

  public Set<Emprunt> getDisplayedEmpruntsAtRow(int row) {
    Set<Emprunt> displayedEmprunts = new HashSet<Emprunt>();
    for (int column = 0; column < getColumnCount(); column++) {
      Echeance echeance = getEcheance(row, column);
      if (echeance != null) {
        displayedEmprunts.add(echeance.getEmprunt());
      }
    }
    return displayedEmprunts;
  }

  public Set<Emprunt> getDisplayedEmprunts() {
    Set<Emprunt> displayedEmprunts = new HashSet<Emprunt>();
    for (int column = 0; column < getColumnCount(); column++) {
      Emprunt emprunt = getEmprunt(column);
      if (emprunt != null) {
        displayedEmprunts.add(emprunt);
      }
    }
    return displayedEmprunts;
  }

  private boolean isActionColumn(int column) {
    return isColumnParEmprunt(column, PAR_EMPRUNT_ACTION_COLUMN_INDEX);
  }

  private boolean isEcheanceInitialeMensualiteHorsAssuranceColumn(int column) {
    return isColumnParEmprunt(column, PAR_EMPRUNT_ECHEANCE_INITIALE_MENSUALITE_HORS_ASSURANCE_COLUMN_INDEX);
  }

  private boolean isEcheanceInitialeMontantCapitalColumn(int column) {
    return isColumnParEmprunt(column, PAR_EMPRUNT_ECHEANCE_INITIALE_MONTANT_CAPITAL_COLUMN_INDEX);
  }

  private boolean isEcheanceInitialeMontantInteretsColumn(int column) {
    return isColumnParEmprunt(column, PAR_EMPRUNT_ECHEANCE_INITIALE_MONTANT_INTERETS_COLUMN_INDEX);
  }

  private boolean isEcheanceInitialeCapitalMontantARembourserColumn(int column) {
    return isColumnParEmprunt(column, PAR_EMPRUNT_ECHEANCE_INITIALE_MONTANT_CAPITAL_RESTANT_A_REMBOURSER_COLUMN_INDEX);
  }

  private boolean isEcheanceRecaleeMensualiteHorsAssuranceColumn(int column) {
    return isColumnParEmprunt(column, PAR_EMPRUNT_ECHEANCE_RECALEE_MENSUALITE_HORS_ASSURANCE_COLUMN_INDEX);
  }

  private boolean isEcheanceRecaleeMontantCapitalColumn(int column) {
    return isColumnParEmprunt(column, PAR_EMPRUNT_ECHEANCE_RECALEE_MONTANT_CAPITAL_COLUMN_INDEX);
  }

  private boolean isEcheanceRecaleeMontantInteretsColumn(int column) {
    return isColumnParEmprunt(column, PAR_EMPRUNT_ECHEANCE_RECALEE_MONTANT_INTERETS_COLUMN_INDEX);
  }

  private boolean isEcheanceRecaleeCapitalMontantARembourserColumn(int column) {
    return isColumnParEmprunt(column, PAR_EMPRUNT_ECHEANCE_RECALEE_MONTANT_CAPITAL_RESTANT_A_REMBOURSER_COLUMN_INDEX);
  }

  private boolean isSeparationEntreEmpruntsColumn(int column) {
    Emprunt emprunt = getEmprunt(column);
    return isColumnParEmprunt(column, emprunt.hasEcheanceRecalee() ? PAR_EMPRUNT_SEPARATION_APRES_EMPRUNT_AVEC_ECHEANCE_RECALEE : PAR_EMPRUNT_SEPARATION_APRES_EMPRUNT_SANS_ECHEANCE_RECALEE);
  }

  private boolean isColumnParEmprunt(int column, int columnParEmpruntToCheck) {
    boolean empruntColumn = isEmpruntColumn(column);
    if (!empruntColumn) {
      return false;
    }
    Emprunt emprunt = getEmprunt(column);
    int columnParEmprunt = column - getFirstAbsoluteColumnPourEmprunt(emprunt);
    if (!emprunt.hasEcheanceRecalee() && columnParEmpruntToCheck >= PAR_EMPRUNT_NOMBRE_COLUMNS_SANS_ECHEANCE_RECALEE) {
      return false;
    }
    return columnParEmprunt == columnParEmpruntToCheck;
  }

  private boolean isEcheanceNumberColumn(int column) {
    return column == ECHEANCE_NUMBER_COLUMN_INDEX;
  }

  private boolean isEcheanceDateColumn(int column) {
    return column == ECHEANCE_DATE_COLUMN_INDEX;
  }

  private boolean isEmptyRowBetweenEcheanceAndTotal(int row) {
    return row == getRowCount() - TOTALS_ROW - EMPTY_ROW_BETWEEN_ECHEANCE_AND_TOTAL;
  }

  private boolean isTotalsRow(int row) {
    return row == getRowCount() - TOTALS_ROW;
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
    if (isEcheanceInitialeMensualiteHorsAssuranceColumn(column)) {
      return "Mensualite hors assurance";
    }
    if (isEcheanceInitialeMontantCapitalColumn(column)) {
      return "Montant capital";
    }
    if (isEcheanceInitialeMontantInteretsColumn(column)) {
      return "Montant interets";
    }
    if (isEcheanceInitialeCapitalMontantARembourserColumn(column)) {
      return "Capital restant à rembourser";
    }
    if (isSeparationEntreEmpruntsColumn(column)) {
      return EMPTY_TITLE;
    }
    if (isEcheanceRecaleeMensualiteHorsAssuranceColumn(column)) {
      return "Mensualite hors assurance";
    }
    if (isEcheanceRecaleeMontantCapitalColumn(column)) {
      return "Montant capital";
    }
    if (isEcheanceRecaleeMontantInteretsColumn(column)) {
      return "Montant interets";
    }
    if (isEcheanceRecaleeCapitalMontantARembourserColumn(column)) {
      return "Capital restant à rembourser";
    }
    return BAD_LOGIC;
  }

  @Override
  public int getRowCount() {
    int nombreEcheances = projetImmobilier.getNombreEcheances();
    if (nombreEcheances > 0) {
      return nombreEcheances + EMPTY_ROW_BETWEEN_ECHEANCE_AND_TOTAL + TOTALS_ROW;
    }
    return 0;
  }

  @Override
  public int getColumnCount() {
    int columnCounts = NOMBRE_COLONNES_FIXES;
    List<Emprunt> emprunts = projetImmobilier.getEmprunts();
    for (Emprunt emprunt : emprunts) {
      columnCounts += emprunt.hasEcheanceRecalee() ? PAR_EMPRUNT_NOMBRE_COLUMNS_AVEC_ECHEANCE_RECALEE : PAR_EMPRUNT_NOMBRE_COLUMNS_SANS_ECHEANCE_RECALEE;
    }
    int noSeparatorForLastEmprunt = 1;
    columnCounts = columnCounts - noSeparatorForLastEmprunt;
    return columnCounts;
  }

  @Override
  public Object getValueAt(int rowIndex, int columnIndex) {
    if (isEmptyRowBetweenEcheanceAndTotal(rowIndex)) {
      return EMPTY_CELL;
    } else if (isTotalsRow(rowIndex)) {
      if (isEcheanceNumberColumn(columnIndex)) {
        return "Totals";
      }
      Emprunt emprunt = getEmprunt(columnIndex);
      if (isEcheanceInitialeMensualiteHorsAssuranceColumn(columnIndex)) {
        return DisplayUtils.getRoundedValueForDisplay(emprunt.getMontantTotalMensualitesHorsAssuranceInitial());
      }
      if (isEcheanceInitialeMontantCapitalColumn(columnIndex)) {
        return DisplayUtils.getRoundedValueForDisplay(emprunt.getMontantTotalCapitalRembourseInitial());
      }
      if (isEcheanceInitialeMontantInteretsColumn(columnIndex)) {
        return DisplayUtils.getRoundedValueForDisplay(emprunt.getMontantTotalInteretsInitial());
      }
      if (isEcheanceRecaleeMensualiteHorsAssuranceColumn(columnIndex)) {
        return DisplayUtils.getRoundedValueForDisplay(emprunt.getMontantTotalMensualitesHorsAssuranceRecale());
      }
      if (isEcheanceRecaleeMontantInteretsColumn(columnIndex)) {
        return DisplayUtils.getRoundedValueForDisplay(emprunt.getMontantTotalInteretsRecale());
      }
      if (isEcheanceRecaleeMontantCapitalColumn(columnIndex)) {
        return DisplayUtils.getRoundedValueForDisplay(emprunt.getMontantTotalCapitalRembourseRecale());
      }
      return EMPTY_CELL;
    }
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
      if (isEcheanceInitialeMensualiteHorsAssuranceColumn(columnIndex)) {
        return DisplayUtils.getRoundedValueForDisplay(echeance.getEcheanceInitiale().getMensualiteHorsAssurance());
      }
      if (isEcheanceInitialeMontantCapitalColumn(columnIndex)) {
        return DisplayUtils.getRoundedValueForDisplay(echeance.getEcheanceInitiale().getMontantCapital());
      }
      if (isEcheanceInitialeMontantInteretsColumn(columnIndex)) {
        return DisplayUtils.getRoundedValueForDisplay(echeance.getEcheanceInitiale().getMontantInteret());
      }
      if (isEcheanceInitialeCapitalMontantARembourserColumn(columnIndex)) {
        return DisplayUtils.getRoundedValueForDisplay(echeance.getEcheanceInitiale().getCapitalRestantARembourserAvantEcheance());
      }
      if (echeance.getEmprunt().hasEcheanceRecalee()) {
        if (isEcheanceRecaleeMensualiteHorsAssuranceColumn(columnIndex)) {
          if (echeance.hasEcheanceRecalee()) {
            return DisplayUtils.getRoundedValueForDisplay(echeance.getEcheanceRecalee().getMensualiteHorsAssurance());
          } else {
            return EMPTY_CELL;
          }
        }
        if (isEcheanceRecaleeMontantCapitalColumn(columnIndex)) {
          if (echeance.hasEcheanceRecalee()) {
            return DisplayUtils.getRoundedValueForDisplay(echeance.getEcheanceRecalee().getMontantCapital());
          } else {
            return EMPTY_CELL;
          }
        }
        if (isEcheanceRecaleeMontantInteretsColumn(columnIndex)) {
          if (echeance.hasEcheanceRecalee()) {
            return DisplayUtils.getRoundedValueForDisplay(echeance.getEcheanceRecalee().getMontantInteret());
          } else {
            return EMPTY_CELL;
          }
        }
        if (isEcheanceRecaleeCapitalMontantARembourserColumn(columnIndex)) {
          if (echeance.hasEcheanceRecalee()) {
            return DisplayUtils.getRoundedValueForDisplay(echeance.getEcheanceRecalee().getCapitalRestantARembourserAvantEcheance());
          } else {
            return EMPTY_CELL;
          }
        }
      }
      if (isSeparationEntreEmpruntsColumn(columnIndex)) {
        return EMPTY_CELL;
      }
    }
    else {
      return EMPTY_CELL;
    }
    return BAD_LOGIC;
  }

  @Override
  public void fireTableStructureChanged() {
    super.fireTableStructureChanged();
  }

  @Override
  public boolean isCellEditable(int row, int col) {
    if (isActionColumn(col)) {
      return true;
    }
    return false;
  }

  public void setTable(JTable table) {
    this.table = table;
  }
}
