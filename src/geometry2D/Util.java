package geometry2D;

public class Util {
	public static double getAngle(double x0, double y0, double x1, double y1) {
		return Math.atan2(y0 - y1, x1 - x0);
	}
	
	public static double averageX(Point... point) {
		double x = 0;
		for (Point p : point) {
			x += p.getX();
		}
		return x / point.length;
	}
	
	public static double averageY(Point... point) {
		double y = 0;
		for (Point p : point) {
			y += p.getY();
		}
		return y / point.length;
	}
	
	public static double getRotationDifference(Line l0, Line l1) {
		double dif = l1.getRot() - l0.getRot();
		if (dif > Math.PI) {
			dif -= 2 * Math.PI;
		} else if (dif < -Math.PI) {
			dif += 2 * Math.PI;
		}
		return dif;
	}
}
