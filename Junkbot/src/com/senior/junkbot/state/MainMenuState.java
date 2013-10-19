package com.senior.junkbot.state;

import java.awt.event.KeyEvent;

import sound.Sounds;

import bitmaps.Bitmaps;

import com.doobs.java2d.gfx.Screen;
import com.doobs.java2d.input.InputHandler;
import com.senior.junkbot.Main;

public class MainMenuState extends GameState {
	public static final int ID = 0;
	
	public MainMenuState(Main main) {
		super(main);
	}
	
	public void tick(InputHandler input) {
		if(input.isKeyPressed(KeyEvent.VK_ENTER)) {
			Sounds.title.stop();
			Sounds.level.play();
			main.changeState(new LevelState(main));
		}
	}
	
	public void render(Screen screen) {
		screen.draw(Bitmaps.mainMenu, 0, 0);
	}
}
