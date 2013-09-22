package com.senior.junkbot.entity;

import java.awt.event.KeyEvent;

import bitmaps.Bitmaps;

import com.doobs.java2d.gfx.Screen;
import com.doobs.java2d.input.InputHandler;
import com.senior.junkbot.level.Level;
import com.senior.junkbot.util.BB;

public class Player extends Entity {
	public static final BB BB = new BB(10, 10);
	private static final double DECELERATION = 0.7;
	
	public Player() {
		super(0, 0);
	}
	
	public Player(int x, int y) {
		super(x, y, null);
	}
	
	public Player(int x, int y, Level level) {
		super(x, y, level);
	}
	
	public void tick(InputHandler input) {
		if(input.keys[KeyEvent.VK_A]) {
			xa--;
		} else if(input.keys[KeyEvent.VK_D]) {
			xa++;
		}
		
		if(input.keys[KeyEvent.VK_W]) {
			ya--;
		} else if(input.keys[KeyEvent.VK_S]) {
			ya++;
		}
		
		this.x += this.xa;
		this.y += this.ya;
		
		this.xa *= DECELERATION;
		this.ya *= DECELERATION;
	}
	
	public void render(Screen screen) {
		screen.draw(Bitmaps.player, (int) (x - level.getCamera().getXO()), (int) (y - level.getCamera().getYO()));
	}
	
	public void respawn() {
		xa = 0;
		ya = 0;
		x = level.getXSpawn();
		y = level.getYSpawn();
	}
	
	// Getters and Setters
}
