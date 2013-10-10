package com.senior.junkbot.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.senior.junkbot.level.Level;
import com.senior.junkbot.tile.AirTile;
import com.senior.junkbot.tile.Tile;

public class Collidables {
	
	public static BB[] calculateCollidables(Level level) {
		int counter = 0;
		
		List<BB> xBB = new ArrayList<BB>();
		
		for(int y = 0; y < level.getHeight(); y++) {
			for(int x = 0; x < level.getWidth(); x++) {
				if(!(level.getTile(x, level.getHeight() - y - 1) instanceof AirTile)) {
					counter++;
					if(x == level.getWidth() - 1) {
						xBB.add(new BB((x - counter + 1) * Tile.size, (level.getHeight() - y - 1) * Tile.size, counter * Tile.size, Tile.size));
					}
				} else if(counter > 0) {
					xBB.add(new BB((x - counter) * Tile.size, (level.getHeight() - y - 1) * Tile.size, counter * Tile.size, Tile.size));
					counter = 0;
				} 
			}
			counter = 0;
		}
		
		BB[] bbs;
		
		bbs = new BB[xBB.size()];
		for(int i = 0; i < xBB.size(); i++) {
			bbs[i] = xBB.get(i);
		}
	
		return bbs;
	}
	
	public static BB[] loadCollidables(String URL) {
		List<BB> temp = new ArrayList<BB>();
		BB[] bbs;
		
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(Collidables.class.getResourceAsStream(URL)));
			String line;
			String[] split;
			while((line = reader.readLine()) != null) {
				split = line.split(";");
				temp.add(new BB(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2]), Integer.parseInt(split[3])));
			}
			reader.close();
			
			bbs = new BB[temp.size()];
			
			for(int i = 0; i < temp.size(); i++) {
				bbs[i] = temp.get(i); 
			}
			
			return bbs;
		} catch(IOException e) {
			e.printStackTrace();
			System.err.println("Could not load " + URL);
			System.exit(1);
		}
		
		return null;
	}
}
