package com.senior.junkbot.tile;

public class Tile {
	public static int size = -1;
	
	public Tile() {
		
	}
	
	// Only call this method after initializing Bitmaps
	public static void init() {
		size = 32;//Bitmaps.test.getWidth();
	}
	
	// Getters and Setters
	public boolean isSolid() {
		return false;
	}
}
