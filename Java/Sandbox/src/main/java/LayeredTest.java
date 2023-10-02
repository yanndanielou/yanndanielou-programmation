
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class LayeredTest extends JPanel {

	public LayeredTest() {

		JPanel content = new JPanel();
		content.setBackground(Color.red);
		content.setPreferredSize(new Dimension(2048, 2048));
		content.setBounds(0, 0, 2048, 2048);
		JPanel control = new JPanel();
		control.setBackground(Color.blue);
		control.setPreferredSize(new Dimension(200, 50));
		control.setBounds(0, 0, 100, 50);

		JScrollPane scroll = new JScrollPane(content);
		scroll.setBounds(0, 0, 400, 400);

		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setPreferredSize(new Dimension(400, 400));

	    layeredPane.add(scroll, 50, 0);
	    layeredPane.add(control, 51, 0);

		this.add(layeredPane, BorderLayout.CENTER);
	}

	public static void main(String[] args) {
		// Create and set up the window.
		JFrame frame = new JFrame("Test - Very lulz");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocation(100, 100);
		frame.setSize(400, 400);

		// Create and set up the content pane.
		frame.setContentPane(new LayeredTest());

		// Display the window.
		frame.pack();
		frame.setVisible(true);
	}
}