/*
 * Controlguide
 * Copyright (c) Siemens AG 2016, All Rights Reserved, Confidential
 */
package hmi;

import hmi.views.EcheancesView;
import hmi.views.LoanOverviewView;
import hmi.views.LoanPropertiesView;
import hmi.views.MainView;
import hmi.views.RealEstateView;
import model.ProjetImmobilier;

public class LoanViewsMediator {
  private static final LoanViewsMediator INSTANCE = new LoanViewsMediator();

  private EcheancesView echeancesView;
  private LoanOverviewView loanOverviewView;
  private LoanPropertiesView loanPropertiesView;
  private RealEstateView realEstateView;
  private MainView mainView;

  protected ProjetImmobilier projetImmobilier;

  private LoanViewsMediator() {
    projetImmobilier = ProjetImmobilier.getInstance();
  }

  public static LoanViewsMediator getInstance() {
    return INSTANCE;
  }

  public void afterEmpruntCreated() {
    EcheancesView.getInstance().afterEmpruntCreated();
    LoanOverviewView.getInstance().afterEmpruntCreated();
  }

  public void onApportPersonnelModified(int apportPersonnel) {
    projetImmobilier.getRealEstate().setApportPersonnel(apportPersonnel);
  }

  public void onPrixNetAcheteurModified(int prixNetAcheteur) {
    projetImmobilier.getRealEstate().setPrixNetAcheteur(prixNetAcheteur);
    realEstateView.afterPrixNetAcheteurModified();
  }

  public void onEmpruntAdded(Double capitalEmprunte, Double annualInterestRate, Double monthlyPayment, Long nombreMensualites) {
    projetImmobilier.addEmprunt(capitalEmprunte, annualInterestRate, monthlyPayment, nombreMensualites);
    afterEmpruntCreated();
  }

  public void setEcheancesView(EcheancesView echeancesView) {
    this.echeancesView = echeancesView;
  }

  public void setLoanOverviewView(LoanOverviewView loanOverviewView) {
    this.loanOverviewView = loanOverviewView;
  }

  public void setLoanPropertiesView(LoanPropertiesView loanPropertiesView) {
    this.loanPropertiesView = loanPropertiesView;
  }

  public void setMainView(MainView mainView) {
    this.mainView = mainView;
  }

  public void setRealEstateView(RealEstateView realEstateView) {
    this.realEstateView = realEstateView;
  }

}
