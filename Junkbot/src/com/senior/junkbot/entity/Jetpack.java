package com.senior.junkbot.entity;

import bitmaps.Bitmaps;

import com.doobs.java2d.gfx.Screen;
import com.senior.junkbot.util.BB;

public class Jetpack extends Entity{
	private Entity entity;
	private boolean facingRight;
	
	public Jetpack() {
		this(0, 0, null);
	}
	
	public Jetpack(int x, int y) {
		this(x, y, null);
	}
	
	public Jetpack(Entity entity) {
		this(0, 0, entity);
	}
	
	public Jetpack(int x, int y, Entity entity) {
		super(x, y);
		this.width = Bitmaps.jetpack.getWidth();
		this.height = Bitmaps.jetpack.getHeight();
		this.entity = entity;
		facingRight = false;
	}
	
	public void tick() {
		if(entity != null) {
			if(entity.getXA() > 0) {
				this.x = entity.getX() - this.width + 1;
				facingRight = true;
			} else if(entity.getXA() < 0){
				this.x = entity.getX() + entity.getWidth();
				facingRight = false;
			}
			
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
		for(int i = 0; i < 9; i++) {
			int red = ((255 / 9) * i) << 16;
			int green = ((255 / 9) * (9 - i)) << 8;
			screen.drawPoint(0xFF000000 | red | green, xo, yo + i);
		}
	}

	public boolean collideWithPlayer(Player player) {
		boolean collided = false;
    	
    	if(BB.intersects(player.getX() + player.getXA(), player.getWidth(), player.getY() + player.getYA(), player.getHeight(), this.x, this.width, this.y, this.height)) 
			collided = true;
    	
    	this.entity = player;
    	
    	return collided;
	}
}
