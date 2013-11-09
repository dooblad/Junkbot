package com.senior.junkbot;

import java.awt.event.KeyEvent;

import sound.Sound;

import com.doobs.java2d.input.InputHandler;

import config.Config;

public class MusicHandler {
	private static final int MENU_SONGS = 3;
	
	public static boolean muted = false;
	public static Sound currentSong;
	public static Sound[] songs;
	
	public static void tick(InputHandler input) {
		if(input.isKeyPressed(KeyEvent.VK_M))
			toggleMute();
	}
	
	public static void changeSong(int songNumber) {
		if(currentSong != null)
			currentSong.stop();
		currentSong = songs[songNumber];
		if(Config.music && !muted)
			songs[songNumber].play();
	}
	
	public static void randomMenuSong() {
		int random = (int) (Math.random() * MENU_SONGS);
		changeSong(random);
	}
	
	public static void randomLevelSong() {
		int random = (int) (Math.random() * (songs.length - MENU_SONGS) + MENU_SONGS - 1);
		changeSong(random);
	}
	
	public static void playMusic() {
		if(Config.music)
			currentSong.play();
	}
	
	public static void pauseMusic() {
		currentSong.pause();
	}
	
	public static void stopMusic() {
		currentSong.stop();
	}
	
	public static void mute() {
		muted = true;
		currentSong.pause();
	}
	
	public static void unmute() {
		muted = false;
		if(Config.music)
			currentSong.play();
	}
	
	public static void toggleMute() {
		if(muted) unmute();
		else mute();
	}
}
