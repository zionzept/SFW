package texture;

import org.newdawn.slick.opengl.Texture;

public class NullTexture implements Texture {
	public static final NullTexture instance = new NullTexture();

	private NullTexture() {
	}

	@Override
	public boolean hasAlpha() {
		return false;
	}

	@Override
	public String getTextureRef() {
		return null;
	}

	@Override
	public void bind() {
	}

	@Override
	public int getImageHeight() {
		return 0;
	}

	@Override
	public int getImageWidth() {
		return 0;
	}

	@Override
	public float getHeight() {
		return 0;
	}

	@Override
	public float getWidth() {
		return 0;
	}

	@Override
	public int getTextureHeight() {
		return 0;
	}

	@Override
	public int getTextureWidth() {
		return 0;
	}

	@Override
	public void release() {
	}

	@Override
	public int getTextureID() {
		return 0;
	}

	@Override
	public byte[] getTextureData() {
		return null;
	}

	@Override
	public void setTextureFilter(int textureFilter) {
	}
}