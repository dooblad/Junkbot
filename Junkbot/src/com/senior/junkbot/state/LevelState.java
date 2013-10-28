package com.senior.junkbot.state;

import com.doobs.java2d.gfx.Screen;
import com.doobs.java2d.input.InputHandler;
import com.senior.junkbot.Main;
import com.senior.junkbot.entity.Player;
import com.senior.junkbot.entity.StaticAnimation;
import com.senior.junkbot.level.Level;
import com.senior.junkbot.util.LevelDebugger;

public class LevelState extends GameState{
	public static final int ID = 1;
	
	private Player player;
	private Level level;
	private LevelDebugger levelDebugger;
	
	public LevelState(Main main) {
		super(main);
		player = new Player();
		level = new Level(player);
		levelDebugger = new LevelDebugger(level, player);
	}
	
	public void tick(InputHandler input) {
		StaticAnimation.tick();
		level.tick(input);
		if(Main.debug)
			levelDebugger.tick(input);
	}
	
	public void render(Screen screen) {
		level.render(screen);
		if(Main.debug)
			levelDebugger.render(screen);
	}
}
