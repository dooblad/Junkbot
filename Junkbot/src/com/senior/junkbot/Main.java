package com.senior.junkbot;

import sound.Sounds;
import bitmaps.Bitmaps;

import com.doobs.java2d.Game2D;
import com.doobs.java2d.GameLoop;
import com.doobs.java2d.gfx.Screen;
import com.doobs.java2d.input.InputHandler;
import com.senior.junkbot.state.GameState;
import com.senior.junkbot.state.MainMenuState;
import com.senior.junkbot.tile.Tile;

public class Main extends GameLoop{
	public static final int WIDTH = 400, HEIGHT = 300;
	public static final int SCALE = 2;
	private static final String NAME = "Junkbot";
	private static final boolean RENDER_FPS = false;
	private static final boolean VSYNC = false;
	
	private GameState currentState;
	
	public static boolean debug = true;
	
	private Game2D game;
	
	public Main() {
		game = new Game2D(WIDTH, HEIGHT, SCALE, NAME, this);
		game.getBitmapLoader().setIgnoredColors(0xFF00FF);
		game.setRenderFPS(RENDER_FPS);
		game.setVSync(VSYNC);
		game.getInputHandler().setKeyRange(600);
		
		Bitmaps.init(game.getBitmapLoader());
		Sounds.init();
		Tile.init();
		
		currentState = new MainMenuState(this);
		
		game.start();
		
		MusicHandler.changeSong(Sounds.title);
	}
	
	public void tick(InputHandler input, boolean paused) {
		currentState.tick(input);
		MusicHandler.tick(input);
	}
	
	public void render(Screen screen, boolean paused) {
		screen.fill(0xFF000000);
		currentState.render(screen);
		game.setRenderFPS(true);
	}
	
	public void changeState(GameState newState) {
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
