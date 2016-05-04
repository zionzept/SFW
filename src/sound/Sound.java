package sound;

import static org.lwjgl.openal.AL10.*;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.lwjgl.LWJGLException;
import org.lwjgl.openal.AL;
import org.newdawn.slick.openal.WaveData;

public class Sound {

	private static ArrayList<Integer> buffer = new ArrayList<Integer>();
	
	public static int PLAYER_DEATH, SATELL_DEATH, SLIME_DEATH, BULLET_HIT, UI_CLICK, UI_HIGHLIGHT;
	public static int BG_MUSIC;
	
	public static void setupAL() throws FileNotFoundException {
		try {
			AL.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		WaveData data;
		data = WaveData.create(new BufferedInputStream(new FileInputStream("res" + File.separatorChar +
                "sound" + File.separatorChar + "xD.wav")));
		buffer.add(alGenBuffers());
		alBufferData(buffer.get(buffer.size() - 1), data.format, data.data, data.samplerate);
		data.dispose();
		BG_MUSIC = alGenSources();
		alSourcei(BG_MUSIC, AL_BUFFER, buffer.get(buffer.size() - 1));
		
		data = WaveData.create(new BufferedInputStream(new FileInputStream("res" + File.separatorChar +
                "sound" + File.separatorChar + "satellDeath.wav")));
		buffer.add(alGenBuffers());
		alBufferData(buffer.get(buffer.size() - 1), data.format, data.data, data.samplerate);
		data.dispose();
		SATELL_DEATH = alGenSources();
		alSourcei(SATELL_DEATH, AL_BUFFER, buffer.get(buffer.size() - 1));
		
		
		data = WaveData.create(new BufferedInputStream(new FileInputStream("res" + File.separatorChar +
                "sound" + File.separatorChar + "slimeDeath.wav")));
		buffer.add(alGenBuffers());
		alBufferData(buffer.get(buffer.size() - 1), data.format, data.data, data.samplerate);
		data.dispose();
		SLIME_DEATH = alGenSources();
		alSourcei(SLIME_DEATH, AL_BUFFER, buffer.get(buffer.size() - 1));
		
		
		data = WaveData.create(new BufferedInputStream(new FileInputStream("res" + File.separatorChar +
                "sound" + File.separatorChar + "bulletHit.wav")));
		buffer.add(alGenBuffers());
		alBufferData(buffer.get(buffer.size() - 1), data.format, data.data, data.samplerate);
		data.dispose();
		BULLET_HIT = alGenSources();
		alSourcei(BULLET_HIT, AL_BUFFER, buffer.get(buffer.size() - 1));
		
		
		data = WaveData.create(new BufferedInputStream(new FileInputStream("res" + File.separatorChar +
                "sound" + File.separatorChar + "UIClick.wav")));
		buffer.add(alGenBuffers());
		alBufferData(buffer.get(buffer.size() - 1), data.format, data.data, data.samplerate);
		data.dispose();
		UI_CLICK = alGenSources();
		alSourcei(UI_CLICK, AL_BUFFER, buffer.get(buffer.size() - 1));
		
		
		
		data = WaveData.create(new BufferedInputStream(new FileInputStream("res" + File.separatorChar +
                "sound" + File.separatorChar + "playerDeath.wav")));
		buffer.add(alGenBuffers());
		alBufferData(buffer.get(buffer.size() - 1), data.format, data.data, data.samplerate);
		data.dispose();
		PLAYER_DEATH = alGenSources();
		alSourcei(PLAYER_DEATH, AL_BUFFER, buffer.get(buffer.size() - 1));
		
		data = WaveData.create(new BufferedInputStream(new FileInputStream("res" + File.separatorChar +
                "sound" + File.separatorChar + "UIHighlight.wav")));
		buffer.add(alGenBuffers());
		alBufferData(buffer.get(buffer.size() - 1), data.format, data.data, data.samplerate);
		data.dispose();
		UI_HIGHLIGHT = alGenSources();
		alSourcei(UI_HIGHLIGHT, AL_BUFFER, buffer.get(buffer.size() - 1));
	}
	
	public static void play(int source) {
		alSourcePlay(source);
	}
	
	public static  void stop(int source) {
		alSourcePlay(source);
	}
	
	public static void setSourceGain(int source, float value) {
		alSourcef(source, AL_GAIN, value);
	}
	
	public static void setSourcePitch(int source, float value) {
		alSourcef(source, AL_PITCH, value);
	}
	
	public static void destroy() {
		for (int i : buffer) {
			alDeleteBuffers(i);
		}
		AL.destroy();
	}
}