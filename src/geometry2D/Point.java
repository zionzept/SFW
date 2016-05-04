package geometry2D;

import static org.lwjgl.opengl.GL11.GL_POINTS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2d;

public class Point extends Shape{
	
	protected static Point NULL = new PointNull();
	
	
	public Point(double x, double y, double rot) {
		super(x, y, rot);
	}
	
	public Point(double x, double y) {
		super(x, y, 0);
	}
	
//	public double distance(Point p) {
//		return Math.hypot(p.getX() - getX(), p.getY() - getY());
//	}
	
	@Override
	protected Point firstPoint() {
		return this;
	}

	@Override 
	public boolean collides(Shape shape) {
		if (this == shape) {
			return false;
		}
		return shape.collidesPoint(this);
	}
	
	@Override
	protected boolean collidesPoint(Point point) {
		return Math.abs(point.getX() - getX()) + Math.abs(point.getY() - getY()) < Shape.pointProxxy;
	}
	
	@Override
	protected boolean collidesLine(Line line) {
		return line.normalizable(this) && line.normalDistance(this) < Shape.pointProxxy;
	}
	
	@Override
	protected boolean collidesCircle(Circle circle) {
		return Math.hypot(circle.getX() - getX(), circle.getY() - getY()) < circle.getRadius();
	}
	
	
//	@Override
//	public double getProximity(Shape s) {
//		if (s == this) {
//			return 0;
//		}
//		double proximity = Double.POSITIVE_INFINITY;
//		for (Point p : s.getInterestPoints()) {
//			double proximityCheck = distance(p);
//			if (proximityCheck < proximity) {
//				proximity = proximityCheck;
//			}
//		}
//		for (Line l : s.getInterestLines()) {
//			double proximityCheck = l.getProximity(this);
//			if (proximityCheck < proximity) {
//				proximity = proximityCheck;
//			}
//		}
//		for (Circle c : s.getInterestCircles()) {
//			Point p = c.getCenter();
//			double proximityCheck = distance(p) - c.getRadius();
//			if (proximityCheck < proximity) {
//				proximity = proximityCheck;
//			}
//		}
//		return Math.max(proximity, 0);
//	}
	
	protected double getProximity(Point point) {
		return Math.hypot(point.getX() - getX(), point.getY() - getY());
	}
	
	@Override
	public void setColor(Color color) {
		setOutlineColor(color);
	}
	
	@Override
	public void render(boolean fill) {
		applyOutlineColor();
		glBegin(GL_POINTS); {
			glVertex2d(getX(), getY());
		} glEnd();
	}
	
	@Override
	public String toString() {
		return "Point\t[x=" + getX() + "][y=" + getY() + "]";
	}
}