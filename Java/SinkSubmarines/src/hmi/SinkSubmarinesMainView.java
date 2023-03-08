package hmi;



import java.awt.*;
import java.awt.event.*;

import javax.imageio.ImageIO;
import javax.swing.*;

public class SinkSubmarinesMainView extends JFrame {
	static final String gapList[] = { "0", "10", "15", "20" };
	final static int maxGap = 20;
	JComboBox horGapComboBox;
	JComboBox verGapComboBox;
	JButton applyButton = new JButton("Apply gaps");
	GridLayout experimentLayout = new GridLayout(10, 10);

	public SinkSubmarinesMainView(String name) {
		super(name);
		setResizable(false);
	}

	public void initGaps() {
		horGapComboBox = new JComboBox(gapList);
		verGapComboBox = new JComboBox(gapList);
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
		
		// Create and set up the window.
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Set up the content pane.
		this.addComponentsToPane(this.getContentPane());
		// Display the window.
		this.pack();
		this.setVisible(true);
		this.setResizable(true);
	}

	public void addComponentsToPane(final Container pane) {
		initGaps();
		final JPanel compsToExperiment = new JPanel();
		compsToExperiment.setLayout(experimentLayout);
		JPanel controls = new JPanel();
		controls.setLayout(new GridLayout(2, 3));

		// Set up components preferred size
		JButton b = new JButton("Just fake button");
		Dimension buttonSize = b.getPreferredSize();
		compsToExperiment.setPreferredSize(new Dimension((int) (buttonSize.getWidth() * 2.5) + maxGap,
				(int) (buttonSize.getHeight() * 3.5) + maxGap * 2));

		// Add buttons to experiment with Grid Layout
		for (int i = 0; i < 1; i++) {
			JButton button = new JButton("B " + i);
			compsToExperiment.add(button);
			try {
				Image img = ImageIO.read(
						getClass().getResource("installer_background.jpg"));
			    

				if (img == null) {
					System.out.println("Image is null");
				} else {
					button.setIcon(new ImageIcon(img));
				}
			} catch (Exception ex) {
				System.out.println(ex);
			}
		}
		// Add controls to set up horizontal and vertical gaps
		controls.add(new Label("Horizontal gap:"));
		controls.add(new Label("Vertical gap:"));
		controls.add(new Label(" "));
		controls.add(horGapComboBox);
		controls.add(verGapComboBox);
		controls.add(applyButton);

		// Process the Apply gaps button press
		applyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Get the horizontal gap value
				String horGap = (String) horGapComboBox.getSelectedItem();
				// Get the vertical gap value
				String verGap = (String) verGapComboBox.getSelectedItem();
				// Set up the horizontal gap value
				experimentLayout.setHgap(Integer.parseInt(horGap));
				// Set up the vertical gap value
				experimentLayout.setVgap(Integer.parseInt(verGap));
				// Set up the layout of the buttons
				experimentLayout.layoutContainer(compsToExperiment);
			}
		});
		pane.add(compsToExperiment, BorderLayout.NORTH);
		pane.add(new JSeparator(), BorderLayout.CENTER);
		pane.add(controls, BorderLayout.SOUTH);
	}



}