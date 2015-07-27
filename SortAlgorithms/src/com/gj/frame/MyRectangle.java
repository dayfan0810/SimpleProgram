package com.gj.frame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class MyRectangle extends Rectangle{
	private static final long serialVersionUID = 1L;
	public Color color = Color.GREEN;
	
	public MyRectangle(int x, int y, int w, int h) {
		super(x, y, w, h);
	}
	
	public void draw(Graphics g) {
		g.setColor(this.color);
		g.fillRect(this.x, this.y, this.width, this.height);
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	public int compareTo(MyRectangle rect) {
		return this.height - rect.height;
	}
	
}	
