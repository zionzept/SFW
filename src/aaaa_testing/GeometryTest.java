package aaaa_testing;

import geometry2D.*;
import input.SHotkey;
import input.SKeyEvent;
import input.SKeyboard;
import input.SMouse;

import java.util.LinkedList;

import org.lwjgl.opengl.GL11;

import window.Handle;
import window.SWindow;

public class GeometryTest implements Handle {
	private SWindow window;
	private boolean fill = true;
	private static final int WIDTH = 1100;
	private static final int HEIGHT = 640;
	
	
	public static void main(String[] args) {
		new GeometryTest();
	}
	
	private GeometryTest() {
		window = SWindow.create("Geometry Test",  SWindow.MODE_FRAMED | SWindow.MODE_GLOBAL_LISTENERS, this);
		
		init();
		window.setSize(WIDTH, HEIGHT);
		window.toggleVisible();
		window.refreshLoop();
	}
	
	private Shape q;
	LinkedList<Shape> shapes;
	
	private void init() {
//		SKeyboard.addHotkey(new SHotkey(SKeyEvent.X) {
//			@Override
//			public void addRequirements() {
//				// TODO Auto-generated method stub
//				
//			}
//
//			@Override
//			public void actuation() {
//				fill = !fill;
//			}
//		});
		
		
		shapes = new LinkedList<Shape>();
		
		//Compound q = new Compound(100, 100, 0, new Rectangle(20, 0, 100, 100));
		q = new Polygon(0, new Point(-50, 50),
				new Point(50, 50),
				new Point(-30, -40),
				new Point(0, 70),
				new Point(30, -40));
		
		
		shapes.add(q);
		shapes.add(new Rectangle(500, 200, 50, 70));
		shapes.add(new Line(500, 100, 400, 100));
		shapes.add(new Line(600, 0, 600, 200));
		shapes.add(new Line(800, 195, 800, 395));
		shapes.add(new Point(200, 500));
		shapes.add(new Compound(400, 200, 0, new Circle(0, 0, 50, 0), new Rectangle(-67, 9, 100, 100)));
		shapes.add(new Circle(700, 300, 11, 0));
		
		Shape.renderInterestPoints = false;
		Shape.renderInterestLines = false;
	}
	
	private void input() {
		fill = !SKeyboard.isPressed(SKeyEvent.X);
		q.setPosition(SMouse.getX(), SMouse.getY());
		System.out.println(SMouse.getX() + ", " + SMouse.getY());
	}
	
	private void gameLoop() {
		for (Shape s : shapes) {
			s.setColor(new Color(1, 1, 1));
			for (Shape s1 : shapes) {
				if (s.collides(s1)) {
					s.setColor(new Color(1, 0, 0, .4));
				}
			}
			s.render(fill);
		}
	}

	@Override
	public void update(double dt) {
		input();
		gameLoop();
	}
}