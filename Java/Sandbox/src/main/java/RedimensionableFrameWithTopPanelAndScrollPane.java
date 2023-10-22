import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class RedimensionableFrameWithTopPanelAndScrollPane {

	private static int NUMBER_BUTTONS_ROWS = 26;
	private static int NUMBER_BUTTONS_COLUMNS = 100;
	

	private static int BUTTON_WIDTH = 70;
	private static int BUTTON_HEIGHT = 20;
	
    public static void main(String[] args) {
        new RedimensionableFrameWithTopPanelAndScrollPane();
    }

    public RedimensionableFrameWithTopPanelAndScrollPane() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                }

                JFrame frame = new JFrame();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLayout(new BorderLayout());
                frame.add(new ButtonsGridPage());
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }

    public class ButtonsGridPage extends JPanel {

        JButton buttonsInGrid[][] = new JButton[NUMBER_BUTTONS_COLUMNS][NUMBER_BUTTONS_ROWS];
        JButton centralButton = new JButton("Central button");
        JPanel topPanel = new JPanel();
        JPanel buttonsPanel = new JPanel();

        public ButtonsGridPage() {
            setLayout(new BorderLayout());

            JPanel pane = new JPanel();
            topPanel.add(centralButton);
            buttonsPanel.setLayout(null);
            pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
            pane.add(topPanel);

//            pane.setPreferredSize(new Dimension(500, 500));

            for (int x = 0; x < NUMBER_BUTTONS_COLUMNS; x++) {
                for (int y = 0; y < NUMBER_BUTTONS_ROWS; y++) {
                    buttonsInGrid[x][y] = new JButton("" + x + " " + y);
                    buttonsInGrid[x][y].setSize(new Dimension(BUTTON_WIDTH,BUTTON_HEIGHT));
                    buttonsInGrid[x][y].setPreferredSize(new Dimension(BUTTON_WIDTH,BUTTON_HEIGHT));
//                    buttonsInGrid[i][j].setMaximumSize(new Dimension(20,10));
                    buttonsInGrid[x][y].setLocation(x*BUTTON_WIDTH, y*BUTTON_HEIGHT);
                    buttonsPanel.add(buttonsInGrid[x][y]);
                    
                }
            }
            
            buttonsPanel.setPreferredSize(new Dimension(BUTTON_WIDTH*NUMBER_BUTTONS_COLUMNS, BUTTON_HEIGHT*NUMBER_BUTTONS_ROWS));

            add(pane, BorderLayout.NORTH);
            add(new JScrollPane(buttonsPanel));
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(500, 500);
        }
    }

}