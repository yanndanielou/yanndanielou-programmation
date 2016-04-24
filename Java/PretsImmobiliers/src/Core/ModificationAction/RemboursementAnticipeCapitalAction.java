package Core.ModificationAction;

import java.text.NumberFormat;

import model.Echeance;
import model.EcheanceProperties;

public abstract class RemboursementAnticipeCapitalAction extends ModificationEcheanceAction {
  protected int capitalRembourse;

  public RemboursementAnticipeCapitalAction(int capitalRembourse, Echeance echeance, boolean gardeNombreEcheances, boolean gardeMensualite) {
    super(true, echeance, gardeNombreEcheances, gardeMensualite);
    this.capitalRembourse = capitalRembourse;
  }

  @Override
  public String toString() {
    return NumberFormat.getIntegerInstance().format(capitalRembourse) + " €";
  }

  @Override
  public EcheanceProperties createEcheanceRecalee() {
    EcheanceProperties echeanceReference = getEcheanceReference();
    double mensualiteHorsAssuranceAvantAction = echeanceReference.getMensualiteHorsAssurance();
    EcheanceProperties echeanceRecalee = new EcheanceProperties(echeance);
    echeanceRecalee.setCapitalRestantARembourserAvantEcheance(echeanceReference.getCapitalRestantARembourserAvantEcheance());
    echeanceRecalee.setMontantCapital(mensualiteHorsAssuranceAvantAction + capitalRembourse - echeanceRecalee.getMontantInteret());
    // TODO: à vérifier
    echeanceRecalee.setMontantAssurance(echeanceReference.getMontantAssurance() * 2);
    return echeanceRecalee;
  }
}
