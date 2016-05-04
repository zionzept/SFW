package ui.componentsOLD;


import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;

import ui.ZUI;

public class Label extends Component{
	
	
	
	public Label(double x, double y, String txt, TrueTypeFont f, Color c, ZUI zui) {
		super(x, y, zui);
		texts.add(new Text(0, 0, txt, f, c));
	}
	
	/*public Label(float x, float y, String txt, TrueTypeFont f, Color c, int animSpeed) {
		xPos = x;
		yPos = y;
		font = f;
	//	textColor = c;
		setAnimationSpeed(animSpeed);
		setFinalText(txt);
		setText("");
		width = 9999;
	}*/
	
	
	
	
}
