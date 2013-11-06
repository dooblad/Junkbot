package com.senior.junkbot.util;

import java.awt.event.KeyEvent;

import bitmaps.Bitmaps;

import com.doobs.java2d.gfx.Screen;
import com.doobs.java2d.input.InputHandler;
import com.senior.junkbot.Main;
import com.senior.junkbot.entity.Entity;
import com.senior.junkbot.entity.Player;
import com.senior.junkbot.level.Level;
import com.senior.junkbot.tile.Tile;

import config.Config;

public class LevelDebugger {
	private static final int FLYING_ACCELERATION = 3;
	
	private Main main;
	private Level level;
	private Player player;
	
	private boolean playerFlying;
    private double[] customSpawnCoords;
    
    private boolean renderDebug;

	public LevelDebugger() {
		this(null, null);
	}
	
	public LevelDebugger(Level level, Player player) {
		this(null, level, player);
	}
	
	public LevelDebugger(Main main, Level level, Player player) {
		this.main = main;
		this.level = level;
		this.player = player;
		customSpawnCoords = new double[2];
		playerFlying = false;
		
		customSpawnCoords[0] = level.getXSpawn();
		customSpawnCoords[1] = level.getYSpawn();
		
		renderDebug = false;
	}
	
	public void tick(InputHandler input) {
		
		// Toggle renderingCollidables
		if(input.isKeyPressed(KeyEvent.VK_F1))
			renderDebug = !renderDebug;
		
		// Flying effects
		if(playerFlying) {
			player.setY(player.getY() - Level.GRAVITY * player.getMass());
			if(input.keys[KeyEvent.VK_W])
				player.setYA(-FLYING_ACCELERATION);
			else if(input.keys[KeyEvent.VK_S])
				player.setYA(FLYING_ACCELERATION);
			else 
				player.setYA(0);
		}
		
		// Level reset
		if(input.isKeyPressed(KeyEvent.VK_R)) {
    		if(input.keys[KeyEvent.VK_SHIFT]) {
    			player.setX(customSpawnCoords[0]);
        		player.setY(customSpawnCoords[1]);
    		} else if(input.keys[KeyEvent.VK_CONTROL]){
    			player.setX(level.getXSpawn());
        		player.setY(level.getYSpawn());
    		}
    		
    	}
    	
    	if(input.keys[KeyEvent.VK_CONTROL]) {
    		// Level control
    		if(input.isKeyPressed(KeyEvent.VK_LEFT))
    			level.previousLevel();
    		else if(input.isKeyPressed(KeyEvent.VK_RIGHT))
    			level.nextLevel();
    		
    		// Player mass increase/decrease
        	if(input.isKeyPressed(KeyEvent.VK_UP))
    			player.setMass(player.getMass() + 0.2);
        	else if(input.isKeyPressed(KeyEvent.VK_DOWN))
        		player.setMass(player.getMass() - 0.2);
    		
        	// Player teleportation
    		if(input.isKeyPressed(KeyEvent.VK_T)) {
    			player.setX(input.getMouseX() / Config.scale + level.getCamera().getXO());
    			player.setY(input.getMouseY() / Config.scale + level.getCamera().getYO());
    		}
    		
    		if(input.isKeyPressed(KeyEvent.VK_F)) 
    			playerFlying = !playerFlying;
    	}
    	
    	// Gives the position of the mouse in the level's coordinate space
    	if(input.isLeftMouseDown()) {
    		int mouseX = (int) (input.getMouseX() / Config.scale + level.getCamera().getXO());
			int mouseY = (int) (input.getMouseY() / Config.scale + level.getCamera().getYO());
			
			int xTile = mouseX / Tile.size;
			int yTile = mouseY / Tile.size;
			
			if(xTile > (level.getWidth() - 1))
				mouseX = (level.getWidth() - 1) * Tile.size;
			else if(mouseX < 0) {
				mouseX = 0;
			}
			
			if(yTile > (level.getHeight() - 1))
				mouseY = (level.getHeight() - 1) * Tile.size;
			else if(mouseY < 0)
				mouseY = 0;
				
			int checkDistance = 8;
    		if(input.keys[KeyEvent.VK_CONTROL]) {
    			for(int x = -checkDistance; x < checkDistance; x++) {
    				if((x + xTile) >= (level.getWidth() - 1) || (x + xTile)< 0) continue;
    				for(int y = -checkDistance; y < checkDistance; y++) {
    					if((y + yTile) >= (level.getHeight() - 1) || (y + yTile) < 0) continue;
    					for(Entity entity : level.entityMap[xTile + x + (yTile + y) * level.getWidth()]) {
    						if(BB.pointIntersects(mouseX, mouseY, entity.getX(), entity.getWidth(), entity.getY(), entity.getHeight())) {
    	    					entity.setPosition(mouseX - entity.getWidth() / 2, mouseY - entity.getHeight() / 2);
    	    				}
    					}
    				}
    			}
    		} else {
    			System.out.println(mouseX + " " + mouseY);
    		}
    	}
    	
    	// Save the player coordinates in case the level is reset
    	customSpawnCoords[0] = player.getX();
		customSpawnCoords[1] = player.getY();
	}
	
	public void render(Screen screen) {
		if(renderDebug) {
			// Render collidables
	    	for(BB bb : level.collidables) {
	    		int x = (int) (bb.getX() - level.getCamera().getXO());
	    		int y = (int) (bb.getY() - level.getCamera().getYO());
	    		screen.drawRect(0xFFFFFF00, x, y, bb.getWidth(), bb.getHeight());
	    	}
	    	
			// Render useful information
	    	if(main != null)
	    		Font.drawStringColored("fps: " + main.getGame2D().getFPS(), 0xFF00FF00, 0, 0, screen);
	    	Font.drawStringColored("current level: " + Level.currentLevel, 0xFF00FF00, 0, Bitmaps.font[0].getHeight() + 1, screen);
		}
		
		
	}
}
