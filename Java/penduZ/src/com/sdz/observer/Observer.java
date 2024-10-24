package com.sdz.observer;

import java.util.ArrayList;
import com.sdz.model.*;
import javax.swing.ImageIcon;

public interface Observer {
	public void update(String mot, int pts, String imgPath, int nbreMot);
	public void restart(String word);
	public void showScore( Score[] list);
	public void accueil();
}
