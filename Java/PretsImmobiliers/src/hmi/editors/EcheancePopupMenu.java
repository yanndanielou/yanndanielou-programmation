package hmi.editors;

import javax.swing.JMenu;

import model.Echeance;

public class EcheancePopupMenu extends JMenu {

  private static final long serialVersionUID = 980990651567443340L;
  private Echeance echeance;

  private ActionPonctuelleEcheancePopupMenu actionPonctuelleEcheancePopupMenu;
  private ActionRecurrenteEcheancePopupMenu actionRecurrenteEcheancePopupMenu;

  public EcheancePopupMenu(Echeance echeance) {
    super(echeance.getEmprunt().getName());
    this.echeance = echeance;
    addActionListener(new EcheancePopupMenuListener());

    actionPonctuelleEcheancePopupMenu = new ActionPonctuelleEcheancePopupMenu(echeance);
    add(actionPonctuelleEcheancePopupMenu);

    actionRecurrenteEcheancePopupMenu = new ActionRecurrenteEcheancePopupMenu(echeance);
    add(actionRecurrenteEcheancePopupMenu);
  }
}
