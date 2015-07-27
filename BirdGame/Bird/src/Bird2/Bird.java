package Bird2;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;

public class Bird {

	/** 定义这只鸟的默认的初始位置的X坐标, INIT_POSX = MainFrame.GAME_WIDTH/8*3*/
	public static final int INIT_POSX = MainFrame.GAME_WIDTH/8*3;
	
	/** 定义这只鸟的默认的初始位置的Y坐标, INIT_POSY = MainFrame.GAME_HEIGHT/2 */
	public static final int INIT_POSY = MainFrame.GAME_HEIGHT/2;
	
	/** 定义这只鸟的默认的宽度， BIRD_WIDTH = 30 */
	public static final int BIRD_WIDTH = 30;
	
	/** 定义这只鸟的默认的高度， BIRD_HEIGHT = 30 */
	public static final int BIRD_HEIGHT = 30;
	
	private static final int maxIndex=30, startToJump = 17;
	
	//记录这只鸟是都掉落至底部，如果没有掉落至底部，不允许主窗口响应ENTER和ESC键
	private boolean isAtBottom = false;
	//记录这只鸟当前所在的位置
	private int pos_x, pos_y;
	//鸟从速度为0开始下落时每一次下落的距离数组，在calculateSpeedArray()中赋值
	private int[] ySpeed= null;
	//当前下降时的速度为ySpeed[speedIndex]
	private int speedIndex = 0;
	//鸟的翅膀扇动时的三张图片
	private Image[] image = new Image[3];
	//当前处在哪张图片
	private int imageIndex = 0;
	
	/**
	 * 初始化这只鸟的基本信息，如它的初始位置，加载运动时的三张图片, 以及加载跳动的速度
	 */
	public Bird() {
		this(INIT_POSX, INIT_POSY);
	}
	
	/**
	 * 实现在指定的位置初始化这只鸟
	 * @param x 指定的x坐标
	 * @param y 指定的y坐标
	 */
	public Bird(int x, int y) {
		Toolkit TK = Toolkit.getDefaultToolkit();
		image[0] = TK.getImage(Bird.class.getClassLoader().getResource("Image//Bird01.png"));
		image[1] = TK.getImage(Bird.class.getClassLoader().getResource("Image//Bird02.png"));
		image[2] = TK.getImage(Bird.class.getClassLoader().getResource("Image//Bird03.png"));
		pos_x = x;
		pos_y = y;
		calculateSpeedArray(50);
	}
	
	/**
	 * 通过指定跳跃时的加速度来计算每一步跳动的距离
	 * @param a 指定的跳跃时的加速度，合适的范围是[50, 70]
	 */
	public void calculateSpeedArray(int a) {
		ySpeed = new int[maxIndex + 10];
		for(int t = 1; t <= maxIndex; t ++ ) {
			ySpeed[t] = (int)(0.5 * a * (2.0*t/100 - 0.01));
		}
	}

	/**
	 * 画出这只鸟，这里需要考虑鸟当前翅膀拍动正处于哪一个状态，然后判断下一个状态，
	 * 使得翅膀的拍动是：上-中-下-中-上
	 * @param g 指定花出这只鸟的画笔
	 */
	public void draw(Graphics g) {
		if(imageIndex >= 0 && imageIndex <= 2) {
			g.drawImage(image[0], pos_x, pos_y,  BIRD_WIDTH, BIRD_HEIGHT, null); 
			if(imageIndex == 2)imageIndex = 3;
			else imageIndex ++;
		}
		else if(imageIndex >= 3 && imageIndex <= 5 || imageIndex >= 9) {
			g.drawImage(image[1], pos_x, pos_y,  BIRD_WIDTH, BIRD_HEIGHT, null);
			if(imageIndex == 5) imageIndex = 6;
			else if(imageIndex == 12) imageIndex = 0; 
			else imageIndex ++;
		}
		else if(imageIndex >= 6 && imageIndex <= 8) {
			g.drawImage(image[2], pos_x, pos_y,  BIRD_WIDTH, BIRD_HEIGHT, null);
			if(imageIndex == 8)imageIndex = 9;
			else imageIndex ++;
		}
	}

	/**
	 * 下落，鸟根据自己当前所处的速度，来确定需要下落的高度
	 * @return 如果下落到底部了，返回false， 成功下落没有到底部返回true
	 */
	public boolean drop() {
		pos_y += ySpeed[speedIndex];
		speedIndex ++ ;
		if(speedIndex >= maxIndex) speedIndex = maxIndex;
		if (pos_y + BIRD_HEIGHT >= MainFrame.GAME_HEIGHT)
			return false;
		return true;
	}
	
	/**
	 * 判断鸟当前能不能跳，当鸟跳出顶部时不能再跳
	 * @return 不过鸟当前在图内部，返回true， 否则返回false
	 */
	public boolean isCanJump() {
		if(pos_y <= 0) return false;
		speedIndex = startToJump;
		return true;
	}
	
	/**
	 * 跳跃，鸟根据自己的当前速度向上移动一小段距离， 如果速度已经达到0，不允许跳动
	 * @return 速度已经降到0（达到本次跳跃的最高点）后，返回false
	 */
	public boolean jump() {
		if(speedIndex == 0) return false;
		pos_y -= ySpeed[speedIndex];
		speedIndex --;
		return true;
	}

	public boolean isAtBottom() {
		return isAtBottom;
	}

	public void setAtBottom(boolean isAtBottom) {
		this.isAtBottom = isAtBottom;
	}

	public void setPos_x(int pos_x) {
		this.pos_x = pos_x;
	}
	
	public int getPos_x() {
		return this.pos_x;
	}
	
	public Rectangle getRect() {
		return new Rectangle(pos_x, pos_y, Bird.BIRD_WIDTH, Bird.BIRD_HEIGHT);
	}

}
