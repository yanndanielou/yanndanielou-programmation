import javax.swing.*;

public class Fenetre_1 extends JFrame
{
	String Title;
		
	public Fenetre_1(String Title)
	{
		Ecouteur ecouteur = new Ecouteur(Title);
		this.Title = Title;
		setTitle(Title);
		setBounds(100, 100, 100, 100); // taille et coordonees
		addMouseListener(ecouteur);
	}
}
