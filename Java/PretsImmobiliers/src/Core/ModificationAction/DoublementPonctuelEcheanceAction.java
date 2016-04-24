package Core.ModificationAction;

import model.Echeance;
import model.EcheanceProperties;

public class DoublementPonctuelEcheanceAction extends ModificationEcheanceAction {

  public DoublementPonctuelEcheanceAction(Echeance echeance) {
    super(true, echeance, false, true);
  }

  @Override
  public String toString() {
    return "Double";
  }

  @Override
  public EcheanceProperties createEcheanceRecalee() {
    EcheanceProperties echeanceReference = getEcheanceReference();
    double mensualiteHorsAssuranceAvantAction = echeanceReference.getMensualiteHorsAssurance();
    EcheanceProperties echeanceRecalee = new EcheanceProperties(echeance);
    echeanceRecalee.setCapitalRestantARembourserAvantEcheance(echeanceReference.getCapitalRestantARembourserAvantEcheance());
    echeanceRecalee.setMontantCapital(mensualiteHorsAssuranceAvantAction * 2 - echeanceRecalee.getMontantInteret());
    // TODO: à vérifier
    echeanceRecalee.setMontantAssurance(echeanceReference.getMontantAssurance() * 2);
    return echeanceRecalee;
  }
}
