package com.senior.junkbot.util;

import java.awt.event.KeyEvent;

import com.doobs.java2d.gfx.Screen;
import com.doobs.java2d.input.InputHandler;
import com.senior.junkbot.Main;
import com.senior.junkbot.entity.Player;
import com.senior.junkbot.level.Level;

public class LevelDebugger {
	private static final int FLYING_ACCELERATION = 3;
	
	private Level level;
	private Player player;
	
	private boolean playerFlying;
    private double[] customSpawnCoords;

	public LevelDebugger() {
		this(null, null);
	}
	
	public LevelDebugger(Level level, Player player) {
		this.level = level;
		this.player = player;
		customSpawnCoords = new double[2];
		playerFlying = false;
		
		customSpawnCoords[0] = level.getXSpawn();
		customSpawnCoords[1] = level.getYSpawn();
	}
	
	public void tick(InputHandler input) {
		
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
    		if(input.isKeyPressed(KeyEvent.VK_LEFT))
    			level.previousLevel();
    		else if(input.isKeyPressed(KeyEvent.VK_RIGHT))
    			level.nextLevel();
    		
    		if(input.isKeyPressed(KeyEvent.VK_T)) {
    			player.setX(input.getMouseX() / Main.SCALE + level.getCamera().getXO());
    			player.setY(input.getMouseY() / Main.SCALE + level.getCamera().getYO());
    		}
    		
    		if(input.isKeyPressed(KeyEvent.VK_F)) 
    			playerFlying = !playerFlying;
    	}
    	
    	// Gives the position of the mouse in the level's coordinate space
    	if(input.isLeftMousePressed()) {
    		int mouseX = (int) (input.getMouseX() / Main.SCALE + level.getCamera().getXO());
    		int mouseY = (int) (input.getMouseY() / Main.SCALE + level.getCamera().getYO());
    		System.out.println(mouseX + " " + mouseY);
    	}
    	
    	// Player mass increase/decrease
    	if(input.isKeyPressed(KeyEvent.VK_Y))
			player.setMass(player.getMass() + 0.2);
    	else if(input.isKeyPressed(KeyEvent.VK_H))
    		player.setMass(player.getMass() - 0.2);
    	
    	// Save the player coordinates in case the level is reset
    	customSpawnCoords[0] = player.getX();
		customSpawnCoords[1] = player.getY();
	}
	
	public void render(Screen screen) {
		// Render collidables
    	for(BB bb : level.collidables) {
    		int x = (int) (bb.getX() - level.getCamera().getXO());
    		int y = (int) (bb.getY() - level.getCamera().getYO());
    		screen.drawRect(0xFFFFFF00, x, y, bb.getWidth(), bb.getHeight());
    	}
	}
	
	// Getters and Setters
	private Level getLevel() {
		return level;
	}
	
	private void setLevel(Level level) {
		this.level = level;
	}
}
