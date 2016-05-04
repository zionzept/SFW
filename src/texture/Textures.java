package texture;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

public class Textures {

	public static HashMap<String, Texture> textures = new HashMap<>();

	public static Texture loadTexture(String path, String format) {
		String prefix = util.Properties.getProperties().getProperty("respath");
		if (prefix != null) {
			path = prefix + path;
		}
		Texture texture = textures.get(path);
		if (texture == null) {
			texture = createTexture(path, format);
		}
		return texture;
	}

	private static Texture createTexture(String path, String format) {
		Texture texture = null;
		try {
			texture = TextureLoader.getTexture(format.toUpperCase(), new FileInputStream(new File(path)));
		} catch (IOException e) {
			try {
				texture = TextureLoader.getTexture(format.toUpperCase(), new FileInputStream(new File(path + '.'
						+ format.toLowerCase())));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		return texture;
	}
}