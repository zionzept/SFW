package ui2.components;

import geometry2D.Color;
import geometry2D.Shape;

import org.newdawn.slick.opengl.Texture;


public class SImage extends SComponent {
	
	
	public SImage(Shape shape, Texture texture) {
		super(shape);
		getHitbox().setOutlineColor(new Color(1, 0, 1));
		getHitbox().setColor(new Color(1, 1, 1, .5));
		getHitbox().setTexture(texture);
	}

	@Override
	public void render() {
//		if (Keyboard.isKeyDown(Keyboard.KEY_X)) {
//			getHitbox().render(true);
//		}
	}
}