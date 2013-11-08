package com.senior.junkbot.entity.enemy;

import bitmaps.Bitmaps;

import com.doobs.java2d.gfx.Screen;
import com.senior.junkbot.entity.Entity;
import com.senior.junkbot.entity.Player;
import com.senior.junkbot.entity.StaticAnimation;
import com.senior.junkbot.entity.projectiles.TurretShot;
import com.senior.junkbot.util.BB;

public class CleanerTurret extends Entity {
	public static final int DEFAULT_FIRE_RATE = 60;
	public static final int DEFAULT_SHOT_LIFE = 120;
	public static final double SHOT_SPEED = 1.0;
	
	private int fireRate, fireCounter;
	
	private int cannonX, cannonY;
	private double shotXA, shotYA;
	private int shotLife;
	
	public CleanerTurret() {
		this(0, 0, 1, 0, DEFAULT_FIRE_RATE, DEFAULT_SHOT_LIFE);
	}
	
	public CleanerTurret(int x, int y) {
		this(x, y, 1, 0, DEFAULT_FIRE_RATE, DEFAULT_SHOT_LIFE);
	}
	
	public CleanerTurret(int x, int y, double xa, double ya) {
		this(x, y, xa, ya, DEFAULT_FIRE_RATE, DEFAULT_SHOT_LIFE);
	}
	
	public CleanerTurret(int x, int y, double xa, double ya, int fireRate) {
		this(x, y, xa, ya, fireRate, DEFAULT_SHOT_LIFE);
	}
	
	public CleanerTurret(int x, int y, double xa, double ya, int fireRate, int shotLife) {
		this(x, y, xa, ya, fireRate, shotLife, 0);
	}
	
	public CleanerTurret(int x, int y, double xa, double ya, int fireRate, int shotLife, int counterOffset) {
		super(x, y);
		this.width = Bitmaps.cleanerTurret[0].getWidth();
		this.height = Bitmaps.cleanerTurret[0].getHeight();
		this.shotXA = xa;
		this.shotYA = ya;
		this.fireRate = fireRate;
		this.fireCounter = fireRate - 1 - counterOffset;
		this.shotLife = shotLife;
		
		calculateCannonPosition();
	}
	
	public void tick() {
		if(++fireCounter >= fireRate) {
			spawnShot();
			fireCounter = 0;
		}
	}
	
	public void render(Screen screen) {
		int xo = (int) (this.x - level.getCamera().getXO());
		int yo = (int) (this.y - level.getCamera().getYO());
		screen.draw(Bitmaps.cleanerTurret[(int) (StaticAnimation.getTimerPercentage() * Bitmaps.cleanerTurret.length)], xo, yo);
		
		if(Math.abs(this.shotXA) > Math.abs(this.shotYA)) {
			screen.draw(Bitmaps.cleanerTurretCannon, cannonX + xo, cannonY + yo);
		} else {
			screen.drawCW(Bitmaps.cleanerTurretCannon, cannonX + xo, cannonY + yo);
		}
	}
	
	private void spawnShot() {
		level.add(new TurretShot((int) (this.x + cannonX), (int) (this.y + cannonY), this.shotXA, this.shotYA, this.shotLife));
	}
	
	private void calculateCannonPosition() {
		if(Math.abs(this.shotXA) > Math.abs(this.shotYA)) {
			this.cannonY = (this.height - Bitmaps.cleanerTurretCannon.getHeight()) / 2;
			if(this.shotXA > 0) {
				this.cannonX = this.width;
			} else {
				this.cannonX = -Bitmaps.cleanerTurretCannon.getWidth();
			}
		} else {				
			this.cannonX = (this.width - Bitmaps.cleanerTurretCannon.getWidth()) / 2;
			if(this.shotYA > 0) {
				this.cannonY = this.height;
			} else {
				this.cannonY = -Bitmaps.cleanerTurretCannon.getWidth();
			}
		}
	}
	
	public boolean collideWithPlayer(Player player) {
		boolean collided = false;
    	
    	if(BB.intersects(player.getX(), player.getWidth(), player.getY() + player.getYA(), player.getHeight(), this.x, this.width, this.y, this.height)) {
			if(player.getYA() > 0) {
				player.setY(this.getY() - player.getHeight());
			} else if(player.getYA() < 0) {
				player.setY(this.y + this.height);
			}
			player.setYA(0);
			player.setOnGround(true);
			collided = true;
		} else if(BB.intersects(player.getX() + player.getXA(), player.getWidth(), player.getY(), player.getHeight(), this.x, this.width, this.y, this.height)) {
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
