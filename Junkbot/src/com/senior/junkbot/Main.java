package com.senior.junkbot;

import java.awt.event.KeyEvent;

import sound.Sounds;
import bitmaps.Bitmaps;

import com.doobs.java2d.Game2D;
import com.doobs.java2d.GameLoop;
import com.doobs.java2d.gfx.Screen;
import com.doobs.java2d.input.InputHandler;
import com.senior.junkbot.entity.Entity;
import com.senior.junkbot.entity.Player;
import com.senior.junkbot.level.Level;
import com.senior.junkbot.tile.Tile;

public class Main extends GameLoop{
	public static final int WIDTH = 320, HEIGHT = 240;
	public static final int SCALE = 2;
	private static final String NAME = "Junkbot";
	private static final boolean RENDER_FPS = false;
	private static final boolean VSYNC = true;
	
	public static boolean debug = true;
	
	private Game2D game;
	private Level level;
	private Player player;
	
	public Main() {
		game = new Game2D(WIDTH, HEIGHT, SCALE, NAME, this);
		game.getBitmapLoader().setIgnoredColors(0xFF00FF);
		game.setRenderFPS(RENDER_FPS);
		game.setVSync(VSYNC);
		
		Bitmaps.init(game.getBitmapLoader());
		Sounds.init();
		Tile.init();
		Entity.init();
		
		player = new Player(0, 0);
		level = new Level(player);
		
		game.start();
	}
	
	public void tick(InputHandler input, boolean paused) {
		level.tick(input);
		if(input.isKeyPressed(KeyEvent.VK_F))
			Sounds.test.togglePlay();
	}
	
	public void render(Screen screen, boolean paused) {
		screen.fill(0xFF000000);
		level.render(screen);
	}
	
	public void renderPaused(Screen screen) {
		
	}
	
	public static void main(String[] args) {
		if(args.length != 0 && args[0].equals("-debug")) debug = true;
		new Main();
	}
	
	public void exit() {
		Sounds.destroy();
		System.exit(0);
	}
	
	// Getters and setters
	public Game2D getGame2D() {
		return game;
	}
}
