package com.senior.junkbot.entity.particles;

import com.doobs.java2d.gfx.Screen;

public class WinPipeParticle extends Particle {
	private static final int MAX_LIFE = 30;
	private static final int COLOR = 0xFF00FFFF;
	
	private int pipeY;
	
	public WinPipeParticle() {
		this(0, 0, 0, 0);
	}
	
	public WinPipeParticle(double x, double y) {
		this(x, y, 0, 0);
	}
		
	public WinPipeParticle(double x, double y, double xa, double ya) {
		this(x, y, xa, ya, 0);
	}
	
	public WinPipeParticle(double x, double y, double xa, double ya, double pipeY) {
		super(x, y, xa, ya, COLOR, MAX_LIFE);
		this.pipeY = (int) pipeY;
	}
	
	public void tick() {
		if(++currentLife >= maxLife || (this.y + this.ya) > pipeY) this.remove();
		
		xa *= 0.9;
		
		this.x += xa;
		this.y += ya;
	}
	
	public void render(Screen screen) {
		super.render(color, screen);
	}
}
