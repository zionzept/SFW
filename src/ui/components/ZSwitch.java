package ui.components;

import java.awt.Color;
import java.awt.Font;

import ui.ZUI;



public class ZSwitch extends Component{

	public ZSwitch(double x, double y, Boolean zb, ZUI zui) {
		super(x, y, zui);
		setLink(zb);
		textRelativeTo = 1;
		//font = new Font("SansSerif", Font.ITALIC|Font.BOLD, 30);
		textColor = Color.WHITE;
		setClickedActionID(65);
		//setImage(Base.textures.loadImage("UI_Button.gif"));
		//setHighlightedImage(Base.textures.loadImage("UI_Button_Highlighted.gif"));
		//setClickedImage(Base.textures.loadImage("UI_Button_Clicked.gif"));
	}
}
