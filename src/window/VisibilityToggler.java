package window;


public class VisibilityToggler extends Task {
	public VisibilityToggler(SWindow window) {
		super(window);
	}

	@Override
	protected boolean perform() {
		window.getShell().setVisible(!window.getShell().isVisible());
		return true;
	}
}