package geometry2D;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;

public abstract class Shape {
	public static boolean renderInterestPoints;
	public static boolean renderInterestLines;
	protected static double pointProxxy = 1E-8;
	
	protected static double PI = Math.PI;
	protected Texture texture;
	
	protected Color color;
	protected Color outlineColor;
	protected Shape parent;
	private double x;
	private double y;
	private double rot;
	
	protected Shape(double x, double y, double rot) {
		this.x = x;
		this.y = y;
		this.rot = rot;
	}
	
	public void setParent(Shape parent) {
		this.parent = parent;
	}
	
	public void setTexture(Texture texture) {
		this.texture = texture;
	}
	
	public Texture getTexture() {
		return texture;
	}
	
	public double getX() {
		if (parent == null) {
			return x;
		}
		return parent.getX() + x * Math.cos(parent.getRotation()) + y * Math.sin(parent.getRotation());
	}
	
	public double getY() {
		if (parent == null) {
			return y;
		}
		return parent.getY() - y * Math.cos(parent.getRotation()) + x * Math.sin(parent.getRotation());
	}
	
	public double getRotation() {
		if (parent == null) {
			return rot;
		}
		double a = parent.getRotation() + rot;
		while (a > PI) {
			a -= 2 * PI;
		}
		while (a < -PI) {
			a += 2 * PI;
		}
		return a;
	}
	
	public double getDirection(Point point) {
		return getDirection(point.getX(), point.getY());
	}
	
	public double getDirection(double x, double y) {
		return Math.atan2(y - this.y, x - this.x);
	}
	
	public void setPosition(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public void setPosition(Point point) {
		this.x = point.getX();
		this.y = point.getY();
	}
	
	public Point getPosition() {
		return new Point(x, y);
	}

	public void move(double dx, double dy) {
		x += dx;
		y += dy;
	}
	
	public void setRotation(double a) {
		rot = a;
	}
	
	public void rotate(double a) {
		rot += a;
		while (rot > PI) {
			rot -= 2 * PI;
		}
		while (rot < -PI) {
			rot += 2 * PI;
		}
	}
	
	/**
	 * Sets a color for rendering, color is not reset after render.
	 * @param color
	 */
	public void setColor(Color color) {
		this.color = color;
	}
	
	public void setOutlineColor(Color color) {
		this.outlineColor = color;
	}
	
	protected void applyColor() {
		if (color == null) {
			GL11.glColor4d(1, 1, 1, 1);
		} else {
			color.use();
		}
	}
	
	protected void applyOutlineColor() {
		if (outlineColor == null) {
			GL11.glColor4d(1, 1, 1, 1);
		} else {
			outlineColor.use();
		}
	}
	
	protected abstract Point firstPoint();
	public abstract boolean collides(Shape shape);
	protected abstract boolean collidesPoint(Point point);
	protected abstract boolean collidesLine(Line line);
	protected abstract boolean collidesCircle(Circle circle);
	
//	public abstract double getProximity(Shape s);
//	
//	public boolean proximity(Shape s, double proximity) {
//		return getProximity(s) <= proximity;
//	}
	
	public abstract void render(boolean fill);
}