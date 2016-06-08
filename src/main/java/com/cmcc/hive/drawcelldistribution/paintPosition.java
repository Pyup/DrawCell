package com.cmcc.hive.drawcelldistribution;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Panel;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class paintPosition extends JFrame{
	public static final long serialVersionUID = 2l;
	private cellPanel cp  = null;
	public paintPosition(){
		cp = new cellPanel();
		this.add(cp);
		this.setSize(800,800);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
}

class cellPanel extends JPanel{
	public static final long serialVersionUID = 1l;
	public void paint(Graphics g){
		super.paint(g);
		g.drawOval(100, 100, 10, 10);
		g.setColor(Color.BLUE);
		g.fillOval(100, 100, 10, 10);
	}
}
