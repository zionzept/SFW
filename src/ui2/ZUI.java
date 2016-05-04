package ui2;

import geometry2D.Point;
import input.SKeyEvent;
import input.SKeyListener;
import input.SMouseEvent;
import input.SMouseListener;
import input.SMouseMoveListener;
import input.SMouseTrackListener;
import input.SMouseWheelEvent;
import input.SMouseWheelListener;

import java.util.LinkedList;

import ui2.components.SComponent;

public class ZUI implements SKeyListener, SMouseListener, SMouseMoveListener, SMouseWheelListener, SMouseTrackListener {	
	public static boolean outlineHitboxes;
	public static ZUI instance;
	
	private static LinkedList<SComponent> components;
	
	private static SComponent highlightedComponent;
	
	private ZUI() {
		components = new LinkedList<>();
	}
	
	public static void create() {
		if (instance == null) {
			instance = new ZUI();
		}
	}
	
	public static boolean add(SComponent component) {
		return components.add(component);
	}
	
	public static void clear() {
		components.clear();
	}
	
	public static void render() {
		if (components != null) {
			for (SComponent component : components) {
				component.render(outlineHitboxes);
			}
		}
	}

	@Override
	public void mouseDown(SMouseEvent e) {
		if (highlightedComponent != null) {
			highlightedComponent.mouseDown(e);
		}
	}

	@Override
	public void mouseUp(SMouseEvent e) {
		if (highlightedComponent != null) {
			highlightedComponent.mouseUp(e);
		}
	}
	
	@Override
	public void mouseMove(SMouseEvent e) {
		Point mousePos = new Point(e.getX(), e.getY());
		if (highlightedComponent != null) {
			if (!highlightedComponent.collides(mousePos)) {
				highlightedComponent.mouseExit(e);
				highlightedComponent = null;
			} else {
				for (SComponent component : components) { //reverse instead?
					if (component.collides(mousePos)) {
						component.mouseMove(e);
						break;
					}
				}
			}
		} else {
			for (SComponent component : components) { //reverse instead?
				if (component.collides(mousePos)) {
					highlightedComponent = component;
					component.mouseEnter(e);
					break;
				}
			}
		}
	}
	
	@Override
	public void mouseEnter(SMouseEvent e) {
		//TODO: needed?
	}
	
	@Override
	public void mouseExit(SMouseEvent e) {
		//TODO: needed?
	}
	
	@Override
	public void mouseScroll(SMouseWheelEvent e) {
		if (highlightedComponent != null) {
			highlightedComponent.mouseScroll(e);
		}
	}

	@Override
	public void keyPress(SKeyEvent e) {
	}

	@Override
	public void keyRelease(SKeyEvent e) {
	}
}