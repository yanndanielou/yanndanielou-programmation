
import java.awt.*;
import java.awt.event.*;

import javax.imageio.ImageIO;
import javax.swing.*;

import hmi.SinkSubmarinesMainView;

import time.TimeManager;

public class SinkSubmarinesMain {

	public static void main(String[] args) {

		TimeManager timeManager = new TimeManager();
		SinkSubmarinesMainView sinkSubmarinesMainView = new SinkSubmarinesMainView("GridLayoutDemo");
		// Schedule a job for the event dispatch thread:
		// creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				sinkSubmarinesMainView.createAndShowGUI();
			}
		});

	}

}