package window;

import org.eclipse.swt.graphics.Point;

import util.Maffs;

public class ShellAdjuster extends Task {
	public static final int CENTER = 0;
	public static final int TOP = 1;
	public static final int LEFT = 1<<1;
	public static final int BOTTOM = 1<<2;
	public static final int RIGHT = 1<<3;
	public static final int TOP_LEFT = TOP | LEFT;
	public static final int TOP_RIGHT = TOP | RIGHT;
	public static final int BOTTOM_LEFT = BOTTOM | LEFT;
	public static final int BOTTOM_RIGHT = BOTTOM | RIGHT;
	public static int alignment = TOP_LEFT;
	
	public static final int INSTANT = 0;
	public static final int LINEAR = 1;
	public static double moveSpeed = 10;
	public static double sizeSpeed = 20;
	public static int mode;
	
	private static final int LOCATION = 1;
	private static final int SIZE = 2;
	private static final int BOUNDS = 3;
	private int type;
	
	private double x;
	private double y;
	private double w;
	private double h;
	public ShellAdjuster(SWindow window, int x, int y) {
		super(window);
		this.x = x;
		this.y = y;
		type = LOCATION;
	}
	
	public ShellAdjuster(int w, SWindow window, int h) {
		super(window);
		this.w = w;
		this.h = h;
		type = SIZE;
	}
	
	public ShellAdjuster(SWindow window, int x, int y, int w, int h) {
		super(window);
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		type = BOUNDS;
	}
	
	public void mergeWith(ShellAdjuster task) {
		switch (task.type) {
		case LOCATION:
			x = task.x;
			y = task.y;
			if (type == SIZE) {
				type = BOUNDS;
			}
			break;
		case SIZE:
			w = task.w;
			h = task.h;
			if (type == LOCATION) {
				type = BOUNDS;
			}
			break;
		case BOUNDS:
			x = task.x;
			y = task.y;
			w = task.w;
			h = task.h;
			type = BOUNDS;
			break;
		}
	}

	@Override
	public boolean perform() {
		Point size = window.getSize();
		Point location = window.getLocation();
		double x = this.x;
		double y = this.y;
		if ((alignment & LEFT) == 0) {
			//not left alignment
			if ((alignment & RIGHT) != 0) {
				//right alignment
				x -= size.x;
			} else {
				//center alignment
				x -= size.x / 2f;
			}
		} // else left alignment
		if ((alignment & TOP) == 0) {
			//not top alignment
			if ((alignment & BOTTOM) != 0) {
				//bottom alignment
				y -= size.y;
			} else {
				//center alignment
				y -= size.y / 2f;
			}
		} // else top alignment
		if (mode == INSTANT) {
			switch(type) {
			case LOCATION:
				window.getShell().setLocation((int)x, (int)y);
				break;
			case SIZE:
				window.getShell().setSize((int)w, (int)h);
				break;
			case BOUNDS:
				window.getShell().setBounds((int)x, (int)y, (int)w, (int)h);
				break;
			}
		} else if (mode == LINEAR) {
			switch(type) {
			case LOCATION:
				if (!smoothMove(x, y, location)) {
					return false;
				}
				break;
			case SIZE:
				if (!smoothScale(size)) {
					return false;
				}
				break;
			case BOUNDS:
				if (!smoothScale(size) || !smoothMove(x, y, location)) {
					return false;
				}
				break;
			}
		}
		return true;
	}
	
	private boolean smoothMove(double x, double y, Point location) {
		if (Math.hypot(x - location.x, y - location.y) <= moveSpeed) {
			window.getShell().setLocation((int)x, (int)y);
		} else {
			double angle = Maffs.getAngle(location, new Point((int)x, (int)y));
			double midX = location.x + Math.cos(angle) * moveSpeed;
			double midY = location.y - Math.sin(angle) * moveSpeed;
			window.getShell().setLocation((int)midX, (int)midY);
			window.addTask(this);
			return false;
		}
		return true;
	}
	
	private boolean smoothScale(Point size) {
		if (Math.hypot(w - size.x, h - size.y) <= sizeSpeed) {
			window.getShell().setSize((int)w, (int)h);
		} else {
			double angle = Maffs.getAngle(size, new Point((int)w, (int)h));
			double midX = size.x + Math.cos(angle) * sizeSpeed;
			double midY = size.y - Math.sin(angle) * sizeSpeed;
			window.getShell().setSize((int)midX, (int)midY);
			window.addTask(this);
			return false;
		}
		return true;
	}
}