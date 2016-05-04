package window;

import java.util.LinkedList;
import java.util.concurrent.locks.ReentrantLock;

public class TaskAdder extends ReentrantLock {
	private static final long serialVersionUID = 1L;
	private LinkedList<Task> tasks;
	
	public TaskAdder() {
		this.tasks = new LinkedList<>();
	}
	
	public void add(Task task) {
		lock();
		try {
			if (task instanceof ShellAdjuster) {
				for (Task queuedTask : tasks) {
					if (queuedTask instanceof ShellAdjuster) {
						((ShellAdjuster)queuedTask).mergeWith((ShellAdjuster)task);
						return;
					}
				}
				tasks.add(task);
				return;
			}
			if (task instanceof VisibilityToggler) {
				int i = 0;
				for (Task queuedTask : tasks) {
					if (queuedTask instanceof VisibilityToggler) {
						System.out.println("merge vis");
						tasks.remove(i);
						return;
					}
					i++;
				}
				tasks.add(task);
				return;
			}
			tasks.add(task);
		} finally {
			unlock();
		}
	}	
	
	public void performTask() {
		lock();
		try {
			if (!tasks.isEmpty()) {
				tasks.removeFirst().run();	
			}
		} finally {
			unlock();			
		}
	}
}