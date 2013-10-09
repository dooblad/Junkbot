package bitmaps;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.doobs.java2d.gfx.Bitmap;
import com.doobs.java2d.gfx.BitmapLoader;
import com.senior.junkbot.level.Level;

public class Bitmaps {
	public static Bitmap player;
	public static Bitmap cleanerBot;
	public static Bitmap winPipe;
	public static Bitmap mainMenu;
	public static Bitmap[][] tiles;
	public static Bitmap[] levels;
	public static Bitmap[][] sign;
	
	public static void init(BitmapLoader loader) {
		player = loadBitmap(loader, "entities/player.png");
		cleanerBot = loadBitmap(loader, "entities/cleanerBot.png");
		winPipe = loadBitmap(loader, "entities/winPipe.png");
		
		mainMenu = loadBitmap(loader, "mainMenu.png");
		
		tiles = loadTileSheet(loader, ("tiles.png"), 1, 1);
		
		levels = new Bitmap[Level.NUM_OF_LEVELS];
		for(int i = 0; i < Level.NUM_OF_LEVELS; i++) {
			levels[i] = loadBitmap(loader, "levels/level" + i + ".png");
		}
		
		sign = loadTileSheet(loader, "scenery/sign.png", 3, 1);
	}
	
	private static Bitmap loadBitmap(BitmapLoader loader, String URL) {
		return loader.loadBitmap(getImage(URL));
	}
	
	private static Bitmap[][] loadTileSheet(BitmapLoader loader, String URL, int tilesX, int tilesY) {
		return loader.loadTileSheet(getImage(URL), tilesX, tilesY);
	}
	
	private static BufferedImage getImage(String URL) {
		try {
			return ImageIO.read((Bitmaps.class.getResource(URL)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
