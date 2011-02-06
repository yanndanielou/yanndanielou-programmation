package com.sdz.model;

import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import com.sdz.observer.Observable;
import com.sdz.observer.Observer;

public class Model implements Observable{

	private Score score;
	private Word mot;
	private ImageIcon image;
	private ScoreSerializer scoreSerializer;
	private ArrayList<Observer> listObserver = new ArrayList<Observer>();
	private int nombreMot = 0;
	
	public Model(){
		this.score = new Score();
		this.mot = new Word();
		this.image = new ImageIcon("images/pendu.jpg");
		this.scoreSerializer = new ScoreSerializer();
		
	}

	public Score getScore() {
		return score;
	}

	public Word getMot() {
		return mot;
	}
	
	public void assign(char c){
		
		if(this.mot.verifyWord(c) != -1){
			
			if(this.mot.isFinished()){
				this.score.initPoint(this.mot.getNombreErreur());
				this.score.setNombreMot(this.score.getNombreMot()+1);
				JOptionPane.showMessageDialog(null,
						                        "Vous avez trouvé le mot: " + this.mot.getWord() +
						                        " en " +
						                        this.mot.getNombreCoup() + " coups, avec " + this.mot.getNombreErreur() +
						                        " erreur(s)\n\t" +
						                        "Vous marquez donc " +
						                        this.score.getPointMarque() +
												" Pts\n\tVotre total de points est maintenant de " +
												this.score.getPoint()+ " Pts",
												"Résultat",
												JOptionPane.INFORMATION_MESSAGE);
				this.mot.initWord();
				this.mot.setNombreErreur(0);
				this.nombreMot++;
				
				this.restartObserver();
			}
			
			this.notifyObserver();
		}
		else{
			this.mot.setNombreErreur(this.mot.getNombreErreur()+1);
			this.notifyObserver();
			
			if(this.mot.getNombreErreur() == 7){
				
				JOptionPane.showMessageDialog(null,
						                        "Le mot était: \n\t" +
						                        this.mot.getWord(),
												"YOU LOSE", JOptionPane.NO_OPTION);
												
												
				
				if(this.scoreSerializer.isAccpeted(this.score)){
					String nom = "";
					nom  = (String)JOptionPane.showInputDialog(null,
										"Donnez votre pseudo :", "Félicitation",
										JOptionPane.QUESTION_MESSAGE);
					 
					
					this.score.setNom(nom);
					this.scoreSerializer.serialize();
					this.scoreObserver();
				}
				else{
					JOptionPane.showMessageDialog(null,
				            "Désolé... vous avez perdu...\n" +
				            "\n\n\t Vous n'avez pas assez de points pour enregistrer votre score ! \n" +
				            "Retentez votre chance à l'occasion !",
				            "YOU LOSE", JOptionPane.NO_OPTION);
					this.accueilObserver();
				}			
			}
			else{
				this.notifyObserver();
			}
		}			
	}
	
	public void assign(char[] c){
		if(this.mot.verifyWord(c) != -1){
			
			if(this.mot.isFinished()){
				this.score.initPoint(this.mot.getNombreErreur());
				this.score.setNombreMot(this.score.getNombreMot()+1);
				JOptionPane.showMessageDialog(null,
						                        "Vous avez trouvé le mot: " + this.mot.getWord() +
						                        " en " +
						                        this.mot.getNombreCoup() + " coups, avec " + this.mot.getNombreErreur() +
						                        " erreur(s)\n\t" +
						                        "Vous marquez donc " +
						                        this.score.getPointMarque() +
												" Pts\n\tVotre total de points est maintenant de " +
												this.score.getPoint()+ " Pts",
												"Résultat",
												JOptionPane.INFORMATION_MESSAGE);
				this.mot.initWord();
				this.mot.setNombreErreur(0);
				this.nombreMot++;
				this.restartObserver();
			}
			else
				this.notifyObserver();
		}
		else{
			this.mot.setNombreErreur(this.mot.getNombreErreur()+1);
			this.notifyObserver();
			
			if(this.mot.getNombreErreur() == 7){
				
				JOptionPane.showMessageDialog(null,
						                        "Le mot était: \n\t" +
						                        this.mot.getWord(),
												"YOU LOSE", JOptionPane.NO_OPTION);
												
												
				
				if(this.scoreSerializer.isAccpeted(this.score)){
					String nom = "";
					nom = (String)JOptionPane.showInputDialog(null,
										"Donnez votre pseudo :", "Félicitation",
										JOptionPane.QUESTION_MESSAGE);
					 
					this.score.setNom(nom);
					this.scoreSerializer.serialize();
					this.scoreObserver();
				}
				else{
					JOptionPane.showMessageDialog(null,
				            "Désolé... vous avez perdu...\n" +
				            "\n\n\t Vous n'avez pas assez de points pour enregistrer votre score ! \n" +
				            "Retentez votre chance à l'occasion !",
				            "YOU LOSE", JOptionPane.NO_OPTION);
					this.accueilObserver();
				}	
				this.mot.initWord();
				this.mot.setNombreErreur(0);
			}
			else{
				this.notifyObserver();
			}
		}		
	}

	public void reset(){
		this.mot.initWord();
		this.mot.setNombreErreur(0);
		this.nombreMot = 0;
		this.score = new Score();
	}
	
	public void addObserver(Observer obs) {
		this.listObserver.add(obs);
		this.notifyObserver();
	}


	public void deleteObserver() {
		this.listObserver = new ArrayList<Observer>();
	}


	public void notifyObserver() {
		 this.image = new ImageIcon("images/pendu"+this.mot.getNombreErreur()+"jpg");
		 
		for(Observer obs : this.listObserver)
			obs.update(this.mot.getSecretWord(), this.score.getPoint(), "images/pendu"+this.mot.getNombreErreur()+".jpg", this.nombreMot);
	}


	public void restartObserver() {
		for(Observer obs : this.listObserver)
			obs.restart(this.mot.getSecretWord());
	}


	public void scoreObserver() {
		for(Observer obs : this.listObserver)
			obs.showScore(this.scoreSerializer.getListeScore());		
	}
	
	public void accueilObserver(){
		for(Observer obs : this.listObserver)
			obs.accueil();
	}
	
	public Score[] getScores(){
		return this.scoreSerializer.getListeScore();
	}
}
