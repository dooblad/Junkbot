package com.senior.junkbot.state.menu;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import sound.Sounds;
import bitmaps.Bitmaps;

import com.doobs.java2d.gfx.Screen;
import com.doobs.java2d.input.InputHandler;
import com.senior.junkbot.Main;
import com.senior.junkbot.entity.menu.BooleanMenuItem;
import com.senior.junkbot.entity.menu.MenuItem;
import com.senior.junkbot.entity.menu.MultiChoiceMenuItem;
import com.senior.junkbot.entity.particles.MenuParticle;
import com.senior.junkbot.state.GameState;
import com.senior.junkbot.util.Font;

import config.Config;

public class MenuState extends GameState {
	protected List<MenuParticle> particles;
	
	protected MenuItem[] menuItems;
	protected int selected;
	protected boolean selectRequested;
	
	public MenuState(Main main) {
		super(main);
		particles = new ArrayList<MenuParticle>();
		selected = 0;
		selectRequested = false;
	}

	public void tick(InputHandler input) {
		selectRequested = false;
		
		if(input.isKeyPressed(KeyEvent.VK_ENTER) || input.isLeftMousePressed())
			selectRequested = true;
		
		boolean changed = false;
		if(input.isKeyPressed(KeyEvent.VK_UP)) {
			if(--selected < 0)
				selected = menuItems.length - 1;
			changed = true;
		} else if(input.isKeyPressed(KeyEvent.VK_DOWN)) {
			if(++selected > menuItems.length - 1)
				selected = 0;
			changed = true;
		}
		
		for(int i = 0; i < menuItems.length; i++) {
			if(menuItems[i].mouseCollides(input)) {
				if(selected != i) 
					changed = true;
				selected = i; 
			}
		}
		
		if(Config.sfx && changed)
			Sounds.select.play();
		
		if(Config.particles)
			spawnParticles();
		
		for(int i = 0; i < particles.size(); i++) {
			particles.get(i).tick();
			if(particles.get(i).isRemoved())
				particles.remove(i--);
		}
	}
	
	public void render(Screen screen) {
		for(int i = 0; i < menuItems.length; i++) {
			int color;
			if(i == selected) color = MenuItem.SELECTED_COLOR;
			else color = MenuItem.DEFAULT_COLOR;
			if(menuItems[i] instanceof BooleanMenuItem) {
				((BooleanMenuItem) menuItems[i]).render(color, screen);
			} else if (menuItems[i] instanceof MultiChoiceMenuItem){
				((MultiChoiceMenuItem) menuItems[i]).render(color, screen);
			} else {
				menuItems[i].render(color, screen);
			}
		}
		
		for(MenuParticle particle : particles) {
			particle.render(screen);
		}
	}
	
	protected void setupMenuItems(String... strings) {
		menuItems = new MenuItem[strings.length];
		for(int i = 0; i < strings.length; i++) {
			menuItems[i] = new MenuItem(strings[i]);
		}
	}
	
	protected void spawnParticles() {
		double xa = Math.random() + 1;
		double ya = (Math.random() - Math.random()) / 2;
		
		int particleLife = (int) (Math.random() * 20 + 10);
		int xo = (int) menuItems[selected].getX();
		int yo = (int) menuItems[selected].getY() + Bitmaps.font[0].getHeight() / 2;
		
		particles.add(new MenuParticle(xo, yo, -xa, ya, 0xFF00FF00, particleLife));
		if(menuItems[selected] instanceof BooleanMenuItem)
			particles.add(new MenuParticle(xo + Font.getPhraseWidth(((BooleanMenuItem) menuItems[selected]).getFullText()), yo, xa, ya, 0xFF00FF00, particleLife));
		else if(menuItems[selected] instanceof MultiChoiceMenuItem)
			particles.add(new MenuParticle(xo + Font.getPhraseWidth(((MultiChoiceMenuItem) menuItems[selected]).getFullText()), yo, xa, ya, 0xFF00FF00, particleLife));
		else 
			particles.add(new MenuParticle(xo + Font.getPhraseWidth(menuItems[selected].getText()), yo, xa, ya, 0xFF00FF00, particleLife));
	}
}
