package com.senior.junkbot.state.menu;

import java.awt.event.KeyEvent;

import bitmaps.Bitmaps;

import com.doobs.java2d.gfx.Screen;
import com.doobs.java2d.input.InputHandler;
import com.senior.junkbot.Main;
import com.senior.junkbot.state.GameState;

public class OptionsMenuState extends GameState {

	public OptionsMenuState(Main main) {
		super(main);
	}
	
	public void tick(InputHandler input) {
		if(input.isKeyPressed(KeyEvent.VK_ESCAPE))
			main.changeState(new MainMenuState(main));
	}
	
	public void render(Screen screen) {
		screen.draw(Bitmaps.optionsMenu, 0, 0);
	}
}
