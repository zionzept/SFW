package texture;

import org.newdawn.slick.opengl.Texture;

public class NullSwapTexture extends SwapTexture {

	@Override
	public boolean hasAlpha() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getTextureRef() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void bind() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getImageHeight() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getImageWidth() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getHeight() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getWidth() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTextureHeight() {
		// TODO Auto-generated method stub
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