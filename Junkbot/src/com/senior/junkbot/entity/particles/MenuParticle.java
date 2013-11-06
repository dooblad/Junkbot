package com.senior.junkbot.entity.particles;

import com.doobs.java2d.gfx.Screen;
import com.senior.junkbot.entity.MovingEntity;

public class MenuParticle extends MovingEntity{
	private static final int DEFAULT_COLOR = 0xFFFFFFFF;
	private static final int DEFAULT_LIFE = 120;
	
	private int currentLife, maxLife;
	private int color;
	
	public MenuParticle() {
		this(0, 0, 0, 0, DEFAULT_COLOR, DEFAULT_LIFE);
	}
	
	public MenuParticle(double x, double y) {
		this(x, y, 0, 0, DEFAULT_COLOR, DEFAULT_LIFE);
	}
	
	public MenuParticle(double x, double y, double xa, double ya) {
		this(x, y, xa, ya, DEFAULT_COLOR, DEFAULT_LIFE);
	}
	
	public MenuParticle(double x, double y, double xa, double ya, int color) {
		this(x, y, xa, ya, color, DEFAULT_LIFE);
	}
	
	public MenuParticle(double x, double y, double xa, double ya, int color, int maxLife) {
		super(x, y);
		this.xa = xa;
		this.ya = ya;
		this.currentLife = 0;
		this.maxLife = maxLife;
		this.color = color;
	}
	
	public void tick() {
		if(++currentLife >= maxLife) this.remove();
		
		this.x += xa;
		this.y += ya;
	}
	
	public void render(Screen screen) {
		screen.drawPoint(color, (int) this.x, (int) this.y);
	}
}
