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

def affiche_quantile(valeur_quantile, liste_courreurs_tries_temps_reel):
	print()		#Ligne vide pour visibilit�
	i = 1
	while(i < valeur_quantile):
		print(math.floor((i / valeur_quantile) * 100), "% de la population",liste_courreurs_tries_temps_reel[len(liste_courreurs_tries_temps_reel) // valeur_quantile * i])
		i = i + 1


def comparerCoureursParTempsReel(courreur1, courreur2):
	#"""Trie deux coureurs en fonction de leur temps r�el"""
    if courreur1.temps_reel < courreur2.temps_reel:
        return -1
    elif courreur1.temps_reel > courreur2.temps_reel:
        return 1
    else:
        return 0

def getDateTimetimeFromStr(time_str):
#Cas o� la dur�e est au format str (hh:mm:ss)
	if isinstance(time_str, str):
		#d�coupage des heures minutes secondes
		duree_split = time_str.split(":")
		
		hours = int(duree_split[0])
		minutes = int(duree_split[1])
		seconds = int(duree_split[2])
		
		return datetime.time(hours, minutes, seconds)
	else:
		print(time_str, "should be str and is", type(time_str))
	

class Course:
	"""Classe repr�sentant une course"""

	categories = dict()
	liste_courreurs = list()
	
	def GetCategorie(cls, nom_categorie):
		"""M�thode de classe permettant de retourner une cat�gorie en fonction de son nom. Cr�e la cat�gorie si elle n'existe pas"""
	
		if nom_categorie in Course.categories.keys():
			return Course.categories[nom_categorie]
		else:
			nouvelle_categorie = Categorie(nom_categorie)
			Course.categories[nom_categorie] = nouvelle_categorie
			return nouvelle_categorie		
	GetCategorie = classmethod(GetCategorie)
	
	def CreerCourreur(cls, classement_officiel, dossard, nom, prenom, classement_categorie, categorie, temps_reel, temps_officiel):
		"""Cr�ation d'un courreur"""
	
		nouveau_courreur = CourreurArrive(classement_officiel, dossard, nom, prenom, classement_categorie, categorie, temps_reel, temps_officiel)
		Course.liste_courreurs.append(nouveau_courreur)
		
	CreerCourreur = classmethod(CreerCourreur)
	



class Categorie:
	"""Classe repr�sentant une cat�gorie"""	
	def __init__(self, nom_categorie):
		"""Constructeur d'une cat�gorie"""
		self._id = nom_categorie
		self._homme = "homme" in nom_categorie.lower()
		self._liste_courreurs = list()
		self._liste_courreurs_par_temps_reel = list()
		self._liste_courreurs_par_temps_reel_est_triee = False
		
		print("Cr�ation de la cat�gorie", nom_categorie, " qui est un homme:", self._homme)
		
	def ajouterCourreur(self, courreur):
		"""Ajout d'un courreur dans une cat�gorie"""
		self._liste_courreurs.append(courreur)
		self._liste_courreurs_par_temps_reel_est_triee = False
		
	def getListeCoureurTrieeParTempsReel(self):
	
		if self._liste_courreurs_par_temps_reel_est_triee == False:
			#trie de la liste
			self._liste_courreurs_par_temps_reel = sorted(self._liste_courreurs, key = lambda x: x._temps_reel)
	
			self._liste_courreurs_par_temps_reel_est_triee = True
			
		return self._liste_courreurs_par_temps_reel
	
	
#
class CourreurArrive:
	"""Classe repr�sentant un coureur arriv�"""


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
		"""Quand on entre notre objet dans l'interpr�teur"""
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
	prenom = row["PR�NOM"]
	classement_categorie = row["CLASS. CAT�GORIE"]
	categorie = row["CAT�GORIE"]
	temps_reel = row["TEMPS R�EL"]
	temps_officiel = row["TEMPS OFFICIEL"]

	#Cr�ation du coureur
	nouveau_coureur = Course.CreerCourreur(classement_officiel, dossard, nom, prenom, classement_categorie, categorie, temps_reel, temps_officiel)

inputResultsFile.close()			

# affichage du nombre de coureurs par cat�gorie
print()	#Ligne vide pour visibilit�
for nom_cat, cat in Course.categories.items():
	print(len(cat._liste_courreurs), "courreurs dans categorie", nom_cat)


# Calcul du nombre de courreurs arriv�s en fonction du temps r�el
# Pour tracer la courbe des arriv�es
liste_courreurs_tries_temps_reel = sorted(Course.liste_courreurs, key = lambda x: x._temps_reel)
#R�ccup�ration du premier et dernier temps
premier_temps_reel_s = liste_courreurs_tries_temps_reel[0]._temps_reel
dernier_temps_reel_s = liste_courreurs_tries_temps_reel[len(liste_courreurs_tries_temps_reel) - 1]._temps_reel

#Calcul de la dur�e en secondes entre premier et dernier
ecart_entre_premier_dernier = datetime.timedelta(hours=dernier_temps_reel_s.hour - premier_temps_reel_s.hour, minutes=dernier_temps_reel_s.minute - premier_temps_reel_s.minute, seconds=dernier_temps_reel_s.second - premier_temps_reel_s.second)
ecart_entre_premier_dernier_seconds = math.floor(ecart_entre_premier_dernier.total_seconds())

#Extraction des centiles, deciles, quartiles, m�diane
#D�cile
affiche_quantile(DECILE, liste_courreurs_tries_temps_reel)
#Quartile
affiche_quantile(QUARTILE, liste_courreurs_tries_temps_reel)
#M�diane
affiche_quantile(MEDIANE, liste_courreurs_tries_temps_reel)

#On souhaite faire une courbe avec NOMBRE_VALEURS_GRAPHIQUE_X valeurs
unite_absisse = ecart_entre_premier_dernier_seconds / NOMBRE_VALEURS_GRAPHIQUE_X

print("premier_temps_reel_s",premier_temps_reel_s)
print("dernier_temps_reel_s",dernier_temps_reel_s)
print("ecart_entre_premier_dernier_seconds",ecart_entre_premier_dernier_seconds)
print("unite_absisse",unite_absisse)

with open('D:\\programmation\\Python\\statistiques_resultats_courses\\marathon_paris_2013\\nombre_courreurs_arrives_fonction_temps.csv', 'w') as nombre_courreurs_arrives_fonction_temps:

	#Nom des colonnes
	nombre_courreurs_arrives_fonction_temps.write("Temps Reel;Nombre courreurs arriv�s;Nombre courreurs arriv�s cumul�")
	
	for cats in Course.categories.keys():
		nombre_courreurs_arrives_fonction_temps.write(";Nombre " + cats + " arriv�s;Nombre " + cats + " arriv�s cumul�")
	
	nombre_courreurs_arrives_fonction_temps.write("\n")
	
	i = 0
	while i <= NOMBRE_VALEURS_GRAPHIQUE_X:
		
		#Temps de course
		premier_temps_reel_datetime = datetime.datetime(100,1,1,premier_temps_reel_s.hour,premier_temps_reel_s.minute,premier_temps_reel_s.second)
		temps_course = (premier_temps_reel_datetime + datetime.timedelta(0,int(i * unite_absisse))).time() # days, seconds, then other fields.
		row = "{}".format(temps_course)
		
		#coureurs qui sont arriv�s
		if i > 1:
			courreurs_arrives = [x for x in liste_courreurs_tries_temps_reel if math.floor(datetime.timedelta(hours=x._temps_reel.hour - premier_temps_reel_s.hour, minutes=x._temps_reel.minute - premier_temps_reel_s.minute, seconds=x._temps_reel.second - premier_temps_reel_s.second).total_seconds()) <= int(i * unite_absisse) and math.floor(datetime.timedelta(hours=x._temps_reel.hour - premier_temps_reel_s.hour, minutes=x._temps_reel.minute - premier_temps_reel_s.minute, seconds=x._temps_reel.second - premier_temps_reel_s.second).total_seconds()) > int((i - 1) * unite_absisse)]
			nombre_courreurs_arrives = len(courreurs_arrives)
		else:
			nombre_courreurs_arrives = 0
			
		courreurs_arrives_cumule = [x for x in liste_courreurs_tries_temps_reel if math.floor(datetime.timedelta(hours=x._temps_reel.hour - premier_temps_reel_s.hour, minutes=x._temps_reel.minute - premier_temps_reel_s.minute, seconds=x._temps_reel.second - premier_temps_reel_s.second).total_seconds()) <= int(i * unite_absisse)]
		nombre_courreurs_arrives_cumule = len(courreurs_arrives_cumule)
		
		row += ";{};{}".format(nombre_courreurs_arrives, nombre_courreurs_arrives_cumule)
		
		#Trie par cat�gorie
		for cats in Course.categories.values():

			if nombre_courreurs_arrives > 0:
				courreurs_arrives_cat = [x for x in courreurs_arrives if x._categorie == cats]
				nombre_courreurs_arrives_cat = len(courreurs_arrives_cat)
			else:
				nombre_courreurs_arrives_cat = 0
			
			courreurs_arrives_cat_cumule = [x for x in courreurs_arrives_cumule if x._categorie == cats]
			nombre_courreurs_arrives_cat_cumule = len(courreurs_arrives_cat_cumule)
		
			row += ";{};{}".format(nombre_courreurs_arrives_cat, nombre_courreurs_arrives_cat_cumule)
	
		print(row)
		
		nombre_courreurs_arrives_fonction_temps.write(row + "\n")
	
		i += 1

