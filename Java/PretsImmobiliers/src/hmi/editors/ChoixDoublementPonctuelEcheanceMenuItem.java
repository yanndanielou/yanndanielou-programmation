package hmi.editors;

import model.Echeance;
import Core.ModificationAction.DoublementPonctuelEcheanceAction;

public class ChoixDoublementPonctuelEcheanceMenuItem extends ModificationEcheanceActionMenuItem {

  private static final long serialVersionUID = -978794474604856189L;

  public ChoixDoublementPonctuelEcheanceMenuItem(Echeance echeance) {
    super("x2", echeance);
    modificationEcheanceAction = new DoublementPonctuelEcheanceAction(echeance);
  }
}