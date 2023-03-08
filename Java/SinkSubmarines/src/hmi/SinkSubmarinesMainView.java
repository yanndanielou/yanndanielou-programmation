package hmi;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SinkSubmarinesMainView extends JFrame {

	private static final Logger LOGGER = LogManager.getLogger(SinkSubmarinesMainView.class);

	public SinkSubmarinesMainView(String name) {
		super(name);
		setResizable(false);
	}

	/**
	 * Create the GUI and show it. For thread safety, this method is invoked from
	 * the event dispatch thread.
	 */
	public void createAndShowGUI() {

		/* Use an appropriate Look and Feel */
		try {
			// UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
		} catch (UnsupportedLookAndFeelException ex) {
			ex.printStackTrace();
		} catch (IllegalAccessException ex) {
			ex.printStackTrace();
		} catch (InstantiationException ex) {
			ex.printStackTrace();
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}
		/* Turn off metal's use of bold fonts */
		UIManager.put("swing.boldMetal", Boolean.FALSE);

		final int window_width = 800;
		// Create and set up the window.
		Container pane = this.getContentPane();
		pane.setLayout(null);
		Insets insets = pane.getInsets();

		JPanel topPanel = new JPanel();
		topPanel.setSize(window_width, 100);
		pane.add(topPanel);
		topPanel.setBackground(Color.BLACK);
		topPanel.setBounds(0, 0, topPanel.getSize().width, topPanel.getSize().height);

		JPanel skyPanel = new JPanel();
		skyPanel.setSize(window_width, 200);
		pane.add(skyPanel);
		skyPanel.setBackground(Color.CYAN);
		skyPanel.setBounds(0, 100, skyPanel.getSize().width, skyPanel.getSize().height);

		JPanel allieBoatPanel = new JPanel();
		allieBoatPanel.setSize(window_width, 50);
		pane.add(allieBoatPanel);
		allieBoatPanel.setBackground(Color.PINK);
		skyPanel.setBounds(0, 300, allieBoatPanel.getSize().width, allieBoatPanel.getSize().height);

		JPanel waterPanel = new JPanel();
		waterPanel.setSize(window_width, 500);
		pane.add(waterPanel);
		waterPanel.setBackground(Color.BLUE);
		skyPanel.setBounds(0, 800, waterPanel.getSize().width, waterPanel.getSize().height);

		this.setSize(window_width, 1300);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		/*
		 * pane. Dimension size = b1.getPreferredSize(); b1.setBounds(25 + insets.left,
		 * 5 + insets.top, size.width, size.height); size = b2.getPreferredSize();
		 * b2.setBounds(55 + insets.left, 40 + insets.top, size.width, size.height);
		 * size = b3.getPreferredSize(); b3.setBounds(150 + insets.left, 15 +
		 * insets.top, size.width + 50, size.height + 20);
		 */

		JButton button = new JButton("Button 1 (PAGE_START)");

		pane.add(button, BorderLayout.PAGE_START);

//Make the center component big, since that's the
//typical usage of BorderLayout.
		button = new JButton("Button 2 (CENTER)");
		button.setPreferredSize(new Dimension(200, 100));
		pane.add(button, BorderLayout.CENTER);

		button = new JButton("Button 3 (LINE_START)");
		pane.add(button, BorderLayout.LINE_START);

		button = new JButton("Long-Named Button 4 (PAGE_END)");
		pane.add(button, BorderLayout.PAGE_END);

		button = new JButton("5 (LINE_END)");
		pane.add(button, BorderLayout.LINE_END);

		// Display the window.

		this.setVisible(true);
		this.setResizable(true);

	}

}