#pragma once

#include <vector>

const int NOMBRE_LETTRES = 10;

class Lettres
{
public:
	Lettres(void);
	~Lettres(void);

	void jouer();

private:

	void nettoyerLigne(std::string & ligne);
	bool testerMot(std::string & mot);
	void afficherResult();
	void trouverMotContenantLettres();
	void entrerLettres();

	void test(std::string & mot);

	std::string _fileName;
	std::vector<std::string> _results;
	std::string _lettres;

};
