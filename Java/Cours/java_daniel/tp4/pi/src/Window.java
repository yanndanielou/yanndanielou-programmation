import java.awt.*; import javax.swing.*;

public class Window extends JFrame
{
	static JLabel Info = new JLabel("Info");
	static JComboBox Combo_Box = new JComboBox();
	static JButton Clear = new JButton("Clear");
	static JPanel Drawing = new JPanel();
	
	public Window()
	{		
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		Info.setAlignmentX(Component.CENTER_ALIGNMENT);
		getContentPane().add(Info);
		Combo_Box.setAlignmentX(Component.CENTER_ALIGNMENT);
		Combo_Box.addItem(100);
		Combo_Box.addItem(500);
		Combo_Box.addItem(1000);
		getContentPane().add(Combo_Box);
		Drawing.setAlignmentX(Component.CENTER_ALIGNMENT);
		getContentPane().add(Drawing);
		Clear.setAlignmentX(Component.CENTER_ALIGNMENT);
		Drawing.setPreferredSize(new Dimension (500, 500));
		Drawing.setBackground(Color.WHITE);
		getContentPane().add(Clear);
		setResizable(false);
		setTitle("Pi");
		setLocation(300, 50);
		setDefaultCloseOperation(2);
		pack(); // Dimensionne
		show();
	}
}
