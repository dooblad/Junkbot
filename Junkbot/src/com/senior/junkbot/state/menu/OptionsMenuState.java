package com.senior.junkbot.state.menu;

import java.awt.event.KeyEvent;

import bitmaps.Bitmaps;

import com.doobs.java2d.gfx.Screen;
import com.doobs.java2d.input.InputHandler;
import com.senior.junkbot.Main;
import com.senior.junkbot.entity.MenuItem;
import com.senior.junkbot.entity.OptionsMenuItem;

public class OptionsMenuState extends MenuState {

	public OptionsMenuState(Main main) {
		super(main);
		menuItems = new MenuItem[]{
				new OptionsMenuItem("SFX:", "on", "off"), 
				new OptionsMenuItem("Music:", "on", "off"), 
				new OptionsMenuItem("Particles:"),
				new MenuItem("Apply"),
				new MenuItem("Cancel")};
		for(int i = 0; i < menuItems.length; i++) {
			menuItems[i].centerHorizontally();
			menuItems[i].setY(120 + i * 25);
		}
		selected = 0;
	}
	
	public void tick(InputHandler input) {
		super.tick(input);
		
		if(input.isKeyPressed(KeyEvent.VK_ESCAPE))
			main.changeState(new MainMenuState(main));
		
		if(menuItems[selected] instanceof OptionsMenuItem)
			((OptionsMenuItem) menuItems[selected]).tick(input);
	}
	
	public void render(Screen screen) {
		screen.draw(Bitmaps.optionsMenu, 0, 0);
		
		super.render(screen);
	}
}
