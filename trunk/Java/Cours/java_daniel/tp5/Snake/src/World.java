import java.awt.*; import javax.swing.*;

public class World extends JFrame // VUE
{
	public JPanel panel = new JPanel();

	public World(int h, int l)
	{
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		//panel.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.setPreferredSize(new Dimension (h, l));
		panel.setBackground(Color.WHITE);
		getContentPane().add(panel);
		setResizable(false);
		setTitle("Snake");
		setLocation(0, 0);
		setDefaultCloseOperation(2);
		pack(); // Dimensionne
		setVisible(true);
	}
}