#include "Partie.h"

Partie::Partie(void)
{
}

Partie::~Partie(void)
{
}

void Partie::jouer()
{
  int choix = demanderChoixJeu();
  while(choix != QUITTER)
  {
	  if(choix == LETTRES)
	  {
		  Lettres partieLettres;
		  partieLettres.jouer();
	  }
	  else
	  {
			Chiffres partieNombre;
			partieNombre.jouer();

	  }
  choix = demanderChoixJeu();
  }
}

int Partie::demanderChoixJeu()
{
	std::cout << "Que voulez vous faire?" << std::endl;
	std::cout << CHIFFRES << " : chiffres" << std::endl;
	std::cout << LETTRES << " : lettres" << std::endl;

	int choix;// = LETTRES;

	std::cin>>choix;


	return choix;



}
