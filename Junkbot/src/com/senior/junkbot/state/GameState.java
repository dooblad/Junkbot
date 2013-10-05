package com.senior.junkbot.state;

import com.doobs.java2d.gfx.Screen;
import com.doobs.java2d.input.InputHandler;
import com.senior.junkbot.Main;

public class GameState {
	protected Main main;
	
	public GameState(Main main) {
		this.main = main;
	}
	
	public void tick(InputHandler input) {
		
	}
	
	public void render(Screen screen) {
		
	}
	
	// Getters and Setters
	public Main getMain() {
		return main;
	}
}
