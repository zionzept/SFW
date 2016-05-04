package geometry2D;

public class PointNull extends Point {

	protected PointNull() {
		super(0, 0);
	}
	
	@Override 
	public boolean collides(Shape shape) {
		return false;
	}
	
	@Override
	protected boolean collidesPoint(Point point) {
		return false;
	}
	
	@Override
	protected boolean collidesLine(Line line) {
		return false;
	}
	
	@Override
	protected boolean collidesCircle(Circle circle) {
		return false;
	}
}