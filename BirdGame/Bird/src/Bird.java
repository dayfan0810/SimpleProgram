
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

public class Bird {

	public static final int INIT_POSX = MainFrame.GAME_WIDTH/8*3;
	public static final int INIT_POSY = MainFrame.GAME_HEIGHT/2;
	public static final int BIRD_WIDTH = 30;
	public static final int BIRD_HEIGHT = 30;

	public boolean isAtBottom = false;
	public int pos_x, pos_y;
	private int[] ySpeed= null;
	private int speedIndex = 0;
	private Image[] image = new Image[3];
	private int imageIndex = 0;
	
	public static final int maxIndex=100, startToJump = 19;
	
	public Bird() {
		image[0] = Toolkit.getDefaultToolkit().getImage("src//Image//Bird01.png");
		image[1] = Toolkit.getDefaultToolkit().getImage("src//Image//Bird02.png");
		image[2] = Toolkit.getDefaultToolkit().getImage("src//Image//Bird03.png");
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
		//Color c = g.getColor();
		//g.setColor(Color.RED);
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
		//g.fillOval(pos_x, pos_y, BIRD_WIDTH, BIRD_HEIGHT);
		//g.setColor(c);
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

	public void setSpeedIndex(int speedIndex) {
		this.speedIndex = speedIndex;
	}

}
