package ui.components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.util.ArrayList;



import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;

import ui.ZUI;
import ui.componentsOLD2.Component.LinkType;




public class Panel {

	String name;
	
	private double xPos, yPos, width, height;
	private Texture texture, hlTexture;
	private boolean hlable, hl;
	public double alpha;
	
	
	public Component lastAddedComponent;
	ArrayList<Component> components = new ArrayList<Component>();
	Component clickedComponent;
	
	public Panel(String name, double x, double y, double w, double h) {
		xPos = x;
		yPos = y;
		width = w;
		height = h;
		alpha = 1f;
	}
	
	public void slide(double x, double y) {
		xPos += x;
		yPos += y;
	}
	
	public void setTexture(Texture texture) {
		this.texture = texture;
	}
	
	
	public void addComponent(Component c) {
		components.add(c);
		lastAddedComponent = c;
	}

	public void link() {
		for (Component c : components) {
			if (c == ZUI.focusedComponent) {
				c.link();
				ZUI.focusedComponent = null;
				ZUI.keyInputTarget = null;
			}
		}
	}
	
	public void render() {
		boolean check = false;
		if (hlTexture != null && hl) {
			hlTexture.bind();
			check = true;
		}
		else if (texture != null) {
			texture.bind();
			check = true;
		}
		if (check) {
			GL11.glBegin(GL11.GL_QUADS);
			GL11.glColor4d(1, 1, 1, alpha);
			GL11.glTexCoord2d(0, 0);
			GL11.glVertex2d(xPos * ZUI.WIDTH, yPos * ZUI.HEIGHT);
			GL11.glTexCoord2d(1, 0);
			GL11.glVertex2d((xPos + width) * ZUI.WIDTH, yPos * ZUI.HEIGHT);
			GL11.glTexCoord2d(1, 1);
			GL11.glVertex2d((xPos + width) * ZUI.WIDTH, (yPos + height) * ZUI.HEIGHT);
			GL11.glTexCoord2d(0, 1);
			GL11.glVertex2d(xPos * ZUI.WIDTH, (yPos + height) * ZUI.HEIGHT);
			GL11.glEnd();
		}
		
		for (Component c : components)
			c.render(xPos, yPos);
	}
	
	public void mouse(double x, double y) {
		if (x >= xPos && x < xPos + width && y >= yPos && y < yPos + height) {
			hl = true;
			if (clickedComponent == null) {
				for (Component c : components)
					c.mouse(x - xPos, y - yPos);
			}
			else {
				clickedComponent.mouse(x - xPos, y - yPos);
			}
		}
		else {
			if (hl)
				for (Component c : components)
					c.hl = false;
			hl = false;
		}
	}
	
	public void press(double x, double y) {
		if (x >= xPos && x < xPos + width && y >= yPos && y < yPos + height) {
			for (Component c : components) {
				if(c.clickedTexture != null) {
					if (c.press(x - xPos, y - yPos)) {
						if (clickedComponent != null)
							clickedComponent.clicked = false;
						clickedComponent = c;
					}
				}
			}
		}
	}
	
	public boolean release(float x, float y) {
		if (x >= xPos && x < xPos + width && y >= yPos && y < yPos + height) {
			if (clickedComponent != null)
			clickedComponent.release(x - xPos, y - yPos);
			for (Component c : components)
				if(c.hlTexture != null)
					c.mouse(x - xPos, y - yPos);
			clickedComponent = null;
			return true;
		}
		else if (clickedComponent != null) {
			clickedComponent.clicked = false;
		}
		clickedComponent = null;
		return false;
	}
}
