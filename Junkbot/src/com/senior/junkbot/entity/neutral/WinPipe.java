package com.senior.junkbot.entity.neutral;

import bitmaps.Bitmaps;

import com.doobs.java2d.gfx.Screen;
import com.senior.junkbot.entity.Entity;
import com.senior.junkbot.entity.Player;
import com.senior.junkbot.entity.particles.WinPipeParticle;
import com.senior.junkbot.util.BB;

import config.Config;

public class WinPipe extends Entity{
	
	public WinPipe() {
		this(0, 0);
	}
	
	public WinPipe(int x, int y) {
		this.x = x;
		this.y = y;
		this.width = Bitmaps.winPipe.getWidth();
		this.height = Bitmaps.winPipe.getHeight();
	}
	
	public void tick() {
		if(Config.particles) {
			for(int i = 0; i < 3; i++) {
				spawnParticles();
			}
		}
	}
	
	public void render(Screen screen) {
		screen.draw(Bitmaps.winPipe, (int) (this.x - level.getCamera().getXO()), (int) (this.y - level.getCamera().getYO()));
	}
	
	private void spawnParticles() {
		int x = (int) (Math.random() * (this.width + 40) - 20);
		int y = (int) (Math.random() * 10) - 37;
		double xa = (this.width / 2.0 - x) / 15;
		level.add(new WinPipeParticle(this.x + x, this.y + y, xa, 1.0, this.y));
	}
	
	public boolean collideWithPlayer(Player player) {
		boolean collided = false;
    	
    	if(BB.intersects(player.getX(), player.getWidth(), player.getY() + player.getYA(), player.getHeight(), this.x, this.width, this.y, this.height)) {
			if(player.getYA() > 0) {
				player.setY(this.getY() - player.getHeight());
			} else if(player.getYA() < 0) {
				player.setY(this.y + this.height);
			}
			player.setYA(0);
			player.setOnGround(true);
			collided = true;
		} else if(BB.intersects(player.getX() + player.getXA(), player.getWidth(), player.getY(), player.getHeight(), this.x, this.width, this.y, this.height)) {
			if(player.getXA() > 0) {
				player.setX(this.getX() - player.getWidth());
			} else if(player.getXA() < 0) {
				player.setX(this.getX() + this.getWidth());
			}
			player.setXA(0);
			collided = true;
		}

    	return collided;
	}
}
