package com.gj.Sort;

import java.awt.Color;

import com.gj.control.SortImage;
import com.gj.frame.MainFrame;
import com.gj.frame.MyRectangle;

public abstract class Algorithm {
	public static int compareTime = 0, swapTime = 0;
	protected SortImage si = null;
	
	public Algorithm(SortImage si) {
		this.si = si;
	}
	
	public int compareTo(MyRectangle ra, MyRectangle rb) {
		try {
			setColor(ra, rb, Color.YELLOW);
			si.repaint(ra, rb);
			Thread.sleep(MainFrame.spaceTime);
			setColor(ra, rb, Color.GREEN);
			si.repaint(ra, rb);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Algorithm.compareTime ++;
		return ra.compareTo(rb);
	}
	
	public void swap(MyRectangle ra, MyRectangle rb) {
		try {
			if(ra != null && rb != null) {
				setColor(ra, rb, Color.YELLOW);
				si.repaint(ra, rb);
				int y = ra.y; ra.y = rb.y; rb.y = y;
				int h = ra.height; ra.height = rb.height; rb.height = h;
				Thread.sleep(MainFrame.spaceTime);
				setColor(ra, rb, Color.GREEN);
				si.repaint(ra, rb);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Algorithm.swapTime ++;
	}

	public void clone(MyRectangle ra, MyRectangle rb, int flag) {
		try {
			if(ra != null && rb != null) {
				setColor(ra, rb, Color.YELLOW);
				if(flag == 0)si.repaint(rb, null);
				else si.repaint(ra, null);
				ra.y = rb.y; ra.height = rb.height;
				Thread.sleep(MainFrame.spaceTime);
				setColor(ra, rb, Color.GREEN);
				if(flag == 0)si.repaint(rb, null);
				else si.repaint(ra, null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Algorithm.swapTime ++;
	}

	private void setColor(MyRectangle a, MyRectangle b, Color c) {
		a.setColor(c);
		b.setColor(c);
	}
	
	public abstract void startSort(MyRectangle[] rects);
}
