package com.senior.junkbot.entity;

import com.doobs.java2d.gfx.Screen;
import com.senior.junkbot.level.Level;

public class Entity {
    protected boolean onGround;

    protected double x, y;
    protected int width, height;
    
    protected Level level;
    protected boolean removed;
    protected int xSlot, ySlot;
    
    private boolean solid;
    
    public Entity() {
    	this(0, 0, null);
    }
    
    public Entity(double x, double y) {
    	this(x, y, null);
    }
    
    public Entity(double x, double y, Level level) {
    	this.x = x;
    	this.y = y;
    	this.level = level;
    	this.onGround = false;
    	this.width = 0;
    	this.height = 0;
    	this.removed = false;
    	this.solid = true;
    }
    
    protected void tick() {
    	
    }
    
    protected void render(Screen screen) {
    	
    }
    
    public void remove() {
        removed = true;
    }

    public void outOfBounds() {
        if (y < 0) return;
        remove();
    }
    
    // Getters and Setters
	public boolean isOnGround() {
		return onGround;
	}

	public void setOnGround(boolean onGround) {
		this.onGround = onGround;
	}
	
	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}
	
	public void setPosition(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	public boolean isRemoved() {
		return removed;
	}

	public void setRemoved(boolean removed) {
		this.removed = removed;
	}

	public int getXSlot() {
		return xSlot;
	}

	public void setXSlot(int xSlot) {
		this.xSlot = xSlot;
	}

	public int getYSlot() {
		return ySlot;
	}

	public void setYSlot(int ySlot) {
		this.ySlot = ySlot;
	}
	
	public boolean isSolid() {
		return solid;
	}
	
	public void setSolid(boolean solid) {
		this.solid = solid;
	}
}
