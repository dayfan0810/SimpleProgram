package com.gj.frame;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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
	public static final int FRAME_HEIGTH = 600;
	public static final int POS_X = 200;
	public static final int POS_Y = 50;
	public static final int CNT_DATA = 15;
	public static final ArrayList<String> sortName = new ArrayList<String>();
	public static int spaceTime = 2;
	private JButton[] buttons = null; 
	private JButton startButton = null, updateButton;
	private JSlider dataSlider, speedSlider; 
	private int nowId = 0;
	private JLabel label = null, compareLabel, swapLabel, delayLabel, dataLabel;
	
	private SortImage si = null;
	
	public MainFrame() {
		setAttribute();
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
		
		buttons = new JButton[sortName.size()];
		for(int i = 0; i < sortName.size(); i ++) {
			int x = xOri + i % 5 * (width + xDif), y = yOri + i / 5 * (height + yDif);
			String name = sortName.get(i);
			name = name.substring(name.lastIndexOf(".") + 1, name.length());
			buttons[i] = new JButton(name);
			buttons[i].setBackground(Color.WHITE);
			buttons[i].setSize(width, height);
			buttons[i].setLocation(x, y);
			buttons[i].addActionListener(new MyListener(i));
			add(buttons[i]);
		}
		buttons[nowId].setBackground(Color.LIGHT_GRAY);
		
		int x = xOri, y = yOri + (sortName.size() / 5 + 1) * (height + yDif) + 2 * yDif;
		
		JLabel l1 = new JLabel("  Data");
		l1.setSize(width, height);
		l1.setLocation(x, y - 2 * yDif);
		this.add(l1);
		
		dataSlider = new JSlider(0, CNT_DATA / 2);
		dataSlider.setSize(width + xDif, height);
		dataSlider.setLocation(x, y);
		dataSlider.setBackground(Color.WHITE);
		this.add(dataSlider);
		dataSlider.setValue(0);
		dataSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				if(si != null) {
					si.setLevel(dataSlider.getValue());
					dataLabel.setText("" + si.getCurrentRects().rects.length);
				}
			}
		});

		JLabel l2 = new JLabel("  Speed");
		l2.setSize(width, height);
		l2.setLocation(x + ( width + 2 * xDif), y - 2 * yDif);
		this.add(l2);
		
		speedSlider = new JSlider(0, 50);
		speedSlider.setSize(width + xDif, height);
		speedSlider.setLocation(x +=( width + 2 * xDif),  y);
		speedSlider.setBackground(Color.WHITE);
		speedSlider.setMajorTickSpacing(2);
		this.add(speedSlider);
		speedSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				spaceTime = speedSlider.getMaximum() - speedSlider.getValue();
				delayLabel.setText(spaceTime + "ms");
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
		
		updateButton = new JButton("Update");
		updateButton.setSize(width, height);
		updateButton.setLocation(x +=( width + 2 * xDif), y);
		this.add(updateButton);
		updateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				si.update();
			}
		});
		
		label = new JLabel();
		label.setLocation(x = 7, y +=( 2 * yDif + height));
		label.setSize(FRAME_WIDTH - 3 * xOri, FRAME_HEIGTH - 5 * yOri - y);
		this.add(label);
		label.setOpaque(true);
		label.setBackground(Color.LIGHT_GRAY);
		
		int wCompare = 80, wText = 40, wValue = 38, h = 20;
		x = 5; y -= h;
		addMsgLabel(x, y, wCompare, h, "Comparation:", JLabel.RIGHT);
		compareLabel = addMsgLabel(x += wCompare, y, wValue, h, "0", JLabel.LEFT);
		addMsgLabel(x += wValue, y, wText, h, "Swap:", JLabel.RIGHT);
		swapLabel = addMsgLabel(x += wText, y, wValue, h, "0", JLabel.LEFT);
		addMsgLabel(x += wValue, y, wText, h, "Delay:", JLabel.RIGHT);
		delayLabel = addMsgLabel(x += wText, y, wValue, h, speedSlider.getValue()+"ms", JLabel.LEFT);
		addMsgLabel(x += wValue, y, wText, h, "Data:", JLabel.RIGHT);
		dataLabel = addMsgLabel(x += wText, y, wValue, h, "51", JLabel.LEFT);
		
	}
	
	private JLabel addMsgLabel(int x, int y, int w, int h, String msg, int align) {
		JLabel l = new JLabel(msg);
		l.setSize(w, h);
		l.setLocation(x, y);
		l.setOpaque(true);
		l.setBackground(Color.WHITE);
		l.setHorizontalAlignment(align);
		this.add(l);
		return l;
	}
	
	public void run() {
		addComponent();
		this.setVisible(true);
		//这行语句必须放在上述setVisible之后= =!
		si = new SortImage(this, CNT_DATA);
		si.updateView();
	}
	
	public void registSort(String sortName) {
		MainFrame.sortName.add(sortName);
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
			
			String s =sortName.get(nowId); 
			if(s.equals("BubbleSort") || s.equals("InsertionSort") || s.equals("SelectionSort")) {
				dataSlider.setMaximum(CNT_DATA / 2);
			}
			else {
				dataSlider.setMaximum(CNT_DATA - 1);
			}
			si.setLevel(dataSlider.getValue());
			dataLabel.setText("" + si.getCurrentRects().rects.length);
		}
	}
	

	public JLabel getLabel() {
		return label;
	}
	public JLabel getCompareLabel() {
		return compareLabel;
	}
	public JLabel getSwapLabel() {
		return swapLabel;
	}
	
	
	public void setComEnable(boolean isTrue) {
		for(int i = 0; i < buttons.length;i ++) buttons[i].setEnabled(isTrue);
		dataSlider.setEnabled(isTrue);
		startButton.setEnabled(isTrue);
		updateButton.setEnabled(isTrue);
		
	}
	
}
