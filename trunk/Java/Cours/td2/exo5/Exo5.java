class Personne{

	String nom;
	int age;

	public Personne(String nom_personne, int age_personne){
		this.age = age_personne;
		this.nom = nom_personne;
	}


	public Personne(){
		this.nom = Saisie.lire_String("Nom: ");
		this.age = Saisie.lire_int("Age ");
	}


	public void affichage(){
		System.out.println("nom: " + nom + " age " + age + " ans");
	}
}

class Scolaire extends Personne{
	String nomEtablissement;
	String niveau;


	public Scolaire(String nom_personne, int age_personne, String nom_etablissement, String niveau){

		super(nom_personne,age_personne);
		this.nomEtablissement = nom_etablissement;
		this.niveau = niveau;
	}


	public Scolaire(){
		super();
		this.nomEtablissement = Saisie.lire_String("Nom de l'établissement:");
		this.niveau = Saisie.lire_String("Niveau: ");;
	}


	public void affichage(){
		super.affichage();
		System.out.println(" établissement: " + nomEtablissement + " niveau: " + niveau);
	}

}

class Etudiant extends Scolaire{
	String formation;


	public Etudiant(String nom_personne, int age_personne, String nom_etablissement, String niveau, String formation){
		super(nom_personne,age_personne,nom_etablissement,niveau);
		this.formation = formation;
	}


	public Etudiant(){
		super();
		this.formation = Saisie.lire_String("Formation: ");
	}


	public void affichage(){
		super.affichage();
		System.out.println(" formation: " + formation);
	}

}



class Lyceen extends Scolaire{
	String filiere_bac;

	public Lyceen(String nom_personne, int age_personne, String nom_etablissement, String niveau, String filiere_bac){
		
		super(nom_personne,age_personne,nom_etablissement,niveau);
		this.filiere_bac = filiere_bac;
	}


	public Lyceen(){
		super();
		this.filiere_bac = Saisie.lire_String("Filière Bac: ");
	}


	public void affichage(){
		super.affichage();
		System.out.println(" filiere bac: " + filiere_bac);
	}

}



public class Exo5{
	public static void main (String [] main){

	Scolaire sc1 = new Scolaire ("Dupont", 16, "Lycee x", "Scondaire");
	sc1.affichage();


	Etudiant e1 = new Etudiant ("Robert", 22, "UPMC x", "Superieur", "Master");
	e1.affichage();

	Lyceen l1 = new Lyceen ("Claude", 22, "Lycee y", "Secondaire", "S");
	l1.affichage();

	Scolaire sc2 = new Scolaire();
	sc2.affichage();

	Lyceen l2 = new Lyceen();
	l2.affichage();
}

}