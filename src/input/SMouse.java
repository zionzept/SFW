package input;

import java.util.HashSet;
import java.util.LinkedList;

import ui2.ZUI;

public class SMouse {
	private static HashSet<Integer> pressedButtons = new HashSet<>();
	private static LinkedList<SHotkey> hotkeys = new LinkedList<>();
	private static LinkedList<SHotkey> wheelHotkeys = new LinkedList<>();
	private static LinkedList<SMouseListener> mouseListeners = new LinkedList<>();
	private static LinkedList<SMouseMoveListener> mouseMoveListeners = new LinkedList<>();
	private static LinkedList<SMouseTrackListener> mouseTrackListeners = new LinkedList<>();
	private static LinkedList<SMouseWheelListener> mouseWheelListeners = new LinkedList<>();
	
	private static int x;
	private static int y;
	private static int px;
	private static int py;

	private SMouse() {}
	
	public static int getX() {
		return x;
	}
	
	public static int getY() {
		return y;
	}
	
	public static int getDX() {
		return x - px;
	}
	
	public static int getDY() {
		return y - py;
	}
	
	public static boolean isPressed(int button) {
		return pressedButtons.contains(button);
	}
	
	public static int pressedButtonCount() {
		return pressedButtons.size();
	}
	
	public static HashSet<Integer> pressedButtons() {
		return pressedButtons; //TODO: security issue :D
	}
	
	public static void addHotkey(SHotkey hotkey) {
		hotkey.setMouseActivated();
		hotkeys.add(hotkey);
	}
	
	public static void addWheelHotkey(SHotkey hotkey) {
		hotkey.setWheelActivated();
		wheelHotkeys.add(hotkey);
	}
	
	public static void addMouseListener(SMouseListener mouseListener) {
		mouseListeners.add(mouseListener);
	}
	
	public static void addMouseMoveListener(SMouseMoveListener mouseMotionListener) {
		mouseMoveListeners.add(mouseMotionListener);
	}
	
	public static void addMouseTrackListener(SMouseTrackListener mouseTrackListener) {
		mouseTrackListeners.add(mouseTrackListener);
	}
	
	public static void addMouseWheelListener(SMouseWheelListener mouseWheelListener) {
		mouseWheelListeners.add(mouseWheelListener);
	}
	
	protected static void mouseDown(SMouseEvent e) {
		pressedButtons.add(e.getButton());
		for (SMouseListener mouseListener : mouseListeners) {
			mouseListener.mouseDown(e);
		}
		for (SHotkey hotkey : hotkeys) {
			hotkey.check(e.getButton());
		}
		if (ZUI.instance != null) {
			ZUI.instance.mouseDown(e);
		}
	}

	protected static void mouseUp(SMouseEvent e) {
		pressedButtons.remove(e.getButton());
		for (SMouseListener mouseListener : mouseListeners) {
			mouseListener.mouseUp(e);
		}
		if (ZUI.instance != null) {
			ZUI.instance.mouseUp(e);
		}
	}

	protected static void mouseMove(SMouseEvent e) {
		px = x;
		py = y;
		x = e.getX();
		y = e.getY();
		for (SMouseMoveListener mouseMoveListener : mouseMoveListeners) {
			mouseMoveListener.mouseMove(e);
		}
		if (ZUI.instance != null) {
			ZUI.instance.mouseMove(e);
		}
	}
	
	protected static void mouseEnter(SMouseEvent e) {
		px = x;
		py = y;
		x = e.getX();
		y = e.getY();
		for (SMouseTrackListener mouseTrackListener : mouseTrackListeners) {
			mouseTrackListener.mouseEnter(e);
		}
		if (ZUI.instance != null) {
			ZUI.instance.mouseEnter(e);
		}
	}
	
	protected static void mouseExit(SMouseEvent e) {
		for (SMouseTrackListener mouseTrackListener : mouseTrackListeners) {
			mouseTrackListener.mouseExit(e);
		}
		if (ZUI.instance != null) {
			ZUI.instance.mouseExit(e);
		}
	}

	protected static void mouseScroll(SMouseWheelEvent e) {
		for (SMouseWheelListener mouseWheelListener : mouseWheelListeners) {
			mouseWheelListener.mouseScroll(e);
		}
		for (SHotkey hotkey : wheelHotkeys) {
			hotkey.check(e.getDirection());
		}
		if (ZUI.instance != null) {
			ZUI.instance.mouseScroll(e);
		}
	}
}