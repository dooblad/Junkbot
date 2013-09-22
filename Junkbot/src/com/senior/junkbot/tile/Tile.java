package com.senior.junkbot.tile;

import bitmaps.Bitmaps;

public class Tile {
	public static int size = -1;
	
	protected byte id;
	protected boolean solid;
	
	public Tile(int id, boolean solid) {
		this.id = (byte) id;
		this.solid = solid;
	}
	
	// Only call this method after initializing Bitmaps
	public static void init() {
		size = Bitmaps.tiles[0][0].getWidth();
	}
	
	// Getters and Setters
	public boolean isSolid() {
		return solid;
	}
	
	public byte getID() {
		return id;
	}
}
