import java.awt.*; import javax.swing.*;

public class Grille extends JFrame
{
	static JTextField Somme_Euro = new JTextField("Tapez");
	JLabel Label_Somme_Euro = new JLabel("Somme en Euro");
	static JButton Conv_Franc = new JButton("Conversion en Franc");
	static JButton Conv_Euro = new JButton("Conversion en Euro");
	static JTextField Somme_Franc = new JTextField("Tapez");
	JLabel Label_Somme_Franc = new JLabel("Somme en Franc");
	
	public Grille()
	{		
		Somme_Euro.setPreferredSize(new Dimension(70, 70));
		setLayout(new GridLayout(3, 2));
		add(Somme_Euro);
		add(Label_Somme_Euro);
		add(Conv_Franc);
		add(Conv_Euro);
		add(Somme_Franc);
		add(Label_Somme_Franc);
		//Somme_Franc.setEditable(false);
		setTitle("Convertisseur");
		setLocation(300, 50);
		setDefaultCloseOperation(2);
		pack(); // Dimensionne
		show();
	}
}
