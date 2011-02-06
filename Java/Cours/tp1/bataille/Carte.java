class Carte {
	
	static final String noms[]={"Deux","Trois", "Quatre","Cinq", "Six", "Sept", "Huit", "Neuf", "Dix", "Valet", "Dame", "Roi","As"};
	static final String couleurs[]={"pique","coeur","carreau", "trefle"};

	private String couleur;
	private String nom;
	private int valeur_symbolique;

	public Carte(int n){
		n--;
		this.couleur=couleurs[n/13];
		this.nom=noms[n%13];
		this.valeur_symbolique=n%13+1;
	}

	public Carte(){
		
	}

	
	public int valeur_carte(){
		return valeur_symbolique;
	}


	public void affiche_carte(){
		System.out.println(nom + " de " + couleur + " est de valeur symbolique " + valeur_symbolique);
	}

	/*public static void main (String [] args) {
		Carte carte = new Carte(1);
		carte.affiche_carte();
	}*/


}