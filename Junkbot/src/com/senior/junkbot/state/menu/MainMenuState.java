package com.senior.junkbot.state.menu;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import sound.Sounds;
import bitmaps.Bitmaps;

import com.doobs.java2d.gfx.Screen;
import com.doobs.java2d.input.InputHandler;
import com.senior.junkbot.Main;
import com.senior.junkbot.MusicHandler;
import com.senior.junkbot.entity.MenuItem;
import com.senior.junkbot.entity.projectiles.MenuParticle;
import com.senior.junkbot.state.GameState;
import com.senior.junkbot.state.LevelState;
import com.senior.junkbot.util.Font;

public class MainMenuState extends GameState {
	public static final int ID = 0;
	
	private List<MenuParticle> particles;
	
	private MenuItem[] menuItems;
	private int selected;
	
	public MainMenuState(Main main) {
		super(main);
		particles = new ArrayList<MenuParticle>();
		menuItems = new MenuItem[] {new MenuItem("play"), new MenuItem("options"), new MenuItem("exit")};
		for(int i = 0; i < menuItems.length; i++) {
			menuItems[i].centerHorizontally();
			menuItems[i].setY(150 + i * 25);
		}
		selected = 0;
	}
	
	public void tick(InputHandler input) {
		if(input.isKeyPressed(KeyEvent.VK_ENTER)) {
			if(selected == 0) {
				MusicHandler.changeSong(Sounds.level);
				main.changeState(new LevelState(main));
			} else if(selected == 1) {
				main.changeState(new OptionsMenuState(main));
			} else if(selected == 2) {
				main.exit();
			}
		}
		
		if(input.isKeyPressed(KeyEvent.VK_UP)) {
			if(--selected < 0)
				selected = 2;
		} else if(input.isKeyPressed(KeyEvent.VK_DOWN)) {
			if(++selected > 2)
				selected = 0;
		}
		
		spawnParticles();
		
		for(int i = 0; i < particles.size(); i++) {
			particles.get(i).tick();
			if(particles.get(i).isRemoved())
				particles.remove(i--);
		}
	}
	
	public void render(Screen screen) {
		screen.draw(Bitmaps.mainMenu, 0, 0);
		
		for(int i = 0; i < menuItems.length; i++) {
			if(i == selected)
				menuItems[i].render(MenuItem.SELECTED_COLOR, screen);
			else
				menuItems[i].render(MenuItem.DEFAULT_COLOR, screen);
		}
		
		for(MenuParticle particle : particles) {
			particle.render(screen);
		}
	}
	
	private void spawnParticles() {
		double xa = Math.random() + 1;
		double ya = (Math.random() - Math.random()) / 2;
		
		int particleLife = (int) (Math.random() * 20 + 10);
		int xo = (int) menuItems[selected].getX();
		int yo = (int) menuItems[selected].getY() + Bitmaps.font[0].getHeight() / 2;
		
		particles.add(new MenuParticle(xo, yo, -xa, ya, 0xFF00FF00, particleLife));
		particles.add(new MenuParticle(xo + Font.getPhraseWidth(menuItems[selected].getText()), yo, xa, ya, 0xFF00FF00, particleLife));
	}
}
