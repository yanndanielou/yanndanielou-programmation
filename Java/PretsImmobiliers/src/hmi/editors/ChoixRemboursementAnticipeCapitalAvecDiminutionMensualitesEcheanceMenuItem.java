package hmi.editors;

import java.text.NumberFormat;

import model.Echeance;
import Core.ModificationAction.RemboursementAnticipeCapitalAction;
import Core.ModificationAction.RemboursementAnticipeCapitalAvecDiminutionMensualiteAction;
import Core.ModificationAction.RemboursementAnticipeCapitalAvecDiminutionNombreEcheancesAction;

public class ChoixRemboursementAnticipeCapitalAvecDiminutionMensualitesEcheanceMenuItem extends ModificationEcheanceActionMenuItem {

  private static final long serialVersionUID = -978794474604856189L;

  public ChoixRemboursementAnticipeCapitalAvecDiminutionMensualitesEcheanceMenuItem(int capitalRembourse, Echeance echeance) {
    super("Avec diminution mensualités", echeance);
    modificationEcheanceAction = new RemboursementAnticipeCapitalAvecDiminutionMensualiteAction(capitalRembourse, echeance);
  }
}