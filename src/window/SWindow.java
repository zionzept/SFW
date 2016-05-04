package window;

import input.GlobalEventManager;
import input.SMouse;
import input.SWTEventManager;

import java.io.Closeable;
import java.io.IOException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.opengl.GLCanvas;
import org.eclipse.swt.opengl.GLData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;

import sound.Sound;
import ui2.ZUI;

public class SWindow {
	public static final int MODE_2D = 2;
	public static final int MODE_3D = 3;
	
	public static final int MODE_GLOBAL_LISTENERS = 4;
	
	public static final int MODE_PLAIN = 8;
	public static final int MODE_FRAMED = 0x4f0;
	
	public static final int MODE_ON_TOP = 1<<14;
	
	public static SWindow instance;
	
	public int compositeX;
	public int compositeY;
	public int compositeW;
	public int compositeH;
	public int x;
	public int y;
	public int w;
	public int h;

	private int mode;
	private Handle handle;

	private Closeable eventManager;
	private static Runnable nextFrameRoutine;
	private static Color background;
	
	private Display display;
	private Shell shell;
	private Composite composite;
	private GLCanvas canvas;
	
	private static boolean running;
	private TaskAdder taskAdder;
	
	public void toggleVisible() {
		taskAdder.add(new VisibilityToggler(this));
	}
	
	public void setLocation(int x, int y) {
		taskAdder.add(new ShellAdjuster(this, x, y));
	}
	
	public void setSize(int w, int h) {
		taskAdder.add(new ShellAdjuster(w, this, h));
	}
	
	public void setBounds(int x, int y, int w, int h) {
		taskAdder.add(new ShellAdjuster(this, x, y, w, h));
	}
	
	public Shell getShell() {
		return shell;
	}
	
