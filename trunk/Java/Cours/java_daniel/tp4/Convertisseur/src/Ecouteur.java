import java.awt.event.*;

public class Ecouteur implements MouseListener
{
	public Ecouteur()
	{
		Grille.Conv_Franc.addMouseListener(this);
		Grille.Conv_Euro.addMouseListener(this);
		Grille.Somme_Euro.addMouseListener(this);
		Grille.Somme_Franc.addMouseListener(this);
	}
	
	public void mouseClicked(MouseEvent e)
	{
		try
		{
			if (e.getSource() == Grille.Conv_Franc)
			{
				String Euro = Grille.Somme_Euro.getText();
				double Franc = Double.parseDouble(Euro) * 6.55957;
				String Text = "" + Franc;
				Grille.Somme_Franc.setText(Text);
			}
			if (e.getSource() == Grille.Conv_Euro)
			{
				String Franc = Grille.Somme_Franc.getText();
				double Euro = Double.parseDouble(Franc) / 6.55957;
				String Text = "" + Euro;
				Grille.Somme_Euro.setText(Text);			
			}
			if (e.getSource() == Grille.Somme_Euro)
			{
				Grille.Somme_Euro.setText("");
			}
			if (e.getSource() == Grille.Somme_Franc)
			{
				Grille.Somme_Franc.setText("");
			}
		}
		catch (NumberFormatException error)
		{
			Grille.Somme_Euro.setText("Entrez des Chiffres");
			Grille.Somme_Franc.setText("Entrez des Chiffres");
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}

