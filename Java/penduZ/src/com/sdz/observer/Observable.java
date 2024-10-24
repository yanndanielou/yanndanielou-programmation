package com.sdz.observer;

import com.sdz.model.Score;

public interface Observable {
	public void addObserver(Observer obs);
	public void notifyObserver();
	public void restartObserver();
	public void scoreObserver();
	public void deleteObserver();
	public void accueilObserver();
	public Score[] getScores();
	public void reset();
}
