package hmi.editors;

import java.text.NumberFormat;

import model.Echeance;
import Core.ModificationAction.RemboursementAnticipeCapitalAction;
import Core.ModificationAction.RemboursementAnticipeCapitalAvecDiminutionNombreEcheancesAction;

public class ChoixRemboursementAnticipeCapitalAvecDiminutionNombreEcheancesEcheanceMenuItem extends ModificationEcheanceActionMenuItem {

  private static final long serialVersionUID = -978794474604856189L;

  public ChoixRemboursementAnticipeCapitalAvecDiminutionNombreEcheancesEcheanceMenuItem(int capitalRembourse, Echeance echeance) {
    super("Avec diminution du nombre d'échéances", echeance);
    modificationEcheanceAction = new RemboursementAnticipeCapitalAvecDiminutionNombreEcheancesAction(capitalRembourse, echeance);
  }
}