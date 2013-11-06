package com.senior.junkbot.state.menu;

import sound.Sounds;
import bitmaps.Bitmaps;

import com.doobs.java2d.gfx.Screen;
import com.doobs.java2d.input.InputHandler;
import com.senior.junkbot.Main;
import com.senior.junkbot.MusicHandler;
import com.senior.junkbot.state.LevelState;

import config.Config;

public class MainMenuState extends MenuState {
	
	public MainMenuState(Main main) {
		super(main);
		
		setupMenuItems("play", "options", "exit");
		for(int i = 0; i < menuItems.length; i++) {
			menuItems[i].centerHorizontally();
			menuItems[i].setY(150 + i * 25);
		}
	}
	
	public void tick(InputHandler input) {
		super.tick(input);
		
		if(selectRequested) {
			if(selected == 0) {
				MusicHandler.changeSong(Sounds.level);
				main.changeState(new LevelState(main));
			} else if(selected == 1) {
				main.changeState(new OptionsMenuState(main));
			} else if(selected == 2) {
				main.exit();
			}
		}
	}
	
	public void render(Screen screen) {
		screen.draw(Bitmaps.mainMenu, (Config.width - Bitmaps.mainMenu.getWidth()) / 2, 55);
		
		super.render(screen);
	}
}
