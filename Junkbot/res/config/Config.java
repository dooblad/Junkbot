package config;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import com.senior.junkbot.Main;

public class Config {
	public static boolean sfx = true, music = true, particles = true;
	public static int width = 400, height = 300;
	public static int scale = 2;
	private static int size;
	
	public static void loadConfig(Main main) {
		String URL = "config.txt";
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(Config.class.getResourceAsStream(URL)));
			String line;
			String[] split;
			while((line = reader.readLine()) != null) {
				split = line.split(":");
				
				if(split[0].equals("sfx")) { // Sound effects
					if(split[1].equals("true"))
						sfx = true;
					else if(split[1].equals("false"))
						sfx = false;
				} else if(split[0].equals("music")) { // Music
					if(split[1].equals("true"))
						music = true;
					else if(split[1].equals("false"))
						music = false;
				} else if(split[0].equals("particles")) { // Particles
					if(split[1].equals("true"))
						particles = true;
					else if(split[1].equals("false"))
						particles = false;
				} else if(split[0].equals("size")) { // Size (0 = "320x240", 1 = "400x300", 2 = "640x480", 3 = "800x600")
					if(split[1].equals("0")) {
						width = 320;
						height = 240;
					} else if(split[1].equals("1")) {
						width = 400;
						height = 300;
					} else if(split[1].equals("2")) {
						width = 640;
						height = 480;
					} else if(split[1].equals("3")) {
						width = 800;
						height = 600;
					}
					size = Integer.parseInt(split[1]);
				} else if(split[0].equals("scale")) { // Window scale
					scale = Integer.parseInt(split[1]);
				}
			}
			reader.close();
		} catch(IOException e) {
			e.printStackTrace();
			System.err.println("Could not load " + URL);
			System.exit(1);
		}
	}
	
	public static void saveConfig() {
		File file = new File("res/config/config.txt");
		BufferedWriter writer = null;
		try {
			file.createNewFile();
			file.setWritable(true);
			writer = new BufferedWriter(new FileWriter(file));
			writer.write("sfx:" + (sfx ? "true" : "false"));
			writer.newLine();
			writer.write("music:" + (music ? "true" : "false"));
			writer.newLine();
			writer.write("particles:" + (particles ? "true" : "false"));
			writer.newLine();
			writer.write("size:" + size);
			writer.newLine();
			writer.write("scale:" + scale);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
