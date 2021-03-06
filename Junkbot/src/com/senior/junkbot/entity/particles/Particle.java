package com.senior.junkbot.entity.particles;

import com.doobs.java2d.gfx.Screen;
import com.senior.junkbot.entity.MovingEntity;
import com.senior.junkbot.util.BB;

public class Particle extends MovingEntity {
	private static final int DEFAULT_COLOR = 0xFFFFFFFF;
	private static final int DEFAULT_LIFE = 120;
	
	protected int currentLife;
	protected int maxLife;
	protected int color;
	
	public Particle() {
		this(0, 0, 0, 0, DEFAULT_COLOR, DEFAULT_LIFE);
	}
	
	public Particle(double x, double y) {
		this(x, y, 0, 0, DEFAULT_COLOR, DEFAULT_LIFE);
	}
	
	public Particle(double x, double y, double xa, double ya) {
		this(x, y, xa, ya, DEFAULT_COLOR, DEFAULT_LIFE);
	}
	
	public Particle(double x, double y, double xa, double ya, int color) {
		this(x, y, xa, ya, color, DEFAULT_LIFE);
	}
	
	public Particle(double x, double y, double xa, double ya, int color, int maxLife) {
		super(x, y, xa, ya);
		this.currentLife = 0;
		this.maxLife = maxLife;
		this.color = color;
	}
	
	public void tick() {
		if(++currentLife >= maxLife) this.remove();
		
		tryMove();
	}
	
	public void tryMove() {
		for(BB bb : level.collidables) {
			if(bb.pointIntersects(this.x + this.xa, this.y)) {
				if(this.x < bb.getX()) {
					this.x = bb.getX();
				} else if(this.x > bb.getX() + bb.getWidth()) {
					this.x = bb.getX() + bb.getWidth();
				}
				
				this.xa = -this.xa;
			}
			
			if(bb.pointIntersects(this.x, this.y + this.ya)) {
				if(this.y < bb.getY()) {
					this.y = bb.getY();
				} else if(this.y > (bb.getY() + bb.getHeight())) {
					this.y = bb.getY() + bb.getHeight();
				}
				
				this.ya = -this.ya;
			}
		}
		
		this.x += xa;
		this.y += ya;
		
	}
	
	public void render(Screen screen) {
		screen.drawPoint(color, (int) (this.x - level.getCamera().getXO()), (int) (this.y - level.getCamera().getYO()));
	}
	
	public void render(int color, Screen screen) {
		screen.drawPoint(color, (int) (this.x - level.getCamera().getXO()), (int) (this.y - level.getCamera().getYO()));
	}
}
