package com.senior.junkbot.entity.menu;

import java.awt.event.KeyEvent;

import sound.Sounds;

import bitmaps.Bitmaps;

import com.doobs.java2d.gfx.Screen;
import com.doobs.java2d.input.InputHandler;
import com.senior.junkbot.util.Font;

import config.Config;

public class MultiChoiceMenuItem extends MenuItem {
	public String[] options;
	private int selected;
	
	public MultiChoiceMenuItem(int x, int y) {
		this(x, y, 0, "", "");
	}
	
	public MultiChoiceMenuItem(int x, int y, String text) {
		this(x, y, 0, text, "");
	}
	
	public MultiChoiceMenuItem(String text, String... options) {
		this(0, 0, 0, text, options);
	}

	public MultiChoiceMenuItem(int x, int y, int selected, String text, String... options) {
		super(x, y, text);
		this.selected = selected;
		this.options = options;
		calculateWidth();
	}
	
	public void tick(InputHandler input) {
		boolean changed = true;
		if(input.isKeyPressed(KeyEvent.VK_LEFT) || input.isRightMousePressed()) {
			if(--selected < 0)
				selected = options.length - 1;
		} else if(input.isKeyPressed(KeyEvent.VK_RIGHT) || input.isLeftMousePressed()) {
			if(++selected > options.length - 1)
				selected = 0;
		} else
			changed = false;
		
		if(changed) {
			Sounds.optionChange.play();
			calculateWidth();
			centerHorizontally();
		}
	}
	
	public void render(int color, Screen screen) {
		Font.drawStringColored(getFullText(), color, this.x, this.y, screen);
	}
	
	public void calculateWidth() {
		this.width = getFullText().length() * Bitmaps.font[0].getWidth();
	}
	
	public void centerHorizontally() {
		this.x = (Config.width - Font.getPhraseWidth(getFullText())) / 2;
	}
	
	// Getters and setters
	public String getFullText() {
		return text + options[selected];
	}
	
	public int getSelected() {
		return selected;
	}
	
	public void setSelected(int selected) {
		this.selected = selected;
	}
}
