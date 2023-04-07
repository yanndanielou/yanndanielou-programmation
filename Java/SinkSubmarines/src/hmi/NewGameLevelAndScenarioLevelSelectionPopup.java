package hmi;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import core.GameManager;
import game.DifficultyLevel;

public class NewGameLevelAndScenarioLevelSelectionPopup extends JFrame {

	private static final Logger LOGGER = LogManager.getLogger(NewGameLevelAndScenarioLevelSelectionPopup.class);

	private static final long serialVersionUID = 1285388602192141194L;

	private JButton ok_button;
	private JButton cancel_button;
	private JLabel baby_sailor_image_label;
	private JLabel medium_sailor_image_label;
	private JLabel old_sailor_image_label;

	private JLabel select_start_level_label;
	private JComboBox<Integer> select_start_level_combobox;
	private JLabel select_play_skill_mode_label;
	private JLabel bottom_label;

	private final int VERTICAL_SPACE_BETWEEN_OBJECTS = 10;
	private final int BASIC_COMPONENTS_HEIGHT = 20;

	private DifficultyLevel difficulty_level_chosen = null;

	public NewGameLevelAndScenarioLevelSelectionPopup(SinkSubmarinesMainViewFrame sinkSubmarinesMainView) {
		super("Sink submarines");
	}

	/**
	 * Create the GUI and show it. For thread safety, this method is invoked from
	 * the event dispatch thread.
	 */
	public void createAndShowGUI() {

		// setLocationRelativeTo(null);

		this.setTitle("New Game");

		this.setSize(290, 320);
		// this.setSize(800, 600);

		getContentPane().setLayout(null);

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

		select_start_level_label = new JLabel("Select start level");
		select_start_level_label.setSize(getWidth(), BASIC_COMPONENTS_HEIGHT);
		select_start_level_label.setHorizontalAlignment(SwingConstants.CENTER);
		select_start_level_label.setLocation(0, VERTICAL_SPACE_BETWEEN_OBJECTS / 2);
		add(select_start_level_label);

		Integer[] list_of_proposed_start_levels = new Integer[] { 1, 2, 3, 4 };
		select_start_level_combobox = new JComboBox<>(list_of_proposed_start_levels);
		select_start_level_combobox.setSize((int) (getWidth() * 0.8), BASIC_COMPONENTS_HEIGHT);
		select_start_level_combobox.setLocation(getWidth() / 2 - select_start_level_combobox.getWidth() / 2,
				get_y_for_next_item_above(select_start_level_label));
		add(select_start_level_combobox);

		select_play_skill_mode_label = new JLabel("Select play skill mode");
		select_play_skill_mode_label.setSize(getWidth(), BASIC_COMPONENTS_HEIGHT);
		select_play_skill_mode_label.setHorizontalAlignment(SwingConstants.CENTER);
		select_play_skill_mode_label.setLocation(0, get_y_for_next_item_above(select_start_level_combobox));
		add(select_play_skill_mode_label);

		JPanel sailors_icons_panel = new JPanel();
		sailors_icons_panel.setLayout(new FlowLayout());

		Icon baby_sailor_icon = new ImageIcon("Images/character_baby_sailor.png");
		Icon medium_sailor_icon = new ImageIcon("Images/character_medium_sailor.png");
		Icon old_sailor_icon = new ImageIcon("Images/character_old_sailor.png");

		int highest_sailor_icon_y = get_y_for_next_item_above(select_play_skill_mode_label);

		baby_sailor_image_label = new JLabel(baby_sailor_icon);
		baby_sailor_image_label.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				difficulty_level_chosen = DifficultyLevel.EASY;
				on_difficulty_level_chosen();
			}
		});
		baby_sailor_image_label.setLocation(getWidth() / 4 - baby_sailor_icon.getIconWidth() / 2,
				highest_sailor_icon_y + (highest_sailor_icon_y - baby_sailor_icon.getIconHeight()) / 2);
		baby_sailor_image_label.setSize(baby_sailor_icon.getIconWidth(), baby_sailor_icon.getIconHeight());
		add(baby_sailor_image_label);

		medium_sailor_image_label = new JLabel(medium_sailor_icon);
		medium_sailor_image_label.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				difficulty_level_chosen = DifficultyLevel.MEDIUM;
				on_difficulty_level_chosen();
			}
		});
		medium_sailor_image_label.setLocation(2 * getWidth() / 4 - medium_sailor_icon.getIconWidth() / 2,
				highest_sailor_icon_y + (highest_sailor_icon_y - medium_sailor_icon.getIconHeight()) / 2);
		medium_sailor_image_label.setSize(medium_sailor_icon.getIconWidth(), medium_sailor_icon.getIconHeight());
		add(medium_sailor_image_label);

		old_sailor_image_label = new JLabel(old_sailor_icon);
		old_sailor_image_label.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				difficulty_level_chosen = DifficultyLevel.HARD;
				on_difficulty_level_chosen();
			}
		});
		old_sailor_image_label.setLocation(3 * getWidth() / 4 - old_sailor_icon.getIconWidth() / 2,
				highest_sailor_icon_y);
		old_sailor_image_label.setSize(old_sailor_icon.getIconWidth(), old_sailor_icon.getIconHeight());
		add(old_sailor_image_label);

		add(sailors_icons_panel);

		int buttons_y = get_y_for_next_item_above(old_sailor_image_label);

		ok_button = new JButton("OK");
		ok_button.addActionListener(e -> {
			setVisible(false);
			dispose();
			GameManager.getInstance().new_game(difficulty_level_chosen);
		});
		int buttons_width = 75;
		ok_button.setSize(buttons_width, BASIC_COMPONENTS_HEIGHT);
		ok_button.setLocation(1 * getWidth() / 3 - ok_button.getWidth() / 2, buttons_y);
		add(ok_button);

		cancel_button = new JButton("Cancel");
		cancel_button.addActionListener(e -> {
			setVisible(false);
			dispose();
		});
		cancel_button.setSize(buttons_width, BASIC_COMPONENTS_HEIGHT);
		cancel_button.setLocation(2 * getWidth() / 3 - cancel_button.getWidth() / 2, buttons_y);
		add(cancel_button);

		bottom_label = new JLabel();
		bottom_label.setHorizontalAlignment(SwingConstants.CENTER);
		int bottom_label_y = get_y_for_next_item_above(ok_button);
		bottom_label.setSize(getWidth(), BASIC_COMPONENTS_HEIGHT);
		bottom_label.setLocation(0, bottom_label_y);
		add(bottom_label);

		this.setVisible(true);
		this.setResizable(false);

		update_ok_button_state();
		update_sailors_images_apparence();
		update_bottom_label_text();
	}

	private int get_y_for_next_item_above(JComponent component_above) {
		int y_for_next_item_above = component_above.getY() + component_above.getHeight()
				+ VERTICAL_SPACE_BETWEEN_OBJECTS;
		LOGGER.info("Below " + component_above + ", next y:" + y_for_next_item_above);
		return y_for_next_item_above;
	}

	private void on_difficulty_level_chosen() {
		update_ok_button_state();
		update_sailors_images_apparence();
		update_bottom_label_text();

	}

	private void update_bottom_label_text() {
		bottom_label.setText("Difficulty chosen:" + difficulty_level_chosen);
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