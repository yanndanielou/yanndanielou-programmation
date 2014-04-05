# -*-coding:Latin-1 -*

import time

class Resultat:

	categories = dict()
	liste_courreurs = list()
	
	def GetCategorie(cls, nom_categorie):
		"""Méthode de classe permettant de retourner une catégorie en fonction de son nom. Crée la catégorie si elle n'existe pas"""
	
		if nom_categorie in Resultat.categories.keys():
			return Resultat.categories[nom_categorie]
		else:
			nouvelle_categorie = Categorie(nom_categorie)
			Resultat.categories[nom_categorie] = nouvelle_categorie
			return nouvelle_categorie		
	GetCategorie = classmethod(GetCategorie)
	
	def CreerCourreur(cls, classement_officiel, dossard, nom, prenom, classement_categorie, categorie, temps_reel, temps_officiel):
		"""Création d'un courreur"""
	
		nouveau_courreur = CourreurArrive(classement_officiel, dossard, nom, prenom, classement_categorie, categorie, temps_reel, temps_officiel)
		Resultat.liste_courreurs.append(nouveau_courreur)
		
	CreerCourreur = classmethod(CreerCourreur)
	



class Categorie:
	"""Classe représentant une catégorie"""	
	def __init__(self, nom_categorie):
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
		self.categorie = Resultat.GetCategorie(categorie)
		self.categorie.ajouterCourreur(self)
		self.temps_reel = temps_reel
		self.temps_officiel = temps_officiel


			
# Programme principal			
			
				
			

#ouverture et lecture du fichier en lecture seule
fichier_csv_resultats = open("D:\\programmation\\Python\\statistiques_resultats_courses\\marathon_paris_2013\\Data\\Resultats marathon 2013.csv", "r")
lignes_contenu_fichier_csv_resultats = fichier_csv_resultats.readlines()
fichier_csv_resultats.close()


#parcours du contenu extrait du fichier
for line in lignes_contenu_fichier_csv_resultats:
# pour debug: affichage du fichier
#	print(line)

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
		

# affichage du nombre de coureurs par catégorie
for nom_cat, cat in Resultat.categories.items():
	print(len(cat.liste_courreurs), "courreurs dans categorie", nom_cat)



#affichage du nombre d'hommes et de femmes
#print(len(CourreurArrive.liste_hommes), "hommes et ", len(CourreurArrive.liste_femmes), "femmes")
	

#Parcours de la liste des coureurs créés


