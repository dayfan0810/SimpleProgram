package Bird3;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;


public class Grade {
	
	private int grade;
	
	public Grade() {
		grade = 0;
	}
	
	public void draw(Graphics g) {
		final int X = 20, Y = 90;
		Color c = g.getColor();
		Font f = g.getFont();
		g.setColor(Color.YELLOW);
		g.setFont(new Font("str", Font.BOLD, 50));
		g.drawString("Score: "+grade, X, Y);
		g.setColor(c);
		g.setFont(f);
	}
	
	public void setGrade(int grade) {
		this.grade = grade;
	}

	public int getGrade() {
		return grade;
	}

}
