#pragma once

#include <vector>
/*
#include "Colonne.h"
#include "Ligne.h"
//#include "Diagonale.h"
#include "Case.h"
*//*
const int NOMBRE_LIGNES = 8;
const int NOMBRE_COLONNES = 8;
const int NOMBRE_CARRES = 8;
*/
const int TAILLE_LIGNE = 30;
const int TAILLE_COLONNE = TAILLE_LIGNE;
//const int TAILLE_CARRE = 8;

const int REINE = 1;
const int VIDE = 0;

class Jeu
{
public:
	Jeu(void);
	~Jeu(void);

	void build();

	bool solve();

	bool solveRecursive();
	bool solve2(int index, int colonne);

	void afficher();

	bool verifierColonne(int y);
	bool verifierLigne(int x);
	bool verifierDiagonale(int x, int y);


private:
	int **_cases;
	//std::vector<int*> _cases;
};