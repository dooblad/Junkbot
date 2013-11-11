package com.senior.junkbot.util;

import java.awt.event.KeyEvent;

import com.doobs.java2d.input.InputHandler;
import com.senior.junkbot.entity.Entity;
import com.senior.junkbot.entity.MovingEntity;

import config.Config;

public class Camera {
	private static final double ACCELERATION_FACTOR = 2.5;
	private static final double DECELERATION_FACTOR = 0.7;
	
	private MovingEntity entity;
	private double xo, yo;
	private double xa, ya;
	private boolean locked;
	
	private int movingCounter;
	
	public Camera() {
		xo = 0;
		yo = 0;
		xa = 0;
		ya = 0;
		locked = true;
		movingCounter = 0;
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
			if(entity.getXA() > 0.01 || entity.getXA() < -0.01) {
				if(++movingCounter > 60)
				this.xa += entity.getXA() * 0.5;
			} else {
				this.xa *= 0.7;
				movingCounter = 0;
			}
			
			if((entity.getXA() > 0 && this.xa < 0) || (entity.getXA() < 0 && this.xa > 0))
				this.xa *= 0.5;
			
			int limit = Config.width / 4;
			if(this.xa > limit + entity.getWidth() * 1.5) 
				this.xa = limit + entity.getWidth() * 1.5;
			if(this.xa < -(limit)) 
				this.xa = -(limit);
			this.xo = (entity.getX() - (Config.width + entity.getWidth()) / 2) + this.xa;
			this.yo = (entity.getY() - (Config.height + entity.getHeight()) / 2);
		}
	}
	
	public void centerOnEntity() {
		this.xo = (entity.getX() - (Config.width + entity.getWidth()) / 2);
		this.yo = (entity.getY() - (Config.height + entity.getHeight()) / 2);
	}
	
	public void addEntity(MovingEntity entity) {
		this.entity = entity;
	}
	
	// Getters and Setters
	public Entity getEntity() {
		return entity;
	}
	
	public void setEntity(MovingEntity entity) {
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
