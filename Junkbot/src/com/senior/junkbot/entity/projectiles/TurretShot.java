package com.senior.junkbot.entity.projectiles;

import bitmaps.Bitmaps;

import com.doobs.java2d.gfx.Screen;
import com.senior.junkbot.entity.MovingEntity;
import com.senior.junkbot.entity.Player;
import com.senior.junkbot.util.BB;

public class TurretShot extends MovingEntity {
	public static int DEFAULT_LIFE = 360;
	
	private int currentLife, life;
	
	public TurretShot() {
		this(0, 0, 0, 0);
	}
	
	public TurretShot(int x, int y, double xa, double ya) {
		this(x, y, xa, ya, DEFAULT_LIFE);
	}
	
	public TurretShot(int x, int y, double xa, double ya, int life) {
		super(x, y);
		this.xa = xa;
		this.ya = ya;
		this.life = life;
		this.currentLife = 0;
		this.width = Bitmaps.turretShot.getWidth();
		this.height = Bitmaps.turretShot.getHeight();
	}
	
	public void tick() {
		if(++currentLife > life || tryMove(this.width, this.height))
			this.remove();
	}
	
	public void render(Screen screen)  {
		screen.draw(Bitmaps.turretShot, (int) (this.x - level.getCamera().getXO()), (int) (this.y - level.getCamera().getYO()));
	}
	
	 protected boolean tryMove() {
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
	
	public boolean collideWithPlayer(Player player) {
		boolean collided = false;

    	if(BB.intersects(player.getX(), player.getWidth(), player.getY() + player.getYA(), player.getHeight(), this.x , this.width , this.y , this.height )) {
			if(player.getYA() > 0) {
				player.setY(this.getY() - player.getHeight());
			} else if(player.getYA() < 0) {
				player.setY(this.y + this.height);
			}
			player.setYA(0);
			player.setOnGround(true);
			collided = true;
		} else if(BB.intersects(player.getX() + player.getXA(), player.getWidth(), player.getY(), player.getHeight(), this.x , this.width , this.y , this.height )) {
			if(player.getXA() > 0) {
				player.setX(this.getX() - player.getWidth());
			} else if(player.getXA() < 0) {
				player.setX(this.getX() + this.getWidth());
			}
			player.setXA(0);
			collided = true;
		}

    	return collided;
	}
}
