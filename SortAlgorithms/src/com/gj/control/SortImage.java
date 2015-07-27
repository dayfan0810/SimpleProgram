package com.gj.control;

/**
 * 
 */
import java.awt.Color;
import java.awt.Graphics;
import java.lang.reflect.Constructor;

import com.gj.Sort.Algorithm;
import com.gj.frame.MainFrame;
import com.gj.frame.MyRectangle;
import com.gj.frame.Rectangles;

public class SortImage {
	private MainFrame frame = null;// ����һ��MainFrame�����ã����ڵ���������ķ���
	private Rectangles[] rectss = null;// ���в�ͬ��������Rectangles����,����Ҫ���ĵ�ǰ�������������ʱ��
										// ֻ���޸��·���currentrects��ָ�򼴿�
	private int currentRects = 0, currentAlgo = 0;// ָ��������������������õ��㷨
	private Algorithm[] als = null;// �����㷨�б�

	/**
	 * �������ڹ���һ�����ڴ�����������ʾ��ˢ�£����������㷨��һ����Ķ���<br>
	 * �˶�������������������̵߳Ķ����
	 * 
	 * @param frame
	 *            �����������Frame������
	 * @param size
	 *            ��Ҫ����size����ͬ�������Ĵ���������
	 */
	public SortImage(MainFrame frame, int size) {
		this.frame = frame;
		initRects(size);
		initAlgorithm();
	}

	// ����ʵ����rectss����
	private void initRects(int size) {
		rectss = new Rectangles[size];
		for (int i = 0; i < size; i++) {
			rectss[i] = new Rectangles(
					frame.getLabel().getWidth() / (size - i), frame.getLabel());
		}
	}

	// ʹ�÷������������������㷨��ʵ����󣬲���ʼ��als��������
	private void initAlgorithm() {
		int size = MainFrame.sortName.size();
		als = new Algorithm[size];
		for (int i = 0; i < size; i++) {
			try {
				Class<?> clazz = Class.forName(MainFrame.sortName.get(i));
				Constructor<?>[] cons = clazz.getConstructors();
				als[i] = (Algorithm) cons[0].newInstance(new Object[] { this });
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * �ػ�����ҳ�棬����Ҫ��������ݷ����ı�ʱ���ô˷���<br>
	 * ���ݷ����ı�������У������㷨�ı䡢�������ı䡢�û�����Ҫ��Update
	 */
	public void updateView() {
		Graphics g = frame.getLabel().getGraphics();
		Color c = g.getColor();
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(0, 0, frame.getLabel().getWidth(), frame.getLabel()
				.getHeight());
		rectss[currentRects].draw(g);
		g.setColor(c);

		if (frame.getCompareLabel() != null) {
			frame.getCompareLabel().setText("0");
		}
		if (frame.getSwapLabel() != null) {
			frame.getSwapLabel().setText("0");
		}
	}

	/**
	 * ����������Ҫ�����ľ��ο򣬵������н�������Ԫ�ص�λ��ʱ���˷���������һ��<br>
	 * ��������������Ԫ�����ڱȽ�ʱ����ʱ��Ҫ�ı����������ο����ɫ�����˷���������һ�Ρ�
	 * @param ra ����Ϊ������Ҫ������Ƚϵ�Ԫ��
	 * @param rb ����Ϊ������Ҫ������Ƚϵ�Ԫ��
	 */
	public void repaint(MyRectangle ra, MyRectangle rb) {
		rectss[currentRects].draw(frame.getLabel().getGraphics(), ra, rb, frame
				.getLabel().getHeight());
		if (frame.getCompareLabel() != null) {
			frame.getCompareLabel().setText("" + Algorithm.compareTime);
		}
		if (frame.getSwapLabel() != null) {
			frame.getSwapLabel().setText("" + Algorithm.swapTime);
		}
	}

	/**
	 * ���õ�ǰ��������������ĵȼ�level ������������ͬ��С�ĵȼ��� <br>
	 * level��ȡֵ��Χ��[0, MainFrame.CNT_DATA),levelֵԽ��������Խ��<br>
	 * ����һ��˷�������´���������
	 * @param level ��������������С�ĵȼ�ֵ, ȡֵ��Χ��[0, MainFrame.CNT_DATA)
	 */
	public void setLevel(int level) {
		if (level <= 0) {
			level = 0;
		} else if (level >= rectss.length) {
			level = rectss.length - 1;
		}
		currentRects = level;
		rectss[currentRects].suffled(frame.getLabel().getHeight());
		updateView();
	}

	/**
	 * ���õ�ǰ���õ������㷨
	 * @param index index
	 */
	public void setCurrentAlgorithm(int index) {
		if (index <= 0)
			index = 0;
		else if (index >= als.length)
			index = als.length - 1;
		currentAlgo = index;
		rectss[currentRects].suffled(frame.getLabel().getHeight());
		updateView();
	}

	
	/**
	 * �õ��������������Ԫ��
	 * @return Rectangles�����ڲ���һ���������ԣ������˴������Ԫ��
	 */
	public Rectangles getCurrentRects() {
		return rectss[currentRects];
	}

	/**
	 * ��ʼ����
	 */
	public void startAlgorithm() {
		new MyThread().start();
	}

	//��������������еĴ��������ǽ������ж�
	private void stopAlgorithm() {
		frame.setComEnable(true);
	}

	/**
	 * ���´������Ԫ������
	 */
	public void update() {
		rectss[currentRects].suffled(frame.getLabel().getHeight());
		updateView();
	}

	private class MyThread extends Thread {
		public void run() {
			als[currentAlgo].startSort(rectss[currentRects].rects);
			stopAlgorithm();
		}
	}

}
