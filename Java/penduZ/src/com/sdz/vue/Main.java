package com.sdz.vue;

import com.sdz.model.Model;
import com.sdz.observer.Observable;

public class Main {
	public static void main(String[] args){
		Observable model = new Model();
		Fenetre fen = new Fenetre(model);
		fen.setVisible(true);
	}
}
