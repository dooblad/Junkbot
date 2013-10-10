package com.senior.junkbot.entity.enemy;

import bitmaps.Bitmaps;

import com.doobs.java2d.gfx.Screen;
import com.doobs.java2d.input.InputHandler;
import com.senior.junkbot.entity.Entity;

public class CleanerBot extends Entity {
	public static final int DEFAULT_SPEED = 1;
	
	private int speed;
	private int turnCounter, turnTime;
	
	private boolean right;
	
	public CleanerBot() {
		this(0, 0, -1);
	}
	
	public CleanerBot(int x, int y) {
		this(x, y, -1);
	}
	
	public CleanerBot(int x, int y, int turnTime) {
		this(x, y, DEFAULT_SPEED, turnTime);
	}
	
	public CleanerBot(int x, int y, int speed, int turnTime) {
		super(x, y);
		this.right = true;
		this.width = Bitmaps.cleanerBot.getWidth();
		this.height = Bitmaps.cleanerBot.getHeight();
		this.speed = speed;
		this.turnTime = turnTime;
		this.turnCounter = 0;
	}
	
	public void tick(InputHandler input) {
		
		if(right) 
			xa = speed;
		else 
			xa = -speed;
		
		if((turnTime != -1 && ++turnCounter >= turnTime) || tryMove(this.width, this.height)) {
			right = !right;
			turnCounter = 0;
		}
	}
	
	public void render(Screen screen) {
		screen.draw(Bitmaps.cleanerBot, (int) (this.x - level.getCamera().getXO()), (int) (this.y - level.getCamera().getYO()));
	}
	
	// Getters and Setters
	public int getTurnTime() {
		return turnTime;
	}
	
	public void setTurnTime(int turnTime) {
		this.turnTime = turnTime;
	}
}
