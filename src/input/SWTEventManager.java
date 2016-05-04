package input;

import java.io.Closeable;
import java.io.IOException;

import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.events.MouseWheelListener;
import org.eclipse.swt.widgets.Canvas;

public class SWTEventManager implements Closeable{
	public SWTEventManager(Canvas canvas) {
		this.canvas = canvas;
		setupSWTInput();
	}
	
	private MouseListener mouseListener;
	private MouseMoveListener mouseMoveListener;
	private MouseWheelListener mouseWheelListener;
	private MouseTrackListener mouseTrackListener;
	private KeyListener keyListener;
	private Canvas canvas;
	

	@Override
	public void close() throws IOException {
		if (!canvas.isDisposed()) {
			canvas.removeMouseListener(mouseListener);
			canvas.removeMouseMoveListener(mouseMoveListener);
			canvas.removeMouseWheelListener(mouseWheelListener);
			canvas.removeMouseTrackListener(mouseTrackListener);
			canvas.removeKeyListener(keyListener);
		}
	}
	
	private void setupSWTInput() {
		mouseListener = new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				SMouseEvent se = new SMouseEvent(e.x, e.y, convertButton(e.button), e.count);
				SMouse.mouseDown(se);
			}
			@Override
			public void mouseUp(MouseEvent e) {
				SMouseEvent se = new SMouseEvent(e.x, e.y, convertButton(e.button), e.count);
				SMouse.mouseUp(se);
			}
		};
		mouseMoveListener = new MouseMoveListener() {
			@Override
			public void mouseMove(MouseEvent e) {
				SMouseEvent se = new SMouseEvent(e.x, e.y, 0, 0);
				SMouse.mouseMove(se);
			}
		};
		mouseWheelListener = new MouseWheelListener() {
			@Override
			public void mouseScrolled(MouseEvent e) {
				int magnitude = Math.abs(e.count);
				int direction = e.count / magnitude;
				SMouseWheelEvent se = new SMouseWheelEvent(e.x, e.y, direction, magnitude);
				SMouse.mouseScroll(se);
			}
		};
		keyListener = new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				SKeyEvent se = new SKeyEvent(e.character, convertKey(e.keyCode, e.keyLocation));
				SKeyboard.keyPress(se);
			}
			@Override
			public void keyReleased(KeyEvent e) {
				SKeyEvent se = new SKeyEvent(e.character, convertKey(e.keyCode, e.keyLocation));
				SKeyboard.keyRelease(se);
			}
		};
		mouseTrackListener = new MouseTrackListener() {
			@Override
			public void mouseEnter(MouseEvent e) {
				SMouseEvent se = new SMouseEvent(e.x, e.y, 0, 0);
				SMouse.mouseEnter(se);
			}

			@Override
			public void mouseExit(MouseEvent e) {
				SMouseEvent se = new SMouseEvent(e.x, e.y, 0, 0);
				SMouse.mouseExit(se);
			}

			@Override
			public void mouseHover(MouseEvent e) {
			}
		};
		canvas.addMouseListener(mouseListener);
		canvas.addMouseMoveListener(mouseMoveListener);
		canvas.addMouseWheelListener(mouseWheelListener);
		canvas.addMouseTrackListener(mouseTrackListener);
		canvas.addKeyListener(keyListener);
	}

	private int convertButton(int button) {
		switch (button) {
		default:
			return button;
		case 1:
			return SMouseEvent.LMB;
		case 2:
			return SMouseEvent.MID;
		case 3:
			return SMouseEvent.RMB;
		case 4:
			return SMouseEvent.MB4;
		case 5:
			return SMouseEvent.MB5;
		}
	}
	
	private int convertKey(int key, int location) {
		switch (key) {
		default:
			return key;
		case 131072:
			if (location == 16384) {
				return SKeyEvent.LSHIFT;
			}
			if (location == 131072) {
				return SKeyEvent.RSHIFT;
			}
			return key;
		case 262144:
			if (location == 16384) {
				return SKeyEvent.LCTRL;
			}
			if (location == 131072) {
				return SKeyEvent.RCTRL;
			}
			return key;
		case 65536:
			if (location == 16384) {
				return SKeyEvent.LALT;
			}
			if (location == 131072) {
				return SKeyEvent.RALT;
			}
			return key;
		case 16777298:
			return SKeyEvent.CAPSLK;
		case 16777299:
			return SKeyEvent.NUMLK;
		case 16777300:
			return SKeyEvent.SCRLK;
		case 16777225:
			if (location == 0) {
				return SKeyEvent.INS;
			}
			if (location == 2) {
				return SKeyEvent.NPINS;
			}
			return key;
		case 16777224:
			if (location == 0) {
				return SKeyEvent.END;
			}
			if (location == 2) {
				return SKeyEvent.NPEND;
			}
			return key;
		case 16777223:
			if (location == 0) {
				return SKeyEvent.HOME;
			}
			if (location == 2) {
				return SKeyEvent.NPHOME;
			}
			return key;
		case 16777222:
			if (location == 0) {
				return SKeyEvent.PGDOWN;
			}
			if (location == 2) {
				return SKeyEvent.NPPGDOWN;
			}
			return key;
		case 16777221:
			if (location == 0) {
				return SKeyEvent.PGUP;
			}
			if (location == 2) {
				return SKeyEvent.NPPGUP;
			}
			return key;
		case 16777301:
			return SKeyEvent.PAUSE;
		case 16777220:
			if (location == 0) {
				return SKeyEvent.RIGHT;
			}
			if (location == 2) {
				return SKeyEvent.NPRIGHT;
			}
			return key;
		case 16777217:
			if (location == 0) {
				return SKeyEvent.UP;
			}
			if (location == 2) {
				return SKeyEvent.NPUP;
			}
			return key;
		case 16777219:
			if (location == 0) {
				return SKeyEvent.LEFT;
			}
			if (location == 2) {
				return SKeyEvent.NPLEFT;
			}
			return key;
		case 16777218:
			if (location == 0) {
				return SKeyEvent.DOWN;
			}
			if (location == 2) {
				return SKeyEvent.NPDOWN;
			}
			return key;
		case 16777263:
			return SKeyEvent.NPDIVIDE;
		case 16777258:
			return SKeyEvent.NPMULTIPLY;
		case 16777261:
			return SKeyEvent.NPMINUS;
		case 16777259:
			return SKeyEvent.NPPLUS;
		case 16777296:
			return SKeyEvent.NPENTER;
		case 16777264:
			return SKeyEvent.NP0;
		case 16777265:
			return SKeyEvent.NP1;
		case 16777266:
			return SKeyEvent.NP2;
		case 16777267:
			return SKeyEvent.NP3;
		case 16777268:
			return SKeyEvent.NP4;
		case 16777269:
			return SKeyEvent.NP5;
		case 16777270:
			return SKeyEvent.NP6;
		case 16777271:
			return SKeyEvent.NP7;
		case 16777272:
			return SKeyEvent.NP8;
		case 16777273:
			return SKeyEvent.NP9;
		case 16777262:
			return SKeyEvent.NPCOMMA;
		case 16777226:
			return SKeyEvent.F1;
		case 16777227:
			return SKeyEvent.F2;
		case 16777228:
			return SKeyEvent.F3;
		case 16777229:
			return SKeyEvent.F4;
		case 16777230:
			return SKeyEvent.F5;
		case 16777231:
			return SKeyEvent.F6;
		case 16777232:
			return SKeyEvent.F7;
		case 16777233:
			return SKeyEvent.F8;
		case 16777234:
			return SKeyEvent.F9;
		case 16777235:
			return SKeyEvent.F10;
		case 16777236:
			return SKeyEvent.F11;
		case 16777237:
			return SKeyEvent.F12;
		}
	}
}