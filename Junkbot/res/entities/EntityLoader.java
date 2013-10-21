package entities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.senior.junkbot.entity.Jetpack;
import com.senior.junkbot.entity.enemy.CleanerTurret;
import com.senior.junkbot.entity.neutral.CleanerBot;
import com.senior.junkbot.entity.neutral.WinPipe;
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
				
				if(split[0].equals("CB")) { // Cleaner Bot (int x, int y, int xa, int ya, int turnTime)
					int x = 0;
					int y = 0;
					double xa = CleanerBot.DEFAULT_SPEED;
					double ya = 0;
					int turnTime = CleanerBot.DEFAULT_TURN_TIME;
					
					for(int i = 1; i < split.length; i++) {
						if(i == 1) x = Integer.parseInt(split[i]);
						else if(i == 2) y = Integer.parseInt(split[i]);
						else if(i == 3) xa = Double.parseDouble(split[i]);
						else if(i == 4) ya = Double.parseDouble(split[i]);
						else if(i == 5) turnTime = Integer.parseInt(split[i]);
					}
					
					level.add(new CleanerBot(x, y, xa, ya, turnTime));
				} else if(split[0].equals("CT")) { // Cleaner Turret (int x, int y, double xa, double ya, int fireRate, int shotLife)
					int x = 0;
					int y = 0;
					double xa = 1;
					double ya = 0;
					int fireRate = CleanerTurret.DEFAULT_FIRE_RATE;
					int shotLife = CleanerTurret.DEFAULT_SHOT_LIFE;
					
					for(int i = 1; i < split.length; i++) {
						if(i == 1) x = Integer.parseInt(split[i]);
						else if(i == 2) y = Integer.parseInt(split[i]);
						else if(i == 3) xa = Integer.parseInt(split[i]);
						else if(i == 4) ya = Integer.parseInt(split[i]);
						else if(i == 5) fireRate = Integer.parseInt(split[i]);
						else if(i == 6) shotLife = Integer.parseInt(split[i]);
					}
					
					level.add(new CleanerTurret(x, y, xa, ya, fireRate, shotLife));
				} else if(split[0].equals("WP")) { // Win Pipe (int x, int y)
					level.add(new WinPipe(Integer.parseInt(split[1]), Integer.parseInt(split[2])));
				} else if(split[0].equals("JP")) { // Jetpack (int x, int y)
					level.add(new Jetpack(Integer.parseInt(split[1]), Integer.parseInt(split[2])));
				} else if(split[0].contains("//")) {
					
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
