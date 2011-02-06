package com.sdz.vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTextArea;

public class AccueilPanel extends ZContainer{
	
	public AccueilPanel(Dimension dim) {
		super(dim);
		initPanel();
	}

	public void initPanel(){
		JLabel titre = new JLabel("Bienvenue dans le jeu du PENDU");
		titre.setHorizontalAlignment(JLabel.CENTER);
		titre.setFont(comics30);
		this.panel.add(titre, BorderLayout.NORTH);
		
		this.panel.add(new JLabel(new ImageIcon("images/accueil.jpg")), BorderLayout.CENTER);
		
		JTextArea texte = new JTextArea(	"Vous avez 7 coups pour trouver le mot caché! et si vous réussissez... et bien on recommence!\n" +
											" Plus vous avez trouvé de mots, plus votre score grandira!!! Alors à vous de jouer!\n" +
											"PROVERBE:\t \"Pas vu, pas pris!\n" +
                      						"\t  Pris! PENDU!!!!!!!!\"");
		texte.setFont(arial);
		texte.setEditable(false);
		texte.setBackground(Color.white);
		
		this.panel.add(texte, BorderLayout.SOUTH);
	}
	
}
