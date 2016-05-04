package window;

import java.io.Closeable;

import input.Input;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import ui2.ZUI;

public class Window implements Closeable {
	public static final int MODE_2D = 2;
	public static final int MODE_3D = 3;
	
	public static final int MODE_UNDECORATED = 4;
	
	public static final int MODE_GLOBAL_LISTENERS = 8;
	
	private Handle handle;
	private boolean created;
	private boolean running;
	
	private static Window instance;
	
	public Window(int width, int height, int mode, Handle handle) {
		if (!created) {
			this.handle = handle;
			setupDisplay(width, height, mode);
			setupOpenGL(width, height, mode);
			created = true;
		}
	}

	public static Window create(int width, int height, int mode, Handle handle) {
		if (instance == null) {
			instance = new Window(width, height, mode, handle);
		}
		return instance;
	}
	
	private void setupDisplay(int width, int height, int mode) {
		if (!Display.isCreated()) {
			try {
				if ((mode & MODE_UNDECORATED) == MODE_UNDECORATED) {
					System.setProperty("org.lwjgl.opengl.Window.undecorated", "true");
				}
				Display.setDisplayMode(new DisplayMode(width, height));
				Display.setVSyncEnabled(true);
				Display.setResizable(true);
				Display.create();
			} catch (LWJGLException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void setupOpenGL(int width, int height, int mode) {
		switch (mode & 7) {
		case MODE_2D:
			setupOpenGL2D(width, height);
			break;
		}
	}
	
	private void setupOpenGL2D(int width, int height) {
		GL11.glLoadIdentity();
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		GL11.glViewport(0, 0, width, height);
		GL11.glOrtho(0, Display.getWidth(), 0, Display.getHeight(), 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
	}
	
	public void loop() {
		running = true;
		preTime = System.nanoTime();
		while (running) {
			updateDT();
			Input.update();
			//ZUI.mouse(Mouse.getX(), Mouse.getY());
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
			handle.update(dt);
			ZUI.render();
			Display.update();
			if (Display.isCloseRequested()) {
				running = false;
			}
		}
		close();
	}
	
	public void close() {
		running = false;
		Mouse.destroy();
		Display.destroy();
		System.exit(0);
	}
	
	private static double dt;
	private static long preTime;
	private static void updateDT() {
		long time = System.nanoTime();
		dt = (time - preTime) / 1E9;
		preTime = time;
	}
}
