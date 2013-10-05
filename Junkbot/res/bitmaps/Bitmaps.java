package bitmaps;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.doobs.java2d.gfx.Bitmap;
import com.doobs.java2d.gfx.BitmapLoader;
import com.senior.junkbot.level.Level;

public class Bitmaps {
	public static Bitmap player;
	public static Bitmap mainMenu;
	public static Bitmap[][] tiles;
	public static Bitmap[] levels;
	
	public static void init(BitmapLoader loader) {
		player = loader.loadBitmap(getImage("player.png"));
		
		mainMenu = loader.loadBitmap(getImage("mainMenu.png"));
		
		tiles = loader.loadTileSheet(getImage("tiles.png"), 1, 1);
		
		levels = new Bitmap[Level.NUM_OF_LEVELS];
		for(int i = 0; i < Level.NUM_OF_LEVELS; i++) {
			levels[i] = loader.loadBitmap(getImage("level" + i + ".png"));
		}
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
