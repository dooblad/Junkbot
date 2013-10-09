package com.senior.junkbot.entity.enemy;

import bitmaps.Bitmaps;

import com.doobs.java2d.gfx.Screen;
import com.doobs.java2d.input.InputHandler;
import com.senior.junkbot.entity.Entity;

public class CleanerBot extends Entity {
	public static final double SPEED = 3.0;
	
	private boolean right;
	
	public CleanerBot() {
		this(0, 0);
	}
	
	public CleanerBot(int x, int y) {
		super(x, y);
		this.right = true;
		this.width = Bitmaps.cleanerBot.getWidth();
		this.height = Bitmaps.cleanerBot.getHeight();
	}
	
	public void tick(InputHandler input) {
		if(right) 
			xa = SPEED;
		else 
			xa = -SPEED;
		
		if(tryMove(this.width, this.height)) 
			right = !right;
	}
	
	public void render(Screen screen) {
		screen.draw(Bitmaps.cleanerBot, (int) (this.x - level.getCamera().getXO()), (int) (this.y - level.getCamera().getYO()));
	}
}
