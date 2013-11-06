package com.senior.junkbot.level;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import bitmaps.Bitmaps;

import com.doobs.java2d.gfx.Screen;
import com.doobs.java2d.input.InputHandler;
import com.senior.junkbot.entity.Entity;
import com.senior.junkbot.entity.Jetpack;
import com.senior.junkbot.entity.Player;
import com.senior.junkbot.entity.enemy.CleanerTurret;
import com.senior.junkbot.entity.neutral.CleanerBot;
import com.senior.junkbot.entity.neutral.WinPipe;
import com.senior.junkbot.entity.particles.Particle;
import com.senior.junkbot.entity.particles.WinPipeParticle;
import com.senior.junkbot.entity.projectiles.TurretShot;
import com.senior.junkbot.tile.AirTile;
import com.senior.junkbot.tile.GroundTile;
import com.senior.junkbot.tile.Tile;
import com.senior.junkbot.util.BB;
import com.senior.junkbot.util.Camera;
import com.senior.junkbot.util.Collidables;

import config.Config;

import entities.EntityLoader;

public class Level {
    public static final double GRAVITY = 0.2;
    public static final int NUM_OF_LEVELS = 7;
    
    public Tile[] tiles;
    public List<Entity> entities;
    public List<Entity>[] entityMap;
    
    private int width, height;
    private Camera camera;
    private Player player;
    private int xSpawn, ySpawn;
    
    public BB[] collidables;
    
    public static int currentLevel = 0;
    
	public Level() {
    	this(null);
    }
	
	public Level(Player player) {
    	camera = new Camera();

		loadLevel();
    	addPlayer(player);
	}
	
    @SuppressWarnings("unchecked")
	public void loadLevel() {
    	this.width = Bitmaps.levels[currentLevel].getWidth();
    	this.height = Bitmaps.levels[currentLevel].getHeight();
    	entities = new ArrayList<Entity>();
        entityMap = new ArrayList[width * height];
        tiles = new Tile[width * height];
        
		for(int x = 0; x < width; x++) {
			for(int y = 0; y < height; y++) {
                entityMap[x + y * width] = new ArrayList<Entity>();
                
                int xx = x * Tile.size;
                int yy = y * Tile.size;
                int color = Bitmaps.levels[currentLevel].pixels[x + y * width] & 0xFFFFFF;
				switch(color) {
					case 0xFFFFFF: tiles[x + y * width] = new AirTile(); break;
					case 0x808080: tiles[x + y * width] = new GroundTile(); break;
					case 0x00FF00: tiles[x + y * width] = new AirTile(); xSpawn = xx; ySpawn = yy; break;
					default: tiles[x + y * width] = new AirTile(); break;
				}
			}
		}
		
		EntityLoader.loadEntities(this);
		collidables = Collidables.calculateCollidables(this);
	}
    
    public void addPlayer(Player player) {
    	this.player = player;
    	camera.addEntity(player);
    	add(player);
    	player.setX(xSpawn);
    	player.setY(ySpawn);
    }

    public void add(Entity e) {
        entities.add(e);
        e.setLevel(this);

        e.setXSlot((int) ((e.getX() + e.getWidth() / 2.0) / Tile.size));
        e.setYSlot((int) ((e.getY() + e.getHeight() / 2.0) / Tile.size));
        if (e.getXSlot() >= 0 && e.getYSlot() >= 0 && e.getXSlot() < width && e.getYSlot() < height) {
            entityMap[e.getXSlot() + e.getYSlot() * width].add(e);
        }
    }

