package hmi.editors;

import java.text.NumberFormat;

import model.Echeance;
import Core.ModificationAction.RemboursementAnticipeCapitalAction;

public class ChoixRemboursementAnticipeCapitalEcheanceMenuItem extends ModificationEcheanceActionMenuItem {

  private static final long serialVersionUID = -978794474604856189L;

  public ChoixRemboursementAnticipeCapitalEcheanceMenuItem(int capitalRembourse, Echeance echeance) {
    super(NumberFormat.getIntegerInstance().format(capitalRembourse) + " €", echeance);
    modificationEcheanceAction = new RemboursementAnticipeCapitalAction(capitalRembourse, echeance);
  }
}