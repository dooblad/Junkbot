package com.senior.junkbot.state;

import java.awt.event.KeyEvent;

import sound.Sounds;

import bitmaps.Bitmaps;

import com.doobs.java2d.gfx.Screen;
import com.doobs.java2d.input.InputHandler;
import com.senior.junkbot.Main;
import com.senior.junkbot.entity.Player;
import com.senior.junkbot.entity.StaticAnimation;
import com.senior.junkbot.entity.menu.MenuItem;
import com.senior.junkbot.level.Level;
import com.senior.junkbot.state.menu.MainMenuState;
import com.senior.junkbot.util.LevelDebugger;

import config.Config;

public class LevelState extends GameState{
	public static final int ID = 1;
	
	private Player player;
	private Level level;
	private LevelDebugger levelDebugger;
	
	private boolean paused;
	private MenuItem[] pauseMenuItems;
	private int selected;
	
	public LevelState(Main main) {
		super(main);
		player = new Player();
		level = new Level(player);
		levelDebugger = new LevelDebugger(main, level, player);
		paused = false;
		pauseMenuItems = new MenuItem[] {new MenuItem("resume"), new MenuItem("quit to menu"), new MenuItem("quit game")};
		for(int i = 0; i < pauseMenuItems.length; i++) {
			pauseMenuItems[i].centerHorizontally();
			pauseMenuItems[i].setY((Config.height - (25 * pauseMenuItems.length) + 15) / 2 + i * 25);
		}
		selected = 0;
	}
	
	public void tick(InputHandler input) {
		if(input.isKeyPressed(KeyEvent.VK_ESCAPE) || input.isKeyPressed(KeyEvent.VK_P)) {
			paused = !paused;
			Sounds.pause.play();
		}
		
		if(!paused) {
			StaticAnimation.tick();
			level.tick(input);
			if(Main.debug)
				levelDebugger.tick(input);
		} else {
			if(input.isKeyPressed(KeyEvent.VK_ENTER)) {
				if(selected == 0) {
					paused = !paused;
					Sounds.pause.play();
				} else if(selected == 1) {
					main.changeState(new MainMenuState(main));
				} else if(selected == 2) {
					main.exit();
				}
			}
			
			if(input.isKeyPressed(KeyEvent.VK_DOWN)) {
				if(++selected >= pauseMenuItems.length)
					selected = 0;
				Sounds.optionChange.play();
			} else if(input.isKeyPressed(KeyEvent.VK_UP)) {
				if(--selected < 0)
					selected = pauseMenuItems.length - 1;
				Sounds.optionChange.play();
			}
		}
	}
	
	public void render(Screen screen) {
		level.render(screen);
		if(paused) {
			screen.draw(Bitmaps.pauseBorder, (Config.width - 130) / 2, (Config.height - 85) / 2);
			for(int i = 0; i < pauseMenuItems.length; i++) {
				pauseMenuItems[i].render(i == selected, screen);
			}
		}
		if(Main.debug)
			levelDebugger.render(screen);
	}
}
