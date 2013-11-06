package com.senior.junkbot.state;

import java.awt.event.KeyEvent;

import sound.Sounds;

import com.doobs.java2d.gfx.Screen;
import com.doobs.java2d.input.InputHandler;
import com.senior.junkbot.Main;
import com.senior.junkbot.entity.Player;
import com.senior.junkbot.entity.StaticAnimation;
import com.senior.junkbot.level.Level;
import com.senior.junkbot.util.LevelDebugger;

import config.Config;

public class LevelState extends GameState{
	public static final int ID = 1;
	
	private Player player;
	private Level level;
	private LevelDebugger levelDebugger;
	
	private boolean paused;
	
	public LevelState(Main main) {
		super(main);
		player = new Player();
		level = new Level(player);
		levelDebugger = new LevelDebugger(main, level, player);
		paused = false;
	}
	
	public void tick(InputHandler input) {
		if(input.isKeyPressed(KeyEvent.VK_ESCAPE) || input.isKeyPressed(KeyEvent.VK_P)) {
			paused = !paused;
			if(Config.sfx)
				Sounds.pause.play();
		}
		
		if(!paused) {
			StaticAnimation.tick();
			level.tick(input);
			if(Main.debug)
				levelDebugger.tick(input);
		}
	}
	
	public void render(Screen screen) {
		level.render(screen);
		if(Main.debug)
			levelDebugger.render(screen);
	}
}
