import javax.swing.*;

public class FenetreMenu extends JFrame
{
	JMenuBar barre;
	JMenu menu1, menu2;
	JMenuItem item11, item12, item21, item22;
	
	public FenetreMenu()
	{
		setTitle("Au menu ce Matin ...");
		setSize(300, 200);
		barre = new JMenuBar();
		setJMenuBar(barre);
		
		menu1 = new JMenu("Menu 1");
		barre.add(menu1);
		JMenuItem item11 = new JMenuItem("Entree 1 ml");
		menu1.add(item11);
		JMenuItem item12 = new JMenuItem("Entree 2 ml");
		menu1.add(item12);
		menu2 = new JMenu("Menu 2");
		barre.add(menu2);
		JMenuItem item21 = new JMenuItem("Entree 1 m2");
		menu2.add(item21);
		JMenuItem item22 = new JMenuItem("Entree 2 m2");
		menu2.add(item22);
	}
}
