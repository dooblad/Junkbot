package com.senior.junkbot.entity;

import bitmaps.Bitmaps;

import com.doobs.java2d.gfx.Screen;
import com.doobs.java2d.input.InputHandler;
import com.senior.junkbot.level.Level;
import com.senior.junkbot.tile.Tile;
import com.senior.junkbot.util.BB;

public class Entity {
    protected boolean onGround;

    protected double xa, ya;
    protected double x, y;
    protected int width, height;
    
    protected Level level;
    protected boolean removed;
    protected int xSlot, ySlot;
    
    public Entity() {
    	this(0, 0, null);
    }
    
    public Entity(int x, int y) {
    	this(x, y, null);
    }
    
    public Entity(int x, int y, Level level) {
    	this.x = x;
    	this.y = y;
    	this.level = level;
    	this.onGround = false;
    	this.xa = 0;
    	this.ya = 0;
    	this.width = 0;
    	this.height = 0;
    	this.removed = false;
    }
    
    public static void init() {
    	Player.width = Bitmaps.player.getWidth();
    	Player.height = Bitmaps.player.getHeight();
    }
    
    public void tick(InputHandler input) {
    	
    }
    public void render(Screen screen) {
    	
    }
    
    protected boolean tryMove(int width, int height) {
    	boolean collided = false;
    	onGround = false;
    	
    	for(BB bb : level.collidables) {
    		if(bb.intersects(this.x + this.xa, this.y, width, height))
    			this.xa = 0;
    		if(bb.intersects(this.x, this.y + this.ya, width, height)) {
    			if(this.ya > 0)
    				onGround = true;
    			this.ya = 0;
    		}
    	}
    	
    	this.x += this.xa;
    	this.y += this.ya;
    	
    	return collided;
    }

    /*protected boolean tryMove(BB bb) {
    	boolean collided = false;
    	onGround = false;
    	
        if(level.isFree(this, bb, xa, 0)) {
        	x += xa;
        } else {
        	collided = true;
    		int tileIn = (int)(x / Tile.size);
        	if(xa > 0) {
        		x = (tileIn + Math.ceil((double) this.w / Tile.size)) * Tile.size - w;
        		xa = 0;
        	} else {
        		x = tileIn * Tile.size;
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
        		y = (tileIn + Math.ceil((double) this.h / Tile.size)) * Tile.size - h;
        		ya = 0;
        	} else {
        		y = tileIn* Tile.size;
        		ya = 0;
        	}
        	if(level.isFree(this, bb, 0, ya)) {
        		y += ya;
        	}
        }
        
        return collided;
    }*/
    
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
}
