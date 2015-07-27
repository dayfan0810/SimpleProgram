import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.JOptionPane;
import javax.swing.Timer;

public class MainFrame extends Frame {

	private static final long serialVersionUID = 1L;
	public static final int GAME_WIDTH = 1366;
	public static final int GAME_HEIGHT = 768;
	public static final int GAME_POSX = 0;
	public static final int GAME_POSY = 0;

	private Grade grade = null;
	private Bird bird = null;
	public static final int MAXNUMOFBLOCK = 10;
	private Block[] block = new Block[MAXNUMOFBLOCK];

	private boolean isDropToBottom = false;
	private boolean isNotGameOver = true;
	private Image offScreenIamge = null;
	private Image offScreenJPG = Toolkit.getDefaultToolkit().getImage("src//Image//bg.jpg");

	Timer dropTimer = null;
	Timer jumpTimer = null;
	Timer blockTimer = null;
	Timer createBlockTimer = null;
	AudioClip backgroundMusic = null;
	AudioClip gameOverMusic = null;

	public MainFrame() {
		this.lauchFrame();// initial the Frame
		showMassage();
		start();
	}

	public void showMassage() {
		JOptionPane.showMessageDialog(this, "Enter: Try again\nEsc: Quit\n"
				+ "Up: Jump", "Message", JOptionPane.DEFAULT_OPTION);
	}

	public void start() {
		isNotGameOver = true;
		isDropToBottom = false;
		initVar();
		initMusic();
		startToDrop();// The bird is to drop
		createBlockTimer.start();// start to create Block
		blockTimer.start();// start to move for each Block
		CorrespondKeyPressed();// correspond the key pressed
	}

	public void initVar() {
		bird = new Bird();
		grade = new Grade();
		dropTimer = new Timer(20, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (bird.drop())
					repaint();
				else
					gameOver(0);
			}
		});
		jumpTimer = new Timer(20, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (isNotGameOver) {
					if (bird.jump())
						repaint();
					else
						startToDrop();
				}
			}
		});
		blockTimer = new Timer(20, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i < MAXNUMOFBLOCK; i++)
					if (isNotGameOver && block[i] != null) {
						if (!block[i].isOutOfFrame()) {
							if(grade.getGrade() >= 0) block[i].moveDown();
							block[i].move();
							if (block[i].isCrossed(bird))
								gameOver(1);
							else if (block[i].isFinished(bird))
								grade.setGrade(grade.getGrade() + 1);
						} else
							block[i] = null;
					}
			}
		});
		createBlockTimer = new Timer(1500, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i < MAXNUMOFBLOCK; i++)
					if (block[i] == null) {
						block[i] = new Block();
						break;
					}
			}
		});
	}
	
	public void initMusic() {
		try { 
			int x = (int)(Math.random()*1000)%3;
			if(x == 0) backgroundMusic = Applet.newAudioClip(new File("src//Image//mao1.mid").toURL());
			else if(x == 1) backgroundMusic = Applet.newAudioClip(new File("src//Image//FloralLife.wav").toURL());
			else backgroundMusic = Applet.newAudioClip(new File("src//Image//Mother.mid").toURL());
			backgroundMusic.loop();
			//gameOverMusic =  Applet.newAudioClip(new File("src//Image//Tombstone.wav").toURL()); 
			//gameOverMusic =  Applet.newAudioClip(new File("src//Image//replay.mp3").toURL()); 
			} catch (Exception e) { 
			e.printStackTrace(); 
			} 

	}

	public void lauchFrame() {
		this.setTitle("Bird");
		this.setLocation(GAME_POSX, GAME_POSY);
		this.setSize(GAME_WIDTH, GAME_HEIGHT);
		this.setResizable(false);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		this.setVisible(true);
	}

	public void CorrespondKeyPressed() {
		this.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_UP && bird.isCanJump()
						&& isNotGameOver) {
					dropTimer.stop();
					jumpTimer.start();
				} else if (!isNotGameOver
						&& e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					System.exit(0);
				} else if (!isNotGameOver
						&& e.getKeyCode() == KeyEvent.VK_ENTER
						&& isDropToBottom) {
					deleteTheVar();
					start();
				}
			}
		});
	}

	public void paint(Graphics g) {
		for (int i = 0; i < MAXNUMOFBLOCK; i++)
			if (block[i] != null) {
				block[i].draw(g);
			}
		if(grade != null)grade.draw(g);
		if(bird != null) bird.draw(g);
	}

	public void update(Graphics g) {
		if (offScreenIamge == null) {
			offScreenIamge = this.createImage(GAME_WIDTH, GAME_HEIGHT);
		}
		Graphics gOffScreen = offScreenIamge.getGraphics();
		Color c = gOffScreen.getColor();
		//gOffScreen.setColor(Color.GREEN);
		//gOffScreen.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
		gOffScreen.drawImage(offScreenJPG, 0, 0, GAME_WIDTH, GAME_HEIGHT, null);
		gOffScreen.setColor(c);
		paint(gOffScreen);
		g.drawImage(offScreenIamge, 0, 0, null);
	}

	// if the Bird attach the bottom style = 0, if Bird attach the Block style =
	// 1
	public void gameOver(int style) {
		if(isNotGameOver) 
			backgroundMusic.stop();
		jumpTimer.stop();
		blockTimer.stop();
		createBlockTimer.stop();
		isNotGameOver = false;
		if (style == 1) {
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			bird.setSpeedIndex(0);
			dropTimer.start();
		} else {
			dropTimer.stop();
			isDropToBottom = true;
		}
	}

	public void startToDrop() {
		jumpTimer.stop();
		dropTimer.start();
	}

	public void deleteTheVar() {
		bird = null;
		jumpTimer = null;
		dropTimer = null;
		for (int i = 0; i < MAXNUMOFBLOCK; i++) {
			block[i] = null;
		}
		blockTimer = null;
		createBlockTimer = null;
		offScreenIamge = null;
	}

	public static void main(String[] args) {
		new MainFrame();
	}

}
