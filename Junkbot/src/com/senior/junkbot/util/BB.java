package com.senior.junkbot.util;

public class BB {
	private double x, y;
	private int width, height;
	
	public BB() {
		this(0, 0, 0, 0);
	}
	
	public BB(int width, int height) {
		this(0, 0, width, height);
	}
	
	public BB(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	/**
	 * @param x The x coordinate that supplements the width of this BB object.
	 * @param y The y coordinate that supplements the height of this BB object.
	 * @param width The width of the object being compared.
	 * @param height The height of the object being compared.
	 * @return if the two objects are intersecting
	 */
	public boolean intersects(double x, double y, int width, int height) {
		//if (x0 >= this.x1 || y0 >= this.y1 || x1 <= this.x0 || y1 <= this.y0) return false;
		if (x >= this.x + this.width || y >= this.y + this.height || x + width <= this.x || y + height <= this.y) return false;
		return true;
	}
	
	// Getters and Setters
	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}
	
	public void setWidth(int width) {
		this.width = width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void setHeight(int height) {
		this.height = height;
	}
}
