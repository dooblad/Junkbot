package com.senior.junkbot.util;

import java.awt.event.KeyEvent;

import com.doobs.java2d.input.InputHandler;
import com.senior.junkbot.Main;
import com.senior.junkbot.entity.Entity;

public class Camera {
	private static final double ACCELERATION_FACTOR = 2.5;
	private static final double DECELERATION_FACTOR = 0.7;
	
	private Entity entity;
	private double xo, yo;
	private double xa, ya;
	private boolean locked;
	
	public Camera() {
		xo = 0;
		yo = 0;
		xa = 0;
		ya = 0;
		locked = true;
	}
	
	public void tick(InputHandler input) {
		if(input.isKeyPressed(KeyEvent.VK_Y)) {
			locked = !locked;
			xa = 0;
			ya = 0;
		}
		
		if(!locked) {
			if(input.keys[KeyEvent.VK_UP])
				ya -= ACCELERATION_FACTOR;
			else if(input.keys[KeyEvent.VK_DOWN])
				ya += ACCELERATION_FACTOR;
			
			if(input.keys[KeyEvent.VK_LEFT])
				xa -= ACCELERATION_FACTOR;
			else if(input.keys[KeyEvent.VK_RIGHT])
				xa += ACCELERATION_FACTOR;
			
			xo += xa;
			yo += ya;
			
			xa *= DECELERATION_FACTOR;
			ya *= DECELERATION_FACTOR;
		} else {
			//xo = entity.getX() - (Main.WIDTH + entity.getWidth()) / 2;
			//yo = entity.getY() - (Main.HEIGHT + entity.getHeight()) / 2;
			if(xa > 1.0 || xa < -1.0)
				xa += entity.getXA() * entity.getXA() * entity.getXA();
			else
				xa -= 1;
			
			int limit = Main.WIDTH / 4;
			if(xa > limit + entity.getWidth() * 1.5) xa = limit + entity.getWidth() * 1.5;
			if(xa < -(limit)) xa = -(limit);
			xo = (entity.getX() - (Main.WIDTH + entity.getWidth()) / 2) + xa;
			yo = (entity.getY() - (Main.HEIGHT + entity.getHeight()) / 2);
		}
	}
	
	public void addEntity(Entity entity) {
		this.entity = entity;
	}
	
	// Getters and Setters
	public Entity getEntity() {
		return entity;
	}
	
	public void setEntity(Entity entity) {
		this.entity = entity;
	}
	
	public double getXO() {
		return xo;
	}
	
	public void setXO(double xo) {
		this.xo = xo;
	}
	
	public double getYO() {
		return yo;
	}
	
	public void setYO(double yo) {
		this.yo = yo;
	}
	
	public boolean isLocked() {
		return locked;
	}
	
	public void setLocked(boolean locked) {
		this.locked = locked;
	}
}
