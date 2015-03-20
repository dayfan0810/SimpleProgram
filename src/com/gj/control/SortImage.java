package com.gj.control;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.lang.reflect.Constructor;

import javax.swing.JLabel;

import com.gj.Sort.Algorithm;
import com.gj.frame.MainFrame;
import com.gj.frame.Rectangles;

public class SortImage {
	private MainFrame frame = null;
	private JLabel label = null;
	private Rectangles[] rectss = null;
	private Image offImage = null;
	private int currentRects = 0, currentAlgo = 0;
	private Algorithm[] als = null;
	private MyThread thread = null;
	
	public SortImage(MainFrame frame, int size) {
		this.frame = frame;
		this.label = frame.getLabel();
		rectss = new Rectangles[size];
		for(int i = 0; i < size; i ++) {
			rectss[i] = new Rectangles(30 * (i + 1), label);
		}
		repaint();
		initAlgorithm();
	}
	
	public void initAlgorithm() {
		als = new Algorithm[MainFrame.sortName.length];
		for(int i = 0; i < MainFrame.sortName.length; i ++) {
			try {
				Class<?> clazz = Class.forName("com.gj.Sort." + MainFrame.sortName[i]);
				Constructor<?>[] cons = clazz.getConstructors();
				als[i] = (Algorithm)cons[0].newInstance(new Object[]{this});
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void repaint() {
		int width = label.getWidth(), height = label.getHeight();
		if(offImage == null) {
			offImage = label.createImage(width, height);
		}
		Graphics g = offImage.getGraphics();
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(0, 0, width, height);
		rectss[currentRects].draw(g);
		label.getGraphics().drawImage(offImage, 0, 0, width, height, null);
	}
	
	public void setLevel(int level) {
		if(level <= 0) level = 0;
		else if(level >= rectss.length) level = rectss.length - 1;
		currentRects = level;
		rectss[currentRects].suffled(label.getHeight());
		repaint();
	}
	
	public void setCurrentAlgorithm(int index) {
		if(index <= 0) index = 0;
		else if(index >= als.length) index = als.length - 1;
		currentAlgo = index;
		rectss[currentRects].suffled(label.getHeight());
		repaint();
	}
	
	public Rectangles getCurrentRects() {
		return rectss[currentRects];
	}
	
	public void startAlgorithm() {
		thread = new MyThread();
		thread.start();
	}
	
	public void stopAlgorithm() {
		frame.setComEnable(true);
	}
	
	private class MyThread extends Thread {
		public void run() {
			als[currentAlgo].startSort(rectss[currentRects].rects);
			stopAlgorithm();
		}
	}
}
