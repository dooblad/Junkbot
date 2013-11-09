package com.senior.junkbot;

import sound.Sounds;
import bitmaps.Bitmaps;

import com.doobs.java2d.Game2D;
import com.doobs.java2d.GameLoop;
import com.doobs.java2d.gfx.Screen;
import com.doobs.java2d.input.InputHandler;
import com.senior.junkbot.state.GameState;
import com.senior.junkbot.state.LevelState;
import com.senior.junkbot.state.menu.LoadState;
import com.senior.junkbot.state.menu.MenuState;
import com.senior.junkbot.tile.Tile;

import config.Config;

public class Main extends GameLoop{
	private static final String NAME = "Junkbot";
	private static final boolean RENDER_FPS = false;
	private static final boolean VSYNC = false;
	
	private GameState currentState;
	
	public static boolean debug = true;
	
	private Game2D game;
	
	public Main() {
		Config.loadConfig(this);
		
		game = new Game2D(Config.width, Config.height, Config.scale, NAME, this);
		
		game.getBitmapLoader().setIgnoredColors(0xFF00FF);
		game.setRenderFPS(RENDER_FPS);
		game.setVSync(VSYNC);
		game.getInputHandler().setKeyRange(600);
		
		Bitmaps.init(game.getBitmapLoader());
		//Sounds.init();
		Tile.init();
		
		currentState = new LoadState(this);
		
		game.start();
	}
	
	public void tick(InputHandler input, boolean paused) {
		currentState.tick(input);
		MusicHandler.tick(input);
	}
	
	public void render(Screen screen, boolean paused) {
		screen.fill(0xFF000000);
		currentState.render(screen);
	}
	
	public void changeState(GameState newState) {
		if(!(currentState instanceof MenuState) && newState instanceof MenuState)
			MusicHandler.randomMenuSong();
		else if(newState instanceof LevelState)
			MusicHandler.randomLevelSong();
		this.currentState = newState;
	}
	
	public void exit() {
		Sounds.destroy();
		System.exit(0);
	}
	
	public static void main(String[] args) {
		if(args.length != 0 && args[0].equals("-debug")) debug = true;
		new Main();
	}
	
	// Getters and setters
	public Game2D getGame2D() {
		return game;
	}
}
