package aaaa_testing;

import geometry2D.Circle;
import geometry2D.Color;
import geometry2D.Rectangle;
import input.SHotkey;
import input.SKeyEvent;
import input.SKeyboard;
import input.SMouse;
import input.SMouseEvent;
import input.SMouseListener;
import input.SMouseMoveListener;
import input.SMouseWheelEvent;
import input.SMouseWheelListener;
import texture.SwapTexture;
import texture.Textures;
import ui2.SActivationListener;
import ui2.ZUI;
import ui2.components.SButton;
import ui2.components.SComponent;
import ui2.components.SImage;
import window.Handle;
import window.SWindow;

public class Test2 implements Handle{
	public static void main(String[] args) {
		new Test2();
	}
	
	public Test2() {
//		final Handle handle = this;
//		new Thread(new Runnable() {
//			@Override
//			public void run() {
//				SWindow window = SWindow.create("hello", SWindow.MODE_2D, handle);
//				init();
//				window.refreshLoop();
//			}
//		}).start();
		SWindow window = SWindow.create("hello", SWindow.MODE_FRAMED | SWindow.MODE_2D | SWindow.MODE_GLOBAL_LISTENERS, this);
		init();
		window.toggleVisible();
		window.refreshLoop();
	}
	
	private SComponent c, c1, c2;
	double speed = .1;
	double speedStep = .1;
	
	private void init() {
		c = new SButton(500, 200, new SwapTexture(
				Textures.loadTexture("res\\brick", "png"),
				Textures.loadTexture("res\\golden scale", "png"),
				Textures.loadTexture("res\\gunmetal", "png")
				));
		c.addActivationListener(new SActivationListener() {
			@Override
			public void activation() {
				speed += speedStep;
			}
		});
		
		SMouse.addMouseMoveListener(new SMouseMoveListener() {
			@Override
			public void mouseMove(SMouseEvent e) {
				if (SMouse.isPressed(SMouseEvent.LMB)) {
					c.getHitbox().move(SMouse.getDX(), SMouse.getDY());
				}
			}
		});
		
		c1 = new SImage(new Circle(250, 500, 100, 0), Textures.loadTexture("res\\Mob_slime_green", "png"));
		c2 = new SImage(new Rectangle(500, 500, 200, 200), Textures.loadTexture("res\\brick", "png"));
		ZUI.add(c);
		ZUI.add(c1);
		ZUI.add(c2);
		
		c2.addMouseListener(new SMouseListener() {
			@Override
			public void mouseDown(SMouseEvent e) {
				c2.getHitbox().setColor(new Color(Math.random(), Math.random(), Math.random(), Math.random()));
			}
			@Override
			public void mouseUp(SMouseEvent e) {
			}
		});
		
		c2.addMouseWheelListener(new SMouseWheelListener() {
			@Override
			public void mouseScroll(SMouseWheelEvent e) {
				c2.getHitbox().move(0, e.getDirection() * -10);
			}
		});
		
		SKeyboard.addHotkey(new SHotkey(SKeyEvent.O) {
			@Override
			public void addRequirements() {
				addRequirementPressedKey(SKeyEvent.LCTRL);
			}
			@Override
			public void actuation() {
				ZUI.outlineHitboxes = !ZUI.outlineHitboxes;
			}
		});
		
		SMouse.addMouseListener(new SMouseListener() {
			@Override
			public void mouseDown(SMouseEvent e) {
				if (e.getButton() == SMouseEvent.LMB) {
					for (int i : SKeyboard.pressedKeys()) {
						System.out.println("Pressed key: " + i);
					}
				}
			}
			@Override
			public void mouseUp(SMouseEvent e) {
			}
		});
	}
double time = 0;
	@Override
	public void update(double dt) {
		System.out.println(dt);
		time+=dt;
		System.out.println(time);
		if (c != null) {
			c.getHitbox().rotate(dt * speed * Math.PI * 2);
			c1.getHitbox().rotate(dt * speed * Math.PI * 2);
			c2.getHitbox().rotate(dt * speed * Math.PI * 2);
		}
	}
}