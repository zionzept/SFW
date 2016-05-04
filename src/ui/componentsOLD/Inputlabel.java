package ui.componentsOLD;

import java.awt.Font;

import ui.ZUI;



public class Inputlabel extends Component{
	
	public Inputlabel(double x, double y, Integer zi, ZUI zui) {
		super(x, y, zui);
		setLink(zi);
		
		focusable = true;
		textRelativeTo = 1;
		//font = new Font("SansSerif", Font.ITALIC|Font.BOLD, 30);
		setClickActionID(64);
		//setImage(Base.textures.loadImage("UI_Button.gif"));
		//setHighlightedImage(Base.textures.loadImage("UI_Button_Highlighted.gif"));
		//setClickedImage(Frame.danganTengoku.ir.loadImage("UI_Button_Clicked.gif"));
	}

	public Inputlabel(double x, double y, Double zd, ZUI zui) {
		super(x, y, zui);
		setLink(zd);
		
		focusable = true;
		textRelativeTo = 1;
	//	font = new Font("SansSerif", Font.ITALIC|Font.BOLD, 30);
		setClickActionID(64);
		//setImage(Base.textures.loadImage("UI_Button.gif"));
		//setHighlightedImage(Base.textures.loadImage("UI_Button_Highlighted.gif"));
		//setClickedImage(Frame.danganTengoku.ir.loadImage("UI_Button_Clicked.gif"));
	}
}
