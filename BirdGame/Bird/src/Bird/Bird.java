package Bird;
import java.awt.Color;
import java.awt.Graphics;

public class Bird {

	public static final int INIT_POSX = 200;
	public static final int INIT_POSY = 250;
	public static final int BIRD_WIDTH = 30;
	public static final int BIRD_HEIGHT = 30;

	public boolean isAtBottom = false;
	public int pos_x, pos_y;
	private int[] ySpeed= null;
	private int speedIndex = 0;
	public static final int maxIndex=30, startToJump = 17;

	public Bird() {
		pos_x = INIT_POSX;
		pos_y = INIT_POSY;
		calculateSpeedArray(50);//calculate the speed 
	}
	
	public void calculateSpeedArray(int a) {
		ySpeed = new int[maxIndex + 10];
		for(int t = 1; t <= maxIndex; t ++ ) {
			ySpeed[t] = (int)(0.5 * a * (2.0*t/100 - 0.01));
		}
	}

	public void draw(Graphics g) {
		Color c = g.getColor();
		g.setColor(Color.RED);
		g.fillOval(pos_x, pos_y, BIRD_WIDTH, BIRD_HEIGHT);
		g.setColor(c);
	}

	public boolean drop() {
		pos_y += ySpeed[speedIndex];
		speedIndex ++ ;
		if(speedIndex >= maxIndex) speedIndex = maxIndex;
		if (pos_y + BIRD_HEIGHT >= MainFrame.GAME_HEIGHT)
			return false;
		return true;
	}
	
	public boolean isCanJump() {
		if(pos_y <= 0) return false;
		speedIndex = startToJump;
		return true;
	}
	
	public boolean jump() {
		if(speedIndex == 0) return false;
		pos_y -= ySpeed[speedIndex];
		speedIndex --;
		return true;
	}
}
