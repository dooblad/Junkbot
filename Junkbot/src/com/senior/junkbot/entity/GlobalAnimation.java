package com.senior.junkbot.entity;

public class GlobalAnimation {
	public static final int TIMER_MAX = 30;
	
	public static int animationTimer = 0;
	
	public static void tick() {
		if(++animationTimer >= TIMER_MAX)
			animationTimer = 0;
	}
	
	public static double getTimerPercentage() {
		return (double) animationTimer / TIMER_MAX;
	}
}
