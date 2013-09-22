package com.senior.junkbot.entity.pickups;

import com.doobs.java2d.gfx.Screen;
import com.doobs.java2d.input.InputHandler;
import com.senior.junkbot.entity.Entity;
import com.senior.junkbot.level.Level;

public class CleanerBot extends Entity{
	
	public CleanerBot() {
		super(0, 0);
	}
	
	public CleanerBot(int x, int y) {
		super(x, y, null);
	}
	
	public CleanerBot(int x, int y, Level level) {
		super(x, y, level);
	}
	
	public void tick(InputHandler input) {
		
	}
	
	public void render(Screen screen) {
		
	}
}
