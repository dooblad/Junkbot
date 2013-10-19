package com.senior.junkbot.entity;

import java.awt.event.KeyEvent;

import bitmaps.Bitmaps;

import com.doobs.java2d.gfx.Screen;
import com.doobs.java2d.input.InputHandler;
import com.senior.junkbot.level.Level;
import com.senior.junkbot.tile.Tile;
import com.senior.junkbot.util.BB;
import com.senior.junkbot.entity.neutral.CleanerBot;
import com.senior.junkbot.entity.neutral.WinPipe;
import com.senior.junkbot.entity.projectiles.TurretShot;

public class Player extends Entity {
	public static final double ACCELERATION = 1.0;
	public static final double DECELERATION = 0.7;
	public static final double JUMP = 7.0;
	public static final double START_MASS = 5.0;
	public static final double TERMINAL_VELOCITY = 10.0;
	
	private double mass;
	
	public Player() {
		this(0, 0, null);
	}
	
	public Player(int x, int y) {
		this(x, y, null);
	}
	
	public Player(int x, int y, Level level) {
		super(x, y, level);
		this.width = Bitmaps.player.getWidth();
		this.height = Bitmaps.player.getHeight();
		this.mass = START_MASS;
	}
	
	public void tick(InputHandler input) {
		if(input.keys[KeyEvent.VK_A]) {
			this.xa -= ACCELERATION;
		} else if(input.keys[KeyEvent.VK_D]) {
			this.xa += ACCELERATION;
		}
		
		if(input.keys[KeyEvent.VK_W]) {
			if(onGround)
				this.ya -= JUMP;
			else
				this.ya -= this.mass / 10;
		}
		
		this.ya += Level.GRAVITY * mass;
		
		if(this.ya > TERMINAL_VELOCITY) this.ya = TERMINAL_VELOCITY;
		
		onGround = false;
		
		tryMove();
		
		this.xa *= DECELERATION;
	}
	
	public void render(Screen screen) {
		screen.draw(Bitmaps.player, (int) (this.x - level.getCamera().getXO()), (int) (this.y - level.getCamera().getYO()));
	}
	
	private void tryMove() {
		boolean collided = false;
		
    	for(BB bb : level.collidables) {
    		if(Math.abs(this.xa) > Math.abs(this.ya)) {
	    		collided |= handleCollisionX(bb);
	    		collided |= handleCollisionY(bb);
    		} else {
    			collided |= handleCollisionY(bb);
	    		collided |= handleCollisionX(bb);
    		}
    		/*if(bb.intersects(this.x + this.xa, this.y, this.width, this.height)) {
    			if(this.x < bb.getX()) {
    				this.x = bb.getX() - this.width;
    			} else if(this.x > bb.getX() + bb.getWidth()) {
    				this.x = bb.getX() + bb.getWidth();
    			}
    			collided = true;
    			this.xa = 0;
    		}
    		
    		if(bb.intersects(this.x, this.y + this.ya, this.width, this.height)) {
    			if(this.y > bb.getY() + bb.getHeight()) {
    				this.y = bb.getY() + bb.getHeight();
    			} else if(this.y < bb.getY()) {
    				this.y = bb.getY() - this.height;
    				onGround = true;
    			}
    			collided = true;
    			this.ya = 0;
    		}*/
    	}
    	
		collideWithEntities(collided);
    	
    	this.x += this.xa;
    	this.y += this.ya;
	}
	
	public boolean handleCollisionX(BB bb) {
		if(bb.intersects(this.x + this.xa, this.y, this.width, this.height)) {
			if(this.x < bb.getX()) {
				this.x = bb.getX() - this.width;
			} else if(this.x > bb.getX() + bb.getWidth()) {
				this.x = bb.getX() + bb.getWidth();
			}
			this.xa = 0;
			return true;
		}
		
		return false;
	}
	
	public boolean handleCollisionY(BB bb) {
		if(bb.intersects(this.x, this.y + this.ya, this.width, this.height)) {
			if(this.y < bb.getY()) {
				this.y = bb.getY() - this.height;
				onGround = true;
			} else if(this.y > (bb.getY() + bb.getHeight())) {
				this.y = bb.getY() + bb.getHeight();
			}

			this.ya = 0;
			return true;
		}
		
		return false;
	}
	
	public void collideWithEntities(boolean playerColliding) {
		int distanceCheck = 3;
		for(int x = (int)(this.x / Tile.size - distanceCheck); x < (int) ((this.x + this.width) / Tile.size + distanceCheck); x++) {
			if(x < 0 || x >= level.getWidth()) continue;
			for(int y = (int)(this.y) / Tile.size - distanceCheck; y < (int)(this.y + this.width) / Tile.size + distanceCheck; y++) {
				if(y < 0 || y >= level.getHeight()) continue;
				for(Entity entity: level.getEntityMap()[x + y * level.getWidth()]) {
					if(entity instanceof CleanerBot && ((CleanerBot) entity).collideWithPlayer(this, playerColliding)) {
						this.x += entity.getXA();
					} else if(entity instanceof WinPipe && ((WinPipe) entity).collideWithPlayer(this)) {
						level.nextLevel();
					} else if(entity instanceof TurretShot && ((TurretShot) entity).collideWithPlayer(this)) {
						level.resetLevel();
					}
				}
			}
		}
	}
	
	public void respawn() {
		this.xa = 0;
		this.ya = 0;
		this.x = level.getXSpawn();
		this.y = level.getYSpawn();
	}
	
	// Getters and Setters
	public double getMass() {
		return mass;
	}
	
	public void setMass(double mass) {
		this.mass = mass;
	}
}
