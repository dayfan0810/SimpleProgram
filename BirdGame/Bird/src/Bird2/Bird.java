package Bird2;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;

public class Bird {

	/** ������ֻ���Ĭ�ϵĳ�ʼλ�õ�X����, INIT_POSX = MainFrame.GAME_WIDTH/8*3*/
	public static final int INIT_POSX = MainFrame.GAME_WIDTH/8*3;
	
	/** ������ֻ���Ĭ�ϵĳ�ʼλ�õ�Y����, INIT_POSY = MainFrame.GAME_HEIGHT/2 */
	public static final int INIT_POSY = MainFrame.GAME_HEIGHT/2;
	
	/** ������ֻ���Ĭ�ϵĿ�ȣ� BIRD_WIDTH = 30 */
	public static final int BIRD_WIDTH = 30;
	
	/** ������ֻ���Ĭ�ϵĸ߶ȣ� BIRD_HEIGHT = 30 */
	public static final int BIRD_HEIGHT = 30;
	
	private static final int maxIndex=30, startToJump = 17;
	
	//��¼��ֻ���Ƕ��������ײ������û�е������ײ�����������������ӦENTER��ESC��
	private boolean isAtBottom = false;
	//��¼��ֻ��ǰ���ڵ�λ��
	private int pos_x, pos_y;
	//����ٶ�Ϊ0��ʼ����ʱÿһ������ľ������飬��calculateSpeedArray()�и�ֵ
	private int[] ySpeed= null;
	//��ǰ�½�ʱ���ٶ�ΪySpeed[speedIndex]
	private int speedIndex = 0;
	//��ĳ���ȶ�ʱ������ͼƬ
	private Image[] image = new Image[3];
	//��ǰ��������ͼƬ
	private int imageIndex = 0;
	
	/**
	 * ��ʼ����ֻ��Ļ�����Ϣ�������ĳ�ʼλ�ã������˶�ʱ������ͼƬ, �Լ������������ٶ�
	 */
	public Bird() {
		this(INIT_POSX, INIT_POSY);
	}
	
	/**
	 * ʵ����ָ����λ�ó�ʼ����ֻ��
	 * @param x ָ����x����
	 * @param y ָ����y����
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
	 * ͨ��ָ����Ծʱ�ļ��ٶ�������ÿһ�������ľ���
	 * @param a ָ������Ծʱ�ļ��ٶȣ����ʵķ�Χ��[50, 70]
	 */
	public void calculateSpeedArray(int a) {
		ySpeed = new int[maxIndex + 10];
		for(int t = 1; t <= maxIndex; t ++ ) {
			ySpeed[t] = (int)(0.5 * a * (2.0*t/100 - 0.01));
		}
	}

	/**
	 * ������ֻ��������Ҫ������ǰ����Ķ���������һ��״̬��Ȼ���ж���һ��״̬��
	 * ʹ�ó����Ķ��ǣ���-��-��-��-��
	 * @param g ָ��������ֻ��Ļ���
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
	 * ���䣬������Լ���ǰ�������ٶȣ���ȷ����Ҫ����ĸ߶�
	 * @return ������䵽�ײ��ˣ�����false�� �ɹ�����û�е��ײ�����true
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
	 * �ж���ǰ�ܲ�������������������ʱ��������
	 * @return ������ǰ��ͼ�ڲ�������true�� ���򷵻�false
	 */
	public boolean isCanJump() {
		if(pos_y <= 0) return false;
		speedIndex = startToJump;
		return true;
	}
	
	/**
	 * ��Ծ��������Լ��ĵ�ǰ�ٶ������ƶ�һС�ξ��룬 ����ٶ��Ѿ��ﵽ0������������
	 * @return �ٶ��Ѿ�����0���ﵽ������Ծ����ߵ㣩�󣬷���false
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
