import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class TestScrollPane {

	private static int NUMBER_BUTTONS_ROWS = 26;
	private static int NUMBER_BUTTONS_COLUMNS = 100;
    public static void main(String[] args) {
        new TestScrollPane();
    }

    public TestScrollPane() {
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

        JFrame frame = new JFrame();
        JButton buttonsInGrid[][] = new JButton[NUMBER_BUTTONS_ROWS][NUMBER_BUTTONS_COLUMNS];
        JButton centralButton = new JButton("Central button");
        JPanel topPanel = new JPanel();
        JPanel buttonsPanel = new JPanel();

        public ButtonsGridPage() {
            setLayout(new BorderLayout());

            JPanel pane = new JPanel();
            topPanel.add(centralButton);
            buttonsPanel.setLayout(new GridLayout(NUMBER_BUTTONS_ROWS, NUMBER_BUTTONS_COLUMNS));
            pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
            pane.add(topPanel);

//            pane.setPreferredSize(new Dimension(500, 500));

            for (int i = 0; i < NUMBER_BUTTONS_ROWS; i++) {
                for (int j = 0; j < NUMBER_BUTTONS_COLUMNS; j++) {
                    buttonsInGrid[i][j] = new JButton("" + i + " " + j);
                    buttonsPanel.add(buttonsInGrid[i][j]);
                }
            }

            add(pane, BorderLayout.NORTH);
            add(new JScrollPane(buttonsPanel));
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(500, 500);
        }
    }

}