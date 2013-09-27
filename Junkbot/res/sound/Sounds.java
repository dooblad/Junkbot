package sound;

import static org.lwjgl.openal.AL10.*;

import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.openal.AL;
import org.lwjgl.util.WaveData;

public class Sounds {
	public static Sound test;
	
	public static void init() {
		try {
            AL.create();
        } catch (LWJGLException e) {
            e.printStackTrace();
            System.exit(1);
        }
        
		test = loadSound("test.wav");
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
	
	public static void destroy() {
		AL.destroy();
	}
}
