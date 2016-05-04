package texture;

import org.newdawn.slick.opengl.Texture;

public class SwapTexture implements Texture {
	public static final SwapTexture NULL = new SwapTexture(NullTexture.instance);
	
	
	public Texture[] texture;
	public Texture activeTexture;
	
	public SwapTexture(Texture... texture) {
		this.texture = texture;
		swap(0);
	}
	
	public void swap(int id) {
		if (id <= 0) {
			activeTexture = texture[0];
		} else if (id >= texture.length - 1) {
			activeTexture = texture[texture.length - 1];
		} else {
			activeTexture = texture[id];
		}
	}

	@Override
	public boolean hasAlpha() {
		return activeTexture.hasAlpha();
	}

	@Override
	public String getTextureRef() {
		return activeTexture.getTextureRef();
	}

	@Override
	public void bind() {
		activeTexture.bind();
	}

	@Override
	public int getImageHeight() {
		return activeTexture.getImageHeight();
	}

	@Override
	public int getImageWidth() {
		return activeTexture.getImageWidth();
	}

	@Override
	public float getHeight() {
		return activeTexture.getHeight();
	}

	@Override
	public float getWidth() {
		return activeTexture.getWidth();
	}

	@Override
	public int getTextureHeight() {
		return activeTexture.getTextureHeight();
	}

	@Override
	public int getTextureWidth() {
		return activeTexture.getTextureWidth();
	}

	@Override
	public void release() {
		activeTexture.release();
	}

	@Override
	public int getTextureID() {
		return activeTexture.getTextureID();
	}

	@Override
	public byte[] getTextureData() {
		return activeTexture.getTextureData();
	}

	@Override
	public void setTextureFilter(int textureFilter) {
		for (int i = 0; i < texture.length; i++) {
			texture[i].setTextureFilter(textureFilter);
		}
	}
}