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
	
	public static boolean intersects(double x1, double width1, double y1, double height1, double x2, double width2, double y2, double height2) {
		return !(x1 >= x2 + width2 || y1 >= y2 + height2 || x1 + width1 <= x2 || y1 + height1 <= y2);
	}
	
	/**
	 * @param x The x coordinate that supplements the width of this BB object.
	 * @param y The y coordinate that supplements the height of this BB object.
	 * @param width The width of the object being compared.
	 * @param height The height of the object being compared.
	 * @return if the two objects are intersecting
	 */
	public boolean intersects(double x, double y, int width, int height) {
		return !(x >= this.x + this.width || y >= this.y + this.height || x + width <= this.x || y + height <= this.y);
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
