package com.senior.junkbot.entity;

import java.awt.event.KeyEvent;

import bitmaps.Bitmaps;

import com.doobs.java2d.gfx.Screen;
import com.doobs.java2d.input.InputHandler;
import com.senior.junkbot.level.Level;

public class Player extends Entity {
	//ZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZ
	//FIX CAMERA SHIT AND HOW PLAYER ACCELERATION INTERACTS WITH IT
	//ZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZ
	private static final double ACCELERATION = 1.0;
	private static final double DECELERATION = 0.7;
	private static final double JUMP = 7.0;
	private static final double START_MASS = 2.5;
	
	public static int width, height;
	
	private double mass;
	
	public Player() {
		this(0, 0, null);
	}
	
	public Player(int x, int y) {
		this(x, y, null);
	}
	
	public Player(int x, int y, Level level) {
		super(x, y, level);
		super.width = Player.width;
		super.height = Player.height;
		mass = START_MASS;
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
		
		if(input.isKeyPressed(KeyEvent.VK_T))
			mass += 0.1;
		
		if(input.isKeyPressed(KeyEvent.VK_G))
			mass -= 0.1;
		
		this.ya += Level.GRAVITY * mass;
		
		tryMove(width, height);
		
		this.xa *= DECELERATION;
	}
	
	public void render(Screen screen) {
		screen.draw(Bitmaps.player, (int) (x - level.getCamera().getXO()), (int) (y - level.getCamera().getYO()));
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
