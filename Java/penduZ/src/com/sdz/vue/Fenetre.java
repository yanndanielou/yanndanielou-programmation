package com.sdz.vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import com.sdz.model.Model;
import com.sdz.model.Score;
import com.sdz.observer.Observable;
import com.sdz.observer.Observer;

public class Fenetre extends JFrame implements Observer{

	private JMenuBar menu = null;

	  private JMenu fichier = null;
	  private JMenuItem nouveau = null;
	  private JMenuItem score = null;
	  private JMenuItem quitter = null
	                              ;
	  private JMenu apropos = null;
	  private JMenuItem apropos2 = null;
	  private JMenuItem rules = null;
	  private JPanel conteneur = new JPanel();
	  private Dimension size;
	  private Observable model;
	  
	  private char[] lettreTab = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i',
              'j',
              'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's',
              't',
              'u', 'v', 'w', 'x', 'y', 'z'};
	  
	public Fenetre(Observable obs){
		this.setTitle("Le PENDU!...");
	    this.setSize(900, 600);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    this.setLocationRelativeTo(null);
	    this.setResizable(false);
	    
	    this.model = obs;
	    this.model.addObserver(this);
	    this.size = new Dimension(this.getWidth(), this.getHeight());
	    
	    menu = new JMenuBar();

	    fichier = new JMenu("Fichier");
	    fichier.setMnemonic('f');
	    //fichier.addActionListener(this);

	    nouveau = new JMenuItem("Nouveau");
	    nouveau.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,
	                                                  InputEvent.CTRL_MASK));
	    nouveau.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				conteneur.removeAll();
				GamePanel gp = new GamePanel(size, model);
				model.addObserver(gp);
				conteneur.add(gp.getPanel(), BorderLayout.CENTER);
				conteneur.revalidate();
				initModel();
			}	    	
	    });


	    score = new JMenuItem("Score");
	    score.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R,
	                                                KeyEvent.CTRL_MASK));
    score.addActionListener(new ActionListener(){

		public void actionPerformed(ActionEvent arg0) {
			conteneur.removeAll();
			conteneur.add(new ScorePanel(size, model.getScores()).getPanel(), BorderLayout.CENTER);
			conteneur.revalidate();
			model.reset();
		}
    	
    });

	    quitter = new JMenuItem("Quitter");
	    quitter.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W,
	                                                  KeyEvent.CTRL_MASK));
	    quitter.addActionListener(new ActionListener(){
	    	public void actionPerformed(ActionEvent e){
	    		System.exit(0);
	    	}
	    });

	    fichier.add(nouveau);
	    fichier.add(score);
	    fichier.addSeparator();
	    fichier.add(quitter);

	    apropos = new JMenu("À propos");
	    apropos.setMnemonic('o');
	    //apropos.addActionListener(this);

	    rules = new JMenuItem("Règles du jeu");
	    rules.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				conteneur.removeAll();
				conteneur.add(new RulesPanel(size).getPanel(), BorderLayout.CENTER);
				conteneur.revalidate();
				model.reset();
			}	    	
	    });

	    apropos2 = new JMenuItem("   ?   ");
	    apropos2.addActionListener(new ActionListener(){
	    	public void actionPerformed(ActionEvent e){
	    		JOptionPane.showMessageDialog(null,
							    		          "Créateur: Cysboy.\nLicense: Freeware.\nCopyright: cysboy@sdz.com",
							    		          "Information", JOptionPane.NO_OPTION);
	    		conteneur.removeAll();
	    		conteneur.add(new AccueilPanel(size).getPanel());
	    		conteneur.revalidate();
	    		model.reset();
	    	}
	    });

	    apropos.add(rules);
	    apropos.add(apropos2);

	    menu.add(fichier);
	    menu.add(apropos);
	    
	    this.conteneur.setPreferredSize(this.size );
	    this.conteneur.setBackground(Color.white);
	    this.conteneur.add(new AccueilPanel(this.size).getPanel());
	    this.setContentPane(this.conteneur);
	    
	    this.setJMenuBar(menu);
	}
	
	public void showScore(Score[] list) {
		conteneur.removeAll();
		conteneur.add(new ScorePanel(this.size, list).getPanel(), BorderLayout.CENTER);
		conteneur.revalidate();
		model.reset();
	}
	
	public void accueil(){
		System.out.println("Mis a jour accueil");
		conteneur.removeAll();
		conteneur.add(new AccueilPanel(size).getPanel(), BorderLayout.CENTER);
		conteneur.revalidate();
	}

	private void initModel(){
		this.model = new Model();
		this.model.addObserver(this);
	}
	public void update(String mot, int pts, String imgPath, int nbreMot) {}
	public void restart(String word) {}

	

}
