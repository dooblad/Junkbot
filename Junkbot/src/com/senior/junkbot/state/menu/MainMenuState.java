package com.senior.junkbot.state.menu;

import java.awt.event.KeyEvent;

import sound.Sounds;

import bitmaps.Bitmaps;

import com.doobs.java2d.gfx.Screen;
import com.doobs.java2d.input.InputHandler;
import com.senior.junkbot.Main;
import com.senior.junkbot.MusicHandler;
import com.senior.junkbot.state.GameState;
import com.senior.junkbot.state.LevelState;

public class MainMenuState extends GameState {
	public static final int ID = 0;
	
	public MainMenuState(Main main) {
		super(main);
	}
	
	public void tick(InputHandler input) {
		if(input.isKeyPressed(KeyEvent.VK_ENTER)) {
			MusicHandler.changeSong(Sounds.level);
			main.changeState(new LevelState(main));
		}
	}
	
	public void render(Screen screen) {
		screen.draw(Bitmaps.mainMenu, 0, 0);
	}
}