    public void tick(InputHandler input) {
    	if(input.isKeyPressed(KeyEvent.VK_R)) {
    		resetLevel();
    	}
    	
    	for(int i = 0; i < entities.size(); i++) {
    		Entity entity = entities.get(i);
    		if(entity instanceof Particle) ((Particle) entity).tick();
    		else if(entity instanceof WinPipeParticle) ((WinPipeParticle) entity).tick();
    		else if(entity instanceof TurretShot) ((TurretShot) entity).tick();
    		else if(entity instanceof CleanerBot) ((CleanerBot) entity).tick();
    		else if(entity instanceof CleanerTurret) ((CleanerTurret) entity).tick();
    		else if(entity instanceof Player) ((Player) entity).tick(input);
    		else if(entity instanceof WinPipe) ((WinPipe) entity).tick();
    		else if(entity instanceof Jetpack) ((Jetpack) entity).tick();
    	}
    	
    	camera.tick(input);
    	
		for (int i = 0; i < entities.size(); i++) {
            Entity e = entities.get(i);
            int xSlotOld = e.getXSlot();
            int ySlotOld = e.getYSlot();
            e.setXSlot((int) ((e.getX() + e.getWidth() / 2.0) / Tile.size));
            e.setYSlot((int) ((e.getY() + e.getHeight() / 2.0) / Tile.size));
            if (e.isRemoved()) {
                if (xSlotOld >= 0 && ySlotOld >= 0 && xSlotOld < width && ySlotOld < height) {
                    entityMap[xSlotOld + ySlotOld * width].remove(e);
                }
                if(!(entities.get(i) instanceof Player)) 
                	entities.remove(i--);
            } else {
                if (e.getXSlot() != xSlotOld || e.getYSlot() != ySlotOld) {
                    if (xSlotOld >= 0 && ySlotOld >= 0 && xSlotOld < width && ySlotOld < height) {
                        entityMap[xSlotOld + ySlotOld * width].remove(e);
                    }
                    if (e.getXSlot() >= 0 && e.getYSlot() >= 0 && e.getXSlot() < width && e.getYSlot() < height) {
                        entityMap[e.getXSlot() + e.getYSlot() * width].add(e);
                    } else {
                        e.outOfBounds();
                    }

                }
            }
        }
    }
    
    public void render(Screen screen) {
    	// Render tiles
    	for(int x = (int) camera.getXO() / Tile.size; x < (camera.getXO() + Config.width) / Tile.size; x++) {
    		if(x < 0 || x >= this.width) continue;
    		for(int y = (int) camera.getYO() / Tile.size; y < (camera.getYO() + Config.height) / Tile.size; y++) {
    			if(y < 0 || y >= this.height) continue;
    			byte id = tiles[x + y * this.width].getID();
    			if(id == 0) continue;
    			screen.draw(Bitmaps.tiles[id -1][0], (int) (x * Tile.size - camera.getXO()), (int) (y * Tile.size - camera.getYO()));
    		}
    	}
    	
    	// Render entities
    	for(Entity entity: entities) {
			if(entity instanceof Particle) ((Particle) entity).render(screen);
			else if(entity instanceof WinPipeParticle) ((WinPipeParticle) entity).render(screen);
			else if(entity instanceof TurretShot) ((TurretShot) entity).render(screen);
			else if(entity instanceof CleanerBot) ((CleanerBot) entity).render(screen);
			else if(entity instanceof CleanerTurret) ((CleanerTurret) entity).render(screen);
			else if(entity instanceof Player) ((Player) entity).render(screen);
			else if(entity instanceof WinPipe) ((WinPipe) entity).render(screen);
			else if(entity instanceof Jetpack) ((Jetpack) entity).render(screen);
		}
    }
    
    public void previousLevel() {
    	currentLevel--;
    	resetLevel();
    }
    
    public void nextLevel() {
    	currentLevel++;
    	resetLevel();
    }
    
    public void resetLevel() {
    	loadLevel();
    	addPlayer(player);
    	player.respawn();
    }
    
    // Getters and Setters
    public Tile getTile(int x, int y) {
    	return tiles[x + y * width];
    }
    
    public List<Entity>[] getEntityMap() {
    	return entityMap;
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
    
    public Camera getCamera() {
    	return camera;
    }
    
    public void setCamera(Camera camera) {
    	this.camera = camera;
    }
    
    public Player getPlayer() {
    	return player;
    }
    
    public void setPlayer(Player player) {
    	this.player = player;
    }
    
    public int getXSpawn() {
    	return xSpawn;
    }
    
    public void setXSpawn(int x) {
    	this.xSpawn = x;
    }
    
    public int getYSpawn() {
    	return ySpawn;
    }
    
    public void setYSpawn(int y) {
    	this.ySpawn = y;
    }
}
