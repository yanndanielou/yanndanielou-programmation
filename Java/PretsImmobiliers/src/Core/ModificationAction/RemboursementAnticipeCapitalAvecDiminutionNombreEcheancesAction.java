package Core.ModificationAction;

import java.text.NumberFormat;

import model.Echeance;
import model.EcheanceProperties;

public class RemboursementAnticipeCapitalAvecDiminutionNombreEcheancesAction extends RemboursementAnticipeCapitalAction {

  public RemboursementAnticipeCapitalAvecDiminutionNombreEcheancesAction(int capitalRembourse, Echeance echeance) {
    super(capitalRembourse, echeance, false, true);
  }

  @Override
  public String toString() {
    return NumberFormat.getIntegerInstance().format(capitalRembourse) + " €";
  }

}
