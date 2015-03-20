package com.gj.Sort;

import java.awt.Color;

import com.gj.control.SortImage;
import com.gj.frame.MainFrame;
import com.gj.frame.MyRectangle;

public abstract class Algorithm {
	protected SortImage si = null;
	
	public Algorithm(SortImage si) {
		this.si = si;
	}
	
	public int compareTo(MyRectangle ra, MyRectangle rb) {
		try {
			setColor(ra, rb, Color.YELLOW);
			si.repaint();
			Thread.sleep(MainFrame.spaceTime);
			setColor(ra, rb, Color.GREEN);
			si.repaint();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ra.compareTo(rb);
	}
	
	public void swap(MyRectangle ra, MyRectangle rb) {
		try {
			if(ra != null && rb != null) {
				setColor(ra, rb, Color.YELLOW);
				si.repaint();
				int y = ra.y; ra.y = rb.y; rb.y = y;
				int h = ra.height; ra.height = rb.height; rb.height = h;
				Thread.sleep(MainFrame.spaceTime);
				setColor(ra, rb, Color.GREEN);
				si.repaint();
			}
		} catch (Exception e) {
		}
	}
	
	private void setColor(MyRectangle a, MyRectangle b, Color c) {
		a.setColor(c);
		b.setColor(c);
	}
	
	public abstract void startSort(MyRectangle[] rects);
}
