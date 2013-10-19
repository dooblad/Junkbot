package com.senior.junkbot.entity;

import com.doobs.java2d.gfx.Screen;
import com.senior.junkbot.level.Level;
import com.senior.junkbot.util.BB;

public class Entity {
    protected boolean onGround;

    protected double xa, ya;
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
    	this.xa = 0;
    	this.ya = 0;
    	this.width = 0;
    	this.height = 0;
    	this.removed = false;
    	this.solid = true;
    }
    
    protected void tick() {
    	
    }
    
    protected void render(Screen screen) {
    	
    }
    
    protected boolean tryMove(int width, int height) {
    	boolean collided = false;
    	
    	for(BB bb : level.collidables) {
    		if(bb.intersects(this.x + this.xa, this.y, width, height)) {
    			if(this.xa > 0) {
    				this.x = bb.getX() - width;
    			} else if(this.xa < 0) {
    				this.x = bb.getX() + bb.getWidth();
    			}
    			this.xa = 0;
    			collided = true;
    		}
    		
    		if(bb.intersects(this.x, this.y + this.ya, width, height)) {
    			if(this.ya > 0) {
    				this.y = bb.getY() - height;
    				onGround = true;
    			} else if(this.ya < 0) {
    				this.y = bb.getY() + bb.getHeight() + 1;
    			}
    			this.ya = 0;
    			collided = true;
    		}
    	}
    	
    	this.x += this.xa;
    	this.y += this.ya;
    	
    	return collided;
    }
    
    protected boolean tryCollideWithEntity(Entity entity) {
    	boolean collided = false;
    	
    	if(BB.intersects(this.x, this.width, this.y + this.ya, this.height, entity.getX(), entity.getWidth(), entity.getY(), entity.getHeight())) {
			if(this.ya > 0) {
				this.y = entity.getY() - this.height;
			} else if(this.ya < 0) {
				this.y = entity.getY() + entity.getHeight();
			}
			this.ya = 0;
			onGround = true;
			collided = true;
		} else if(BB.intersects(this.x + this.xa, this.width, this.y, this.height, entity.getX(), entity.getWidth(), entity.getY(), entity.getHeight())) {
			if(this.xa > 0) {
				this.x = entity.getX() - this.width;
			} else if(this.xa < 0) {
				this.x = entity.getX() + entity.getWidth();
			}
			this.xa = 0;
			collided = true;
		}
		
		

    	return collided;
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
