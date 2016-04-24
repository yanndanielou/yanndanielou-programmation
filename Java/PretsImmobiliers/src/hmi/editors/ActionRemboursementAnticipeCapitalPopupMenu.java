package hmi.editors;

import java.text.NumberFormat;

import javax.swing.JMenu;

import model.Echeance;

public class ActionRemboursementAnticipeCapitalPopupMenu extends JMenu {

  private static final long serialVersionUID = 980990651567443340L;
  private Echeance echeance;
  private int capitalRembourse;

  private ChoixRemboursementAnticipeCapitalAvecDiminutionNombreEcheancesEcheanceMenuItem choixRemboursementAnticipeCapitalAvecDiminutionNombreEcheancesEcheanceMenuItem;
  private ChoixRemboursementAnticipeCapitalAvecDiminutionMensualitesEcheanceMenuItem choixRemboursementAnticipeCapitalAvecDiminutionMensualitesEcheanceMenuItem;

  public ActionRemboursementAnticipeCapitalPopupMenu(int capitalRembourse, Echeance echeance) {
    super(NumberFormat.getIntegerInstance().format(capitalRembourse) + " €");
    this.capitalRembourse = capitalRembourse;
    this.echeance = echeance;
    addActionListener(new EcheancePopupMenuListener());

    choixRemboursementAnticipeCapitalAvecDiminutionNombreEcheancesEcheanceMenuItem = new ChoixRemboursementAnticipeCapitalAvecDiminutionNombreEcheancesEcheanceMenuItem(capitalRembourse, echeance);
    add(choixRemboursementAnticipeCapitalAvecDiminutionNombreEcheancesEcheanceMenuItem);

    choixRemboursementAnticipeCapitalAvecDiminutionMensualitesEcheanceMenuItem = new ChoixRemboursementAnticipeCapitalAvecDiminutionMensualitesEcheanceMenuItem(capitalRembourse, echeance);
    add(choixRemboursementAnticipeCapitalAvecDiminutionMensualitesEcheanceMenuItem);
  }

}