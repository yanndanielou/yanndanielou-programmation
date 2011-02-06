import java.awt.event.*;

public class Ecouteur extends MouseAdapter // implements MouseListener oblige à redéfinir tt les méthodes  
{
	String Title;
		
	public Ecouteur(String Title)
	{
		this.Title = Title;
	}
	
	public void mousePressed(MouseEvent e)
	{
		System.out.println("Fenetre " + Title + " : Cliqué, posx = " + e.getX() + ", posy = " + e.getY());
	}
	
	public void mouseReleased(MouseEvent e)
	{
		System.out.println("Fenetre " + Title + " : Relaché, posx = " + e.getX() + ", posy = " + e.getY());
	}
}
