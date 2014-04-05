# -*-coding:Latin-1 -*

import csv
import datetime
import time

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
		self.temps_reel = getDateTimetimeFromStr(temps_reel)
		self.temps_officiel = getDateTimetimeFromStr(temps_officiel)
		self.attente_dans_sas = datetime.timedelta(hours=self.temps_reel.hour - self.temps_officiel.hour, minutes=self.temps_reel.minute - self.temps_officiel.minute, seconds=self.temps_reel.second - self.temps_officiel.second)


	
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
	nouveau_coureur = CourreurArrive(classement_officiel, dossard, nom, prenom, classement_categorie, categorie, temps_reel, temps_officiel)
		

# affichage du nombre de coureurs par catégorie
for nom_cat, cat in Course.categories.items():
	print(len(cat.liste_courreurs), "courreurs dans categorie", nom_cat)



#affichage du nombre d'hommes et de femmes
#print(len(CourreurArrive.liste_hommes), "hommes et ", len(CourreurArrive.liste_femmes), "femmes")
	

#Parcours de la liste des coureurs créés


