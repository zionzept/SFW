package util;

import org.eclipse.swt.graphics.Point;

public class Maffs {
	public static double getAngle(Point p0, Point p1) {
		return Math.atan2(p0.y - p1.y, p1.x - p0.x);
	}
}
