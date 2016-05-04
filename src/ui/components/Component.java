package ui.components;

import geometry2D.Shape;

import org.newdawn.slick.opengl.Texture;

import ui.ZUI;

public abstract class Component {
	
	private ZUI zui;
	
	private double x;
	private double y;
	private double w;
	private double h;
	
	private Shape hitbox;
	
	private boolean highlightable;
	private boolean clickable;
	private boolean highlight;
	private boolean click;
	
	
	
	public Component(double x, double y, double w, double h) {
		zui = ZUI.zui;
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}
	
	public abstract void render();
}