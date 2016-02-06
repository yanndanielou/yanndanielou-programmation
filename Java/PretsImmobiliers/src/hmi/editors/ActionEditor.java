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

import Core.ModificationAction.ModificationEcheanceAction;

//CTRL + SHIFT + O pour générer les imports
public class ActionEditor extends DefaultCellEditor implements ItemListener {

  private static final long serialVersionUID = 8048140623564771611L;

  private EcheancesTableModel echeancesTableModel;

  public ActionEditor(EcheancesTableModel echeancesTableModel) {
    super(new JComboBox<ModificationEcheanceAction>());
    this.echeancesTableModel = echeancesTableModel;
    //  comboBox().addItem(new NoOperationAction());
    //  comboBox().addItem(new DoublementPonctuelEcheanceAction());
    // comboBox().addItem(new AugmentationDesEcheancesAction());
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
    /* ModificationEcheanceAction action = (ModificationEcheanceAction) value;
     Echeance echeance = echeancesTableModel.getEcheance(row, column);
     action.setEcheance(echeance);*/
    return super.getTableCellEditorComponent(table, value, isSelected, row, column);
  }

  @Override
  public void itemStateChanged(ItemEvent event) {
    /*  Object item = event.getItem();
      ItemSelectable itemSelectable = event.getItemSelectable();
      Object source = event.getSource();
      ModificationEcheanceAction action = (ModificationEcheanceAction) item;
      JComboBox<ModificationEcheanceAction> comboBox = comboBox();
      Container parent = comboBox.getParent();

      if (action instanceof AugmentationDesEcheancesAction) {
        AugmentationDesEcheancesAction augmentationDesEcheancesAction = (AugmentationDesEcheancesAction) action;
        ChoixAugmentationEcheancePopup choixAugmentationEcheancePopup = new ChoixAugmentationEcheancePopup();
        int pourcentageAugmentationEcheance = choixAugmentationEcheancePopup.getPourcentageAugmentationEcheance();
        augmentationDesEcheancesAction.setAugmentationPourcentage(pourcentageAugmentationEcheance);
      }*/
    //   action.getEcheance().setModificationEcheanceAction(action);
  }
}