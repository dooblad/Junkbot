package com.senior.junkbot.entity;

import bitmaps.Bitmaps;

import com.doobs.java2d.gfx.Screen;
import com.senior.junkbot.util.BB;

public class Jetpack extends Entity {
	private static final double THRUST = -0.45;
	private static final int DEFAULT_FUEL = 90;
	
	private MovingEntity entity;
	private boolean facingRight;
	private boolean active;
	
	private int fuel, maxFuel;
	
	public Jetpack() {
		this(0, 0, null);
	}
	
	public Jetpack(int x, int y) {
		this(x, y, null);
	}
	
	public Jetpack(MovingEntity entity) {
		this(0, 0, entity);
	}
	
	public Jetpack(int x, int y, MovingEntity entity) {
		super(x, y);
		this.width = Bitmaps.jetpack.getWidth();
		this.height = Bitmaps.jetpack.getHeight();
		this.entity = entity;
		this.facingRight = false;
		this.active = false;
		this.maxFuel = DEFAULT_FUEL;
		this.fuel = maxFuel; 
	}
	
	public void tick() {
		if(entity != null) {
			if(entity.getXA() > 0)
				facingRight = true;
			else if(entity.getXA() < 0)
				facingRight = false;
			
			if(facingRight)
				this.x = entity.getX() - this.width + 1;
			else
				this.x = entity.getX() + entity.getWidth();
			
			if(active && fuel > 0) {
				applyThrust();
				fuel--;
			} else if(fuel < maxFuel)
				fuel++;
			
			this.y = entity.getY() + (entity.getHeight() - this.height) / 2 + 1;
		}
		
	}
	
	public void render(Screen screen) {
		int xo = (int) (this.x - level.getCamera().getXO());
		int yo = (int) (this.y - level.getCamera().getYO());
		
		screen.draw(Bitmaps.jetpack, xo, yo);
		
		xo += 2;
		yo += 2;
		
		// Render fuel tank
		for(int i = 0; i < (int) (((double) fuel / maxFuel) * 9); i++) {
			int red = ((255 / 9) * (9 - i)) << 16;
			int green = ((255 / 9) * i) << 8;
			screen.drawPoint(0xFF000000 | red | green, xo, (yo + 8 - i));
		}
	}

	public boolean collideWithPlayer(Player player) {
		boolean collided = false;
    	
    	if(BB.intersects(player.getX() + player.getXA(), player.getWidth(), player.getY() + player.getYA(), player.getHeight(), this.x, this.width, this.y, this.height)) 
			collided = true;
    	
    	this.entity = player;
    	
    	return collided;
	}

	public void activate() {
		this.active = true;
	}
	
	public void deactivate() {
		this.active = false;
	}
	
	public void applyThrust() {
		entity.applyYA(THRUST);
	}
}
