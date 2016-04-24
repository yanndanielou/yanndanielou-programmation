package Core.ModificationAction;

import java.text.NumberFormat;

import model.Echeance;
import model.EcheanceProperties;

public class RemboursementAnticipeCapitalAvecDiminutionMensualiteAction extends RemboursementAnticipeCapitalAction {

  public RemboursementAnticipeCapitalAvecDiminutionMensualiteAction(int capitalRembourse, Echeance echeance) {
    super(capitalRembourse, echeance, true, false);
  }

  @Override
  public String toString() {
    return NumberFormat.getIntegerInstance().format(capitalRembourse) + " €";
  }

}