	public void addTask(Task task) {
		taskAdder.add(task);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static SWindow create(String title, int mode, Handle handle) {
		return SWindow.create(title, new float[] {0f, 0f, 0f}, mode, handle);
	}
	
	public static SWindow create(String title, float[] colorData, int mode, Handle handle) {
		running = true;
		return new SWindow(title, colorData, mode, handle);
	}

	private SWindow(String name, float[] colorData, int mode, final Handle handle) {
		instance = this;
		this.mode = mode;
		this.handle = handle;
		taskAdder = new TaskAdder();
		
		display = Display.getDefault();
		background = new Color(display, (int) (colorData[0] * 255), (int) (colorData[1] * 255),
				(int) (colorData[2] * 255));
		shell = createShell(this, name, colorData.length > 3 ? colorData[3] : 1f);
		composite = createComposite(shell, background);
		
		
		canvas = createCanvas(composite);
		nextFrameRoutine = createNextFrameRunnable();
		setupResizeListener();
		setupInput();
		setupOpenGL(colorData);
		preTime = System.nanoTime();
		display.asyncExec(nextFrameRoutine);
	}

	private Shell createShell(final SWindow window, String title, float alpha) {
		Shell shell;
		int shellMode = mode & 0b11111111111111111111111111111000;
		
		shell = new Shell(display, shellMode | SWT.BORDER);
		shell.setLayout(new FillLayout());
		shell.setText(title);
		shell.setSize(1000, 680);
		if (alpha < 1) {
			int intAlpha = Math.max(0, Math.min(255, (int) (alpha * 255)));
			shell.setAlpha(intAlpha);
		}
		
		shell.setBackground(background);
		shell.addControlListener(new ControlAdapter() {
			@Override
			public void controlMoved(ControlEvent e) {
				Point shellLocation = window.shell.getLocation();
				Point compositeLocation = composite.toDisplay(0, 0);
				window.x = shellLocation.x;
				window.y = shellLocation.y;
				window.compositeX = compositeLocation.x;
				window.compositeY = compositeLocation.y;
				if (eventManager instanceof GlobalEventManager) {
					GlobalEventManager gem = (GlobalEventManager) eventManager;
					gem.setXOffset(-compositeX);
					gem.setYOffset(-compositeY);
				}
			}
			@Override
			public void controlResized(ControlEvent e) {
				Point shellSize = window.shell.getSize();
				Point compositeSize = window.composite.getSize();
				window.w = shellSize.x;
				window.h = shellSize.y;
				window.compositeW = compositeSize.x;
				window.compositeH = compositeSize.y;
	// 			System.out.println(window.w + " " + window.h + " " + window.compositeW + " " + window.compositeH);
				//TODO: ^ some coord mismatch when using PLAIN?
			}
		});
		
		return shell;
	}
	
	private Composite createComposite(Shell shell, Color color) {
		Composite composite = new Composite(shell, SWT.NONE);
		composite.setLayout(new FillLayout());
		composite.setBackground(color);
		return composite;
	}
	
	private GLCanvas createCanvas(Composite composite) {
		GLData data = new GLData();
//		data.alphaSize = 8;
//		data.depthSize = 8;
//		data.stencilSize = 8;
//		data.samples = 8;
//		data.sampleBuffers = 8;
		data.doubleBuffer = true;
		GLCanvas canvas = new GLCanvas(composite, SWT.NO_BACKGROUND, data);
		canvas.setCurrent();
		try {
			GLContext.useContext(canvas);
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		return canvas;
	}
	
	private Runnable createNextFrameRunnable() {
		Runnable newFrame = new Runnable() {
			@Override
			public void run() {
				if (!canvas.isDisposed()) {
					taskAdder.performTask();
					canvas.setCurrent();
					try {
						GLContext.useContext(canvas); //only needed for multiple windows on one thread?
					} catch (LWJGLException e) {
						e.printStackTrace();
					}
					GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
					GL11.glLoadIdentity();
					// fpsCounter.notifyNewFrame();
					updateDT();
					//Input.update();
					//ZUI.mouse(Mouse.getX(), Mouse.getY());
					GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
					handle.update(dt);
					ZUI.render();
					canvas.swapBuffers();
					canvas.update();
					//Thread.dumpStack();wwwwwwwwwwwwww
					//System.out.println(x + " " + y + " " + w + " " + h + " " + shell.isVisible());
					display.asyncExec(this);
				}
			}
		};
		return newFrame;
	}
	
	private void setupResizeListener() {
		canvas.addListener(SWT.Resize, new Listener() {
			public void handleEvent(Event event) {
				Rectangle bounds = canvas.getBounds();
				GL11.glMatrixMode(GL11.GL_PROJECTION);
				GL11.glLoadIdentity();
				GL11.glViewport(0, 0, bounds.width, bounds.height);
				GL11.glOrtho(0, bounds.width, bounds.height, 0, 1, -1);
				// GL11.glOrtho(0, bounds.width, bounds.height, 0, 1000000,
				// -1000000);
				GL11.glMatrixMode(GL11.GL_MODELVIEW);

				nextFrameRoutine.run();
			}
		});
	}
	
	private void setupInput() {
		ZUI.create();
		if (isMode(MODE_GLOBAL_LISTENERS)) {
			eventManager = new GlobalEventManager();
		} else {
			eventManager = new SWTEventManager(canvas);
		}
	}
	
	private void setupOpenGL(float[] colorData) {
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_MULT); //what?
		GL11.glHint(GL11.GL_PERSPECTIVE_CORRECTION_HINT, GL11.GL_NICEST);
		
		GL11.glEnable(GL11.GL_POINT_SMOOTH);
		GL11.glHint(GL11.GL_POINT_SMOOTH, GL11.GL_NICEST);
		GL11.glPointSize(1f);
		
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glHint(GL11.GL_LINE_SMOOTH, GL11.GL_NICEST);
		GL11.glLineWidth(1f);
		
//		GL11.glEnable(GL11.GL_POLYGON_SMOOTH);
//		GL11.glHint(GL11.GL_POLYGON_SMOOTH, GL11.GL_NICEST);
		
		
		
		GL11.glClearColor(colorData[0], colorData[1], colorData[2], colorData.length > 3 ? colorData[3] : 1f);
	}
	
	public Point getLocation() {
		return new Point(x, y);
	}
	
	public Point getSize() {
		return new Point(w, h);
	}
	
	public boolean isMode(int mode) {
		return (this.mode & mode) == mode;
	}
	
	public void refreshLoop() {
		while (!shell.isDisposed() && running) {
			if (!display.readAndDispatch() || true) {
				display.sleep();
			}
		}
		cleanup();
	}
	
	private static void cleanup() {
		Sound.destroy();
		if (instance.eventManager != null) {
			try {
				instance.eventManager.close();
			} catch (IOException e) {
				e.printStackTrace();
			}			
		}
		instance.display.dispose();
	}
	
	public static void destroy() {
		running = false;
	}
	
	private static double dt;
	private static long preTime;
	private static void updateDT() {
		long time = System.nanoTime();
		dt = (time - preTime) / 1E9;
		preTime = time;
	}
}