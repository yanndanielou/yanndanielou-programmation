package hmi.editors;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

import model.Echeance;
import Core.ModificationAction.ModificationEcheanceAction;

public abstract class ModificationEcheanceActionMenuItem extends JMenuItem implements ActionListener {

  private static final long serialVersionUID = 5750467627135245425L;

  protected ModificationEcheanceAction modificationEcheanceAction;

  protected Echeance echeance;

  public ModificationEcheanceActionMenuItem(String name, Echeance echeance) {
    super(name);
    this.echeance = echeance;
    addActionListener(this);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    echeance.getEmprunt().applyModificationEcheanceAction(modificationEcheanceAction, echeance);
  }
}