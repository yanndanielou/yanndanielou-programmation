import java.awt.*; import javax.swing.*;

public class Drawing extends JFrame
{
	public JPanel Draw = new JPanel();
	
	public Drawing()
	{
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		Draw.setAlignmentX(Component.CENTER_ALIGNMENT);
		Draw.setPreferredSize(new Dimension (500, 500));
		Draw.setBackground(Color.WHITE);
		getContentPane().add(Draw);
		setResizable(false);
		setTitle("Dessin en Clavier");
		setLocation(300, 50);
		setDefaultCloseOperation(2);
		pack(); // Dimensionne
		setVisible(true);
	}
}
