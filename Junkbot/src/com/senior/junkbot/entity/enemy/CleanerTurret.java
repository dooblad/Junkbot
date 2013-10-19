package com.senior.junkbot.entity.enemy;

import bitmaps.Bitmaps;

import com.doobs.java2d.gfx.Screen;
import com.senior.junkbot.entity.Entity;
import com.senior.junkbot.entity.projectiles.TurretShot;

public class CleanerTurret extends Entity {
	public static final int DEFAULT_FIRE_RATE = 60;
	public static final int DEFAULT_SHOT_LIFE = 120;
	public static final double SHOT_SPEED = 1.0;
	
	private int fireRate, fireCounter;
	
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
		super(x, y);
		this.width = Bitmaps.cleanerTurret.getWidth();
		this.height = Bitmaps.cleanerTurret.getHeight();
		this.shotXA = xa;
		this.shotYA = ya;
		this.fireRate = fireRate;
		this.fireCounter = fireRate - 1;
		this.shotLife = shotLife;
	}
	
	public void tick() {
		if(++fireCounter >= fireRate) {
			int xo = (int) (this.x + (this.width - Bitmaps.turretShot.getWidth()) / 2);
			int yo = (int) (this.y + (this.height - Bitmaps.turretShot.getHeight()) / 2);
			level.add(new TurretShot(xo, yo, this.shotXA, this.shotYA, this.shotLife));
			fireCounter = 0;
		}
	}
	
	public void render(Screen screen) {
		screen.draw(Bitmaps.cleanerTurret, (int) (this.x - level.getCamera().getXO()), (int) (this.y - level.getCamera().getYO()));
	}
}
