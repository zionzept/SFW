package ui.components;

import java.awt.FontMetrics;
import java.awt.Graphics;


import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;

import ui.ZUI;


public class Text {
	String text;
	Color color;
	TrueTypeFont font;
	double xPos, yPos;
	
	public Text(double x, double y, String txt, TrueTypeFont f, Color c) {
		text = txt;
		font = f;
		color = c;
		xPos = x;
		yPos = y;
	}
	
	/*public int getTextWidth(Graphics g) {
		FontMetrics metrics = g.getFontMetrics(font);
		return metrics.stringWidth(getText());
	}
	
	public int getTextHeight(Graphics g) {
		FontMetrics metrics = g.getFontMetrics(font);
		return metrics.getHeight();
	}*/

	public void render(double x, double y) {
		//font.drawString((x + xPos) * ZionUI.WIDTH, (y + yPos) * ZionUI.HEIGHT, text, color);
	}
	
}
 