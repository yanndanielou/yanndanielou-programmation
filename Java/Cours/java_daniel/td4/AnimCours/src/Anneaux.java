import java.awt.event.*;
import javax.swing.*;
import javax.swing.*;


public class Anneaux extends JFrame implements ActionListener
{
	Ardoise ardoise = new Ardoise();
	JButton arret = new JButton("arret");
	JButton reprise = new JButton("reprendre");
	
	Anneaux()
	{
		arret.setActionCommand("arreter");
		arret.addActionListener(ardoise);
		arret.addActionListener(this);
		
		reprise.setActionCommand("reprise");
		reprise.addActionListener(ardoise);
		reprise.addActionListener(this);
		reprise.setEnabled(false);
		
		JPanel boutons = new JPanel();
		boutons.setBackground(Color.WHITE);
		boutons.add(arret);
		boutons.add(arret);
		
		
		
	}
}
