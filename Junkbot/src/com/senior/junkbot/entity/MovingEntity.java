package com.senior.junkbot.entity;

import com.senior.junkbot.level.Level;
import com.senior.junkbot.util.BB;

public class MovingEntity extends Entity {
    protected double xa, ya;
    
    public MovingEntity() {
    	this(0, 0, null);
    }
    
    public MovingEntity(double x, double y) {
    	this(x, y, null);
    }
    
    public MovingEntity(double x, double y, Level level) {
    	this(x, y, 0, 0, level);
    }
    
    public MovingEntity(double x, double y, double xa, double ya) {
    	this(x, y, xa, ya, null);
    }
    
    public MovingEntity(double x, double y, double xa, double ya, Level level) {
    	super(x, y, level);
    	this.xa = xa;
    	this.ya = ya;
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

    // Getters and setters
    public double getXA() {
		return xa;
	}

	public void setXA(double xa) {
		this.xa = xa;
	}
	
	public void applyXA(double xa) {
		this.xa += xa;
	}

	public double getYA() {
		return ya;
	}

	public void setYA(double ya) {
		this.ya = ya;
	}
	
	public void applyYA(double ya) {
		this.ya += ya;
	}

}
