import java.awt.*; import javax.swing.*;


public class ListeProg extends JFrame
{
	static String[] Languages = {"C", "C++", "Java", "Basic"};
	static JList My_List = new JList(Languages);
	JPanel My_Panel = new JPanel();
	
	
	public ListeProg()
	{
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		My_List.setAlignmentX(Component.CENTER_ALIGNMENT);
		getContentPane().add(My_List);
		My_Panel.setAlignmentX(Component.CENTER_ALIGNMENT);
		My_Panel.setPreferredSize(new Dimension (500, 500));
		My_Panel.setBackground(Color.WHITE);
		getContentPane().add(My_Panel);

		setResizable(false);
		setTitle("Title");
		setLocation(300, 50);
		setDefaultCloseOperation(2);
		pack(); // Dimensionne
		setVisible(true);
	}
}
