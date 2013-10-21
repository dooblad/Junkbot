package com.senior.junkbot.entity.neutral;

import bitmaps.Bitmaps;

import com.doobs.java2d.gfx.Screen;
import com.senior.junkbot.entity.Entity;
import com.senior.junkbot.entity.Particle;
import com.senior.junkbot.entity.Player;
import com.senior.junkbot.util.BB;

public class CleanerBot extends Entity {
	public static final double DEFAULT_SPEED = 1.0;
	public static final int DEFAULT_TURN_TIME = -1;
	
	private int turnCounter, turnTime;
	
	public CleanerBot() {
		this(0, 0, DEFAULT_SPEED, 0, DEFAULT_TURN_TIME);
	}
	
	public CleanerBot(int x, int y) {
		this(x, y, DEFAULT_SPEED, 0, DEFAULT_TURN_TIME);
	}
	
	public CleanerBot(int x, int y, double xa, double ya) {
		this(x, y, xa, ya, DEFAULT_TURN_TIME);
	}
	
	public CleanerBot(int x, int y, double xa, double ya, int turnTime) {
		super(x, y);
		this.width = Bitmaps.cleanerBot.getWidth();
		this.height = Bitmaps.cleanerBot.getHeight();
		this.xa = xa;
		this.ya = ya;
		this.turnTime = turnTime;
		this.turnCounter = 0;
	}
	
	public void tick() {
		if(turnTime != -1 && ++turnCounter >= turnTime) {
			this.xa = -xa;
			this.ya = -ya;
			turnCounter = 0;
		}
		
		spawnParticles();
			
		tryMove();
	}
	
	public void render(Screen screen) {
		screen.draw(Bitmaps.cleanerBot, (int) (this.x - level.getCamera().getXO()), (int) (this.y - level.getCamera().getYO()));
	}
	
	private void spawnParticles() {
		double tempXA = (-this.xa + (this.xa < 0 ? Math.random() : -Math.random())) / 4;
		double tempYA = (-this.ya + (this.ya < 0 ? Math.random() : -Math.random())) / 4;
		
		int openingLength = 5;
		int particleLife = (int) (Math.random() * 20 + 10);
		int xo = 0;
		int yo = 0;
		
		if(Math.abs(this.xa) > Math.abs(this.ya)) {
			if(this.xa < 0) {
				xo = this.width;
			}
			tempYA = Math.random() - Math.random();
			yo = (int)(Math.random() * openingLength) + (this.height - openingLength) / 2;
		} else {
			if(this.ya < 0) {
				yo = this.height;
			}
			tempXA = Math.random() - Math.random();
			xo = (int)(Math.random() * openingLength) + (this.width - openingLength) / 2;
		}
		
		level.add(new Particle(this.x + xo, this.y + yo, tempXA, tempYA, 0xFF00FF00, particleLife));
	}
	
	private void tryMove() {
    	for(BB bb : level.collidables) {
    		if(bb.intersects(this.x + this.xa, this.y, width, height)) {
    			if(this.xa > 0) {
    				this.x = bb.getX() - width;
    			} else if(this.xa < 0) {
    				this.x = bb.getX() + bb.getWidth();
    			}
    			this.xa = -xa;
    		}
    		
    		if(bb.intersects(this.x, this.y + this.ya, width, height)) {
    			if(this.ya > 0) {
    				this.y = bb.getY() - height;
    			} else if(this.ya < 0) {
    				this.y = bb.getY() + bb.getHeight() + 1;
    			}
    			this.ya = -ya;
    		}
    	}
    	
    	this.x += this.xa;
    	this.y += this.ya;
    }
	
	public boolean collideWithPlayer(Player player, boolean collidedX, boolean collidedY) {
		boolean collided = false;
    	
    	if(BB.intersects(player.getX(), player.getWidth(), player.getY() + player.getYA(), player.getHeight(), this.x, this.width, this.y, this.height)) {
			if(collidedY) {
				this.ya = -ya;
			} else {
	    		if(player.getY() > this.y + this.height) {
					player.setY(this.y + this.height + 1);
				} else if(player.getY() < this.y) {
					player.setY(this.y - player.getHeight());
				}
				player.setYA(0);
				player.setOnGround(true);
			}
			collided = true;
		} else if(BB.intersects(player.getX() + player.getXA(), player.getWidth(), player.getY(), player.getHeight(), this.x, this.width, this.y, this.height)) {
			if(collidedX) {
				this.xa = -xa;
			} else {
				if(player.getX() > this.x + this.width) {
					player.setX(this.x + this.width);
				} else if(player.getX() < this.x) {
					player.setX(this.x - player.getWidth());
				}
				player.setXA(0);
			}
			collided = true;
		}

    	return collided;
	}
	
	// Getters and Setters
	public int getTurnTime() {
		return turnTime;
	}
	
	public void setTurnTime(int turnTime) {
		this.turnTime = turnTime;
	}
}
