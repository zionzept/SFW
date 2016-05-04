package aaaa_testing;

import geometry2D.Circle;
import geometry2D.Rectangle;
import input.MouseAdapter;
import input.SMouseEvent;
import input.SMouseListener;
import texture.SwapTexture;
import texture.Textures;
import ui2.ZUI;
import ui2.components.SButton;
import ui2.components.SComponent;
import ui2.components.SImage;
import window.Handle;
import window.Window;

public class Test implements Handle{
	public static void main(String[] args) {
		new Test();
	}
	
	public Test() {
		ZUI.create();
		Window w = null;
		w = new Window(1000, 600, Window.MODE_2D, this);
		init();
		w.loop();
		w.close();
	}
	
	private void init() {
		final SComponent c = new SButton(500, 500, new SwapTexture(
				Textures.loadTexture("res\\brick", "png"),
				Textures.loadTexture("res\\golden scale", "png"),
				Textures.loadTexture("res\\Mob_Slime_Green", "png")
				));
		c.addMouseListener(new SMouseListener() {
//			@Override
//			public void mousePressed(int button) {
//				c.getHitbox().rotate(.1);
//				c.getHitbox().move(1, 0);
//			}

			@Override
			public void mouseDown(SMouseEvent e) {
				System.out.println(e);
				c.getHitbox().rotate(.1);
				c.getHitbox().move(1, 0);
			}

			@Override
			public void mouseUp(SMouseEvent e) {
				System.out.println(e);
			}
		});
		
		final SComponent c1 = new SImage(new Circle(300, 300, 100, 0), Textures.loadTexture("res\\brick", "png"));
		final SComponent c2 = new SImage(new Rectangle(500, 300, 100, 100), Textures.loadTexture("res\\brick", "png"));
		
		ZUI.add(c);
		ZUI.add(c1);
		ZUI.add(c2);
	}

	@Override
	public void update(double dt) {
	}
}