package com.senior.junkbot.state;

import com.doobs.java2d.gfx.Screen;
import com.doobs.java2d.input.InputHandler;
import com.senior.junkbot.Main;
import com.senior.junkbot.entity.Player;
import com.senior.junkbot.level.Level;

public class LevelState extends GameState{
	public static final int ID = 1;
	
	private Level level;
	private Player player;
	
	public LevelState(Main main) {
		super(main);
		player = new Player();
		level = new Level(player);
	}
	
	public void tick(InputHandler input) {
		level.tick(input);
	}
	
	public void render(Screen screen) {
		level.render(screen);
	}
}
