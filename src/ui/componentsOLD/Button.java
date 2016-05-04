package ui.componentsOLD;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;

import org.newdawn.slick.opengl.Texture;

import ui.ZUI;
import ui.componentsOLD.Component;
import wrap.ZTexture;



public class Button extends Component{
	
	public Button(double x, double y, Texture[] textures, ZTexture overlayTexture,ZUI zui) {
		super(x, y, zui);
		setTexture(textures[0]);
		setHighlightedTexture(textures[1]);
		setClickedTexture(textures[2]);
		setOverlayTexture(overlayTexture, CENTER);
	}
}
		
		
		
	