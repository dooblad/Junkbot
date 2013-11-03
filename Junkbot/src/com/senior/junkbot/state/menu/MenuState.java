package com.senior.junkbot.state.menu;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import sound.Sounds;

import bitmaps.Bitmaps;

import com.doobs.java2d.gfx.Screen;
import com.doobs.java2d.input.InputHandler;
import com.senior.junkbot.Main;
import com.senior.junkbot.entity.MenuItem;
import com.senior.junkbot.entity.OptionsMenuItem;
import com.senior.junkbot.entity.projectiles.MenuParticle;
import com.senior.junkbot.state.GameState;
import com.senior.junkbot.util.Font;

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
		
		if(changed)
			Sounds.select.play();
		
		spawnParticles();
		
		for(int i = 0; i < particles.size(); i++) {
			particles.get(i).tick();
			if(particles.get(i).isRemoved())
				particles.remove(i--);
		}
	}
	
	public void render(Screen screen) {
		for(int i = 0; i < menuItems.length; i++) {
			if(menuItems[i] instanceof OptionsMenuItem) {
				if(i == selected)
					((OptionsMenuItem) menuItems[i]).render(MenuItem.SELECTED_COLOR, screen);
				else
					((OptionsMenuItem) menuItems[i]).render(MenuItem.DEFAULT_COLOR, screen);
			} else {
				if(i == selected)
					menuItems[i].render(MenuItem.SELECTED_COLOR, screen);
				else
					menuItems[i].render(MenuItem.DEFAULT_COLOR, screen);
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
		if(menuItems[selected] instanceof OptionsMenuItem) {
			OptionsMenuItem item = ((OptionsMenuItem) menuItems[selected]);
			particles.add(new MenuParticle(xo + Font.getPhraseWidth(item.getFullText()), yo, xa, ya, 0xFF00FF00, particleLife));
		} else 
			particles.add(new MenuParticle(xo + Font.getPhraseWidth(menuItems[selected].getText()), yo, xa, ya, 0xFF00FF00, particleLife));
	}
}
