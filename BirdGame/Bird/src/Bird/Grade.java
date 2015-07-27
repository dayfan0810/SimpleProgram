package Bird;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;


public class Grade {
	public static final boolean[][] num = {
		{true, true, true, false, true, true, true},
		{false, false, true, false, false, true, false},
		{true, false, true, true, true, false, true},
		{true, false, true, true, false, true, true},
		{false, true, true, true, false, true, false},
		{true, true, false, true, false, true, true},
		{true, true, false, true, true, true, true},
		{true, false, true, false, false, true, false},
		{true, true, true, true, true, true, true},
		{true, true, true, true, false, true, true}};
	private int grade;
	
	Rectangle[][] rect = new Rectangle[2][7]; 
	
	public Grade() {
		grade = 0;
		initRect();
	}

	
	private void initRect() {
		final int X = 30, Y = 30, H = 50, h = 10;
		rect[0][0] = new Rectangle(X,Y,H, h);
		rect[0][1] = new Rectangle(X,Y,h, H);
		rect[0][2] = new Rectangle(X+H-h,Y,h, H);
		rect[0][3] = new Rectangle(X,Y+H-h,H, h);
		rect[0][4] = new Rectangle(X,Y+H-h,h, H);
		rect[0][5] = new Rectangle(X+H-h,Y+H-h,h, H);
		rect[0][6] = new Rectangle(X,Y+2*H-2*h, H, h);
		
		for(int i=0;i<7;i++) {
			rect[1][i] = new Rectangle();
			rect[1][i].x = rect[0][i].x + H + h;
			rect[1][i].y = rect[0][i].y;
			rect[1][i].height = rect[0][i].height;
			rect[1][i].width = rect[0][i].width;
		}
	}
	
	public void draw(Graphics g) {
		int shi = grade / 10 % 10, ge = grade % 10; 
			for (int j = 0; j < 7; j++) if(num[shi][j]){
				Color c = g.getColor();
				g.setColor(Color.YELLOW);
				g.fillRect(rect[0][j].x,rect[0][j].y,rect[0][j].width,rect[0][j].height);
				g.setColor(c);
			}
			for (int j = 0; j < 7; j++) if(num[ge][j]){
				Color c = g.getColor();
				g.setColor(Color.YELLOW);
				g.fillRect(rect[1][j].x,rect[1][j].y,rect[1][j].width,rect[1][j].height);
				g.setColor(c);
			}
	}
	
	public void setGrade(int grade) {
		this.grade = grade;
	}

	public int getGrade() {
		return grade;
	}

}
