package ui;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;

import org.lwjgl.input.Mouse;

import ui.components.Component;
import ui.components.Panel;

public abstract class ZUI {
	
	public static ZUI zui;
	
	public static int WIDTH;
	public static int HEIGHT;
	public static float FONT_SIZE = 1.3f;
	public static float mouseX, mouseY;
	
	private Component lastPressedComponent;
	
	public static ArrayList<Panel> panels;
	public static Component focusedComponent;
	public static Component keyInputTarget;
	
	private boolean lmbFlop;
	
	public ZUI(int w, int h) {
		zui = this;
		panels = new ArrayList<Panel>();
		WIDTH = w;
		HEIGHT = h;
	}
	
	public void establishDualRadiationLinks(ZUI zui) {
		
	}
	
	public void mouse(float x, float y) {
		mouseX = x;
		mouseY = y;
		ArrayList<Panel> panels2 = panels;
		for (Panel p : panels2) {
			p.mouse(x, y);
		}
		if (Mouse.isButtonDown(0)) {
			if (!lmbFlop) {
				lmbFlop = true;
				try {
					for (Panel p : panels2)
						p.press(x, y);
				} catch(ConcurrentModificationException e) {
				}
			}
		} else if (lmbFlop) {
			lmbFlop = false;
			try {
				for (Panel p : panels2)
					p.release(x, y);
			} catch(ConcurrentModificationException e) {
			}
		}
	}
	
	public void render() {
		for (Panel p : panels) {
			p.render();
		}
	}
	
	public void clear() {
		panels.clear();
	}
	
	public void add(Panel p) {
		panels.add(p);
	}
	
	public void remove(Panel p) {
		panels.remove(p);
	}
	
	public abstract void executeAction(int actionID);
	public abstract void clicksound();
	public abstract void hlsound();
}