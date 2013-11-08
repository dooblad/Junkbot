package com.senior.junkbot.state.menu;

import java.awt.event.KeyEvent;

import bitmaps.Bitmaps;

import com.doobs.java2d.gfx.Screen;
import com.doobs.java2d.input.InputHandler;
import com.senior.junkbot.Main;
import com.senior.junkbot.MusicHandler;
import com.senior.junkbot.entity.menu.BooleanMenuItem;
import com.senior.junkbot.entity.menu.MenuItem;
import com.senior.junkbot.entity.menu.MultiChoiceMenuItem;

import config.Config;

public class OptionsMenuState extends MenuState {

	public OptionsMenuState(Main main) {
		super(main);
		menuItems = new MenuItem[]{
				new BooleanMenuItem(Config.sfx, "sfx:"), 
				new BooleanMenuItem(Config.music, "music:"), 
				new BooleanMenuItem(Config.particles, "particles:"),
				new MultiChoiceMenuItem("size:", "320x240", "400x300", "640x480", "800x600"),
				new MultiChoiceMenuItem("scale:", "1", "2", "3"),
				new MenuItem("apply"),
				new MenuItem("cancel")};
		for(int i = 0; i < menuItems.length; i++) {
			menuItems[i].centerHorizontally();
			menuItems[i].setY(75 + i * 25);
		}
		selected = 0;
		setDefaultItemSelections();
	}
	
	public void tick(InputHandler input) {
		super.tick(input);
		
		if(selectRequested) {
			if(selected == 5) {
				applySettings();
				main.changeState(new MainMenuState(main));
			} else if(selected == 6)
				main.changeState(new MainMenuState(main));
		}
			
		
		if(input.isKeyPressed(KeyEvent.VK_ESCAPE))
			main.changeState(new MainMenuState(main));
		
		if(menuItems[selected] instanceof BooleanMenuItem)
			((BooleanMenuItem) menuItems[selected]).tick(input);
		else if(menuItems[selected] instanceof MultiChoiceMenuItem)
			((MultiChoiceMenuItem) menuItems[selected]).tick(input);
	}
	
	public void render(Screen screen) {
		screen.draw(Bitmaps.optionsMenu, (Config.width - Bitmaps.optionsMenu.getWidth()) / 2, 30);
		
		super.render(screen);
	}
	
	public void applySettings() {
		Config.sfx = ((BooleanMenuItem)menuItems[0]).getValue();
		Config.music = ((BooleanMenuItem)menuItems[1]).getValue();
		if(Config.music == false)
			MusicHandler.stopMusic();
		else
			MusicHandler.playMusic();
		Config.particles = ((BooleanMenuItem)menuItems[2]).getValue();
		Config.size = ((MultiChoiceMenuItem)menuItems[3]).getSelected();
		int[] size = Config.parseIntToSize(Config.size);
		main.getGame2D().setScreenSize(size[0], size[1]);
		Config.scale = ((MultiChoiceMenuItem)menuItems[4]).getSelected() + 1;
		main.getGame2D().setScale(Config.scale);
		Config.saveConfig();
	}
	
	public void setDefaultItemSelections() {
		((BooleanMenuItem)menuItems[0]).setValue(Config.sfx);
		((BooleanMenuItem)menuItems[1]).setValue(Config.music);
		((BooleanMenuItem)menuItems[2]).setValue(Config.particles);
		((MultiChoiceMenuItem)menuItems[3]).setSelected(Config.size);
		((MultiChoiceMenuItem)menuItems[4]).setSelected(Config.scale - 1);
	}
}
