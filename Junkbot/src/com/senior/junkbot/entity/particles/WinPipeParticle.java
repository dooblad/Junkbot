package com.senior.junkbot.entity.particles;

import com.doobs.java2d.gfx.Screen;

public class WinPipeParticle extends Particle {
	private static final int MAX_LIFE = 60;
	private static final int COLOR = 0xFF0000FF;
	
	public WinPipeParticle() {
		this(0, 0, 0, 0);
	}
	
	public WinPipeParticle(double x, double y) {
		this(x, y, 0, 0);
	}
		
	public WinPipeParticle(double x, double y, double xa, double ya) {
		super(x, y, xa, ya, COLOR, MAX_LIFE);
	}
	
	public void tick() {
		xa *= 0.8;
		
		this.x += xa;
		this.y += ya;
	}
	
	public void render(Screen screen) {
		super.render(COLOR, screen);
	}
}
