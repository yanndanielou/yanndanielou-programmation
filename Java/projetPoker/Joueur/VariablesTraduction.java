/** Cette classe regrouppe toutes les donnes du fichier langue*/

public interface VariablesTraduction{

  String  separationTraductions = ";";

  String [] languesPossibles = {"fr", "en", "de"};

  //mots de liaison
  int trad_ou = 1;

  int trad_envoyerServeur = trad_ou + 1;
  int trad_recuServeur = trad_envoyerServeur + 1;
  int trad_carteRecue = trad_recuServeur + 1;


  int trad_seCoucher = trad_carteRecue + 1;
  int trad_parole = trad_seCoucher + 1;
  int trad_suivre = trad_parole + 1;
  int trad_miser = trad_suivre + 1;
  int trad_relancer = trad_miser + 1;
  int trad_tapis = trad_relancer + 1;

  int trad_toucheAppuyee = trad_tapis + 1;

  int trad_suiviPartie = trad_toucheAppuyee + 1;

  int trad_dimensionFichierCartes = trad_suiviPartie + 1;

  int trad_tailleCarte = trad_dimensionFichierCartes + 1;

  int trad_argentJoueur = trad_tailleCarte + 1;
  int trad_miseJoueur = trad_argentJoueur + 1;
  int trad_argentJoueurAdverse = trad_miseJoueur + 1;
  int trad_miseJoueurAdverse = trad_argentJoueurAdverse + 1;
  int trad_argentPot = trad_miseJoueurAdverse + 1;

	int trad_vousAvez = trad_argentPot+1;

  int trad_pasCombinaison = trad_vousAvez + 1;
  int trad_paire = trad_pasCombinaison + 1;
  int trad_doublePaire = trad_paire + 1;
  int trad_brelan = trad_doublePaire + 1;
  int trad_suite = trad_brelan + 1;
  int trad_couleur = trad_suite + 1;
  int trad_full = trad_couleur + 1;
  int trad_carre = trad_full + 1;
  int trad_quintFlush = trad_carre + 1;

  int trad_joueur = trad_quintFlush+1;
  int trad_entrezNom = trad_joueur + 1;
  int trad_identification = trad_entrezNom + 1;

  int trad_demandeRecave = trad_identification +1;
  int trad_configuration = trad_demandeRecave +1;

  int trad_debutTour = trad_configuration +1;

  int trad_egalite = trad_debutTour +1;
  int trad_gagne = trad_egalite +1;

}
