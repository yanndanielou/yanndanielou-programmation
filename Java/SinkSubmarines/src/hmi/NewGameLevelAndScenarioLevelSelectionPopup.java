package hmi;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.InputMethodEvent;
import java.awt.event.InputMethodListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import core.GameManager;
import game.DifficultyLevel;
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

	private DifficultyLevel difficulty_level_chosen = null;

	public NewGameLevelAndScenarioLevelSelectionPopup(SinkSubmarinesMainView sinkSubmarinesMainView) {
		super("Sink submarines");
		this.sinkSubmarinesMainView = sinkSubmarinesMainView;
	}

	class BabySailorImageMouseListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			difficulty_level_chosen = DifficultyLevel.EASY;
			on_difficulty_level_chosen(difficulty_level_chosen);
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

	}

	/**
	 * Create the GUI and show it. For thread safety, this method is invoked from
	 * the event dispatch thread.
	 */
	public void createAndShowGUI() {

		this.setTitle("New Game");

		this.setSize(600, 400);

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

		/*
		 * Forbid close setAlwaysOnTop(true);
		 * setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		 */
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		setLayout(new BorderLayout());

		JPanel sailors_icons_panel = new JPanel();
		sailors_icons_panel.setSize(getWidth(), getHeight());
		sailors_icons_panel.setLayout(new FlowLayout());

		Icon baby_sailor_icon = new ImageIcon("Images/character_baby_sailor.png");
		baby_sailor_image_label = new JLabel(baby_sailor_icon);
		baby_sailor_image_label.addMouseListener(new BabySailorImageMouseListener());
		sailors_icons_panel.add(baby_sailor_image_label);

		Icon medium_sailor_icon = new ImageIcon("Images/character_medium_sailor.png");
		medium_sailor_image_label = new JLabel(medium_sailor_icon);
		medium_sailor_image_label.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				difficulty_level_chosen = DifficultyLevel.MEDIUM;
				on_difficulty_level_chosen(difficulty_level_chosen);

			}

		});
		sailors_icons_panel.add(medium_sailor_image_label);

		Icon old_sailor_icon = new ImageIcon("Images/character_old_sailor.png");
		old_sailor_image_label = new JLabel(old_sailor_icon);
		old_sailor_image_label.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				difficulty_level_chosen = DifficultyLevel.MEDIUM;
				on_difficulty_level_chosen(difficulty_level_chosen);
			}
		});
		sailors_icons_panel.add(old_sailor_image_label);

		add(sailors_icons_panel, BorderLayout.CENTER);

		JPanel bottom_with_buttons_panel = new JPanel();
		bottom_with_buttons_panel.setLayout(new FlowLayout());
		add(bottom_with_buttons_panel, BorderLayout.AFTER_LAST_LINE);

		ok_button = new JButton("OK");
		ok_button.addActionListener(e -> {
			setVisible(false);
			dispose();
			GameManager.getInstance().new_game(difficulty_level_chosen);
		});

		bottom_with_buttons_panel.add(ok_button);

		cancel_button = new JButton("Cancel");
		bottom_with_buttons_panel.add(cancel_button);

		// Display the window.

		this.setVisible(true);
		this.setResizable(false);

		update_ok_button_state();
		update_sailors_images_apparence();

	}

	private void on_difficulty_level_chosen(DifficultyLevel difficulty_level_chosen) {
		update_ok_button_state();
		update_sailors_images_apparence();
	}

	private void update_sailors_images_apparence() {
		baby_sailor_image_label.setBorder(
				difficulty_level_chosen == DifficultyLevel.EASY ? BorderFactory.createLineBorder(Color.black) : null);
		medium_sailor_image_label.setBorder(
				difficulty_level_chosen == DifficultyLevel.MEDIUM ? BorderFactory.createLineBorder(Color.black) : null);
		old_sailor_image_label.setBorder(
				difficulty_level_chosen == DifficultyLevel.HARD ? BorderFactory.createLineBorder(Color.black) : null);
	}

	private void update_ok_button_state() {
		ok_button.setEnabled(difficulty_level_chosen != null);
	}

}