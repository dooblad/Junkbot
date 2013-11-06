package com.senior.junkbot.entity.menu;

import java.awt.event.KeyEvent;

import sound.Sounds;

import bitmaps.Bitmaps;

import com.doobs.java2d.gfx.Screen;
import com.doobs.java2d.input.InputHandler;
import com.senior.junkbot.util.Font;

import config.Config;

public class BooleanMenuItem extends MenuItem {
	private static final String[] options = {"on", "off"};
	
	private boolean value;
	
	public BooleanMenuItem(int x, int y) {
		this(x, y, false, null);
	}
	
	public BooleanMenuItem(String text) {
		this(0, 0, false, text);
	}
	
	public BooleanMenuItem(boolean value, String text) {
		this(0, 0, value, text);
	}
	
	public BooleanMenuItem(int x, int y, String text) {
		this(x, y, false, text);
	}
	
	public BooleanMenuItem(int x, int y, boolean value, String text) {
		super(x, y, text);
		this.value = false;
		this.text = text;
	}
	
	public void tick(InputHandler input) {
		if(input.isKeyPressed(KeyEvent.VK_LEFT) || input.isRightMousePressed() || input.isKeyPressed(KeyEvent.VK_RIGHT) || input.isLeftMousePressed()) {
			value = !value;
			if(Config.sfx)
				Sounds.optionChange.play();
			calculateWidth();
			centerHorizontally();
		}
	}
	
	public void render(int color, Screen screen) {
		Font.drawStringColored(text + options[value ? 0 : 1], color, (int) this.x, (int) this.y, screen);
	}
	
	public void calculateWidth() {
		this.width = getFullText().length() * Bitmaps.font[0].getWidth();
	}
	
	public void centerHorizontally() {
		this.x = (Config.width - Font.getPhraseWidth(getFullText())) / 2;
	}
	
	public String getFullText() {
		return text + options[value ? 0 : 1];
	}
	
	// Getters and setters
	public boolean getValue() {
		return value;
	}
	
	public void setValue(boolean value) {
		this.value = value;
	}
}
