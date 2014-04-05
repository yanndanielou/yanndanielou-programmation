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
		self._classement_officiel = classement_officiel
		self._dossard = dossard
		self._nom = nom
		self._prenom = prenom
		self._classement_categorie = classement_categorie
		self._categorie = Course.GetCategorie(categorie)
		self._categorie.ajouterCourreur(self)
		self._temps_reel_str = temps_reel
		self._temps_reel = getDateTimetimeFromStr(self._temps_reel_str)
		self._temps_officiel_str = temps_officiel
		self._temps_officiel = getDateTimetimeFromStr(self._temps_officiel_str)
		self._attente_dans_sas = datetime.timedelta(hours=self._temps_reel.hour - self._temps_officiel.hour, minutes=self._temps_reel.minute - self._temps_officiel.minute, seconds=self._temps_reel.second - self._temps_officiel.second)

	def __repr__(self):
		"""Quand on entre notre objet dans l'interpréteur"""
		return "{} {}, temps reel ({}), temps officiel ({}) \t".format(
				self._prenom, self._nom, self._temps_reel_str, self._temps_officiel_str)	
	
####################################################################################
###################			 Programme principal		############################
####################################################################################


#ouverture et lecture du fichier en lecture seule
inputResultsFile = open('D:\\programmation\\Python\\statistiques_resultats_courses\\marathon_paris_2013\\Data\\Resultats marathon 2013.csv', 'r')		
csv_reader = csv.DictReader(inputResultsFile, delimiter=';', quotechar='|')
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

inputResultsFile.close()			

# affichage du nombre de coureurs par catégorie
print()	#Ligne vide pour visibilité
for nom_cat, cat in Course.categories.items():
	print(len(cat.liste_courreurs), "courreurs dans categorie", nom_cat)


# Calcul du nombre de courreurs arrivés en fonction du temps réel
# Pour tracer la courbe des arrivées
liste_courreurs_tries_temps_reel = sorted(Course.liste_courreurs, key = lambda x: x._temps_reel)
#Réccupération du premier et dernier temps
premier_temps_reel_s = liste_courreurs_tries_temps_reel[0]._temps_reel
dernier_temps_reel_s = liste_courreurs_tries_temps_reel[len(liste_courreurs_tries_temps_reel) - 1]._temps_reel

#Calcul de la durée en secondes entre premier et dernier
ecart_entre_premier_dernier = datetime.timedelta(hours=dernier_temps_reel_s.hour - premier_temps_reel_s.hour, minutes=dernier_temps_reel_s.minute - premier_temps_reel_s.minute, seconds=dernier_temps_reel_s.second - premier_temps_reel_s.second)
ecart_entre_premier_dernier_seconds = math.floor(ecart_entre_premier_dernier.total_seconds())

#Extraction des centiles, deciles, quartiles, médiane
#Décile
affiche_quantile(DECILE)
#Quartile
affiche_quantile(QUARTILE)
#Médiane
affiche_quantile(MEDIANE)

#On souhaite faire une courbe avec NOMBRE_VALEURS_GRAPHIQUE_X valeurs
unite_absisse = ecart_entre_premier_dernier_seconds / NOMBRE_VALEURS_GRAPHIQUE_X

print("premier_temps_reel_s",premier_temps_reel_s)
print("dernier_temps_reel_s",dernier_temps_reel_s)
print("ecart_entre_premier_dernier_seconds",ecart_entre_premier_dernier_seconds)
print("unite_absisse",unite_absisse)

with open('D:\\programmation\\Python\\statistiques_resultats_courses\\marathon_paris_2013\\nombre_courreurs_arrives_fonction_temps.csv', 'w') as nombre_courreurs_arrives_fonction_temps:
	#nombre_courreurs_arrives_fonction_temps.writerow(["Temps course","Nombre courreurs arrivés"])
   
	#spamwriter = csv.writer(nombre_courreurs_arrives_fonction_temps, delimiter='\t')
	
	
	i = 0
	while i <= NOMBRE_VALEURS_GRAPHIQUE_X:
		
		#Nombre de coureurs qui sont 
		courreurs_arrives = [x for x in liste_courreurs_tries_temps_reel if math.floor(datetime.timedelta(hours=x._temps_reel.hour - premier_temps_reel_s.hour, minutes=x._temps_reel.minute - premier_temps_reel_s.minute, seconds=x._temps_reel.second - premier_temps_reel_s.second).total_seconds()) <= int(i * unite_absisse)]
		
		#Temps de course
		premier_temps_reel_datetime = datetime.datetime(100,1,1,premier_temps_reel_s.hour,premier_temps_reel_s.minute,premier_temps_reel_s.second)
		temps_course = (premier_temps_reel_datetime + datetime.timedelta(0,int(i * unite_absisse))).time() # days, seconds, then other fields.
		
		row = "{};{}".format(len(courreurs_arrives),temps_course)
		
		print(row)
		
		row  = row  + "\n"

		nombre_courreurs_arrives_fonction_temps.write(row)
	
		i += 1

