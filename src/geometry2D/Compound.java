package geometry2D;

import java.util.Arrays;
import java.util.LinkedList;

public class Compound extends Shape {

	private LinkedList<Shape> shapes;
	
	public Compound(double x, double y, double rot, Shape... s) {
		super(x, y, rot);
		shapes = new LinkedList<Shape>(Arrays.asList(s));
		for (Shape ss : shapes) {
			ss.parent = this;
		}
	}
	
	public boolean addShapes(Shape... shape) {
		boolean ret = true;
		for (Shape s : shape) {
			if (!shapes.add(s)) {
				ret = false;
			}
		}
		return ret;
	}
	
	public boolean removeShapes(Shape... shape) {
		boolean ret = true;
		for (Shape s : shape) {
			if (!shapes.remove(s)) {
				ret = false;
			}
		}
		return ret;
	}
	
	@Override
	public void setColor(Color color) {
		for (Shape s : shapes) {
			s.setColor(color);
		}
	}
	
	@Override
	protected Point firstPoint() {
		if (shapes.size() > 0) {
			return shapes.get(0).firstPoint();
		} else {
			return Point.NULL;
		}
	}
	
	@Override
	public boolean collides(Shape shape) {
		if (this == shape) {
			return false;
		}
		for (Shape s : shapes) {
			if (s.collides(shape)) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	protected boolean collidesPoint(Point point) {
		for (Shape s : shapes) {
			if (s.collidesPoint(point)) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	protected boolean collidesLine(Line line) {
		for (Shape s : shapes) {
			if (s.collidesLine(line)) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	protected boolean collidesCircle(Circle circle) {
		for (Shape s : shapes) {
			if (s.collidesCircle(circle)) {
				return true;
			}
		}
		return false;
	}
	
//	@Override
//	public double getProximity(Shape s) {
//		double proximity = Double.POSITIVE_INFINITY;
//		for (Shape ss : shapes) {
//			double checkProximity = ss.getProximity(s);
//			if (checkProximity < proximity) {
//				proximity = checkProximity;
//			}
//		}
//		return proximity;
//	}
//	
//	@Override
//	public boolean proximity(Shape s, double proximity) {
//		for (Shape ss : shapes) {
//			if (ss.proximity(s, proximity)) {
//				return true;
//			}
//		}
//		return false;
//	}

	@Override
	public void render(boolean fill) {
		for (Shape ss : shapes) {
			ss.render(fill);
		}
	}
}