package geometry2D;

import static org.lwjgl.opengl.GL11.*;

public class Rectangle extends Shape{
	
	private Line l0;
	private Line l1;
	private Line l2;
	private Line l3;
	
	public Rectangle(double x, double y, double w, double h) {
		super(x, y, 0);
		Point p0 = new Point(x - w / 2, y - h / 2);
		Point p1 = new Point(x - w / 2, y + h / 2);
		Point p2 = new Point(x + w / 2, y + h / 2);
		Point p3 = new Point(x + w / 2, y - h / 2);
		this.l0 = new Line(p0, p1);
		this.l1 = new Line(p1, p2);
		this.l2 = new Line(p2, p3);
		this.l3 = new Line(p3, p0);
		init();
	}
	
	private void init() {
		this.l0.parent = this;
		this.l1.parent = this;
		this.l2.parent = this;
		this.l3.parent = this;
		this.l0.move(-getX(), -getY());
		this.l1.move(-getX(), -getY());
		this.l2.move(-getX(), -getY());
		this.l3.move(-getX(), -getY());
		this.l0.setColor(Color.BLUE);
		this.l1.setColor(Color.BLUE);
		this.l2.setColor(Color.BLUE);
		this.l3.setColor(Color.BLUE);
	}
	
	@Override
	protected Point firstPoint() {
		return l0.firstPoint();
	}
	
	@Override
	public boolean collides(Shape shape) {
		if (shape == this) {
			return false;
		} 
		return shape.collidesPoint(l0.firstPoint()) ||
				shape.collidesPoint(l1.firstPoint()) ||
				shape.collidesPoint(l2.firstPoint()) ||
				shape.collidesPoint(l3.firstPoint()) ||
				shape.collidesLine(l0) ||
				shape.collidesLine(l1) ||
				shape.collidesLine(l2) ||
				shape.collidesLine(l3) ||
				collidesPoint(shape.firstPoint());
	}
	
	@Override
	protected boolean collidesPoint(Point point) {
		boolean b0 = l0.leftOfLine(point);
		boolean b1 = l1.leftOfLine(point);
		boolean b2 = l2.leftOfLine(point);
		boolean b3 = l3.leftOfLine(point);
		return b0 == b1 &&
				b1 == b2 &&
				b2 == b3;
	}
	
	@Override
	protected boolean collidesLine(Line line) {
		return l0.collidesLine(line) ||
				l1.collidesLine(line) ||
				l2.collidesLine(line) ||
				l3.collidesLine(line);
	}
	
	@Override
	protected boolean collidesCircle(Circle circle) {
		return l0.firstPoint().collidesCircle(circle) ||
				l1.firstPoint().collidesCircle(circle) ||
				l2.firstPoint().collidesCircle(circle) ||
				l3.firstPoint().collidesCircle(circle) ||
				l0.collidesCircle(circle) ||
				l1.collidesCircle(circle) ||
				l2.collidesCircle(circle) ||
				l3.collidesCircle(circle) ||
				collidesPoint(circle.firstPoint());
	}
	
	@Override
	public void setOutlineColor(Color color) {
		outlineColor = color;
		l0.setColor(color);
		l1.setColor(color);
		l2.setColor(color);
		l3.setColor(color);
	}
	
	@Override
	public void render(boolean fill) {
		if (fill) {
			applyColor();
			if (texture == null) {
				glDisable(GL_TEXTURE_2D);
				glBegin(GL_QUADS); {
					glVertex2d(l0.firstPoint().getX(), l0.firstPoint().getY());
					glVertex2d(l1.firstPoint().getX(), l1.firstPoint().getY());
					glVertex2d(l2.firstPoint().getX(), l2.firstPoint().getY());
					glVertex2d(l3.firstPoint().getX(), l3.firstPoint().getY());
				} glEnd();
			} else {
				glEnable(GL_TEXTURE_2D);
				texture.bind();
				glBegin(GL_QUADS); {
					glTexCoord2d(0, 0);							//TODO: different texcoord calculations?
					glVertex2d(l0.firstPoint().getX(), l0.firstPoint().getY());
					glTexCoord2d(0, 1);
					glVertex2d(l1.firstPoint().getX(), l1.firstPoint().getY());
					glTexCoord2d(1, 1);
					glVertex2d(l2.firstPoint().getX(), l2.firstPoint().getY());
					glTexCoord2d(1, 0);
					glVertex2d(l3.firstPoint().getX(), l3.firstPoint().getY());
				} glEnd();
			}
		} if (!fill || renderInterestLines) {
			l0.render(true);
			l1.render(true);
			l2.render(true);
			l3.render(true);
		}
	}
}