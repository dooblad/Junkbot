package com.senior.junkbot.entity;

import java.awt.event.KeyEvent;

import bitmaps.Bitmaps;

import com.doobs.java2d.gfx.Screen;
import com.doobs.java2d.input.InputHandler;
import com.senior.junkbot.level.Level;
import com.senior.junkbot.tile.Tile;
import com.senior.junkbot.entity.enemy.CleanerBot;

public class Player extends Entity {
	public static final double ACCELERATION = 1.0;
	public static final double DECELERATION = 0.7;
	public static final double JUMP = 7.0;
	public static final double START_MASS = 2.5;
	
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
		
		if(input.keys[KeyEvent.VK_W] && onGround) {
			this.ya -= JUMP;
		}
		
		this.ya += Level.GRAVITY * mass;
		
		onGround = false;
		
		int distanceCheck = 3;
		for(int x = (int)(this.x / Tile.size - distanceCheck); x < (int) ((this.x + this.width) / Tile.size + distanceCheck); x++) {
			if(x < 0 || x >= level.getWidth()) continue;
			for(int y = (int)(this.y) / Tile.size - distanceCheck; y < (int)(this.y + this.width) / Tile.size + distanceCheck; y++) {
				if(y < 0 || y >= level.getHeight()) continue;
				for(Entity entity: level.getEntityMap()[x + y * level.getWidth()]) {
					if(entity.isSolid() && !(entity instanceof Player) && tryCollideWithEntity(entity)) {
						if(entity instanceof CleanerBot) {
							//System.out.println(this.xa);
							//this.xa = entity.getXA();
							this.x += entity.getXA();
						} else if(entity instanceof WinPipe) {
							level.nextLevel();
						}
					}
				}
			}
		}
		
		tryMove(width, height);
		
		this.xa *= DECELERATION;
	}
	
	public void render(Screen screen) {
		screen.draw(Bitmaps.player, (int) (this.x - level.getCamera().getXO()), (int) (this.y - level.getCamera().getYO()));
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
