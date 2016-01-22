package model;

import java.util.ArrayList;
import java.util.List;

public class ProjetImmobilier {
  private List<Emprunt> emprunts = new ArrayList<Emprunt>();
  private RealEstate realEstate;

  private static final ProjetImmobilier INSTANCE = new ProjetImmobilier();

  private ProjetImmobilier() {
    realEstate = new RealEstate();
  }

  public static ProjetImmobilier getInstance() {
    return INSTANCE;
  }

  public List<Emprunt> getEmprunts() {
    return emprunts;
  }

  public Emprunt addEmprunt() {
    Emprunt newEmprunt = new Emprunt();
    emprunts.add(newEmprunt);
    return newEmprunt;
  }

  /*
    public Emprunt addEmprunt(Double capitalEmprunte, Double annualInterestRate, Double monthlyPayment, Long nombreMensualites) {
      Emprunt newEmprunt = null;
      if (monthlyPayment != null) {
        newEmprunt = new Emprunt(capitalEmprunte, annualInterestRate, monthlyPayment);
      } else if (nombreMensualites != null) {
        long nombreMensualitesUnboxed = nombreMensualites;
        newEmprunt = new Emprunt(capitalEmprunte, annualInterestRate, (int) nombreMensualitesUnboxed);
      }

      if (newEmprunt != null) {
        emprunts.add(newEmprunt);
      }
      return newEmprunt;
    }
  */
  public double getMontantTotalInterets() {
    double montantTotalInterets = 0;
    for (Emprunt emprunt : emprunts) {
      montantTotalInterets += emprunt.getMontantTotalInterets();
    }
    return montantTotalInterets;
  }

  public RealEstate getRealEstate() {
    return realEstate;
  }
}
