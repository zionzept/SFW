package ui2.components;

import java.util.LinkedList;

import geometry2D.Shape;

public class SContainer extends SComponent {

	private LinkedList<SComponent> components;
	
	public SContainer(Shape hitbox) {
		super(hitbox);
	}

	@Override
	protected void render() {
		components.forEach(x -> render());
	}
}