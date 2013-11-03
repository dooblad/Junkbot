package bitmaps;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.doobs.java2d.gfx.Bitmap;
import com.doobs.java2d.gfx.BitmapLoader;
import com.senior.junkbot.level.Level;

public class Bitmaps {
	public static Bitmap player;
	public static Bitmap jetpack;
	public static Bitmap[] cleanerTurret;
	public static Bitmap cleanerTurretCannon;
	public static Bitmap turretShot;
	public static Bitmap cleanerBot;
	public static Bitmap winPipe;
	public static Bitmap mainMenu, optionsMenu;
	public static Bitmap[][] tiles;
	public static Bitmap[] levels;
	public static Bitmap[] sign;
	public static Bitmap[] font;
	
	public static void init(BitmapLoader loader) {
		player = loadBitmap(loader, "entities/player.png");
		
		jetpack = loadBitmap(loader, "entities/jetpack.png");
		
		cleanerTurret = loader.load1DTileSheet(getImage("entities/cleanerTurret.png"), 5);
		cleanerTurretCannon = loadBitmap(loader, "entities/cleanerTurretCannon.png");
		turretShot = loadBitmap(loader, "entities/turretShot.png");
		
		cleanerBot = loadBitmap(loader, "entities/cleanerBot.png");
		winPipe = loadBitmap(loader, "entities/winPipe.png");
		
		mainMenu = loadBitmap(loader, "menus/mainMenu.png");
		optionsMenu = loadBitmap(loader, "menus/optionsMenu.png");
		
		tiles = loader.load2DTileSheet(getImage("tiles.png"), 1, 1);
		
		levels = new Bitmap[Level.NUM_OF_LEVELS];
		for(int i = 0; i < Level.NUM_OF_LEVELS; i++) {
			levels[i] = loadBitmap(loader, "levels/level" + i + ".png");
		}
		
		sign = loader.load1DTileSheet(getImage("scenery/sign.png"), 3);
		
		font = loader.load1DTileSheet(getImage("font.png"), 64);
	}
	
	private static Bitmap loadBitmap(BitmapLoader loader, String URL) {
		return loader.loadBitmap(getImage(URL));
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
