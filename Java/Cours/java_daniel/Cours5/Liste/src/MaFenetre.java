import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;


public class MaFenetre extends JFrame implements ListSelectionListener
{
	JList maListe;
	String [] couleur = {"rose", "jaune", "vert"};
	JPanel ardoise;
	
	public MaFenetre()
	{
		setTitle("Liste");
		setSize(300,200);
		Container contenu = getContentPane();
		maListe = new JList(couleur);
		maListe.setSelectedIndex(1);
		maListe.addListSelectionListener(this);
		contenu.add(maListe,"West");
		ardoise = new JPanel();
		ardoise.setBackground(Color.white);
		contenu.add(ardoise, "Center");	
	}
	
	public void valueChanged (ListSelectionEvent e)
	{
		if (((String) maListe.getSelectedValue()) == "rose")
			ardoise.setBackground(Color.pink);
		else if (((String) maListe.getSelectedValue()) == "jaune")
			ardoise.setBackground(Color.yellow);
		else if (((String) maListe.getSelectedValue()) == "vert")
			ardoise.setBackground(Color.green);
	}
}
