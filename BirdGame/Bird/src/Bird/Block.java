package Bird;

import java.awt.Color;
import java.awt.Graphics;


public class Block {
	public static final int BLOCK_WIDTH = 70;
	public static final int DISTANCE = 150;
	public static final int SPEED = 5;//per 20ms
	private int upBorder, leftBorder;
	private boolean finished = false;

	public Block() {
		finished = false;
		leftBorder = MainFrame.GAME_WIDTH;
		upBorder = (int)(Math.random() * 10000) % 250 + 50;
	}
	
	public void draw(Graphics g) {
		Color c = g.getColor();
		g.setColor(Color.BLUE);
		g.fillRect(leftBorder, 0, BLOCK_WIDTH, upBorder);
		g.fillRect(leftBorder, upBorder+DISTANCE, BLOCK_WIDTH, MainFrame.GAME_HEIGHT-upBorder-DISTANCE);
		g.setColor(c);
	}
	
	public boolean isOutOfFrame() {
		if(leftBorder + BLOCK_WIDTH <= 0) return true;
		return false;
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
	
	public void setFinished(boolean finished) {
		this.finished = finished;
	}

	
}
