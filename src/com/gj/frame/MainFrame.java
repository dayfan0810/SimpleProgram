package com.gj.frame;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.gj.control.SortImage;

public class MainFrame extends JFrame {
		
	private static final long serialVersionUID = 1L;
	public static final int FRAME_WIDTH = 900;
	public static final int FRAME_HEIGTH = 550;
	public static final int POS_X = 250;
	public static final int POS_Y = 80;
	public static final int CNT_DATA = 10;
	public static final String[] sortName = {
		"BubbleSort",  "SelectionSort", "InsertionSort", "ShellSort",
		"MergeSort", "QuickSort", "HeapSort", "MySort"
	};
	public static int spaceTime = 2;
	private JButton[] buttons = null; 
	private JButton startButton = null;
	private JSlider dataSlider, speedSlider; 
	private int nowId = 0;
	private JLabel label = null;
	
	private SortImage si = null;
	
	public MainFrame() {
		setAttribute();
		addComponent();
	}
	
	private void setAttribute() {
		this.setLayout(null);
		this.setSize(FRAME_WIDTH, FRAME_HEIGTH);
		this.setLocation(POS_X, POS_Y);
		this.getContentPane().setBackground(Color.WHITE);
		this.setTitle("Comparation Of Sort");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	/**
	 * 添加组件以及组件的基本响应
	 */
	private void addComponent() {
		final int xOri = 10, yOri = 10;
		final int xDif = 30, yDif = 10;
		final int width = 130, height = 25;
		
		buttons = new JButton[sortName.length];
		for(int i = 0; i < sortName.length; i ++) {
			int x = xOri + i % 5 * (width + xDif), y = yOri + i / 5 * (height + yDif);
			buttons[i] = new JButton(sortName[i]);
			buttons[i].setBackground(Color.WHITE);
			buttons[i].setSize(width, height);
			buttons[i].setLocation(x, y);
			buttons[i].addActionListener(new MyListener(i));
			add(buttons[i]);
		}
		buttons[nowId].setBackground(Color.LIGHT_GRAY);
		
		int x = xOri, y = yOri + (sortName.length / 5 + 1) * (height + yDif) + 2 * yDif;
		
		JLabel l1 = new JLabel("  Data");
		l1.setSize(width, height);
		l1.setLocation(x, y - 2 * yDif);
		this.add(l1);
		
		dataSlider = new JSlider(0, CNT_DATA - 1);
		dataSlider.setSize(width + xDif, height);
		dataSlider.setLocation(x, y);
		dataSlider.setBackground(Color.WHITE);
		this.add(dataSlider);
		dataSlider.setValue(0);
		dataSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				if(si != null) si.setLevel(dataSlider.getValue());
			}
		});

		JLabel l2 = new JLabel("  Speed");
		l2.setSize(width, height);
		l2.setLocation(x + ( width + 2 * xDif), y - 2 * yDif);
		this.add(l2);
		
		speedSlider = new JSlider(0, 100);
		speedSlider.setSize(width + xDif, height);
		speedSlider.setLocation(x +=( width + 2 * xDif),  y);
		speedSlider.setBackground(Color.WHITE);
		speedSlider.setMajorTickSpacing(1);
		this.add(speedSlider);
		speedSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				spaceTime = speedSlider.getMaximum() - speedSlider.getValue();
			}
		});
		
		startButton = new JButton("Excute");
		startButton.setSize(width, height);
		startButton.setLocation(x +=( width + 2 * xDif), y);
		this.add(startButton);
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setComEnable(false);
				si.startAlgorithm();
			}
		});
		
		label = new JLabel();
		label.setLocation(7, y +=( 2 * yDif + height));
		label.setSize(FRAME_WIDTH - 3 * xOri, FRAME_HEIGTH - 5 * yOri - y);
		label.setOpaque(true);
		label.setBackground(Color.LIGHT_GRAY);
		this.add(label);
		
		this.setVisible(true);
		
		//这行语句必须放在上述setVisible之后= =!
		si = new SortImage(this, CNT_DATA);
		si.repaint();
	}
	
	private class MyListener implements ActionListener {
		private int id;
		public MyListener(int id) {
			this.id = id;
		}
		public void actionPerformed(ActionEvent e) {
			for(int i = 0; i < buttons.length; i ++) {
				if(i != this.id) buttons[i].setBackground(Color.WHITE);
				else {
					si.setCurrentAlgorithm(id);
					buttons[i].setBackground(Color.LIGHT_GRAY);
				}
			}
			nowId = id;
		}
	}
	
	
	public JLabel getLabel() {
		return label;
	}
	
	public void setComEnable(boolean isTrue) {
		for(int i = 0; i < buttons.length;i ++) buttons[i].setEnabled(isTrue);
		dataSlider.setEnabled(isTrue);
		startButton.setEnabled(isTrue);
		
	}
	
	public static void main(String[] args) {
		new MainFrame();
	}
	
}
