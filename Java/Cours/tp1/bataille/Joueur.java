import 	java.util.*;
class Joueur{

Paquet paquet;
	

	public Joueur(){
	paquet = new Paquet(0);


	}
/*
public static void distribuer(Paquet general, Paquet j1, Paquet j2){

	for(int i = 0; i < 52;i++){
		j1.ajouterCarte(general.getCarte(i));
		j1.nb_cartes++;
			if(i<52){
				i++;
				j2.ajouterCarte(general.getCarte(i));
				j2.nb_cartes++;
			}
		}
}


	public static void main (String [] args) {

	Joueur joueur1 = new Joueur();
	Joueur joueur2 = new Joueur();
	Paquet paquet_general = new Paquet(52);

	paquet_general.melanger();

	distribuer(paquet_general,joueur1.paquet, joueur2.paquet);

	System.out.println("joueur 1 final \n");	
	joueur1.paquet.afficher();
	System.out.println("");
	joueur1.paquet.suivante().affiche_carte();
	joueur1.paquet.suivante().affiche_carte();
	joueur1.paquet.suivante().affiche_carte();
	//System.out.println("joueur 2 final \n");	
//	joueur2.paquet.afficher();
	}*/
}