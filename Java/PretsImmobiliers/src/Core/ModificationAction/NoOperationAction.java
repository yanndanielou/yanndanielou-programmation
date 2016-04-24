package Core.ModificationAction;

import model.Echeance;
import model.EcheanceProperties;

public class NoOperationAction extends ModificationEcheanceAction {

  public NoOperationAction(Echeance echeance) {
    super(true, echeance, true, true);
  }

  @Override
  public String toString() {
    return "-";
  };

  @Override
  public EcheanceProperties createEcheanceRecalee() {
    return getEcheanceReference();
  }
}
