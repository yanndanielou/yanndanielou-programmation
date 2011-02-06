import 	java.util.*;

class Banque{
  private String NomBanque;
  /*private*/public ArrayList Compte;
  private int numero_prochain_compte;

  public Banque(String nom_banque){
    Compte = new ArrayList();
    this.NomBanque= nom_banque;
    this.numero_prochain_compte=1;
  }

  public void creer_compte_anonyme(String nom, String prenom, double apport_initial){
    this.Compte.add(new CompteAnonyme(nom, prenom, numero_prochain_compte, apport_initial));
    numero_prochain_compte++;
  }

  public void creer_compte_entreprise(String nom_entreprise, double apport_initial){
    this.Compte.add(new CompteEntreprise(nom_entreprise, numero_prochain_compte, apport_initial));
    numero_prochain_compte++;
  }

  public void creer_compte_personne(String nom, String prenom, double apport_initial){
    this.Compte.add(new ComptePersonnePhysique(nom, prenom, this.numero_prochain_compte, apport_initial,false));
    this.numero_prochain_compte++;
  }
    
}
