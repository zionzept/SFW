package input;

public class SMouseEvent {
	public static final int LMB = 1;
	public static final int RMB = 2;
	public static final int MID = 3;
	public static final int MB4 = 4;
	public static final int MB5 = 5;

	private int x;
	private int y;
	private int button;
	private int count;
	private long time;
	
	public SMouseEvent(int x, int y, int button, int count) {
		time = System.currentTimeMillis();
		this.button = button;
		this.x = x;
		this.y = y;
		this.count = count;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getButton() {
		return button;
	}
	
	public int getCount() {
		return count;
	}
	
	public long getTime() {
		return time;
	}
	
	@Override
	public String toString() {
		return "MouseEvent{x=" + x + " y=" + y + " button=" + button + " count=" + count + " time=" + time + "}";
	}
}