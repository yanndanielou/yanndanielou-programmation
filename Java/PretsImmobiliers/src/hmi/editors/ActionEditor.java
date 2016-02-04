/*
 * Controlguide
 * Copyright (c) Siemens AG 2016, All Rights Reserved, Confidential
 */
package hmi.editors;

import hmi.EcheancesTableModel;

import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;

import model.Echeance;
import Core.ModificationAction.DoublementPonctuelEcheanceAction;
import Core.ModificationAction.ModificationEcheanceAction;
import Core.ModificationAction.NoOperationAction;

//CTRL + SHIFT + O pour générer les imports
public class ActionEditor extends DefaultCellEditor implements ItemListener {

  private static final long serialVersionUID = 8048140623564771611L;

  private EcheancesTableModel echeancesTableModel;

  public ActionEditor(EcheancesTableModel echeancesTableModel) {
    super(new JComboBox<ModificationEcheanceAction>());
    this.echeancesTableModel = echeancesTableModel;
    comboBox().addItem(new NoOperationAction());
    comboBox().addItem(new DoublementPonctuelEcheanceAction());
    comboBox().addItemListener(this);
  }

  @SuppressWarnings("unchecked")
  private JComboBox<ModificationEcheanceAction> comboBox() {
    return (JComboBox<ModificationEcheanceAction>) getComponent();
  }

  @Override
  public Object getCellEditorValue() {
    return super.getCellEditorValue();
  }

  @Override
  public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
    ModificationEcheanceAction action = (ModificationEcheanceAction) value;
    Echeance echeance = echeancesTableModel.getEcheance(row, column);
    action.setEcheance(echeance);
    return super.getTableCellEditorComponent(table, value, isSelected, row, column);
  }

  @Override
  public void itemStateChanged(ItemEvent event) {
    Object item = event.getItem();
    ModificationEcheanceAction action = (ModificationEcheanceAction) item;
    event.getStateChange();
  }
}