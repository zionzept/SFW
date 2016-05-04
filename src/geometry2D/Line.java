package geometry2D;

import org.lwjgl.opengl.GL11;

public class Line extends Shape{
	private Point p0;
	private Point p1;
	
	public Line(Point p0, double l, double rot) {
		super(p0.getX() + l * Math.cos(rot) / 2, p0.getY() - l * Math.sin(rot) / 2, rot);
		this.p0 = p0;
		this.p1 = new Point(0, 0);
		init(l);
	}
	
	public Line(double x0, double y0, double x1, double y1) {
		super((x0 + x1) / 2, (y0 + y1) / 2, Util.getAngle(x0, y0, x1, y1));
		this.p0 = new Point(0, 0);
		this.p1 = new Point(0, 0);
		init(Math.hypot(x1 - x0, y1 - y0));
	}
	
	public Line(Point p0, Point p1) {
		super((p0.getX() + p1.getX()) / 2, (p0.getY() + p1.getY()) / 2, 
				Util.getAngle(p0.getX(), p0.getY(), p1.getX(), p1.getY()));
		this.p0 = p0;
		this.p1 = p1;
		init(Math.hypot(p1.getX() - p0.getX(), p1.getY() - p0.getY()));
	}
	
	private void init(double l) {
		this.p0.parent = this;
		this.p1.parent = this;
		this.p0.setPosition(-l / 2, 0);
		this.p1.setPosition(l / 2, 0);
		this.p0.setColor(Color.RED);
		this.p1.setColor(Color.RED);
	}
	
	/**
	 * Calculates if a normal from the line to the given point actually intersects the line
	 * @param p
	 * @return
	 */
	protected boolean normalizable(Point p) {
		double rot = getRot() + PI / 2;
		boolean a = (p.getX() - p0.getX()) * Math.sin(rot) + (p.getY() - p0.getY()) * Math.cos(rot) >= 0;
		boolean b = (p.getX() - p1.getX()) * Math.sin(rot) + (p.getY() - p1.getY()) * Math.cos(rot) <= 0;
		return a && b;
	}
	
	/**
	 * Calculates the normal distance from the line to a given point, even if the normal
	 * does not intersect the line.
	 * @param p			
	 * @return
	 */
	protected double normalDistance(Point p) {
		return Math.abs((p.getX() - getX()) * Math.sin(getRot()) + (p.getY() - getY()) * Math.cos(getRot()));
	}
	
	protected boolean leftOfLine(Point p) {
		return (p.getX() - getX()) * Math.sin(getRot()) + (p.getY() - getY()) * Math.cos(getRot()) < 0;
	}
	
	@Override
	protected Point firstPoint() {
		return p0;
	}

	@Override
	public boolean collides(Shape shape) {
		if (this == shape) {
			return false;
		}
		return shape.collidesPoint(p0) ||
				shape.collidesPoint(p1) ||
				shape.collidesLine(this);
	}
	
	@Override
	protected boolean collidesPoint(Point point) {
		return normalizable(point) && normalDistance(point) < Shape.pointProxxy;
	}
	
	@Override
	protected boolean collidesLine(Line line) {
		double dx0 = p1.getX() - p0.getX();
		double dy0 = p1.getY() - p0.getY();
		double dx1 = line.p1.getX() - line.p0.getX();
		double dy1 = line.p1.getY() - line.p0.getY();
		double s = (-dy0 * (p0.getX() - line.p0.getX()) + dx0 * (p0.getY() - line.p0.getY())) / (-dx1 * dy0 + dx0 * dy1);
		double t = ( dx1 * (p0.getY() - line.p0.getY()) - dy1 * (p0.getX() - line.p0.getX())) / (-dx1 * dy0 + dx0 * dy1);
		return s >= 0 && s <= 1 && t >= 0 && t <= 1;
	}
	
	@Override
	protected boolean collidesCircle(Circle circle) {
		return circle.collidesPoint(p0) ||
				circle.collidesPoint(p1) ||
				circle.collidesLine(this);
	}

//	@Override
//	public double getProximity(Shape s) {
//		if (this == s) {
//			return 0;
//		}
//		for (Line l : s.getInterestLines()) {
//			if (intersectsLine(l)) {
//				return 0;
//			}
//		}
//		double proximity = Double.POSITIVE_INFINITY;
//		for (Point p : s.getInterestPoints()) {
//			if (normalizable(p)) {
//				double proximityCheck = normalDistance(p);
//				if (proximityCheck < proximity) {
//					proximity = proximityCheck;
//				}
//			}
//		}
//		for (Circle c : s.getInterestCircles()) {
//			Point p = c.getCenter();
//			if (normalizable(p)) {
//				double proximityCheck = normalDistance(p) - c.getRadius();
//				if (proximityCheck < proximity) {
//					proximity = proximityCheck;
//				}
//			}
//		}
//		
//		double proximityCheck = p0.getProximity(s);
//		if (proximityCheck < proximity) {
//			proximity = proximityCheck;
//		}
//		proximityCheck = p1.getProximity(s);
//		if (proximityCheck < proximity) {
//			proximity = proximityCheck;
//		}
//		return Math.max(proximity, 0);
//	}
	
	protected double getProximity(Point point) {
		if (normalizable(point)) {
			return normalDistance(point);
		} else {
			return Math.min(p0.getProximity(point), p1.getProximity(point));
		}
	}
	
	@Override
	public void setColor(Color color) {
		setOutlineColor(color);
	}

	@Override
	public void render(boolean fill) {
		applyOutlineColor();
		GL11.glBegin(GL11.GL_LINES); {
			GL11.glVertex2d(p0.getX(), p0.getY());
			GL11.glVertex2d(p1.getX(), p1.getY());
		} GL11.glEnd();
		if (renderInterestPoints) {
			p0.render(true);
			p1.render(true);
		}
	}
	
	@Override
	public String toString() {
		return "Segment\t[x=" + getX() + "][y=" + getY() + "][a=" + getRot() + "]";
	}
	
	@Override
	public int hashCode() {
		return 1;
	}
}