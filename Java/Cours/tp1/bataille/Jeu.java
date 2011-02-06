class Jeu{


	public Jeu(){}


public void distribuer(Paquet general, Paquet j1, Paquet j2){

	for(int i = 0; i < 52;i++){
		j1.ajouterCarte(general.getCarte(i));
//		j1.nb_cartes++;
			if(i<52){
				i++;
				j2.ajouterCarte(general.getCarte(i));
//				j2.nb_cartes++;
			}
		}
}

public int comparer_cartes(Carte carte_j1, Carte carte_j2){

	if(carte_j1.valeur_carte() > carte_j2.valeur_carte())
		return 1;
	else if (carte_j1.valeur_carte() < carte_j2.valeur_carte())
		return 2;
	else 
		return 0;
}


	public static void main (String [] args) {

	Jeu jeu = new Jeu();

	Joueur joueur1 = new Joueur();
	Joueur joueur2 = new Joueur();
	Paquet paquet_general = new Paquet(52);

	paquet_general.melanger();
	paquet_general.afficher();

	jeu.distribuer(paquet_general,joueur1.paquet, joueur2.paquet);

	System.out.println("joueur 1 debut jeu \n");	
	joueur1.paquet.afficher();
	System.out.println("");
	System.out.println("joueur 2  debut jeu \n");	
	joueur2.paquet.afficher();
	System.out.println("");


	switch(jeu.comparer_cartes(joueur1.paquet.piocher(), joueur2.paquet.piocher())){
		case 1:
			joueur1.paquet.ajouterCarte(joueur2.paquet.getCarte(0));
			joueur2.paquet.donner_carte();
			System.out.println("1");
		break;
		case 2:
			joueur2.paquet.ajouterCarte(joueur1.paquet.getCarte(0));
			joueur1.paquet.donner_carte();
			System.out.println("2");
		break;
		default:
			System.out.println("bataille");
			break;
	}


	System.out.println("\n\njoueur 1 fin de la partie\n\n");	
	joueur1.paquet.afficher();
	System.out.println("\n\njoueur 2 fin de la partie\n\n");	
	joueur2.paquet.afficher();
//	System.out.println(jeu.comparer_cartes(joueur1.paquet.piocher(), joueur2.paquet.piocher()));


/*	joueur1.paquet.piocher().affiche_carte();
	joueur1.paquet.suivante().affiche_carte();
	joueur1.paquet.piocher().affiche_carte();
	joueur1.paquet.suivante().affiche_carte();*/
	}



}