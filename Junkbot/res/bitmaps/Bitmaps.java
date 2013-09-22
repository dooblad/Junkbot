package bitmaps;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.doobs.java2d.gfx.Bitmap;
import com.doobs.java2d.gfx.BitmapLoader;

public class Bitmaps {
	public static Bitmap player;
	public static Bitmap level;
	public static Bitmap[] tiles;
	
	public static void init(BitmapLoader loader) {
		player = loader.loadBitmap(getImage("player.png"));
		level = loader.loadBitmap(getImage("level.png"));
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
