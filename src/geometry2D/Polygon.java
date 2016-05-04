package geometry2D;

import org.lwjgl.opengl.GL11;

public class Polygon extends Shape {
	
	protected Line[] lines;
	private boolean clockwise;
	
	private Line dbgLINE;
	private Line dbgLINE2;
	
	public Polygon(double rot, Point... point) {
		super(Util.averageX(point), Util.averageY(point), rot);
		// Create lines
		lines = new Line[point.length];
		for (int i = 0; i < lines.length; i++) {
			lines[i] = new Line(point[i], point[(i + 1) % point.length]);
			lines[i].parent = this;
			lines[i].move(-getX(), -getY());
			lines[i].setColor(Color.BLUE);
		}
		// Set clockwise t/f
		double a = 0;
		for (int i = 0; i < lines.length; i++) {
			a += Util.getRotationDifference(lines[i], lines[(i + 1) % lines.length]);
		}
		clockwise = a < 0;
		System.out.println(clockwise ? "clockwise" : "counter-clockwise");
	}

	@Override
	protected Point firstPoint() {
		if (lines.length > 0) {
			return lines[0].firstPoint();
		}
		return Point.NULL;
	}

	@Override
	public boolean collides(Shape shape) {
		if (shape == this) {
			return false;
		}
		for (int i = 0; i < lines.length; i++) {
			if (shape.collidesLine(lines[i]) || shape.collidesPoint(lines[i].firstPoint())) {
				return true;
			}
		}
		return collidesPoint(shape.firstPoint());
	}

	@Override
	protected boolean collidesPoint(Point point) {
		int id = -1;
		int id2 = -1;
		double distance = Double.POSITIVE_INFINITY;
		for (int i = 0; i < lines.length; i++) {
			double checkDistance = lines[i].getProximity(point);
			if (Math.abs(checkDistance - distance) < Shape.pointProxxy && id2 == -1) {
				id2 = i;
			} else if (checkDistance < distance) {
				distance = checkDistance;
				id = i;
				id2 = -1;
			}
		}
		if (id >= 0) {
			dbgLINE = lines[id];
			if (lines[id].leftOfLine(point) != clockwise) {
				if (id2 >= 0) {
					dbgLINE2 = lines[id2];
					return lines[id2].leftOfLine(point) != clockwise;
				} 
				dbgLINE2 = null;
				return true;
			}
		}
		dbgLINE = null;
		dbgLINE2 = null;
		return false;
	}

	@Override
	protected boolean collidesLine(Line line) {
		for (int i = 0; i < lines.length; i++) {
			if (lines[i].collidesLine(line)) {
				return true;
			}
		}
		return false;
	}

	@Override
	protected boolean collidesCircle(Circle circle) {
		for (int i = 0; i < lines.length; i++) {
			if (circle.collidesLine(lines[i]) || circle.collidesPoint(lines[i].firstPoint())) {
				return true;
			}
		}
		return collidesPoint(circle.firstPoint());
	}

	@Override
	public void render(boolean fill) {
		if (fill) {
			applyColor();
			if (texture == null) {
				GL11.glDisable(GL11.GL_TEXTURE_2D);
				GL11.glBegin(GL11.GL_POLYGON); {
					GL11.glVertex2d(getX(), getY());
					for (int i = 0; i < lines.length; i++) {
						GL11.glVertex2d(lines[i].firstPoint().getX(), lines[i].firstPoint().getY());
					}
					GL11.glVertex2d(lines[0].firstPoint().getX(), lines[0].firstPoint().getY());
				} GL11.glEnd();
			} else {
				GL11.glEnable(GL11.GL_TEXTURE_2D);						//TODO: calculate coords?
				texture.bind();
				GL11.glBegin(GL11.GL_POLYGON); {
					GL11.glVertex2d(getX(), getY());
					for (int i = 0; i < lines.length; i++) {
						GL11.glVertex2d(lines[i].firstPoint().getX(), lines[i].firstPoint().getY());
					}
					GL11.glVertex2d(lines[0].firstPoint().getX(), lines[0].firstPoint().getY());
				} GL11.glEnd();
			}
		} if (!fill || renderInterestLines) {
			applyOutlineColor();
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			for (int i = 0; i < lines.length; i++) {
				if (lines[i] == dbgLINE || lines[i] == dbgLINE2) {
					lines[i].setColor(new Color(0, 1, .5));
				} else {
					lines[i].setColor(color);
				}
				lines[i].render(true);
			}
		}
	}
}