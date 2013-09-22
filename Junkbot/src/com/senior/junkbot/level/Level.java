package com.senior.junkbot.level;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import bitmaps.Bitmaps;

import com.doobs.java2d.gfx.Screen;
import com.doobs.java2d.input.InputHandler;
import com.senior.junkbot.Main;
import com.senior.junkbot.entity.Entity;
import com.senior.junkbot.entity.Player;
import com.senior.junkbot.entity.pickups.CleanerBot;
import com.senior.junkbot.entity.pickups.JunkRemover;
import com.senior.junkbot.tile.AirTile;
import com.senior.junkbot.tile.GroundTile;
import com.senior.junkbot.tile.Tile;
import com.senior.junkbot.util.BB;
import com.senior.junkbot.util.Camera;

public class Level {
    public static final double GRAVITY = 0.2;
    
    private Tile[] tiles;
    private List<Entity> entities;
    private List<Entity>[] entityMap;
    private int width, height;
    private Camera camera;
    private Player player;
    private int xSpawn, ySpawn;
    
    public static int currentLevel = 0;
    
	public Level() {
    	this(null);
    }
	
	public Level(Player player) {
		switchLevel();
    	camera = new Camera();
    	addPlayer(player);
	}
	
    @SuppressWarnings("unchecked")
	public void switchLevel() {
    	//this.width = Art.levels[currentLevel].width;
    	//this.height = Art.levels[currentLevel].height;
    	entities = new ArrayList<Entity>();
        entityMap = new ArrayList[width * height];
        tiles = new Tile[width * height];

		for(int x = 0; x < width; x++) {
			for(int y = 0; y < height; y++) {
                entityMap[x + y * width] = new ArrayList<Entity>();
                
                int color = Bitmaps.level.pixels[x + y * width] & 0xFFFFFF;
				switch(color) {
					case 0xFFFFFF: tiles[x + y * width] = new AirTile(); break;
					case 0x808080: tiles[x + y * width] = new GroundTile(); break;
					case 0x00FF00: tiles[x + y * width] = new AirTile(); xSpawn = x * Tile.size; ySpawn = y * Tile.size; break;
					case 0x800000: tiles[x + y * width] = new AirTile(); add(new JunkRemover(x * Tile.size, y * Tile.size, this)); break;
					case 0x400000: tiles[x + y * width] = new AirTile(); add(new CleanerBot(x * Tile.size, y * Tile.size, this)); break;
					default: tiles[x + y * width] = new AirTile(); break;
				}
			}
		}
	}
    
    public void addPlayer(Player player) {
    	this.player = player;
    	camera.addEntity(player);
    	add(player);
    }

    public void add(Entity e) {
        entities.add(e);
        e.setLevel(this);

        e.xSlot = (int) ((e.x + e.w / 2.0) / Tile.size);
        e.ySlot = (int) ((e.y + e.h / 2.0) / Tile.size);
        if (e.xSlot >= 0 && e.ySlot >= 0 && e.xSlot < width && e.ySlot < height) {
            entityMap[e.xSlot + e.ySlot * width].add(e);
        }
    }

    public void tick(InputHandler input) {
    	if(input.keys[KeyEvent.VK_R]) {
    		resetLevel();
    	}
    	
    	for(int i = 0; i < entities.size(); i++) {
    		Entity entity = entities.get(i);
    		if(entity instanceof Player) ((Player)entity).tick(input);
    	}
    	
    	camera.tick(input);
    	
		for (int i = 0; i < entities.size(); i++) {
            Entity e = entities.get(i);
            int xSlotOld = e.xSlot;
            int ySlotOld = e.ySlot;
            e.xSlot = (int) ((e.x + e.w / 2.0) / Tile.size);
            e.ySlot = (int) ((e.y + e.h / 2.0) / Tile.size);
            if (e.removed) {
                if (xSlotOld >= 0 && ySlotOld >= 0 && xSlotOld < width && ySlotOld < height) {
                    entityMap[xSlotOld + ySlotOld * width].remove(e);
                }
                if(!(entities.get(i) instanceof Player)) 
                	entities.remove(i--);
            } else {
                if (e.xSlot != xSlotOld || e.ySlot != ySlotOld) {
                    if (xSlotOld >= 0 && ySlotOld >= 0 && xSlotOld < width && ySlotOld < height) {
                        entityMap[xSlotOld + ySlotOld * width].remove(e);
                    }
                    if (e.xSlot >= 0 && e.ySlot >= 0 && e.xSlot < width && e.ySlot < height) {
                        entityMap[e.xSlot + e.ySlot * width].add(e);
                    } else {
                        e.outOfBounds();
                    }

                }
            }
        }
    }
    
    public void render(Screen screen) {
    	for(int x = (int)(camera.getXO()) / Tile.size - 1; x < (int)(camera.getXO() + Main.WIDTH * Main.SCALE) / Tile.size + 1; x++) {
    		if(x < 0 || x >= width) continue;
        	for(int y = (int)(camera.getYO()) / Tile.size - 1; y < (int)(camera.getYO() + Main.HEIGHT * Main.SCALE) / Tile.size + 1; y++) {
        		if(y < 0 || y >= height) continue;
        		Tile tile = tiles[x + y * width];
    			if(tile instanceof AirTile) continue;
    			
        	}
    	}
    	
    	for(Entity entity: entities) {
			if(entity instanceof Player) ((Player)entity).render(screen);
		}
    }
    
    public boolean isFree(Entity e, BB bb, double xa, double ya) {
    	
    	int x0 = (int)((e.getX()) / Tile.size);
    	int y0 = (int)((e.getY()) / Tile.size);
    	int x1 = (int)((e.getX() + bb.getW()) / Tile.size);
    	int y1 = (int)((e.getY() + bb.getH()) / Tile.size);
    	
    	boolean isFree = true;
    	for(int x = x0 - 1; x < x1 + 1; x++) {
    		if(x < 0 || x >= this.width) continue;
    		for(int y = y0 - 1; y < y1 + 1; y++) {
    			if(y < 0 || y >= this.height) continue;
    			//if(tiles[x + y * this.width].isSolid() && temp.intersects(x * Tile.size, y * Tile.size, (x + 1) * Tile.size, (y + 1) * Tile.size)) 
    			if(tiles[x + y * this.width].isSolid() && bb.intersects((int) e.getX(), (int) e.getY(), x * Tile.size, y * Tile.size, Tile.size, Tile.size))
    				isFree = false;
    			else ;
    		}
    	}
    	
    	return isFree;
    }
    
    public void nextLevel() {
    	currentLevel++;
    	entities.clear();
    	switchLevel();
    	addPlayer(player);
    	player.respawn();
    }
    
    public void resetLevel() {
    	entities.clear();
    	switchLevel();
    	addPlayer(player);
    	player.respawn();
    }
    
    // Getters and Setters
    public int getXSpawn() {
    	return xSpawn;
    }
    
    public void setXSpawn() {
    	
    }
    
    public int getYSpawn() {
    	return ySpawn;
    }
    
    public void setYSpawn() {
    	
    }
}
