package com.senior.junkbot;

import com.doobs.java2d.Game2D;
import com.doobs.java2d.GameLoop;
import com.doobs.java2d.gfx.Screen;
import com.doobs.java2d.input.InputHandler;

public class Main extends GameLoop{
	public static final int WIDTH = 320, HEIGHT = 240;
	public static final int SCALE = 2;
	private static final String NAME = "Junkbot";
	private static final boolean RENDER_FPS = false;
	private static final boolean VSYNC = true;
	
	public static boolean debug = true;
	
	private Game2D game;
	
	public Main() {
		game = new Game2D(WIDTH, HEIGHT, SCALE, NAME, this);
		game.getBitmapLoader().setIgnoredColors(0xFF00FF);
		game.setRenderFPS(RENDER_FPS);
		game.setVSync(VSYNC);
		game.start();
	}
	
	public void tick(InputHandler input) {
		
	}
	
	public void tickPaused(InputHandler input) {
		
	}
	
	public void render(Screen screen) {
		
	}
	
	public void renderPaused(Screen screen) {
		
	}
	
	public static void main(String[] args) {
		if(args.length != 0 && args[0].equals("-debug")) debug = true;
		new Main();
	}
	
	public void exit() {
		System.exit(0);
	}
	
	// Getters and setters
	public Game2D getGame2D() {
		return game;
	}
}
