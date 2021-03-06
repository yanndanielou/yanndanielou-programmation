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
    Emprunt newEmprunt = new Emprunt("Emprunt " + String.valueOf(emprunts.size() + 1));
    newEmprunt.modifyCapitalEmprunte(getCapitalRestantAEmprunter());
    emprunts.add(newEmprunt);
    return newEmprunt;
  }

  public void removeEmprunt(Emprunt empruntToDelete) {
    emprunts.remove(empruntToDelete);
  }

  public double getCapitalEmprunte() {
    double capitalEmprunte = 0;
    for (Emprunt emprunt : emprunts) {
      capitalEmprunte += emprunt.getCapitalEmprunte();
    }
    return capitalEmprunte;
  }

  public int getNombreEcheances() {
    int nombreEcheances = 0;
    for (Emprunt emprunt : emprunts) {
      nombreEcheances = Math.max(nombreEcheances, emprunt.getEcheances().size());
    }
    return nombreEcheances;
  }

  public double getCapitalRestantAEmprunter() {
    double capitalEmprunte = getCapitalEmprunte();
    double capitalRestanteAEmprunter = realEstate.getPrixNetAcheteur() - capitalEmprunte - realEstate.getApportPersonnel() + realEstate.getFraisNotaire();
    return Math.max(capitalRestanteAEmprunter, 0L);
  }

  public double getMontantTotalInterets() {
    double montantTotalInterets = 0;
    for (Emprunt emprunt : emprunts) {
      montantTotalInterets += emprunt.getMontantTotalInteretsInitial();
    }
    return montantTotalInterets;
  }

  public double getMontantTotalAssurances() {
    double montantTotalAssurances = 0;
    for (Emprunt emprunt : emprunts) {
      montantTotalAssurances += emprunt.getMontantTotalAssuranceInitial();
    }
    return montantTotalAssurances;
  }

  public RealEstate getRealEstate() {
    return realEstate;
  }

}
