package hmi.editors;

import javax.swing.JMenu;

import model.Echeance;

public class ActionRemboursementsAnticipesCapitalPopupMenu extends JMenu {

  private static final long serialVersionUID = 980990651567443340L;
  private Echeance echeance;

  ActionRemboursementAnticipeCapitalPopupMenu rembourement1KEuros;
  ActionRemboursementAnticipeCapitalPopupMenu rembourement5KEuros;
  ActionRemboursementAnticipeCapitalPopupMenu rembourement10KEuros;

  public ActionRemboursementsAnticipesCapitalPopupMenu(Echeance echeance) {
    super("Remboursement anticipe");
    this.echeance = echeance;
    addActionListener(new EcheancePopupMenuListener());

    rembourement1KEuros = new ActionRemboursementAnticipeCapitalPopupMenu(1_000, echeance);
    add(rembourement1KEuros);

    rembourement5KEuros = new ActionRemboursementAnticipeCapitalPopupMenu(5_000, echeance);
    add(rembourement5KEuros);

    rembourement10KEuros = new ActionRemboursementAnticipeCapitalPopupMenu(10_000, echeance);
    add(rembourement10KEuros);

  }
}