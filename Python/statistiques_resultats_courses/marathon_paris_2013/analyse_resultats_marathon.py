# -*-coding:Latin-1 -*

import math
import csv
import datetime
import time
#import const

#constantes
NOMBRE_VALEURS_GRAPHIQUE_X = 1000
CENTILE = 100
DECILE = 10
QUARTILE = 4
MEDIANE = 2


def affiche_quantile(valeur_quantile):
	print()		#Ligne vide pour visibilité
	i = 1
	while(i < valeur_quantile):
		print(math.floor((i / valeur_quantile) * 100), "% de la population",liste_courreurs_tries_temps_reel[len(liste_courreurs_tries_temps_reel) // valeur_quantile * i])
		i = i + 1


def comparerCoureursParTempsReel(courreur1, courreur2):
	#"""Trie deux coureurs en fonction de leur temps réel"""
    if courreur1.temps_reel < courreur2.temps_reel:
        return -1
    elif courreur1.temps_reel > courreur2.temps_reel:
        return 1
    else:
        return 0

def getDateTimetimeFromStr(time_str):
#Cas où la durée est au format str (hh:mm:ss)
	if isinstance(time_str, str):
		#découpage des heures minutes secondes
		duree_split = time_str.split(":")
		
		hours = int(duree_split[0])
		minutes = int(duree_split[1])
		seconds = int(duree_split[2])
		
		return datetime.time(hours, minutes, seconds)
	else:
		print(time_str, "should be str and is", type(time_str))
	

class Course:
	"""Classe représentant une course"""

	categories = dict()
	liste_courreurs = list()
	
	def GetCategorie(cls, nom_categorie):
		"""Méthode de classe permettant de retourner une catégorie en fonction de son nom. Crée la catégorie si elle n'existe pas"""
	
		if nom_categorie in Course.categories.keys():
			return Course.categories[nom_categorie]
		else:
			nouvelle_categorie = Categorie(nom_categorie)
			Course.categories[nom_categorie] = nouvelle_categorie
			return nouvelle_categorie		
	GetCategorie = classmethod(GetCategorie)
	
	def CreerCourreur(cls, classement_officiel, dossard, nom, prenom, classement_categorie, categorie, temps_reel, temps_officiel):
		"""Création d'un courreur"""
	
		nouveau_courreur = CourreurArrive(classement_officiel, dossard, nom, prenom, classement_categorie, categorie, temps_reel, temps_officiel)
		Course.liste_courreurs.append(nouveau_courreur)
		
	CreerCourreur = classmethod(CreerCourreur)
	



class Categorie:
	"""Classe représentant une catégorie"""	
	def __init__(self, nom_categorie):
		"""Constructeur d'une catégorie"""
		self.id = nom_categorie
		self.homme = "homme" in nom_categorie.lower()
		self.liste_courreurs = list()
		
		print("Création de la catégorie", nom_categorie, " qui est un homme:", self.homme)
		
	def ajouterCourreur(self, courreur):
		"""Ajout d'un courreur dans une catégorie"""
		self.liste_courreurs.append(courreur)
	
	
#
class CourreurArrive:
	"""Classe représentant un coureur arrivé"""


	def __init__(self, classement_officiel, dossard, nom, prenom, classement_categorie, categorie, temps_reel, temps_officiel):
		"""Constructeur de CourreurArrive"""
		self.classement_officiel = classement_officiel
		self.dossard = dossard
		self.nom = nom
		self.prenom = prenom
		self.classement_categorie = classement_categorie
		self.categorie = Course.GetCategorie(categorie)
		self.categorie.ajouterCourreur(self)
		self.temps_reel_str = temps_reel
		self.temps_reel = getDateTimetimeFromStr(self.temps_reel_str)
		self.temps_officiel_str = temps_officiel
		self.temps_officiel = getDateTimetimeFromStr(self.temps_officiel_str)
		self.attente_dans_sas = datetime.timedelta(hours=self.temps_reel.hour - self.temps_officiel.hour, minutes=self.temps_reel.minute - self.temps_officiel.minute, seconds=self.temps_reel.second - self.temps_officiel.second)

	def __repr__(self):
		"""Quand on entre notre objet dans l'interpréteur"""
		return "{} {}, temps reel ({}), temps officiel ({}) \t".format(
				self.prenom, self.nom, self.temps_reel_str, self.temps_officiel_str)	
	
####################################################################################
###################			 Programme principal		############################
####################################################################################


#ouverture et lecture du fichier en lecture seule
csvfile = open('D:\\programmation\\Python\\statistiques_resultats_courses\\marathon_paris_2013\\Data\\Resultats marathon 2013.csv', 'r')		
csv_reader = csv.DictReader(csvfile, delimiter=';', quotechar='|')
for row in csv_reader:
	#print(row)
	classement_officiel = row["CLASS. OFFICIEL"]
	dossard = row["DOSSARD"]
	nom = row["NOM"]
	prenom = row["PRÉNOM"]
	classement_categorie = row["CLASS. CATÉGORIE"]
	categorie = row["CATÉGORIE"]
	temps_reel = row["TEMPS RÉEL"]
	temps_officiel = row["TEMPS OFFICIEL"]

	#Création du coureur
	nouveau_coureur = Course.CreerCourreur(classement_officiel, dossard, nom, prenom, classement_categorie, categorie, temps_reel, temps_officiel)

csvfile.close()			

# affichage du nombre de coureurs par catégorie
print()	#Ligne vide pour visibilité
for nom_cat, cat in Course.categories.items():
	print(len(cat.liste_courreurs), "courreurs dans categorie", nom_cat)


# Calcul du nombre de courreurs arrivés en fonction du temps réel
# Pour tracer la courbe des arrivées
liste_courreurs_tries_temps_reel = sorted(Course.liste_courreurs, key = lambda x: x.temps_reel)
#Réccupération du premier et dernier temps
premier_temps_reel_s = liste_courreurs_tries_temps_reel[0].temps_reel
dernier_temps_reel_s = liste_courreurs_tries_temps_reel[len(liste_courreurs_tries_temps_reel) - 1].temps_reel

#Calcul de la durée en secondes entre premier et dernier
ecart_entre_premier_dernier = datetime.timedelta(hours=dernier_temps_reel_s.hour - premier_temps_reel_s.hour, minutes=dernier_temps_reel_s.minute - premier_temps_reel_s.minute, seconds=dernier_temps_reel_s.second - premier_temps_reel_s.second)
ecart_entre_premier_dernier_seconds = math.floor(ecart_entre_premier_dernier.total_seconds())

#Extraction des centiles, deciles, quartiles, médiane
#Décile
affiche_quantile(DECILE)
#Décile
affiche_quantile(QUARTILE)
#Médiane
affiche_quantile(MEDIANE)

	
#Médiane
print()		#Ligne vide pour visibilité
print("Le coureur médian est", liste_courreurs_tries_temps_reel[len(liste_courreurs_tries_temps_reel)// 2])

#On souhaite faire une courbe avec NOMBRE_VALEURS_GRAPHIQUE_X valeurs
unite_absisse = ecart_entre_premier_dernier_seconds // NOMBRE_VALEURS_GRAPHIQUE_X


#affichage du nombre d'hommes et de femmes
#print(len(CourreurArrive.liste_hommes), "hommes et ", len(CourreurArrive.liste_femmes), "femmes")
	

#Parcours de la liste des coureurs créés


