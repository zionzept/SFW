package input.controller;

import net.java.games.input.Rumbler;

public class RumblerManager {
	private final float defaultDecreaseRate = .016f;
	
	private float decreaseRate;
	
	private Rumbler[] rumblers;
	private float intensity;
	
	public RumblerManager(Rumbler[] rumblers) {
		this.rumblers = rumblers;
		decreaseRate = defaultDecreaseRate;
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(20);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				update();
			}
		}).start();
	}
	
	public void setRumble(float intensity) {
		this.intensity = intensity;
		decreaseRate = defaultDecreaseRate;
	}
	
	public void setRumble(float intensity, float decreaseRate) {
		this.intensity = intensity;
		this.decreaseRate = decreaseRate;
	}
	
	private void update() {
		if (intensity > 0 ) {
			intensity -= decreaseRate;
			if (intensity <= 0) {
				intensity = 0;
				decreaseRate = defaultDecreaseRate;
			}
			for (Rumbler r : rumblers) {
				r.rumble(intensity);
			}
		}
	}
	
	public void rumblePattern(int i) {
		switch (i) {
		case 0:
				setRumble(.37f);
			break;
		case 1:
			new Thread(new Runnable() {
				public void run() {
					setRumble(.35f);
					try {
						Thread.sleep(360);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					setRumble(.33f);
					try {
						Thread.sleep(360);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					setRumble(.33f);
				}
			}).start();
			break;
		case 2:
			new Thread(new Runnable() {
				public void run() {
					setRumble(.36f);
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					setRumble(.36f);
				}
			}).start();
			break;
		case 3:
			new Thread(new Runnable() {
				public void run() {
					setRumble(.32f);
					try {
						Thread.sleep(160);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					setRumble(.36f);
					try {
						Thread.sleep(400);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					setRumble(.32f);
					try {
						Thread.sleep(160);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					setRumble(.36f);
				}
			}).start();
			break;
		case 4:
			setRumble(.20f, .0006f);
			break;
		}
	}
}