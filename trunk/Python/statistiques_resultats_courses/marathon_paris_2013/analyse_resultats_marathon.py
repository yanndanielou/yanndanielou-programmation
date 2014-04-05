# -*-coding:Latin-1 -*

import time

#def is_home(category):
#	if category.

#
class CourreurArrive:
	"""Classe représentant un coureur arrivé"""

	liste_coureurs = list()

	def __init__(self, classement_officiel, dossard, nom, prenom, classement_categorie, categorie, temps_reel, temps_officiel):
		"""Constructeur de CourreurArrive"""
		self.classement_officiel = classement_officiel
		self.dossard = dossard
		self.nom = nom
		self.prenom = prenom
		self.classement_categorie = classement_categorie
		self.categorie = categorie
		self.temps_reel = temps_reel
		self.temps_officiel = temps_officiel
		self.is_homme = 
		CourreurArrive.liste_coureurs.append(self)
		



#ouverture en lecture seule
fichier_csv_resultats = open("D:\\temp\\analyse_resultats_marathon\\Resultats marathon 2013.csv", "r")
lignes_contenu_fichier_csv_resultats = fichier_csv_resultats.readlines()
fichier_csv_resultats.close()


#parcours du fichier
for line in lignes_contenu_fichier_csv_resultats:
# pour debug: affichage du fichier
#	print(ligne)


	#extraction des différentes colonnes
	line_splited = line.split(';')
	
	if len(line_splited) == 8:
		classement_officiel = line_splited[0]
		dossard = line_splited[1]
		nom = line_splited[2]
		prenom = line_splited[3]
		classement_categorie = line_splited[4]
		categorie = line_splited[5]
		temps_reel = line_splited[6]
		temps_officiel = line_splited[7]
		
		#Création du coureur
		nouveau_coureur = CourreurArrive(classement_officiel, dossard, nom, prenom, classement_categorie, categorie, temps_reel, temps_officiel)
		
		
	






