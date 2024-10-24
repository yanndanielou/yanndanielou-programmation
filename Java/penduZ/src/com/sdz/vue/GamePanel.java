package com.sdz.vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.sdz.controler.Controler;
import com.sdz.model.Score;
import com.sdz.observer.Observable;
import com.sdz.observer.Observer;

public class GamePanel extends ZContainer implements Observer{

	private Dimension dimension = new Dimension();
	private int pts = 0;
	private ImageIcon img;
	private String mot;
	private JLabel 	nombreMot, 
					score,
					motSecret;
	private ImageLabel imageLabel;
	private JButton bouton[];
	
	
	private Controler controler;
	
	public GamePanel(Dimension dim, Observable mod) {
		super(dim);
		this.controler = new Controler(mod);
		initPanel();
	}

	protected void initPanel() {
		JPanel leftContent = new JPanel();
		JPanel rightContent = new JPanel();
		//System.out.println("Size : " + this.dimension.getWidth());
		//Dimension dim = new Dimension((int)(this.dimension.getWidth()/2), (int)this.dimension.getHeight());
		
		JPanel head = new JPanel();
		this.imageLabel = new ImageLabel(); 
		this.imageLabel.setPreferredSize(new Dimension(300, 300));
		this.imageLabel.setVerticalAlignment(JLabel.CENTER);
		this.nombreMot = new JLabel();
		this.score = new JLabel(); 
		
		this.dimension = new Dimension(400, 530); 
		rightContent.setPreferredSize(this.dimension);
		rightContent.add(this.imageLabel, BorderLayout.CENTER);
		rightContent.setBackground(Color.white);
		
		leftContent.setPreferredSize(this.dimension);
		
		Dimension dim = new Dimension(410, 200);
		head.setPreferredSize(new Dimension(410, 100));
		
		this.nombreMot.setText("Nombre de mot trouvé : 0 ");
		this.nombreMot.setPreferredSize(new Dimension(300, 20));
		this.nombreMot.setHorizontalAlignment(JLabel.CENTER);
		this.nombreMot.setFont(arial);
		
		head.add(this.nombreMot, BorderLayout.NORTH);
		
		this.score.setText("Votre score actuel est de : 0 ");
		this.score.setPreferredSize(new Dimension(300, 20));
		this.score.setHorizontalAlignment(JLabel.CENTER);
		this.score.setFont(arial);
		head.add(this.score, BorderLayout.SOUTH);
		head.setBackground(Color.white);
		
		this.motSecret = new JLabel("fffffffffffffffffff");
		this.motSecret.setPreferredSize(new Dimension(400, 60));
		this.motSecret.setForeground(Color.blue);
		this.motSecret.setFont(comics30);
		this.motSecret.setHorizontalAlignment(JLabel.CENTER);
		head.add(this.motSecret);
		
		JPanel body = new JPanel();
		body.setPreferredSize(dim);
		body.setBackground(Color.white);
		char carac[] = {'a', 'b','c', 'd', 'e', 'f',
						'g', 'h', 'i', 'j', 'k', 'l', 
						'm', 'n', 'o', 'p', 'q', 'r', 
						's', 't' ,'u', 'v', 'w', 'x', 
						'y', 'z'};
		
		BoutonListener bl = new BoutonListener();
		Dimension buttonDimension = new Dimension(50,30);
		this.bouton = new JButton[26];
		int i = 0;
		for(char c : carac){
			this.bouton[i] = new JButton(String.valueOf(c).toUpperCase());
			bouton[i].addActionListener(bl);
			bouton[i].setPreferredSize(buttonDimension);
			body.add(bouton[i]);
			i++;
		}
		
		leftContent.add(head, BorderLayout.NORTH);
		leftContent.add(body, BorderLayout.CENTER);
		leftContent.setBackground(Color.white);
		
		this.panel.add(leftContent);
		this.panel.add(rightContent);
		
	}

	
	public void restart(String word) {		
		for(JButton bout : this.bouton)
			bout.setEnabled(true);
		
		this.imageLabel.setImagePath("images/pendu0.jpg");
		this.motSecret.setText(word);
	}

	public void update(String mot, int pts, String path, int nbreMot) {
		this.motSecret.setText(mot);
		this.score.setText("Votre score actuel est de : " +pts);
		this.imageLabel.setImagePath(path);
		this.nombreMot.setText("Nombre de mot trouvé : " +nbreMot);	
		
	}
	public void accueil(){}
	public void showScore(Score[] list) {}

	class BoutonListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			((JButton)e.getSource()).setEnabled(false);
			controler.control(((JButton)e.getSource()).getText().charAt(0));
		}		
	}
	
}
