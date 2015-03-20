package com.gj.frame;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import javax.swing.JLabel;


public class Rectangles {
	public MyRectangle[] rects = null;
	
	public Rectangles(int size, JLabel label) {
		Random r = new Random();
		int width = label.getWidth() / size, height = label.getHeight();
		size = label.getWidth() / width;
		rects = new MyRectangle[size];
		for(int i = 0; i < size; i ++) {
			int x = width * i, h = r.nextInt(height); 
			rects[i] = new MyRectangle(x, height - h, width, h);
		}
	}
	
	public void draw(Graphics g) {
		Color c = g.getColor();
		for(int i = 0; i < rects.length; i ++) {
			rects[i].draw(g);
		}
		g.setColor(c);
	}
	
	public void suffled(int height) {
		if(rects == null) return ;
		Random r = new Random();
		for(int i = 0; i < rects.length; i ++) {
			rects[i].height = r.nextInt(height);
			rects[i].y = height - rects[i].height; 
		}
	}
	
}
