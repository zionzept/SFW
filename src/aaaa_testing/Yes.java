package aaaa_testing;

import input.MouseAdapter;
import input.MouseListener;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import ui2.ZUI;
import ui2.components.SButton;
import window.Handle;
import window.Window;

public class Yes implements Handle {
	public static void main(String[] args) {
		new Yes();
	}

	private static final int WIDTH = 1800;
	private static final int HEIGHT = WIDTH / 16 * 9;

	public Yes() {
		SButton b = new SButton((int) (Math.random() * WIDTH), (int) (Math.random() * HEIGHT));
		ZUI.add(b);
		b.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(int button) {
				if (button == ZUI.LMB) {
					 GL11.glClearColor(.5f, 0, 0, 1);				
				}
			}
		});

		b = new SButton((int) (Math.random() * WIDTH), (int) (Math.random() * HEIGHT));
		ZUI.add(b);
		b.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(int button) {
				if (button == ZUI.LMB) {
					if (button == ZUI.LMB) {
						 GL11.glClearColor(0, .5f, 0, 1);				
					}
				}
			}
		});
		Window.create(WIDTH, HEIGHT, Window.MODE_2D, this);
	}

	@Override
	public void update(double dt) {
		// System.out.println(dt);
	}
}