#pragma once

#include <vector>

#include "Colonne.h"
#include "Ligne.h"
#include "Carre.h"
#include "Case.h"

const int NOMBRE_LIGNES = 9;
const int NOMBRE_COLONNES = 9;
const int NOMBRE_CARRES = 9;

const int TAILLE_LIGNE = 9;
const int TAILLE_COLONNE = 9;
const int TAILLE_CARRE = 9;

class Jeu
{
public:
	Jeu(void);
	~Jeu(void);

	void build();

	bool solveRecursive();
	bool solve(int index);

	void afficher();

	Case* getCase(int x, int y);


private:
	std::vector<Ligne*> _lignes;
	std::vector<Colonne*> _colonnes;
	std::vector<Carre*> _carres;

	std::vector<Case*> _cases;
};


inline Case* Jeu::getCase(int x, int y){
	return _cases[x + TAILLE_LIGNE * y];}