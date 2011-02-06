#pragma once

#include <vector>

const int NOMBRE_NOMBRES = 6;

const int ADDITION			= 1000;
const int SOUSTRACTION		= ADDITION + 1;
const int MULTIPLICATION	= SOUSTRACTION + 1;
const int DIVISION			= MULTIPLICATION + 1;

class Chiffres
{
public:
	Chiffres(void);
	~Chiffres(void);
	
	void jouer();
	void tirerNombres();

private:

	void etapeGenerique(std::vector<int> & nombres, char numEtape);
	void rotation(std::vector<int> & entree,char rang);
	void testerOrdre(std::vector<int> & aTester);
	void testerVecteur(std::vector<int> & vecteur);
	int  calculerVecteur(std::vector<int> vecteur, std::vector<int> operations);
	void afficher();
	void operationSuivante(std::vector<int> & operations, int index, bool & fini);
	void afficherResultats();

	void test();

	int _resultat;
	int _valeurApprochee;
	int _ecart;

	std::string _resultatApproche;
	std::string _meilleurResultat;
	std::vector<int> _nombres;
	std::vector<std::string> _resultatsTrouves;
};
