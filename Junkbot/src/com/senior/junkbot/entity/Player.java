package com.senior.junkbot.entity;

import java.awt.event.KeyEvent;

import sound.Sounds;

import bitmaps.Bitmaps;

import com.doobs.java2d.gfx.Screen;
import com.doobs.java2d.input.InputHandler;
import com.senior.junkbot.level.Level;
import com.senior.junkbot.tile.Tile;
import com.senior.junkbot.util.BB;
import com.senior.junkbot.entity.neutral.CleanerBot;
import com.senior.junkbot.entity.neutral.WinPipe;
import com.senior.junkbot.entity.projectiles.TurretShot;

public class Player extends MovingEntity {
	public static final double ACCELERATION = 1.0;
	public static final double DECELERATION = 0.7;
	public static final double JUMP = 7.0;
	public static final double START_MASS = 5.0;
	public static final double TERMINAL_VELOCITY = 10.0;
	
	private Jetpack jetpack;
	
	private boolean collidedX, collidedY;
	
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
		this.jetpack = null;
		this.collidedX = false;
		this.collidedY = false;
		this.mass = START_MASS;
	}
	
	public void tick(InputHandler input) {
		if(input.keys[KeyEvent.VK_A]) {
			this.xa -= ACCELERATION;
		} else if(input.keys[KeyEvent.VK_D]) {
			this.xa += ACCELERATION;
		}
		
		boolean jumped = false;
		if(input.keys[KeyEvent.VK_W]) {
			if(onGround) {
				this.ya -= JUMP;
				jumped = true;
			} else
				this.ya -= this.mass / 10;
		}
		
		this.ya += Level.GRAVITY * mass;
		
		if(jetpack != null) { 
			if(input.keys[KeyEvent.VK_SPACE])
				jetpack.activate();
			else
				jetpack.deactivate();
		}
		
		if(this.ya > TERMINAL_VELOCITY) this.ya = TERMINAL_VELOCITY;
		
		boolean wasCollidingY = collidedY;
		onGround = false;
		collidedX = false;
		collidedY = false;
		tryMove();
		
		// This set of variables is to prevent the jump noise from being spammed while attached to a CleanerBot
		if(jumped && wasCollidingY)
			Sounds.jump.play();
		
		this.xa *= DECELERATION;
	}
	
	public void render(Screen screen) {
		if(this.xa > 0) 
			screen.draw(Bitmaps.player, (int) (this.x - level.getCamera().getXO()), (int) (this.y - level.getCamera().getYO()));
		else 
			screen.drawFlipped(Bitmaps.player, (int) (this.x - level.getCamera().getXO()), (int) (this.y - level.getCamera().getYO()), (byte) 0x10);
	}
	
	private void tryMove() {
		
    	for(BB bb : level.collidables) {
    		if(Math.abs(this.xa) > Math.abs(this.ya)) {
	    		this.collidedX |= handleCollisionX(bb);
	    		this.collidedY |= handleCollisionY(bb);
    		} else {
    			this.collidedY |= handleCollisionY(bb);
	    		this.collidedX |= handleCollisionX(bb);
    		}
    	}
    	
		collideWithEntities(this.collidedX, this.collidedY);
    	
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
	
	public void collideWithEntities(boolean collidedX, boolean collidedY) {
		int distanceCheck = 3;
		for(int x = (int)(this.x / Tile.size - distanceCheck); x < (int) ((this.x + this.width) / Tile.size + distanceCheck); x++) {
			if(x < 0 || x >= level.getWidth()) continue;
			for(int y = (int)(this.y) / Tile.size - distanceCheck; y < (int)(this.y + this.width) / Tile.size + distanceCheck; y++) {
				if(y < 0 || y >= level.getHeight()) continue;
				for(Entity entity: level.getEntityMap()[x + y * level.getWidth()]) {
					if(entity instanceof CleanerBot && ((CleanerBot) entity).collideWithPlayer(this, collidedX, collidedY)) {
						this.x += ((MovingEntity) entity).getXA();
					} else if(entity instanceof TurretShot && ((TurretShot) entity).collideWithPlayer(this)) {
						level.resetLevel();
					} else if(entity instanceof WinPipe && ((WinPipe) entity).collideWithPlayer(this)) {
						level.nextLevel();
					} else if(entity instanceof Jetpack && ((Jetpack) entity).collideWithPlayer(this)) {
						this.jetpack = (Jetpack) entity;
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
	public void setJetpack(Jetpack jetpack) {
		this.jetpack = jetpack;
	}
	
	public double getMass() {
		return mass;
	}
	
	public void setMass(double mass) {
		this.mass = mass;
	}
}
