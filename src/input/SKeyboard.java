package input;

import java.util.HashSet;
import java.util.LinkedList;

import ui2.ZUI;

public class SKeyboard {
	private static HashSet<Integer> pressedKeys = new HashSet<Integer>();
	private static LinkedList<SHotkey> hotkeys = new LinkedList<SHotkey>();
	private static LinkedList<SKeyListener> keyListeners = new LinkedList<SKeyListener>();
	
	private SKeyboard() {}
	
	public static boolean isPressed(int key) {
		return pressedKeys.contains(key);
	}
	
	public static int pressedKeyCount() {
		return pressedKeys.size();
	}
	
	public static HashSet<Integer> pressedKeys() {
		return pressedKeys; //TODO: roubustness  issue :D
	}
	
	public static void addHotkey(SHotkey hotkey) {
		hotkey.setKeyboardActivated();
		hotkeys.add(hotkey);
	}

	public static void addKeyListener(SKeyListener keyListener) {
		keyListeners.add(keyListener);
	}
	
	protected static void keyPress(SKeyEvent e) {
		pressedKeys.add(e.getKeyCode());
		for (SKeyListener keyListener : keyListeners) {
			keyListener.keyPress(e);
		}
		for (SHotkey hotkey : hotkeys) {
			hotkey.check(e.getKeyCode());
		}
		if (ZUI.instance != null) {
			ZUI.instance.keyPress(e);
		}
		
	}
	
	protected static void keyRelease(SKeyEvent e) {
		pressedKeys.remove(e.getKeyCode());
		for (SKeyListener keyListener : keyListeners) {
			keyListener.keyRelease(e);
		}
		if (ZUI.instance != null) {
			ZUI.instance.keyRelease(e);
		}
	}
}