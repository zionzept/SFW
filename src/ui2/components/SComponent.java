package ui2.components;

import geometry2D.Point;
import geometry2D.Shape;
import input.SKeyListener;
import input.SMouseEvent;
import input.SMouseListener;
import input.SMouseMoveListener;
import input.SMouseTrackListener;
import input.SMouseWheelEvent;
import input.SMouseWheelListener;

import java.util.LinkedList;

import ui2.SActivationListener;

public abstract class SComponent implements SMouseListener, SMouseMoveListener, SMouseWheelListener, SMouseTrackListener {
	
	private LinkedList<SKeyListener> keyListeners;
	private LinkedList<SMouseListener> mouseListeners;
	private LinkedList<SMouseMoveListener> mouseMoveListeners;
	private LinkedList<SMouseTrackListener> mouseTrackListeners;
	private LinkedList<SMouseWheelListener> mouseWheelListeners;
	
	private LinkedList<SActivationListener> activationListeners;
	
	private Shape hitbox;
	
	public SComponent(Shape hitbox) {  //Hwat?
		keyListeners = new LinkedList<>();
		mouseListeners = new LinkedList<>();
		mouseMoveListeners = new LinkedList<>();
		mouseTrackListeners = new LinkedList<>();
		mouseWheelListeners = new LinkedList<>();
		activationListeners = new LinkedList<>();
		setHitbox(hitbox);
	}
	
	public void setHitbox(Shape hitbox) {
		this.hitbox = hitbox;
	}

	public Shape getHitbox() {
		return hitbox;
	}
	
	public boolean collides(Point point) {
		return hitbox.collides(point);
	}
	
	protected void activation() {
		for (SActivationListener listener : activationListeners) {
			listener.activation();
		}
	}
	
	@Override
	public final void mouseDown(SMouseEvent e) {
		for (SMouseListener listener : mouseListeners) {
			listener.mouseDown(e);
		}
	}
	
	@Override
	public final void mouseUp(SMouseEvent e) {
		for (SMouseListener listener : mouseListeners) {
			listener.mouseUp(e);
		}
	}
	
	@Override
	public final void mouseMove(SMouseEvent e) {
		for (SMouseMoveListener listener : mouseMoveListeners) {
			listener.mouseMove(e);
		}
	}
	
	@Override
	public final void mouseEnter(SMouseEvent e) {
		for (SMouseTrackListener mouseTrackListener : mouseTrackListeners) {
			mouseTrackListener.mouseEnter(e);
		}
	}
	
	@Override
	public final void mouseExit(SMouseEvent e) {
		for (SMouseTrackListener mouseTrackListener : mouseTrackListeners) {
			mouseTrackListener.mouseExit(e);
		}
	}
	
	@Override
	public final void mouseScroll(SMouseWheelEvent e) {
		for (SMouseWheelListener listener : mouseWheelListeners) {
			listener.mouseScroll(e);
		}
	}
	
	public boolean addKeyListener(SKeyListener listener) {
		return keyListeners.add(listener);
	}
	
	public boolean removeKeyListener(SKeyListener listener) {
		return keyListeners.remove(listener);
	}
	
	public boolean addMouseListener(SMouseListener listener) {
		return mouseListeners.add(listener);
	}
	
	public boolean removeMouseListener(SMouseListener listener) {
		return mouseListeners.remove(listener);
	}
	
	public boolean addMouseMoveListener(SMouseMoveListener listener) {
		return mouseMoveListeners.add(listener);
	}
	
	public boolean removeMouseMotionListener(SMouseMoveListener listener) {
		return mouseMoveListeners.remove(listener);
	}

	public boolean addMouseTrackListener(SMouseTrackListener listener) {
		return mouseTrackListeners.add(listener);
	}
	
	public boolean removeMouseTrackListener(SMouseTrackListener listener) {
		return mouseTrackListeners.remove(listener);
	}
	
	public boolean addMouseWheelListener(SMouseWheelListener listener) {
		return mouseWheelListeners.add(listener);
	}
	
	public boolean removeMouseWheelListener(SMouseWheelListener listener) {
		return mouseWheelListeners.remove(listener);
	}
	
	public boolean addActivationListener(SActivationListener listener) {
		return activationListeners.add(listener);
	}
	
	public boolean removeActivationListener(SActivationListener listener) {
		return activationListeners.remove(listener);
	}
	
	public void render(boolean outline) {
		render();
		hitbox.render(true);
		if (outline) {
			if (hitbox != null) {
				hitbox.render(false);
			}
		}
	}
	
	protected abstract void render();
}