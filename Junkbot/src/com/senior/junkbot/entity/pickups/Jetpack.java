package com.senior.junkbot.entity.pickups;

import java.awt.event.KeyEvent;

import sound.Sounds;
import bitmaps.Bitmaps;

import com.doobs.java2d.gfx.Screen;
import com.doobs.java2d.input.InputHandler;
import com.senior.junkbot.entity.Entity;
import com.senior.junkbot.entity.MovingEntity;
import com.senior.junkbot.entity.Player;
import com.senior.junkbot.entity.particles.Particle;
import com.senior.junkbot.level.Level;
import com.senior.junkbot.util.BB;

import config.Config;

public class Jetpack extends Entity {
	private static final double THRUST = -3.0;
	private static final int DEFAULT_FUEL = 90;
	
	private MovingEntity entity;
	private boolean facingRight;
	private boolean active;
	
	private int fuel, maxFuel;
	private int soundTimer;
	
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
		this.soundTimer = 0;
	}
	
	public void tick(InputHandler input) {
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
				applyThrust(input);
				fuel--;
				if(soundTimer++ % 10 == 0)
					Sounds.jetpack.play();
				for(int i = 0; i < 3; i++) {
					spawnParticles();
				}
			} else if(!input.keys[KeyEvent.VK_SPACE]){
				if(fuel < maxFuel)
					fuel++;
				soundTimer = 0;
				Sounds.jetpack.stop();
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
		for(int i = 0; i < (int) (((double) fuel / maxFuel) * 9); i++) {
			int red = ((255 / 9) * (9 - i)) << 16;
			int green = ((255 / 9) * i) << 8;
			screen.drawPoint(0xFF000000 | red | green, xo, (yo + 8 - i));
		}
	}
	
	public void spawnParticles() {
		int color = 0xFFFF0000 | (int) (Math.random() * 255) << 8;
		if(Config.particles)
			level.add(new Particle(this.x + this.width / 2, this.y + this.height, Math.random() - Math.random(), Math.random() * 2, color, 30));
	}

	public boolean collideWithPlayer(Player player) {
		boolean collided = false;
    	
    	if(BB.intersects(player.getX(), player.getWidth(), player.getY(), player.getHeight(), this.x, this.width, this.y, this.height)) {
			collided = true;
			this.entity = player;
    	}
    	
    	
    	return collided;
	}

	public void activate() {
		this.active = true;
	}
	
	public void deactivate() {
		this.active = false;
	}
	
	public void applyThrust(InputHandler input) {
		if(entity instanceof Player) {
			Player player = (Player) entity;
			if(input.keys[KeyEvent.VK_W])
				entity.setYA(THRUST - Level.GRAVITY * player.getMass());
			else if(input.keys[KeyEvent.VK_S])
				entity.setYA(-THRUST);
			else
				entity.setYA(-Level.GRAVITY * player.getMass());
			//entity.applyYA(THRUST);
		}
	}
	
	// Getters and setters
	public boolean hasEntity() {
		return entity != null;
	}
	
	public void setEntity(MovingEntity entity) {
		this.entity = entity;
	}
	
	public boolean isActive() {
		return active;
	}
	
	public int getFuel() {
		return fuel;
	}
	
	public void setFuel(int fuel) {
		this.fuel = fuel;
	}
	
	public int getMaxFuel() {
		return maxFuel;
	}
	
	public void setMaxFuel(int maxFuel) {
		this.maxFuel = maxFuel;
	}
}
