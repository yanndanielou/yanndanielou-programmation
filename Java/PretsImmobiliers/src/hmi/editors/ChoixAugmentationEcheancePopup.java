/*
 * Controlguide
 * Copyright (c) Siemens AG 2016, All Rights Reserved, Confidential
 */
package hmi.editors;

import javax.swing.JOptionPane;

public class ChoixAugmentationEcheancePopup extends JOptionPane {

  private static final long serialVersionUID = -978794474604856189L;

  public int getPourcentageAugmentationEcheance() {
    String nom = showInputDialog(null, "Entrer augmentation capital (%)", "Modification echeances", JOptionPane.QUESTION_MESSAGE);
    return Integer.valueOf(nom);
  }

}