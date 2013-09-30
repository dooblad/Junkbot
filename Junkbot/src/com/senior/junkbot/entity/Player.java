package com.senior.junkbot.entity;

import java.awt.event.KeyEvent;

import bitmaps.Bitmaps;

import com.doobs.java2d.gfx.Screen;
import com.doobs.java2d.input.InputHandler;
import com.senior.junkbot.level.Level;
import com.senior.junkbot.util.BB;

public class Player extends Entity {
	private static final double DECELERATION = 0.7;
	private static final double JUMP = 17.0;
	
	public static int width, height;
	
	//ZZZZZZZZZZZZZZZZz
	private boolean colliding;
	//ZZZZZZZZZZZZZZZZz
	
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
		colliding = true;
	}
	
	public void tick(InputHandler input) {
		if(input.keys[KeyEvent.VK_A]) {
			this.xa--;
		} else if(input.keys[KeyEvent.VK_D]) {
			this.xa++;
		}
		
		if(input.keys[KeyEvent.VK_W] && onGround) {
			this.ya -= JUMP;
		} else if(input.keys[KeyEvent.VK_S]) {
			this.ya++;
		}
		
		this.ya += Level.GRAVITY;
		
		//ZZZZZZZZZZZZZZZZZZ
		if(input.isKeyPressed(KeyEvent.VK_V))
			colliding = !colliding;
		
		if(colliding) {
			tryMove(width, height);
		} else {
			this.x += this.xa;
			this.y += this.ya;
		}
		//ZZZZZZZZZZZZZZZZZZ
		
		
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
}
