package sound;

import java.io.IOException;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.openal.AL;
import org.lwjgl.util.WaveData;

import com.jcraft.oggdecoder.OggData;
import com.jcraft.oggdecoder.OggDecoder;

import static org.lwjgl.openal.AL10.*;

public class Sounds {
	public static Sound test;
	public static Sound level;
	
	public static void init() {
		try {
            AL.create();
        } catch (LWJGLException e) {
            e.printStackTrace();
            System.exit(1);
        }
        
		test = loadSound("test.wav");
		level = loadOGGSound("level.ogg");
    }
	
	public static Sound loadSound(String URL) {
		WaveData data = WaveData.create("sound/" + URL);
		
        IntBuffer buffer = BufferUtils.createIntBuffer(1);
        alGenBuffers(buffer);
        alBufferData(buffer.get(0), data.format, data.data, data.samplerate);
        data.dispose();
        IntBuffer source = BufferUtils.createIntBuffer(1); 
        alGenSources(source);
        alSourcei(source.get(0), AL_BUFFER, buffer.get(0));
        
        alDeleteBuffers(buffer);
        
        return new Sound(source);
	}
	
	public static Sound loadOGGSound(String URL) {
		try {
			OggDecoder decoder = new OggDecoder();
			OggData data = decoder.getData(Sounds.class.getResourceAsStream(URL));
			
			IntBuffer buffer = BufferUtils.createIntBuffer(1);
			alGenBuffers(buffer);
			alBufferData(buffer.get(0), data.channels > 1 ? AL_FORMAT_STEREO16 : AL_FORMAT_MONO16, data.data, data.rate);
			
			IntBuffer source = BufferUtils.createIntBuffer(1);
			alGenSources(source);
			alSourcei(source.get(0), AL_BUFFER, buffer.get(0));
			
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
