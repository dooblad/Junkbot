package com.senior.junkbot.entity;

import bitmaps.Bitmaps;

import com.doobs.java2d.gfx.Screen;
import com.doobs.java2d.input.InputHandler;

public class WinPipe extends Entity{
	
	public WinPipe() {
		this(0, 0);
	}
	
	public WinPipe(int x, int y) {
		this.x = x;
		this.y = y;
		this.width = Bitmaps.winPipe.getWidth();
		this.height = Bitmaps.winPipe.getHeight();
	}
	
	public void tick(InputHandler input) {
		
	}
	
	public void render(Screen screen) {
		screen.draw(Bitmaps.winPipe, (int) (this.x - level.getCamera().getXO()), (int) (this.y - level.getCamera().getYO()));
	}
}
