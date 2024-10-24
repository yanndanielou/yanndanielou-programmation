package com.sdz.vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.sdz.model.Score;

public class ScorePanel extends ZContainer{

	private int[] policeSize = {25, 24, 23, 22, 21, 20, 19, 18, 16, 14};
	private Dimension dimension;
	private Score[] list;
	
	public ScorePanel(Dimension dim, Score[] list) {
		super(dim);
		this.list = list;
		initPanel();
	}

	protected void initPanel() {
		JPanel leftContent = new JPanel();
		JPanel rightContent = new JPanel();
		
		
		this.dimension = new Dimension(360, 530); 
		rightContent.setPreferredSize(this.dimension);
		rightContent.add(new JLabel(new ImageIcon("images/pendu7.jpg")), BorderLayout.CENTER);
		rightContent.setBackground(Color.white);
		
		this.dimension = new Dimension(500, 530);
		leftContent.setPreferredSize(this.dimension);
		leftContent.setBackground(Color.white);
		
		for(int i = 0; i < this.list.length; i++){
			JLabel lab = new JLabel(this.list[i].toString());
			lab.setFont(new Font("Comics Sans MS", Font.BOLD, policeSize[i]));
			lab.setPreferredSize(new Dimension(440, 40));
			leftContent.add(lab);
		}
		
		
		this.panel.add(leftContent, BorderLayout.CENTER);
		this.panel.add(rightContent, BorderLayout.WEST);
		System.out.println("SCOREPANEL OOKK  ! ! ");
	}

}
