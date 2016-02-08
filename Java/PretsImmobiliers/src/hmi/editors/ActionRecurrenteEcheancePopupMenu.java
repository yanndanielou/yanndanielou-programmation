package hmi.editors;

import javax.swing.JMenu;

import model.Echeance;

public class ActionRecurrenteEcheancePopupMenu extends JMenu {

  private static final long serialVersionUID = 980990651567443340L;
  private Echeance echeance;

  private AugmentationEcheancesRecurrentePopupMenu augmentationEcheancesRecurrentePopupMenu;

  public ActionRecurrenteEcheancePopupMenu(Echeance echeance) {
    super("Action reccurente");
    this.echeance = echeance;
    addActionListener(new EcheancePopupMenuListener());

    augmentationEcheancesRecurrentePopupMenu = new AugmentationEcheancesRecurrentePopupMenu(echeance);
    add(augmentationEcheancesRecurrentePopupMenu);

  }
}
