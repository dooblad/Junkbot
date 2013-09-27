package sound;

import static org.lwjgl.openal.AL10.*;

import java.nio.IntBuffer;

public class Sound {
	private IntBuffer source;
	private boolean playing;
	
	public Sound(IntBuffer source) {
		this.source = source;
		playing = false;
	}
	
	public void play() {
		if(!playing) {
			alSourcePlay(source);
			playing = true;
		}
	}
	
	public void pause() {
		if(playing) {
			alSourcePause(source);
			playing = false;
		}
	}
	
	public void stop() {
		if(playing) {
			alSourceStop(source);
			playing = false;
		}
	}
	
	public void togglePlay() {
		if(playing) {
			alSourceStop(source);
			playing = false;
		} else {
			alSourcePlay(source);
			playing = true;
		}
	}
	
	// Getters and Setters
	public IntBuffer getSource() {
		return source;
	}
	
	public boolean isPlaying() {
		return playing;
	}
}
