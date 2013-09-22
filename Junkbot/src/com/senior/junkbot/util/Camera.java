package com.senior.junkbot.util;

import com.doobs.java2d.input.InputHandler;
import com.senior.junkbot.entity.Entity;

public class Camera {
	private Entity entity;
	private double xo, yo;
	
	public Camera() {
		xo = 0;
		yo = 0;
	}
	
	public void tick(InputHandler input) {
		
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
}
