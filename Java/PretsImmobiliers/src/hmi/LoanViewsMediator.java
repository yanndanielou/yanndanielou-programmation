package hmi;

import hmi.views.EcheancesView;
import hmi.views.EmpruntsInitiauxPropertiesView;
import hmi.views.LoanOverviewView;
import hmi.views.MainView;
import hmi.views.RealEstateView;
import model.Emprunt;
import model.ProjetImmobilier;

public class LoanViewsMediator {
  private static final LoanViewsMediator INSTANCE = new LoanViewsMediator();

  private EcheancesView echeancesView;
  private LoanOverviewView loanOverviewView;
  private EmpruntsInitiauxPropertiesView empruntsInitiauxPropertiesView;
  private RealEstateView realEstateView;
  private MainView mainView;

  protected ProjetImmobilier projetImmobilier;

  private LoanViewsMediator() {
    projetImmobilier = ProjetImmobilier.getInstance();
  }

  public static LoanViewsMediator getInstance() {
    return INSTANCE;
  }

  public void onApportPersonnelModified(int apportPersonnel) {
    projetImmobilier.getRealEstate().setApportPersonnel(apportPersonnel);
  }

  public void onPrixNetAcheteurModified(int prixNetAcheteur) {
    projetImmobilier.getRealEstate().setPrixNetAcheteur(prixNetAcheteur);
    realEstateView.afterPrixNetAcheteurModified();
    loanOverviewView.afterPrixNetAcheteurModified();
  }

  public void onFraisAgenceModified(int fraisAgence) {
    projetImmobilier.getRealEstate().setFraisAgence(fraisAgence);
    realEstateView.afterFraisAgenceModified();
    loanOverviewView.afterFraisAgenceModified();
  }

  public void onFraisNotaireModified(int fraisNotaire) {
    projetImmobilier.getRealEstate().setFraisNotaire(fraisNotaire);
    realEstateView.afterFraisNotaireModified();
    loanOverviewView.afterFraisNotaireModified();
  }

  public void onEmpruntAdded() {
    Emprunt empruntCreated = projetImmobilier.addEmprunt();
    EcheancesView.getInstance().afterEmpruntCreated();
    EmpruntsInitiauxPropertiesView.getInstance().afterEmpruntCreated(empruntCreated);
    LoanOverviewView.getInstance().afterEmpruntCreated();
    MainView.getInstance().afterEmpruntCreated();
  }

  public void onEmpruntDeleted(Emprunt empruntToDelete) {
    projetImmobilier.removeEmprunt(empruntToDelete);
    EcheancesView.getInstance().afterEmpruntDeleted();
    EmpruntsInitiauxPropertiesView.getInstance().afterEmpruntDeleted(empruntToDelete);
    LoanOverviewView.getInstance().afterEmpruntDeleted();
  }

  /*
    public void onEmpruntAdded(Double capitalEmprunte, Double annualInterestRate, Double monthlyPayment, Long nombreMensualites) {
      Emprunt empruntCreated = projetImmobilier.addEmprunt(capitalEmprunte, annualInterestRate, monthlyPayment, nombreMensualites);
      afterEmpruntCreated(empruntCreated);
    }
  */
  public void setEcheancesView(EcheancesView echeancesView) {
    this.echeancesView = echeancesView;
  }

  public void setLoanOverviewView(LoanOverviewView loanOverviewView) {
    this.loanOverviewView = loanOverviewView;
  }

  public void setLoanPropertiesView(EmpruntsInitiauxPropertiesView empruntsInitiauxPropertiesView) {
    this.empruntsInitiauxPropertiesView = empruntsInitiauxPropertiesView;
  }

  public void setMainView(MainView mainView) {
    this.mainView = mainView;
  }

  public void setRealEstateView(RealEstateView realEstateView) {
    this.realEstateView = realEstateView;
  }

  public void onCapitalEmprunteModified(Emprunt emprunt, double capitalEmprunte) {
    emprunt.modifyCapitalEmprunte(capitalEmprunte);
    afterEmpruntModified(emprunt);
  }

  public void onAnnualInterestRateModified(Emprunt emprunt, double tauxAnnuel) {
    emprunt.modifyTauxAnnuel(tauxAnnuel);
    afterEmpruntModified(emprunt);
  }

  public void onMensualiteHorsAssuranceModified(Emprunt emprunt, double monthlyPayment) {
    emprunt.modifyMensualiteHorsAssurance(monthlyPayment);
    afterEmpruntModified(emprunt);
  }

  public void onNombreEcheancesDesireModified(Emprunt emprunt, int nombreEcheances) {
    emprunt.modifyNombreEcheancesDesire(nombreEcheances);
    afterEmpruntModified(emprunt);
  }

  private void afterEmpruntModified(Emprunt emprunt) {
    EcheancesView.getInstance().afterEmpruntModified();
    EmpruntsInitiauxPropertiesView.getInstance().afterEmpruntModified(emprunt);
    LoanOverviewView.getInstance().afterEmpruntModified();
  }

}
