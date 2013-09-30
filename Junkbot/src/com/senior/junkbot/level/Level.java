package com.senior.junkbot.level;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import bitmaps.Bitmaps;

import collidables.Collidables;

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
    public static final double GRAVITY = 1.0;
    public static final int NUM_OF_LEVELS = 1;
    
    private Tile[] tiles;
    private List<Entity> entities;
    private List<Entity>[] entityMap;
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
		loadLevel();
    	camera = new Camera();
    	addPlayer(player);
	}
	
    @SuppressWarnings("unchecked")
	public void loadLevel() {
    	this.width = Bitmaps.levels[currentLevel].getWidth();
    	this.height = Bitmaps.levels[currentLevel].getHeight();
    	entities = new ArrayList<Entity>();
        entityMap = new ArrayList[width * height];
        tiles = new Tile[width * height];
        
        collidables = Collidables.loadCollidables("level" + currentLevel + ".txt");

		for(int x = 0; x < width; x++) {
			for(int y = 0; y < height; y++) {
                entityMap[x + y * width] = new ArrayList<Entity>();
                
                int color = Bitmaps.levels[currentLevel].pixels[x + y * width] & 0xFFFFFF;
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
    	
    	if(input.isLeftMousePressed()) {
    		int mouseX = (int) (input.getMouseX() / Main.SCALE + camera.getXO());
    		int mouseY = (int) (input.getMouseY() / Main.SCALE + camera.getYO());
    		System.out.println(mouseX + " " + mouseY);
    	}
    		
    	for(int i = 0; i < entities.size(); i++) {
    		Entity entity = entities.get(i);
    		if(entity instanceof Player) ((Player)entity).tick(input);
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
    	
    	/*//for(int x = (int)(camera.getXO()) / Tile.size - 1; x < (int)(camera.getXO() + Main.WIDTH * Main.SCALE) / Tile.size + 1; x++) {
    	for(int x = 0; x < width; x++) {
    		if(x < 0 || x >= width) continue;
        	//for(int y = (int)(camera.getYO()) / Tile.size - 1; y < (int)(camera.getYO() + Main.HEIGHT * Main.SCALE) / Tile.size + 1; y++) {
    		for(int y = 0; y < height; y++) {
    			if(y < 0 || y >= height) continue;
        		byte id = tiles[x + y * width].getID();
    			if(id == 0) continue;
    			screen.draw(Bitmaps.tiles[id - 1][0], (int) (x * Tile.size - camera.getXO()), (int) (y  - camera.getYO()));
        	}
    	}*/
    	
    	// Render tiles
    	for(int x = 0; x < this.width; x++) {
    		for(int y = 0; y < this.height; y++) {
    			byte id = tiles[x + y * this.width].getID();
    			if(id == 0) continue;
    			screen.draw(Bitmaps.tiles[id -1][0], (int) (x * Tile.size - camera.getXO()), (int) (y * Tile.size - camera.getYO()));
    		}
    	}
    	
    	// Render collidables
    	for(BB bb : collidables) {
    		int x = (int) (bb.getX() - camera.getXO());
    		int y = (int) (bb.getY() - camera.getYO());
    		screen.drawPoint(0xFFFFFF00, x, y);
    		screen.drawPoint(0xFFFFFF00, x + bb.getWidth(), y);
    		screen.drawPoint(0xFFFFFF00, x + bb.getWidth(), y + bb.getHeight());
    		screen.drawPoint(0xFFFFFF00, x, y + bb.getHeight());
    	}
    	
    	// Render entities
    	for(Entity entity: entities) {
			if(entity instanceof Player) ((Player)entity).render(screen);
		}
    }
    
    /*public boolean isFree(Entity e, BB bb, double xa, double ya) {
    	
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
    }*/
    
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
    public Camera getCamera() {
    	return camera;
    }
    
    public void setCamera(Camera camera) {
    	this.camera = camera;
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
