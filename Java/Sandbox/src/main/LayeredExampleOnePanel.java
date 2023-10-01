package main;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;
import javax.swing.border.Border;

@SuppressWarnings("serial")
public class LayeredExampleOnePanel extends JPanel {

    public LayeredExampleOnePanel() {
        int gridRows = 200;
        int gridCols = 100;
        JPanel panelA = new JPanel(new GridLayout(gridRows, gridCols));
        for (int i = 0; i < gridRows; i++) {
            for (int j = 0; j < gridCols; j++) {
                String labelText = String.format("[%d, %d]", j + 1, i + 1);
                JLabel label = new JLabel(labelText);
                panelA.add(label);
            }
        }
        panelA.setSize(panelA.getPreferredSize());
        panelA.setLocation(0, 0);

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.add(panelA, JLayeredPane.DEFAULT_LAYER);
        layeredPane.setPreferredSize(new Dimension(1600, 1200));

        JScrollPane scrollPane = new JScrollPane(layeredPane);
        scrollPane.getViewport().setPreferredSize(new Dimension(800, 650));

        setLayout(new BorderLayout());
        add(scrollPane);
    }

    private static void createAndShowGui() {
        JFrame frame = new JFrame("LayeredExample");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new LayeredExampleOnePanel());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> createAndShowGui());
    }
}