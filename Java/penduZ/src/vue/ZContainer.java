package com.sdz.vue;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JPanel;

public abstract class ZContainer {
	protected JPanel panel;
	
	protected Font comics30 = new Font("Comics Sans MS", Font.BOLD, 30);
	protected Font comics40 = new Font("Comics Sans MS", Font.BOLD, 40);
	protected Font comics20 = new Font("Comics Sans MS", Font.BOLD, 20);
	protected Font arial = new Font("Arial", Font.BOLD, 15);
	protected Font dialog = new Font("Dialog", Font.BOLD + Font.ITALIC, 15);
	
	
	public ZContainer(Dimension dim){
		this.panel = new JPanel();
		this.panel.setPreferredSize(dim);
		this.panel.setBackground(Color.white);
		
	}
	
	protected JPanel getPanel(){
		return this.panel;
	}
	
	protected abstract void initPanel();	
	
}
