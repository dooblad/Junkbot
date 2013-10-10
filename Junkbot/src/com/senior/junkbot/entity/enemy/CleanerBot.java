package com.senior.junkbot.entity.enemy;

import bitmaps.Bitmaps;

import com.doobs.java2d.gfx.Screen;
import com.doobs.java2d.input.InputHandler;
import com.senior.junkbot.entity.Entity;
import com.senior.junkbot.entity.Player;
import com.senior.junkbot.util.BB;

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
	
	public boolean collideWithPlayer(Player player) {
		boolean collided = false;
    	
    	if(BB.intersects(player.getX(), player.getWidth(), player.getY() + player.getYA(), player.getHeight(), this.x, this.width, this.y, this.height)) {
			if(player.getYA() > 0) {
				player.setY(this.getY() - player.getHeight());
			} else if(player.getYA() < 0) {
				player.setY(this.y + this.height);
			}
			player.setYA(0);
			player.setOnGround(true);
			collided = true;
		} else if(BB.intersects(player.getX() + player.getXA(), player.getWidth(), player.getY(), player.getHeight(), this.x, this.width, this.y, this.height)) {
			if(player.getXA() > 0) {
				player.setX(this.getX() - player.getWidth());
			} else if(player.getXA() < 0) {
				player.setX(this.getX() + this.getWidth());
			}
			player.setXA(0);
			collided = true;
		}

    	return collided;
	}
	
	public void switchDirection() {
		right = !right;
	}
	
	// Getters and Setters
	public int getTurnTime() {
		return turnTime;
	}
	
	public void setTurnTime(int turnTime) {
		this.turnTime = turnTime;
	}
}
