package ui2.components;

import geometry2D.Circle;
import geometry2D.Color;
import geometry2D.Rectangle;
import input.SMouseEvent;
import input.SMouseListener;
import input.SMouseMoveListener;
import input.SMouseTrackListener;
import input.SMouseWheelEvent;
import input.SMouseWheelListener;
import texture.SwapTexture;


public class SButton extends SComponent {
	
	private SwapTexture texture = SwapTexture.NULL;
	
	public SButton(double x, double y, final SwapTexture texture) {
		super(new Circle(x, y, 100, 0));
		getHitbox().setTexture(texture);
		this.texture = texture;
		
		addMouseListener(new SMouseListener() {
			@Override
			public void mouseDown(SMouseEvent e) {
				if (e.getButton() == SMouseEvent.LMB) {
					texture.swap(2);
					getHitbox().setOutlineColor(new Color(0, 1, 1));
					activation();
				}
			}

			@Override
			public void mouseUp(SMouseEvent e) {
				if (e.getButton() == SMouseEvent.LMB) {
					texture.swap(1);
					getHitbox().setOutlineColor(new Color(1, 0, 0));
				}
			}
		});
		
		addMouseTrackListener(new SMouseTrackListener() {
			@Override
			public void mouseEnter(SMouseEvent e) {
				texture.swap(1);
				getHitbox().setOutlineColor(new Color(1, 0, 0));
			}
			@Override
			public void mouseExit(SMouseEvent e) {
				texture.swap(0);
				getHitbox().setOutlineColor(new Color(0, 1, 0));
			}
		});
	}

	@Override
	public void render() {
//		if (Keyboard.isKeyDown(Keyboard.KEY_X)) {
//			getHitbox().render(true);
//		}
	}	
}