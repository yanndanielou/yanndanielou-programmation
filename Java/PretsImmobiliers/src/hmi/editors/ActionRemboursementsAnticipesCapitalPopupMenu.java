/*
 * Controlguide
 * Copyright (c) Siemens AG 2016, All Rights Reserved, Confidential
 */
package hmi.editors;

import javax.swing.JMenu;

import model.Echeance;

public class ActionRemboursementsAnticipesCapitalPopupMenu extends JMenu {

  private static final long serialVersionUID = 980990651567443340L;
  private Echeance echeance;

  ChoixRemboursementAnticipeCapitalEcheanceMenuItem rembourement1KEuros;
  ChoixRemboursementAnticipeCapitalEcheanceMenuItem rembourement5KEuros;
  ChoixRemboursementAnticipeCapitalEcheanceMenuItem rembourement10KEuros;

  public ActionRemboursementsAnticipesCapitalPopupMenu(Echeance echeance) {
    super("Remboursement anticipe");
    this.echeance = echeance;
    addActionListener(new EcheancePopupMenuListener());

    rembourement1KEuros = new ChoixRemboursementAnticipeCapitalEcheanceMenuItem(1_000, echeance);
    add(rembourement1KEuros);

    rembourement5KEuros = new ChoixRemboursementAnticipeCapitalEcheanceMenuItem(5_000, echeance);
    add(rembourement5KEuros);

    rembourement10KEuros = new ChoixRemboursementAnticipeCapitalEcheanceMenuItem(10_000, echeance);
    add(rembourement10KEuros);

  }
}