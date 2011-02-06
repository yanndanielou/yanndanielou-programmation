import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionListener;

import javax.swing.*;


public class Ardoise extends JPanel implements ActionListener
{
	int dep = 0;
	Timer declencheur;
	int largeur = 200, hauteur = 200;
	Ardoise()
	{
		setPreferredSize(new Dimension(largeur, hauteur));
		setBackground(Color.WHITE);
		declencheur = new Timer(100, this);
		declencheur.start();
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == declencheur)
		{
			repaint();
			dep = (dep - 1) % 10;
		}
		else
		{
			String commande = e.getActionCommand();
			if (commande.equals("arreter"))
				declencheur.stop();
			else if (commande.equals("reprendre"))
				declencheur.restart();
		}
	}
	public void paintComponent (Graphics g)
	{
		super.paintComponent(g);
		for (int i = dep - 5; i < largeur/2; i += 10)
		{
			for (int j = i; j < i + 5; j++)
			{
				if (j>0)
					g.drawOval(j, j, largeur - 2*j, hauteur - 2*j);
			}
		}
	}
}
