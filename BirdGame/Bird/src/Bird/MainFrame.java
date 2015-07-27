package Bird;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JOptionPane;
import javax.swing.Timer;

public class MainFrame extends Frame {

	private static final long serialVersionUID = 1L;
	public static final int GAME_WIDTH = 500;
	public static final int GAME_HEIGHT = 600;
	public static final int GAME_POSX = 400;
	public static final int GAME_POSY = 50;
	public static final int NUMOFBIRD = 2;// can not be changed

	Grade grade = null;
	private int dropNum = 0;
	private Bird[] bird = new Bird[10];
	public static final int MAXNUMOFBLOCK = 10;
	private Block[] block = new Block[MAXNUMOFBLOCK];

	private boolean isNotGameOver = true;
	private Image offScreenIamge = null;

	Timer[] birdDropTimer = new Timer[10];
	Timer[] birdJumpTimer = new Timer[10];
	Timer blockTimer = null;
	Timer createBlockTimer = null;

	int index;

	public MainFrame() {
		this.lauchFrame();// initial the Frame
		showMassage();
		start();
	}

	public void showMassage() {
		JOptionPane.showMessageDialog(this, "[ 一 ]  [ 个 ]  [ 都 ]  [ 不 ]  [ 能 ]  [ 死 ] ！！！\n" 
				+ "Enter: Try again\nEsc: Quit\n"
				+ "Up: The right one\nSpace: The left one\n"
				+ "Addition: This is a personal game", "Message",
				JOptionPane.DEFAULT_OPTION);
	}

	public void start() {
		isNotGameOver = true;
		dropNum = 0;
		initBird();
		initTimer();
		for (int i = 0; i < NUMOFBIRD; i++)
			startToDrop(i);// The bird is to drop
		createBlockTimer.start();// start to create Block
		blockTimer.start();// start to move for each Block
		CorrespondKeyPressed();// correspond the key pressed
	}

	public void initBird() {
		grade = new Grade();
		for (int i = 0; i < NUMOFBIRD; i++) {
			bird[i] = new Bird();
			bird[i].pos_x -= i * 200;
			bird[i].pos_x += 50;
		}
	}

	public void initTimer() {
		/*
		 * for(index =0; index < NUMOFBIRD;index++) { birdDropTimer[index] = new
		 * Timer(20, new ActionListener() { public void
		 * actionPerformed(ActionEvent e) {
		 * System.out.println(NUMOFBIRD+"=========================");
		 * System.out.println(index); System.out.println(bird[index]); if(index
		 * < 2) { if (bird[index].drop()) repaint(); else {
		 * bird[index].isAtBottom = true; gameOver(0); } } } });
		 * birdJumpTimer[index] = new Timer(20, new ActionListener() { public
		 * void actionPerformed(ActionEvent e) { if(index<2){ if
		 * (bird[index].jump()) repaint(); else startToDrop(0); } } }); }
		 */

		birdDropTimer[0] = new Timer(20, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (bird[0] != null) {
					if (bird[0].drop())
						repaint();
					else {
						bird[0].isAtBottom = true;
						gameOver(0);
					}
				}
			}
		});
		birdJumpTimer[0] = new Timer(20, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (bird[0] != null) {
					if (bird[0].jump())
						repaint();
					else
						startToDrop(0);
				}
			}
		});

		birdDropTimer[1] = new Timer(20, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (bird[1] != null) {
					if (bird[1].drop())
						repaint();
					else {
						bird[1].isAtBottom = true;
						gameOver(0);
					}
				}
			}
		});
		birdJumpTimer[1] = new Timer(20, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (bird[1] != null) {
					if (bird[1].jump())
						repaint();
					else
						startToDrop(1);
				}
			}
		});
		blockTimer = new Timer(20, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i < MAXNUMOFBLOCK; i++)
					if (block[i] != null && isNotGameOver) {
						if (!block[i].isOutOfFrame()) {
							block[i].move();
							if (block[i].isFinished(bird[0])) {/*
								for (int j = 0; j < MAXNUMOFBLOCK; j++)
									if (block[j] != null) {
										block[j].setFinished(true);
									}*/
								grade.setGrade(grade.getGrade() + 1);
							}
							for (int j = 0; j < NUMOFBIRD; j++) {
								if (block[i].isCrossed(bird[j]))
									gameOver(1);
							}
						} else
							block[i] = null;
					}
			}
		});
		createBlockTimer = new Timer(1000, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i < MAXNUMOFBLOCK; i++)
					if (block[i] == null) {
						block[i] = new Block();
						break;
					}
			}
		});
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
		this.setBackground(Color.GREEN);
		this.setVisible(true);
	}

	public void CorrespondKeyPressed() {
		this.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_UP && bird[0].isCanJump()
						&& isNotGameOver) {
					birdDropTimer[0].stop();
					birdJumpTimer[0].start();
				} else if (e.getKeyCode() == KeyEvent.VK_SPACE
						&& bird[1].isCanJump() && isNotGameOver) {
					birdDropTimer[1].stop();
					birdJumpTimer[1].start();
				} else if (!isNotGameOver
						&& e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					System.exit(0);
				} else if (!isNotGameOver
						&& e.getKeyCode() == KeyEvent.VK_ENTER
						&& dropNum == NUMOFBIRD) {
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
		for (int i = 0; i < NUMOFBIRD; i++)
			if (bird[i] != null) {
				bird[i].draw(g);
			}
		if (grade != null)
			grade.draw(g);
	}

	public void update(Graphics g) {
		if (offScreenIamge == null) {
			offScreenIamge = this.createImage(GAME_WIDTH, GAME_HEIGHT);
		}
		Graphics gOffScreen = offScreenIamge.getGraphics();
		Color c = gOffScreen.getColor();
		gOffScreen.setColor(Color.GREEN);
		gOffScreen.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
		gOffScreen.setColor(c);
		paint(gOffScreen);
		g.drawImage(offScreenIamge, 0, 0, null);
	}

	// if the Bird attach the bottom style = 0, if Bird attach the Block style =
	// 1
	public void gameOver(int style) {
		isNotGameOver = false;
		blockTimer.stop();
		createBlockTimer.stop();

		if (style == 1) {
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			for (int i = 0; i < NUMOFBIRD; i++)
				if (!bird[i].isAtBottom) {
					birdJumpTimer[i].stop();
					birdDropTimer[i].start();
				}
		} else {
			for (int i = 0; i < NUMOFBIRD; i++)
				if (bird[i].isAtBottom) {
					dropNum++;
					if (dropNum > NUMOFBIRD)
						dropNum = NUMOFBIRD;
					birdJumpTimer[i].stop();
					birdDropTimer[i].stop();
				}
			repaint();
		}
	}

	public void deleteTheVar() {
		for (int i = 0; i < NUMOFBIRD; i++) {
			bird[i] = null;
			birdJumpTimer[i] = null;
			birdDropTimer[i] = null;
		}
		for (int i = 0; i < MAXNUMOFBLOCK; i++) {
			block[i] = null;
		}
		blockTimer = null;
		createBlockTimer = null;
		offScreenIamge = null;
	}

	public void startToDrop(int i) {
		birdJumpTimer[i].stop();
		birdDropTimer[i].start();
	}

	public static void main(String[] args) {
		new MainFrame();
	}

}
