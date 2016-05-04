package geometry2D;

import org.lwjgl.opengl.GL11;

public class Circle extends Shape{
	private static final int QUALITY_LOW = 16;
	private static final int QUALITY_NORMAL = 32;
	private static final int QUALITY_HIGH = 64;
	
	private Point center;
	private double r;
	private int quality;
	
	public Circle(double x, double y, double r, double rot) {
		super(x, y, rot);
		this.r = r;
		this.center = new Point(0, 0);
		this.center.parent = this;
		this.quality = QUALITY_NORMAL;
	}
	
	protected double getRadius() {
		return r;
	}
	
	@Override
	protected Point firstPoint() {
		return center;
	}
	
	@Override
	public boolean collides(Shape shape) {
		if (this == shape) {
			return false;
		}
		return shape.collidesCircle(this);
	}
	
	@Override
	protected boolean collidesPoint(Point point) {
		return Math.hypot(point.getX() - getX(), point.getY() - getY()) <= r;
	}
	
	@Override
	protected boolean collidesLine(Line line) {
		return line.normalizable(center) && line.normalDistance(center) <= r;
	}
	
	@Override
	protected boolean collidesCircle(Circle circle) {
		return Math.hypot(circle.getX() - getX(), circle.getY() - getY()) < circle.r + r;
	}
	
	@Override
	public void render(boolean fill) {
		if (fill) {
			applyColor();
			if (texture == null) {
				GL11.glDisable(GL11.GL_TEXTURE_2D);
				GL11.glBegin(GL11.GL_POLYGON); {
					GL11.glVertex2d(getX(), getY());
					for (int i = 0; i <= quality; i++) {
						double angle = 2 * PI * i / quality + getRot();
						GL11.glVertex2d(getX() + r * Math.cos(angle), getY() - r * Math.sin(angle));
					}
				} GL11.glEnd();
			} else {
				GL11.glEnable(GL11.GL_TEXTURE_2D);
				getTexture().bind();
				GL11.glBegin(GL11.GL_POLYGON); {
					GL11.glTexCoord2d(.5, .5);					//TODO: different texcoord calculations?
					GL11.glVertex2d(getX(), getY());
					for (int i = 0; i <= quality; i++) {
						double texAngle = 2 * PI * i / quality;
						double vertexAngle = texAngle + getRot();
						GL11.glTexCoord2d(.5 + .5 * Math.cos(texAngle), .5 - .5 * Math.sin(texAngle));
						GL11.glVertex2d(getX() + r * Math.cos(vertexAngle), getY() - r * Math.sin(vertexAngle));
					}
				} GL11.glEnd();
			}	
		} if (!fill || renderInterestLines) {
			applyOutlineColor();
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glBegin(GL11.GL_LINES); {
				double prevAngle = getRot();
				for (int i = 0; i < quality; i++) {
					double angle = 2 * PI * (i + 1) / quality + getRot();
					GL11.glVertex2d(getX() + r * Math.cos(prevAngle), getY() - r * Math.sin(prevAngle));
					GL11.glVertex2d(getX() + r * Math.cos(angle), getY() - r * Math.sin(angle));
					prevAngle = angle;
				}
			} GL11.glEnd();
		}
		
	}
	
	@Override
	public String toString() {
		return "Circle\t[x=" + getX() + "][y=" + getY() + "][r=" + r + "]";
	}
}