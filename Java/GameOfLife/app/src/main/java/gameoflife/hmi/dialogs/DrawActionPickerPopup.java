package gameoflife.hmi.dialogs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import common.hmi.utils.HMIUtils;
import gameoflife.hmi.DrawAction;
import gameoflife.hmi.GameOfLifeMainViewFrame;

public class DrawActionPickerPopup extends JFrame {

	@SuppressWarnings("unused")
	private static final Logger LOGGER = LogManager.getLogger(DrawActionPickerPopup.class);

	private static final long serialVersionUID = 1285388602192141194L;

	private JButton okButton;
	private JButton cancelButton;
	private JLabel drawAliveCellImageLabel;
	private JLabel drawDeadCellImageLabel;
	private JLabel toggleCellStatusImageLabel;

	private JLabel bottomLabel;

	private static final  int VERTICAL_SPACE_BETWEEN_OBJECTS = 10;
	private static final  int BASIC_COMPONENTS_HEIGHT = 20;

	private DrawAction drawActionChosen = null;

	public DrawActionPickerPopup(GameOfLifeMainViewFrame gameOfLifeMainViewFrame) {
		super("Select draw action");

		// setLocationRelativeTo(null);

		this.setTitle("Draw action picker");

	//	this.setSize(200, 200);

		// getContentPane().setLayout(null);

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

		JPanel iconsPanel = new JPanel();
		iconsPanel.setLayout(new FlowLayout());

		drawAliveCellImageLabel = HMIUtils
				.createJLabelFromImage("src/main/resources/images/DrawAliveCellButtonIcon.png");
		drawDeadCellImageLabel = HMIUtils.createJLabelFromImage("src/main/resources/images/DrawDeadCellButtonIcon.png");
		toggleCellStatusImageLabel = HMIUtils
				.createJLabelFromImage("src/main/resources/images/DrawToggleCellStateButtonIcon.png");

		drawAliveCellImageLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				drawActionChosen = DrawAction.SET_ALIVE;
				onDrawActionChosen();
			}
		});
		iconsPanel.add(drawAliveCellImageLabel);

		drawDeadCellImageLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				drawActionChosen = DrawAction.SET_DEAD;
				onDrawActionChosen();
			}
		});
		iconsPanel.add(drawDeadCellImageLabel);

		toggleCellStatusImageLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				drawActionChosen = DrawAction.TOGGLE_STATE;
				onDrawActionChosen();
			}
		});
		iconsPanel.add(toggleCellStatusImageLabel);

		add(iconsPanel, BorderLayout.CENTER);

		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new FlowLayout());

		okButton = new JButton("OK");
		okButton.addActionListener(e -> {
			gameOfLifeMainViewFrame.getHmiPresenter().setDrawActionInProgress(drawActionChosen);
			setVisible(false);
			dispose();
		});

		int buttonsWidth = 75;
		okButton.setSize(buttonsWidth, BASIC_COMPONENTS_HEIGHT);
		// okButton.setLocation(1 * getWidth() / 3 - okButton.getWidth() / 2, buttonsY);
		buttonsPanel.add(okButton);

		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(e -> {
			setVisible(false);
			dispose();
		});
		cancelButton.setSize(buttonsWidth, BASIC_COMPONENTS_HEIGHT);
		// cancelButton.setLocation(2 * getWidth() / 3 - cancelButton.getWidth() / 2,
		// buttonsY);
		buttonsPanel.add(cancelButton);

		add(buttonsPanel, BorderLayout.SOUTH);

		bottomLabel = new JLabel();
		bottomLabel.setHorizontalAlignment(SwingConstants.CENTER);
		// int bottomLabelY = getYForNextItemAbove(okButton);
		bottomLabel.setSize(getWidth(), BASIC_COMPONENTS_HEIGHT);
		// bottomLabel.setLocation(0, bottomLabelY);
		add(bottomLabel, BorderLayout.NORTH);

		this.setVisible(true);
		this.setResizable(false);

		updateOkButtonState();
		updateImagesApparence();
		updateBottomLabelText();
		
		pack();
		
		setLocationRelativeTo(gameOfLifeMainViewFrame);
	}

	private void onDrawActionChosen() {
		updateOkButtonState();
		updateImagesApparence();
		updateBottomLabelText();

	}

	private void updateBottomLabelText() {
		bottomLabel.setText("Difficulty chosen:" + drawActionChosen);
	}

	private void updateImagesApparence() {
		drawDeadCellImageLabel.setBorder(
				drawActionChosen == DrawAction.SET_DEAD ? BorderFactory.createLineBorder(Color.black) : null);
		drawAliveCellImageLabel.setBorder(
				drawActionChosen == DrawAction.SET_ALIVE ? BorderFactory.createLineBorder(Color.black) : null);
		toggleCellStatusImageLabel.setBorder(
				drawActionChosen == DrawAction.TOGGLE_STATE ? BorderFactory.createLineBorder(Color.black) : null);
	}

	private void updateOkButtonState() {
		boolean okButtonEnabled = drawActionChosen != null;
		okButton.setEnabled(okButtonEnabled);
		
		if(okButtonEnabled) {
			getRootPane().setDefaultButton(okButton);
			okButton.requestFocus();
		}
		else {
			getRootPane().setDefaultButton(cancelButton);
			cancelButton.requestFocus();
		}

	}

}