package com.senior.junkbot.entity;

import java.awt.event.KeyEvent;

import com.doobs.java2d.gfx.Screen;
import com.doobs.java2d.input.InputHandler;
import com.senior.junkbot.Main;
import com.senior.junkbot.util.Font;

public class OptionsMenuItem extends MenuItem {
	public String[] options;
	private int selected;
	
	public OptionsMenuItem(int x, int y) {
		this(x, y, null);
	}
	
	public OptionsMenuItem(String text) {
		this(0, 0, text);
	}
	public OptionsMenuItem(String text, String... options) {
		this(0, 0, text, options);
	}
	
	public OptionsMenuItem(int x, int y, String text) {
		this(x, y, text, "");
	}
	
	public OptionsMenuItem(int x, int y, String text, String... options) {
		super(x, y, text);
		this.options = options;
		this.selected = 0;
	}
	
	public void tick(InputHandler input) {
		if(input.isKeyPressed(KeyEvent.VK_LEFT) || input.isLeftMousePressed()) {
			if(--selected < 0)
				selected = options.length - 1;
		} else if(input.isKeyPressed(KeyEvent.VK_RIGHT)) {
			if(++selected >= options.length)
				selected = 0;
		}
	}
	
	public void render(int color, Screen screen) {
		Font.drawStringColored(text + options[selected], color, (int) this.x, (int) this.y, screen);
	}
	
	public void centerHorizontally() {
		this.x = (Main.WIDTH - Font.getPhraseWidth(this.text + options[selected])) / 2;
	}
	
	// Getters and setters
	public String getFullText() {
		return this.text + this.options[this.selected];
	}
	
	public int getSelected() {
		return selected;
	}
	
	public void setSelected(int selected) {
		this.selected = selected;
	}
}
