package entities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.senior.junkbot.entity.WinPipe;
import com.senior.junkbot.entity.enemy.CleanerBot;
import com.senior.junkbot.level.Level;

public class EntityLoader {
	
	public static void loadEntities(Level level) {
		String URL = "level" + Level.currentLevel + ".txt";
		
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(EntityLoader.class.getResourceAsStream(URL)));
			String line;
			String[] split;
			while((line = reader.readLine()) != null) {
				split = line.split(";");
				switch(split[0]) {
				case "CB": 
					if(split.length < 5)
						level.add(new CleanerBot(Integer.parseInt(split[1]), Integer.parseInt(split[2]), Integer.parseInt(split[3])));
					else
						level.add(new CleanerBot(Integer.parseInt(split[1]), Integer.parseInt(split[2]), Integer.parseInt(split[3]), Integer.parseInt(split[4])));
					break;
				case "WP": 
					level.add(new WinPipe(Integer.parseInt(split[1]), Integer.parseInt(split[2])));
					break;
				default: break;
				}
			}
			reader.close();
		} catch(IOException e) {
			e.printStackTrace();
			System.err.println("Could not load " + URL);
			System.exit(1);
		}
	}
}
