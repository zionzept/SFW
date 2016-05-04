package input;

public class SMouseWheelEvent {
	public static final int FORWARD = 1;
	public static final int BACKWARD = -1;
	
	private int x;
	private int y;
	private int direction;
	private int magnitude;
	private long time;
	
	public SMouseWheelEvent(int x, int y, int direction, int magnitude) {
		this.time = System.currentTimeMillis();
		this.x = x;
		this.y = y;
		this.direction = direction; 
		this.magnitude = magnitude;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getDirection() {
		return direction;
	}
	
	public int getMagnitude() {
		return magnitude;
	}
	
	public long getTime() {
		return time;
	}
	
	@Override
	public String toString() {
		return "MouseWheelEvent{x=" + x + " y=" + y + " direction=" + direction + " magnitude=" + magnitude + " time=" + time + "}";
	}
}