defaut: livraison

livraison:
	cd /home/yann/Java/projetPoker
	cd Serveur
	rm -f Serveur/*.class
	rm -f Serveur/*.ja*
	cd ..
	cd Joueur/
	pwd
	rm -f Joueur/*.class
	rm -f Joueur/*.ja*
	cd ..
	cp Src/*.java Serveur
	cd /home/yann/Java/projetPoker/Serveur
	pwd
	cd Serveur
	rm -f Serveur/Bouton.java Serveur/Client.java Serveur/Controleur.java Serveur/Fenetre.java Serveur/InstanceJoueur.java Serveur/Langues.java Serveur/Panneau.java Serveur/Preference.java
	javac Serveur/*.java
	jar cfm Serveur/Serveur.jar Serveur/Manifest.txt Serveur/*.class
	cd ..
	cp Src/*.java Joueur
	cd Joueur
	rm -f Joueur/CalculMain.java Joueur/Carte.java Joueur/InstanceServeur.java Joueur/Joueur.java Joueur/Log.java Joueur/Paquet.java Joueur/Partie.java Joueur/River.java Joueur/Scores.java Joueur/Serveur.java
	javac Joueur/*.java
	jar cfm Joueur/Joueur.jar Joueur/Manifest.txt Joueur/*.class

archivage : 
	zip archivename.zip Src/*.java

netoyage :
	rm Src/*.*~ Src/*.class