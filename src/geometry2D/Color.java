package geometry2D;

import org.lwjgl.opengl.GL11;

public class Color {
	public static final Color RED = new Color(1, 0, 0);
	public static final Color GREEN = new Color(0, 1, 0);
	public static final Color BLUE = new Color(0, 0, 1);
	
	private double r;
	private double g;
	private double b;
	private double a;
	
	public Color(double r, double g, double b, double a) {
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}
	
	public Color(double r, double g, double b) {
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = 1;
	}
	
	public void use() {
		GL11.glColor4d(r, g, b, a);
	}
}