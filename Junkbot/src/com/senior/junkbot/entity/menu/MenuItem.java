package com.senior.junkbot.entity.menu;

import bitmaps.Bitmaps;

import com.doobs.java2d.gfx.Screen;
import com.doobs.java2d.input.InputHandler;
import com.senior.junkbot.entity.Entity;
import com.senior.junkbot.util.BB;
import com.senior.junkbot.util.Font;

import config.Config;

public class MenuItem extends Entity {
	public static final int DEFAULT_COLOR = 0xFF4B692F, SELECTED_COLOR = 0xFF6ABE30;
	
	protected String text;
	
	public MenuItem(int x, int y) {
		this(x, y, null);
	}
	
	public MenuItem(String text) {
		this(0, 0, text);
	}
	
	public MenuItem(int x, int y, String text) {
		super(x, y);
		this.width = text.length() * Bitmaps.font[0].getWidth();
		this.height = Bitmaps.font[0].getHeight();
		this.text = text;
	}
	
	protected void tick() {
		
	}
	
	public void render(int color, Screen screen) {
		Font.drawStringColored(text, color, (int) this.x, (int) this.y, screen);
	}
	
	public boolean mouseCollides(InputHandler input) {
		return BB.pointIntersects(input.getMouseX() / Config.scale, input.getMouseY() / Config.scale, this.x, this.width, this.y, this.height);
	}
	
	public void centerHorizontally() {
		this.x = (Config.width - Font.getPhraseWidth(this.text)) / 2;
	}
	
	// Getters and setters
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
}
