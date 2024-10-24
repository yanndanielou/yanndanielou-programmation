package com.sdz.model;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JOptionPane;

public class ScoreSerializer {
	private Score[] listeScore = new Score[10];
	
	public ScoreSerializer(){
		try {
			File file = new File("file/score.scr");
			if(file.length() > 0)
			{
				ObjectInputStream ois = new ObjectInputStream(
											new BufferedInputStream(
												new FileInputStream(
														file)));
				
				listeScore = (Score[])ois.readObject();
				ois.close();
			}
			else{
				System.out.println("On passe dans le else");
				for(int i = 0; i < 10; i++){
					listeScore[i] = new Score();
				}
			}
			
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Erreur de chargement depuis le fichier score !\n" + e.getCause()+"\n", "ERREUR", JOptionPane.ERROR_MESSAGE);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Erreur de chargement depuis le fichier score !\n" + e.getCause()+"\n", "ERREUR", JOptionPane.ERROR_MESSAGE);
		} catch (ClassNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Erreur de chargement depuis le fichier score !\n" + e.getCause()+"\n", "ERREUR", JOptionPane.ERROR_MESSAGE);
		}	
	}
	
	public boolean isAccpeted(Score score){
		boolean bok = false;
				
		for(int i = 0; i < listeScore.length; i++){
			Score scr = new Score();
			scr = listeScore[i];
			System.out.println("Score : " + scr.toString());
			
			if(score.getPoint() >= scr.getPoint()){
				bok = true;
				listeScore[i] = score;
				score = scr;
			}
		}		
		return bok;
	}
	
	
	public void serialize(){
		try {
			ObjectOutputStream oos = new ObjectOutputStream(
										new BufferedOutputStream(
												new FileOutputStream(
														new File("file/score.scr"))));
			oos.writeObject(listeScore);
			oos.close();
			
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Erreur d'enregistrement dans le fichier score !", "ERREUR", JOptionPane.ERROR_MESSAGE);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Erreur d'enregistrement dans le fichier score !", "ERREUR", JOptionPane.ERROR_MESSAGE);
		}
		
	}
	
	public Score[] getListeScore(){
		return this.listeScore;
	}
	
}
