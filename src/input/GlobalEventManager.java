package input;

import java.io.Closeable;
import java.io.PrintStream;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseListener;
import org.jnativehook.mouse.NativeMouseMotionListener;
import org.jnativehook.mouse.NativeMouseWheelEvent;
import org.jnativehook.mouse.NativeMouseWheelListener;

public class GlobalEventManager implements NativeKeyListener, NativeMouseListener, NativeMouseMotionListener, NativeMouseWheelListener, Closeable {
	private int xOffset;
	private int yOffset;
	
	public GlobalEventManager() {
		LogManager.getLogManager().reset();
		Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
		logger.setLevel(Level.OFF);
		PrintStream origOut = System.out;
		System.setOut(null);
		if (!GlobalScreen.isNativeHookRegistered()) {
			try {
				GlobalScreen.registerNativeHook();
			} catch (NativeHookException e) {
				e.printStackTrace();
			}
		}
		System.setOut(origOut);
		GlobalScreen.addNativeKeyListener(this);
		GlobalScreen.addNativeMouseListener(this);
		GlobalScreen.addNativeMouseMotionListener(this);
		GlobalScreen.addNativeMouseWheelListener(this);
	}
	
	public void setXOffset(int offset) {
		xOffset = offset;
	}
	
	public void setYOffset(int offset) {
		yOffset = offset;
	}
	
	@Override
	public void close() {
		if (GlobalScreen.isNativeHookRegistered()) {
			GlobalScreen.removeNativeKeyListener(this);
			GlobalScreen.removeNativeMouseListener(this);
			GlobalScreen.removeNativeMouseMotionListener(this);
			GlobalScreen.removeNativeMouseWheelListener(this);
			try {
				GlobalScreen.unregisterNativeHook();
			} catch (NativeHookException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void nativeKeyPressed(NativeKeyEvent e) {
		int key = convertKey(e.getRawCode(), e.getKeyLocation());
		SKeyEvent se = new SKeyEvent(getChar(key), key);
		SKeyboard.keyPress(se);
	}

	@Override
	public void nativeKeyReleased(NativeKeyEvent e) {
		int key = convertKey(e.getRawCode(), e.getKeyLocation());
		SKeyEvent se = new SKeyEvent(getChar(key), key);
		SKeyboard.keyRelease(se);
	}

	@Override
	public void nativeMousePressed(NativeMouseEvent e) {
		SMouseEvent se = new SMouseEvent(e.getX() + xOffset, e.getY() + yOffset, convertButton(e.getButton()), e.getClickCount());
		SMouse.mouseDown(se);
	}

	@Override
	public void nativeMouseReleased(NativeMouseEvent e) {
		SMouseEvent se = new SMouseEvent(e.getX() + xOffset, e.getY() + yOffset, convertButton(e.getButton()), e.getClickCount());
		SMouse.mouseUp(se);
	}

	@Override
	public void nativeMouseWheelMoved(NativeMouseWheelEvent e) {
		SMouseWheelEvent se = new SMouseWheelEvent(e.getX() + xOffset, e.getY() + yOffset, -e.getWheelRotation(), e.getScrollAmount());
		SMouse.mouseScroll(se);
	}

	@Override
	public void nativeMouseMoved(NativeMouseEvent e) {
		SMouseEvent se = new SMouseEvent(e.getX() + xOffset, e.getY() + yOffset,  0, 0);
		SMouse.mouseMove(se);
	}
	
	@Override
	public void nativeMouseDragged(NativeMouseEvent e) {
		nativeMouseMoved(e);
	}
	
	private int convertButton(int button) {
		switch (button) {
		default:
			return button;
		case 1:
			return SMouseEvent.LMB;
		case 2:
			return SMouseEvent.RMB;
		case 3:
			return SMouseEvent.MID;
		case 4:
			return SMouseEvent.MB4;
		case 5:
			return SMouseEvent.MB5;
		}
	}
	
	private int convertKey(int key, int location) {
		if (key > 95 && key < 105) {
			return key + 80;
		}
		if (key > 64 && key < 91) {
			return key + 32;
		}
		switch (key) {
		default:
			return key;
		case 160:
			return SKeyEvent.LSHIFT;
		case 161:
			return SKeyEvent.RSHIFT;
		case 162:
			return SKeyEvent.LCTRL;
		case 163:
			return SKeyEvent.RCTRL;
		case 164:
			return SKeyEvent.LALT;
		case 165:
			return SKeyEvent.RALT;
		case 191:
			return SKeyEvent.APOSTROPHE;
		case 187:
			return SKeyEvent.PLUS;
		case 188:
			return SKeyEvent.COMMA;
		case 189:
			return SKeyEvent.DASH;
		case 190:
			return SKeyEvent.PERIOD;
		case 226:
			return SKeyEvent.LESSTHAN;
		case 20:
			return SKeyEvent.CAPSLK;
		case 144:
			return SKeyEvent.NUMLK;
		case 145:
			return SKeyEvent.SCRLK;
		case 46:
			return SKeyEvent.DELETE;
		case 45:
			return SKeyEvent.INS;
		case 35:
			return SKeyEvent.END;
		case 36:
			return SKeyEvent.HOME;
		case 34:
			return SKeyEvent.PGDOWN;
		case 33:
			return SKeyEvent.PGUP;
		case 19:
			return SKeyEvent.PAUSE;
		case 39:
			return SKeyEvent.RIGHT;
		case 38:
			return SKeyEvent.UP;
		case 37:
			return SKeyEvent.LEFT;
		case 40:
			return SKeyEvent.DOWN;
		case 220:
			return SKeyEvent.PARAGRAPH;
		case 111:
			return SKeyEvent.NPDIVIDE;
		case 106:
			return SKeyEvent.NPMULTIPLY;
		case 109:
			return SKeyEvent.NPMINUS;
		case 107:
			return SKeyEvent.NPPLUS;
		case 110:
			return SKeyEvent.NPCOMMA;
		case 112:
			return SKeyEvent.F1;
		case 113:
			return SKeyEvent.F2;
		case 114:
			return SKeyEvent.F3;
		case 115:
			return SKeyEvent.F4;
		case 116:
			return SKeyEvent.F5;
		case 117:
			return SKeyEvent.F6;
		case 118:
			return SKeyEvent.F7;
		case 119:
			return SKeyEvent.F8;
		case 120:
			return SKeyEvent.F9;
		case 121:
			return SKeyEvent.F10;
		case 122:
			return SKeyEvent.F11;
		case 123:
			return SKeyEvent.F12;
		case 221:
			return SKeyEvent.Å;
		case 222:
			return SKeyEvent.Ä;
		case 192:
			return SKeyEvent.Ö;
		}
	}
	
	private char getChar(int key) {
		return (char)key;
	}
	
	@Override
	public void nativeKeyTyped(NativeKeyEvent e) {}
	@Override
	public void nativeMouseClicked(NativeMouseEvent e) {}
}