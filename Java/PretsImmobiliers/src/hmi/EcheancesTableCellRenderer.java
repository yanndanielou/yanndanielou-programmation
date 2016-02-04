package hmi;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class EcheancesTableCellRenderer extends DefaultTableCellRenderer {

  private static final long serialVersionUID = -8859175508976953908L;

  @Override
  public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
    return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
  }
}
