package hmi;

import java.awt.FlowLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRootPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import main.SinkSubmarinesMain;

public class NewGameLevelAndScenarioLevelSelectionPopup extends JFrame {

	private static final Logger LOGGER = LogManager.getLogger(NewGameLevelAndScenarioLevelSelectionPopup.class);

	private static final long serialVersionUID = 1285388602192141194L;

	private JButton ok_button;
	private JButton cancel_button;
	private JLabel baby_sailor_image_label;
	private JLabel medium_sailor_image_label;
	private JLabel old_sailor_image_label;

	private SinkSubmarinesMainView sinkSubmarinesMainView;

	public NewGameLevelAndScenarioLevelSelectionPopup(SinkSubmarinesMainView sinkSubmarinesMainView) {
		super("Sink submarines");
		this.sinkSubmarinesMainView = sinkSubmarinesMainView;
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

		setAlwaysOnTop(true);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setUndecorated(true);
		getRootPane().setWindowDecorationStyle(JRootPane.NONE);

		setLayout(new FlowLayout());

		Icon baby_sailor_icon = new ImageIcon("Images/character_baby_sailor.png");
		baby_sailor_image_label = new JLabel(baby_sailor_icon);
		add(baby_sailor_image_label);
		baby_sailor_image_label.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

		});

		Icon medium_sailor_icon = new ImageIcon("Images/character_medium_sailor.png");
		medium_sailor_image_label = new JLabel(medium_sailor_icon);
		add(medium_sailor_image_label);

		Icon old_sailor_icon = new ImageIcon("Images/character_old_sailor.png");
		old_sailor_image_label = new JLabel(old_sailor_icon);
		add(old_sailor_image_label);

		this.setSize(200, 200);

		// Display the window.

		this.setVisible(true);
		this.setResizable(false);

	}

	// GameManager.getInstance().new_game("data/GameDataModel.json");

}