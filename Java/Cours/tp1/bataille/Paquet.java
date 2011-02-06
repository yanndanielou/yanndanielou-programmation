import 	java.util.*;
class Paquet {

	private	Carte cartex= new Carte();
	int nb_cartes;
	ArrayList Cartes;// = new ArrayList();
	int num_carte_en_cours;

	public Paquet (int nb_cartes){
		
	Cartes = new ArrayList();
	this.num_carte_en_cours = 0;

		for(int i=0;i<nb_cartes;i++){
			this.Cartes.add(new Carte(i+1));
			this.nb_cartes=nb_cartes;
		}
	}

	public Paquet (){
	}

	public Carte getCarte(int i){
		Carte tempo_carte1 = new Carte();
		tempo_carte1 = (Carte) (this.Cartes.get(i));
		return tempo_carte1;
	}

	public void ajouterCarte(Carte toAdd){

//		Carte tempo_carte1 = new Carte(9);
	//	tempo_carte1.affiche_carte();
		Cartes.add(toAdd);
		nb_cartes++;
	//	this.Cartes.add(general.Cartes.get(i));
	}



	public Carte suivante(){
		Carte tempo_carte_suivante = new Carte();
		tempo_carte_suivante = (Carte) (this.Cartes.get(num_carte_en_cours));
		num_carte_en_cours++;
		return tempo_carte_suivante;
	}


	public void donner_carte(){
		this.Cartes.remove(0);
		this.nb_cartes--;
	}

	public Carte piocher(){
		Carte tempo_carte_piocher = new Carte();
		tempo_carte_piocher = (Carte) (this.Cartes.get(0));
		return tempo_carte_piocher;
	}

	public void melanger(){

		int compteur_melange=52;

		int pos_carte1= (int)(Math.random()*52);

		int pos_carte2= (int)(Math.random()*52);

		while(compteur_melange-- > 1){

			pos_carte1= (int)(Math.random()*52);
			pos_carte2= (int)(Math.random()*52);
				while(pos_carte1==pos_carte2){
					pos_carte2= (int)(Math.random()*52);
				}
			Carte tempo_carte1 = new Carte();
			Carte tempo_carte2 = new Carte();
	
			tempo_carte1 = (Carte)(this.Cartes.get(pos_carte1));
			tempo_carte2 = (Carte)(this.Cartes.get(pos_carte2));	
	
			this.Cartes.set(pos_carte1,tempo_carte2);
			this.Cartes.set(pos_carte2,tempo_carte1);
	
			//System.out.println("j'ai melange " + pos_carte1+ " et " +pos_carte2);
		}

	}

	public void afficher(){
		for(int i=0;i<this.nb_cartes;i++){
		((Carte)(this.Cartes.get(i))).affiche_carte();
		}
	}

	/*public static void main (String [] args) {
		Paquet paquet1 =  new Paquet(52);
		paquet1.afficher();
		paquet1.melanger();
		paquet1.afficher();
	
	}*/

}