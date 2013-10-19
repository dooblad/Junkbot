package com.senior.junkbot.entity.scenery;

import com.doobs.java2d.gfx.Screen;

public class Sign extends Scenery {
	
	private String text;
	private int xTiles, yTiles;
	
	public Sign() {
		super();
	}
	
	public Sign(int x, int y) {
		super(x, y);
	}
	
	public Sign(int x, int y, int xTiles, int yTiles) {
		super(x, y);
		this.xTiles = xTiles;
		this.yTiles = yTiles;
	}
	
	public Sign(String text, int x, int y, int xTiles, int yTiles) {
		super(x, y);
		this.text = text;
		this.xTiles = xTiles;
		this.yTiles = yTiles;
	}
	
	public void tick() {
		
	}
	
	public void render(Screen screen) {
		
	}

	// Getters and Setters
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getXTiles() {
		return xTiles;
	}

	public void setXTiles(int xTiles) {
		this.xTiles = xTiles;
	}

	public int getYTiles() {
		return yTiles;
	}

	public void setYTiles(int yTiles) {
		this.yTiles = yTiles;
	}
}
