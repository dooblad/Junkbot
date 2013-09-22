package com.senior.junkbot.util;

public class BB {
	private int w, h;
	
	public BB() {
		this(0, 0);
	}
	
	public BB(int w, int h) {
		this.w = w;
		this.h = h;
	}
	
	/**
	 * 
	 * @param x The x coordinate that supplements the width of this BB object.
	 * @param y The y coordinate that supplements the height of this BB object.
	 * @param x0 The leftmost x coordinate of the object being compared.
	 * @param y0 The topmost x coordinate of the object being compared.
	 * @param w The width of the object being compared.
	 * @param h The height of the object being compared.
	 * @return if the two objects are intersecting
	 */
	public boolean intersects(int x, int y, int x0, int y0, int w, int h) {
		//if (x0 >= this.x1 || y0 >= this.y1 || x1 <= this.x0 || y1 <= this.y0) return false;
		if (x0 >= x + this.w || y0 >= y + this.h || x0 + w <= x || y0 + h <= y) return false;
		return true;
	}
	
	// Getters and Setters
	public int getW() {
		return w;
	}
	
	public void setW(int w) {
		this.w = w;
	}
	
	public int getH() {
		return h;
	}
	
	public void setH(int h) {
		this.h = h;
	}
}
