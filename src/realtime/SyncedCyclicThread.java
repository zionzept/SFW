package realtime;

public class SyncedCyclicThread implements Runnable {

	private Thread thread;
	private Runnable runnable;
	private boolean running;
	private long refTime;
	
	public SyncedCyclicThread(Runnable runnable) {
		thread = new Thread(this);
		this.runnable = runnable;
	}
	
	public void start() {
		thread.start();
	}

	@Override
	public void run() {
		running = true;
		refTime = System.currentTimeMillis();
		while (!thread.isInterrupted() && running) {
			runnable.run();
		}
	}
	
	public void syncedSleep(long millis) throws InterruptedException {
		long newTime = System.currentTimeMillis();
		if (!Thread.currentThread().equals(thread)) {
			throw new RuntimeException("SyncedCyclicThread: Must be called from the thread assigned to this object.");
		}
		long dt = newTime - refTime;
		refTime += millis;
		if (dt < millis) {
			long sleepTime = millis - dt; // greater than zero
			Thread.sleep(sleepTime);
		}
	}
}