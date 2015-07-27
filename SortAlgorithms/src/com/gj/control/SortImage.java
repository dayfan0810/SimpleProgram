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
	private MainFrame frame = null;// 持有一个MainFrame的引用，用于调用主窗体的方法
	private Rectangles[] rectss = null;// 含有不同数据量的Rectangles对象,当需要更改当前所需排序的数据时，
										// 只需修改下方的currentrects的指向即可
	private int currentRects = 0, currentAlgo = 0;// 指向正在排序的数据与所用的算法
	private Algorithm[] als = null;// 排序算法列表

	/**
	 * 方法用于构建一个用于处理排序结果显示与刷新，保留排序算法的一个类的对象<br>
	 * 此对象可以用来开启排序线程的对象等
	 * 
	 * @param frame
	 *            此类所服务的Frame窗体类
	 * @param size
	 *            需要构建size个不同数据量的待排序数组
	 */
	public SortImage(MainFrame frame, int size) {
		this.frame = frame;
		initRects(size);
		initAlgorithm();
	}

	// 用于实例化rectss属性
	private void initRects(int size) {
		rectss = new Rectangles[size];
		for (int i = 0; i < size; i++) {
			rectss[i] = new Rectangles(
					frame.getLabel().getWidth() / (size - i), frame.getLabel());
		}
	}

	// 使用反射来构造所用排序算法的实体对象，并初始化als对象数组
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
	 * 重画整个页面，当需要排序的数据发生改变时调用此方法<br>
	 * 数据发生改变的条件有：排序算法改变、数据量改变、用户主动要求Update
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
	 * 更新两个需要交换的矩形框，当排序中交换两个元素的位置时，此方法被调用一次<br>
	 * 当排序中有两个元素正在比较时（这时需要改变这两个矩形框的颜色），此方法被调用一次。
	 * @param ra 参数为两个需要交换或比较的元素
	 * @param rb 参数为两个需要交换或比较的元素
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
	 * 设置当前的数据量，这里的等级level 代表数据量不同大小的等级， <br>
	 * level的取值范围是[0, MainFrame.CNT_DATA),level值越大，数据量越大<br>
	 * 调用一遍此方法会更新带排序数组
	 * @param level 代表着数据量大小的等级值, 取值范围是[0, MainFrame.CNT_DATA)
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
	 * 设置当前所用的排序算法
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
	 * 拿到正在排序的数组元素
	 * @return Rectangles对象，内部有一个数组属性，保存了带排序的元素
	 */
	public Rectangles getCurrentRects() {
		return rectss[currentRects];
	}

	/**
	 * 开始排序
	 */
	public void startAlgorithm() {
		new MyThread().start();
	}

	//排序结束后所进行的处理，并不是将排序中断
	private void stopAlgorithm() {
		frame.setComEnable(true);
	}

	/**
	 * 更新待排序的元素数组
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
