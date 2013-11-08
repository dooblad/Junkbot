package com.senior.junkbot.entity.particles;

import com.doobs.java2d.gfx.Screen;

public class WinPipeParticle extends Particle {
	private static final int MAX_LIFE = 30;
	private static final int COLOR = 0xFF00FFFF;
	
	public WinPipeParticle() {
		this(0, 0, 0, 0);
	}
	
	public WinPipeParticle(double x, double y) {
		this(x, y, 0, 0);
	}
		
	public WinPipeParticle(double x, double y, double xa, double ya) {
		this(x, y, xa, ya, COLOR);
	}
	
	public WinPipeParticle(double x, double y, double xa, double ya, int color) {
		super(x, y, xa, ya, color, MAX_LIFE);
	}
	
	public void tick() {
		if(++currentLife >= maxLife) this.remove();
		
		xa *= 0.9;
		
		this.x += xa;
		this.y += ya;
	}
	
	public void render(Screen screen) {
		super.render(color, screen);
	}
}
