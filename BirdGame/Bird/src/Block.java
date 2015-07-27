
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;


public class Block {
	public static final int BLOCK_WIDTH = 70;
	public static final int DISTANCE = 150;
	public static final int SPEED = 5;//per 20ms
	public static final int UP = MainFrame.GAME_HEIGHT/7;
	private int upBorder, leftBorder;
	private boolean finished = false;
	public int DOWNSPEED, xOfBeginMoveDown, yOfStopMoveDown, yOfStartMoveDown;
	private Image image = Toolkit.getDefaultToolkit().getImage("src//Image//pipe.png");
	
	public Block() {
		finished = false;
		leftBorder = MainFrame.GAME_WIDTH;
		upBorder = (int)(Math.random() * 10000) % (MainFrame.GAME_HEIGHT-2*UP-DISTANCE) + UP - DISTANCE;
		DOWNSPEED = (((int)(Math.random()*1000000) % 5) - 2) * ((int)(Math.random()*1000000) % 2 + 1);
		xOfBeginMoveDown = (int)(Math.random()*10000000) % (MainFrame.GAME_WIDTH/8*4) + MainFrame.GAME_WIDTH/8*3;
		yOfStartMoveDown = UP;
		yOfStopMoveDown = MainFrame.GAME_HEIGHT - UP;
		//if(DOWNSPEED > 0) yOfStopMoveDown = (int)(Math.random()*10000000) % (MainFrame.GAME_HEIGHT-UP-DISTANCE-upBorder) + upBorder;
		//else if(DOWNSPEED < 0) yOfStopMoveDown = (int)(Math.random()*10000000) % (upBorder-UP) + upBorder ;
	}
	
	public void draw(Graphics g) {
		Color c = g.getColor();
		//g.setColor(Color.BLUE);
		g.drawImage(image, leftBorder, 0, BLOCK_WIDTH, upBorder, null);
		g.drawImage(image, leftBorder, upBorder+DISTANCE, BLOCK_WIDTH, MainFrame.GAME_HEIGHT-upBorder-DISTANCE, null);
		//g.fillRect(leftBorder, 0, BLOCK_WIDTH, upBorder);
		//g.fillRect(leftBorder, upBorder+DISTANCE, BLOCK_WIDTH, MainFrame.GAME_HEIGHT-upBorder-DISTANCE);
		g.setColor(c);
	}
	
	public boolean isOutOfFrame() {
		if(leftBorder + BLOCK_WIDTH <= 0) return true;
		return false;
	}
	
	public void moveDown() {
		if(leftBorder < xOfBeginMoveDown ) {
			if( (upBorder-yOfStartMoveDown)*(upBorder-yOfStopMoveDown) < 0 )
				upBorder += DOWNSPEED;
			else {
				DOWNSPEED = -DOWNSPEED;
				upBorder += DOWNSPEED;
			}
		}
	}
	
	public void move() {
		leftBorder -= SPEED;
	}
	
	public boolean isCrossed(Bird bird) {
		int x = bird.pos_x+Bird.BIRD_WIDTH/2, y = bird.pos_y+Bird.BIRD_WIDTH/2;
		if(y <= upBorder || y >= upBorder+DISTANCE) {
			if(Math.abs(leftBorder - x) < Bird.BIRD_WIDTH/2) {
				return true;
			}
		}
		if(x >= leftBorder && x <= leftBorder+BLOCK_WIDTH) {
			int dis = Math.min(y-upBorder, upBorder+DISTANCE-y);
			if(dis < Bird.BIRD_WIDTH/2) return true;
		}
		return false;
	}
	
	public boolean isFinished(Bird bird) {
		if(this.finished) return false;
		int mid = this.leftBorder + Block.BLOCK_WIDTH/2;
		if (bird.pos_x < mid) return false;
		this.finished = true;
		return true;
	}
	
}
