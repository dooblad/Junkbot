package com.senior.junkbot;

import java.awt.event.KeyEvent;

import sound.Sound;

import com.doobs.java2d.input.InputHandler;

import config.Config;

public class MusicHandler {
	public static boolean muted = false;
	public static Sound currentSong;
	
	public static void tick(InputHandler input) {
		if(input.isKeyPressed(KeyEvent.VK_M))
			toggleMute();
	}
	
	public static void changeSong(Sound song) {
		if(currentSong != null)
			currentSong.stop();
		currentSong = song;
		if(Config.music && !muted)
			song.play();
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
