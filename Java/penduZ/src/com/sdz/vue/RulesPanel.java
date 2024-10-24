package com.sdz.vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTextArea;

public class RulesPanel extends ZContainer{

	public RulesPanel(Dimension dim) {
		super(dim);
		initPanel();
	}

	public void initPanel(){
		JLabel titre = new JLabel();
		titre.setFont(comics30);
		titre.setText("le jeu du PENDU :");
		titre.setHorizontalAlignment(JLabel.CENTER);
		
		this.panel.add(titre, BorderLayout.CENTER);
		
		this.panel.add(new JLabel(new ImageIcon("pendu.jpg")), BorderLayout.CENTER);
		
		JTextArea accueil = new JTextArea();
		accueil.setBackground(Color.white);
		accueil.setText(	"\n\n\nVous avez 7 coups pour trouver le mot caché ! Et si vous réussissez : on recommence !\n" +
							"Plus vous avez trouvé de mots, plus votre score grandira ! ! Alors à vous de jouer!\n" +
							"\n\nCOMPTE DES POINTS:\n\n\tMot trouvé sans erreur :.........100Pts\n\tMot trouvé avec 1 erreur :........50Pts\n\t" +
							"Mot trouvé avec 2 erreurs :......35Pts\n\tMot trouvé avec 3 erreurs :......25Pts\n\tMot trouvé avec 4 erreurs :......15Pts" +
							"\n\tMot trouvé avec 5 erreurs :......10Pts\n\tMot trouvé avec 6 erreurs :........5Pts\n\n\n" +
							"Je vous souhaite bien du plaisir.... \nEt, si vous pensez pouvoir trouver un mot en un coup, c'est que vous pensez que le dictionnaire est petit !\n" +
							"Hors, pour votre information, il comprend plus de 336 000 mots... Donc bonne chance!! ;)");
		accueil.setFont(arial);
		accueil.setEditable(false);
		this.panel.add(accueil, BorderLayout.SOUTH);
	}
	
}
