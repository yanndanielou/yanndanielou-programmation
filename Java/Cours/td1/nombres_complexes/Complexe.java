/*Vous allez definir une bibiliotheque de methodes utilitaires pour la manipulation de nombres complexes. Pour cela, vous allez definir une classe Complexe ayant les attributs PartieReelle, partieImaginaire, Module. Cette classe contiendra un constructeur et une methode d'affichage. Vous definirez également une classe OperationComplexe contenant des méthodes utilitaires pour la manipulation de complexes. Les methodes devant être implémentées sont: calcul du module, addition, multiplication*/



class Complexe {
	public double partieReelle;
	public double partieImaginaire;
	public double module;

	public Complexe(){
	
	}

	public Complexe(double partieReelle, double partieImaginaire){
		this.partieReelle=partieReelle;
		this.partieImaginaire=partieImaginaire;
	}



	public Complexe(double partieReelle, double partieImaginaire, double module){
		this.partieReelle=partieReelle;
		this.partieImaginaire=partieImaginaire;
		this.module=module;
	}

	public  void affichage(){
		System.out.println("Voici les arguments:");
		System.out.println("Partie réelle: " + partieReelle);
		System.out.println("Partie Immaginaire: " +  partieImaginaire);
		System.out.println("Module: " + module);
	}

}