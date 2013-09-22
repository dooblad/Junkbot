package com.senior.junkbot.entity;

import com.doobs.java2d.gfx.Screen;
import com.doobs.java2d.input.InputHandler;
import com.senior.junkbot.level.Level;
import com.senior.junkbot.tile.Tile;
import com.senior.junkbot.util.BB;

public class Entity {
    public boolean onGround;

    public double xa, ya;
    public double x, y;
    public int w, h;
    
    public Level level;
    public boolean removed;
    public int xSlot, ySlot;
    
    public Entity() {
    	this(0, 0, null);
    }
    
    public Entity(int x, int y) {
    	this(x, y, null);
    }
    
    public Entity(int x, int y, Level level) {
    	this.x = x;
    	this.x = x;
    	this.level = level;
    	this.onGround = false;
    	this.xa = 0;
    	this.ya = 0;
    	this.w = -1;
    	this.h = -1;
    	this.removed = false;
    }
    
    public void tick(InputHandler input) {
    	
    }
    public void render(Screen screen) {
    	
    }

    public boolean tryMove(BB bb) {
    	boolean collided = false;
    	onGround = false;
    	double epsilon = 0.01;
    	
        if(level.isFree(this, bb, xa, 0)) {
        	x += xa;
        } else {
        	collided = true;
    		int tileIn = (int)(x / Tile.size);
        	if(xa > 0) {
        		x = (tileIn + Math.ceil((double) this.w / Tile.size)) * Tile.size - w - epsilon;
        		xa = 0;
        	} else {
        		x = tileIn * Tile.size + epsilon;
        		xa = 0;
        	}
        	if(level.isFree(this, bb, xa, 0)) {
        		x += xa;
        	}
        }
        
        if(level.isFree(this, bb, 0, ya)) {
        	y += ya;
        } else {
        	collided = true;
        	int tileIn = (int)(y / Tile.size);
        	if(ya > 0) {
        		onGround = true;
        		y = (tileIn + Math.ceil((double) this.h / Tile.size)) * Tile.size - h - epsilon;
        		ya = 0;
        	} else {
        		y = tileIn* Tile.size + epsilon;
        		ya = 0;
        	}
        	if(level.isFree(this, bb, 0, ya)) {
        		y += ya;
        	}
        }
        
        return collided;
    }

    protected void hitWall(double xa, double ya) {
    	
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
	
	public double getXA() {
		return xa;
	}

	public void setXA(double xa) {
		this.xa = xa;
	}

	public double getYA() {
		return ya;
	}

	public void setYA(double ya) {
		this.ya = ya;
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

	public int getW() {
		return w;
	}

	public void setW(int w) {
		this.w = w;
	}

	public int getH() {
		return h;
	}

	public void setH(int h) {
		this.h = h;
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

	public int getxSlot() {
		return xSlot;
	}

	public void setxSlot(int xSlot) {
		this.xSlot = xSlot;
	}

	public int getySlot() {
		return ySlot;
	}

	public void setySlot(int ySlot) {
		this.ySlot = ySlot;
	}
}
