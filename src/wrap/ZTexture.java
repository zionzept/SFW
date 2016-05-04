package wrap;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;


import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import ui.ZUI;


public class ZTexture {
	
	private Texture value;
	private int height, width;
	private InputStream is;
	
	public ZTexture(BufferedImage image, int w, int h) {
		height = image.getHeight();
		width = image.getWidth();
	    BufferedImage dimg = new BufferedImage(w, h, image.getType());  
	    Graphics2D g = dimg.createGraphics();  
	    g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);  
	    g.drawImage(image, 0, 0, w, h, 0, 0, width, height, null);  
	    g.dispose(); 
	    try {
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			ImageIO.write(dimg, "png", os);
			is = new ByteArrayInputStream(os.toByteArray());
			value = TextureLoader.getTexture("PNG", is);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Texture texture() {
		return value;
	}
	
	public float height() {
		return height * ZUI.FONT_SIZE;
	}
	
	public float width() {
		return width * ZUI.FONT_SIZE;
	}
}
