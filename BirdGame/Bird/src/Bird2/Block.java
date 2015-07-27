package Bird2;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;


public class Block {
	public static final int BLOCK_WIDTH = 70;
	public static final int DISTANCE = 180;
	public static final int SPEED = 5;//per 20ms
	private int upBorder, leftBorder;
	private boolean finished = false;
	private Image upImage = Toolkit.getDefaultToolkit().getImage("src//Image//pipe.png");
	private Image downImage = Toolkit.getDefaultToolkit().getImage("src//Image//pipe.png");
	
	public Block() {
		final int UP = MainFrame.GAME_HEIGHT/7;
		finished = false;
		leftBorder = MainFrame.GAME_WIDTH;
		upBorder = (int)(Math.random() * 10000) % (MainFrame.GAME_HEIGHT-2*UP-DISTANCE) + UP;
	}
	
	public void draw(Graphics g) {
		Color c = g.getColor();
		//g.setColor(Color.BLUE);
		g.drawImage(upImage, leftBorder, 0, BLOCK_WIDTH, upBorder, null);
		g.drawImage(downImage, leftBorder, upBorder+DISTANCE, BLOCK_WIDTH, MainFrame.GAME_HEIGHT-upBorder-DISTANCE, null);
		//g.fillRect(leftBorder, 0, BLOCK_WIDTH, upBorder);
		//g.fillRect(leftBorder, upBorder+DISTANCE, BLOCK_WIDTH, MainFrame.GAME_HEIGHT-upBorder-DISTANCE);
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
		Rectangle rect = bird.getRect();
		if(rect.intersects(this.getUpRect()) || rect.intersects(this.getDownRect()))
			return true;
		return false;
	}
	
	public boolean isFinished(Bird bird) {
		if(this.finished) return false;
		int mid = this.leftBorder + Block.BLOCK_WIDTH/2;
		if (bird.getPos_x() < mid) return false;
		this.finished = true;
		return true;
	}
	
	public Rectangle getUpRect() {
		return new Rectangle(leftBorder, 0, Block.BLOCK_WIDTH, upBorder);
	}
	
	public Rectangle getDownRect() {
		return new Rectangle(leftBorder, upBorder + Block.DISTANCE, Block.BLOCK_WIDTH, MainFrame.GAME_HEIGHT - upBorder - Block.DISTANCE);
	}
	
}
