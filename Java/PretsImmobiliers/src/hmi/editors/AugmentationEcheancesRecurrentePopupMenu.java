package hmi.editors;

import javax.swing.JMenu;

import model.Echeance;

public class AugmentationEcheancesRecurrentePopupMenu extends JMenu {

  private static final long serialVersionUID = 980990651567443340L;
  private Echeance echeance;

  private ChoixAugmentationMenuItem augmenter5Pourcents;
  private ChoixAugmentationMenuItem augmenter10Pourcents;
  private ChoixAugmentationMenuItem augmenter15Pourcents;
  private ChoixAugmentationMenuItem augmenter20Pourcents;
  private ChoixAugmentationMenuItem augmenter25Pourcents;

  public AugmentationEcheancesRecurrentePopupMenu(Echeance echeance) {
    super("Augmentation echeances");
    this.echeance = echeance;
    addActionListener(new EcheancePopupMenuListener());

    augmenter5Pourcents = new ChoixAugmentationMenuItem(5, echeance);
    add(augmenter5Pourcents);

    augmenter10Pourcents = new ChoixAugmentationMenuItem(10, echeance);
    add(augmenter10Pourcents);

    augmenter15Pourcents = new ChoixAugmentationMenuItem(15, echeance);
    add(augmenter15Pourcents);

    augmenter20Pourcents = new ChoixAugmentationMenuItem(20, echeance);
    add(augmenter20Pourcents);

    augmenter25Pourcents = new ChoixAugmentationMenuItem(25, echeance);
    add(augmenter25Pourcents);

  }
}
