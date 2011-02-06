import 	java.util.*;

class Compte{
  private int numero_compte;
  private double solde;
  private double decouvert_autorise;

  public Compte(int numero_compte, double apport_initial, boolean entreprise){
    this.numero_compte=numero_compte;
    this.solde=apport_initial;
    
    if(entreprise==true)
      this.decouvert_autorise=0;
    else    
      this.decouvert_autorise=-apport_initial;   
  }

  public Compte(int numero_compte, double apport_initial){
   this.numero_compte=numero_compte;
   this.solde=apport_initial;
  }


  public void crediter(double montant_credit){
    this.solde += montant_credit;
    return;
  }


  public boolean debiter(double montant_debit){
      if(this.solde - montant_debit < this.decouvert_autorise)
	return false;

      else{
	this.solde-= montant_debit;
	return true;
      }
  }


  public String toString(){
    return "n°: " + numero_compte + " a un solde de " + solde + " avec un découvert autorisé de " + decouvert_autorise;
  }

}

class ComptePersonnePhysique extends Compte{

  private String nom;
  private String prenom;
  private boolean anonyme;

  public ComptePersonnePhysique(String nom, String prenom, int numero_compte, double apport_initial, boolean is_anonyme){
    super(numero_compte, apport_initial,false); 
    this.anonyme=is_anonyme;
    this.nom=nom;
    this.prenom=prenom;
  }


   public String toString(){
    String chaine_retour = "Compte personne physique ";
      if(anonyme==false)
	chaine_retour += nom + " " + prenom + " ";
      else
	chaine_retour += "********* ";
      chaine_retour += super.toString();    
    return chaine_retour;
    } 
}


class CompteAnonyme extends ComptePersonnePhysique {

  public CompteAnonyme(String nom, String prenom, int numero_compte, double apport_initial){
    super(nom, prenom, numero_compte,apport_initial,true);
  }
}


class CompteEntreprise extends Compte{

  private String nom_entreprise;


  public CompteEntreprise(String nom_entreprise, int numero_compte, double apport_initial){
    super(numero_compte, apport_initial, true); 
    this.nom_entreprise=nom_entreprise;
  }


   public String toString(){
    return "Compte entreprise de " + nom_entreprise + " : " + super.toString();
    } 

}
