package sound;

import java.io.IOException;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.openal.AL;
import org.lwjgl.util.WaveData;

import com.jcraft.oggdecoder.OggData;
import com.jcraft.oggdecoder.OggDecoder;
import com.senior.junkbot.MusicHandler;

import static org.lwjgl.openal.AL10.*;

public class Sounds {
	public static Sound ground, jetpack, jump;
	public static Sound jetpackGet, levelComplete;
	public static Sound select, optionChange, pause;
	
	public static void init() {
		try {
            AL.create();
        } catch (LWJGLException e) {
            e.printStackTrace();
            System.exit(1);
        }
        
		ground = loadSound("sfx/ground.ogg", false);
		jetpack = loadSound("sfx/jetpack.ogg", true);
		jump = loadSound("sfx/jump.ogg", false);
		
		jetpackGet = loadSound("sfx/jetpackGet.wav", false);
		levelComplete = loadSound("sfx/levelComplete.wav", false);
		
		select = loadSound("sfx/select.wav", false);
		optionChange = loadSound("sfx/optionChange.wav", false);
		pause = loadSound("sfx/pause.wav", false);
		
		MusicHandler.songs = new Sound[] {
			loadSound("music/title/Aethereal.ogg", true),
			loadSound("music/title/eCommerce.ogg", true),
			loadSound("music/title/Hackers Title.ogg", true),
			loadSound("music/level/2ndBallad.ogg", true),
			loadSound("music/level/AngryRobotIII.ogg", true),
			loadSound("music/level/Battle.ogg", true),
			loadSound("music/level/Causeway.ogg", true),
			loadSound("music/level/Club Fight.ogg", true),
		};
    }
	
	public static Sound loadSound(String URL, boolean looping) {
		Sound result = null;
		if(URL.contains(".wav")) {
			WaveData data = WaveData.create("sound/" + URL);
			
	        IntBuffer buffer = BufferUtils.createIntBuffer(1);
	        alGenBuffers(buffer);
	        alBufferData(buffer.get(0), data.format, data.data, data.samplerate);
	        data.dispose();
	        IntBuffer source = BufferUtils.createIntBuffer(1); 
	        alGenSources(source);
	        alSourcei(source.get(0), AL_BUFFER, buffer.get(0));
	        
	        alDeleteBuffers(buffer);
	        
	       result = new Sound(source);
		} else if(URL.contains(".ogg")) {
			try {
				OggDecoder decoder = new OggDecoder();
				OggData data = decoder.getData(Sounds.class.getResourceAsStream(URL));
				
				IntBuffer buffer = BufferUtils.createIntBuffer(1);
				alGenBuffers(buffer);
				alBufferData(buffer.get(0), data.channels > 1 ? AL_FORMAT_STEREO16 : AL_FORMAT_MONO16, data.data, data.rate);
				
				IntBuffer source = BufferUtils.createIntBuffer(1);
				alGenSources(source);
				alSourcei(source.get(0), AL_BUFFER, buffer.get(0));
				
				if(looping) 
					alSourcei(source.get(0), AL_LOOPING, AL_TRUE);
				
				alDeleteBuffers(buffer);
				
				
				if (alGetError() != AL_NO_ERROR) {
					//Error check
				}
				
				result = new Sound(source);
			} catch(IOException e) {
				e.printStackTrace();
				System.exit(1);
			}
		}
		return result;
	}
	
	public static Sound loadOGGSound(String URL, boolean looping) {
		try {
			OggDecoder decoder = new OggDecoder();
			OggData data = decoder.getData(Sounds.class.getResourceAsStream(URL));
			
			IntBuffer buffer = BufferUtils.createIntBuffer(1);
			alGenBuffers(buffer);
			alBufferData(buffer.get(0), data.channels > 1 ? AL_FORMAT_STEREO16 : AL_FORMAT_MONO16, data.data, data.rate);
			
			IntBuffer source = BufferUtils.createIntBuffer(1);
			alGenSources(source);
			alSourcei(source.get(0), AL_BUFFER, buffer.get(0));
			
			if(looping) 
				alSourcei(source.get(0), AL_LOOPING, AL_TRUE);
			
			alDeleteBuffers(buffer);
			
			
			if (alGetError() != AL_NO_ERROR) {
				//Error check
			}
			
			return new Sound(source);
		} catch(IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		return null;
	}
	
	public static void destroy() {
		AL.destroy();
	}
}
