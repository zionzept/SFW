package file;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

public abstract class TextureBase {
	
	public static Texture WOOD;
	public static Texture STEEL;
	
	
	
	public static void initTextures() {
		WOOD = loadTexture("woodTexture");
		STEEL = loadTexture("Sphere Steel");
	}
	
	private static Texture loadTexture(String key) {
		try {
			return TextureLoader.getTexture("PNG", new FileInputStream(new File("res/" + key + ".png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}