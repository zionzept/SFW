package window;

import java.util.LinkedList;

public abstract class Task {

	protected SWindow window;
	protected LinkedList<Task> dekimashitaTasks;
	
	public Task(SWindow window) {
		this.window = window;
		dekimashitaTasks = new LinkedList<>();
	}
	
	public void addDekimashitaTasks(Task... tasks) {
		for (Task task : tasks) {
			dekimashitaTasks.add(task);
		}
	}
	
	public void run() {
		if (perform()) {
			while (!dekimashitaTasks.isEmpty()) {
				window.addTask(dekimashitaTasks.removeFirst());
			}
		}
	}
	
	protected abstract boolean perform();
	}