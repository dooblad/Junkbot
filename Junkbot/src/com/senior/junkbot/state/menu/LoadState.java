package com.senior.junkbot.state.menu;

import org.lwjgl.LWJGLException;
import org.lwjgl.openal.AL;

import sound.Sound;

import bitmaps.Bitmaps;

import com.doobs.java2d.gfx.Screen;
import com.doobs.java2d.input.InputHandler;
import com.senior.junkbot.Main;
import com.senior.junkbot.MusicHandler;
import com.senior.junkbot.state.GameState;
import com.senior.junkbot.util.Font;

import config.Config;

import static sound.Sounds.*;

public class LoadState extends GameState {
	private int loadCounter;
	private boolean rendered;
	private String[] files;
	
	public LoadState(Main main) {
		super(main);
		loadCounter = 0;
		rendered = true;
		MusicHandler.songs = new Sound[8];
		files = new String[] {
			"sfx/ground.wav",
			"sfx/jetpack.ogg",
			"sfx/jump.ogg",
			"sfx/jetpackGet.wav",
			"sfx/levelComplete.wav",
			"sfx/select.wav",
			"sfx/optionChange.wav",
			"sfx/pause.wav",
			"music/menu/Aethereal.ogg",
			"music/menu/eCommerce.ogg",
			"music/menu/Hackers Title.ogg",
			"music/level/2ndBallad.ogg",
			"music/level/AngryRobotIII.ogg",
			"music/level/Battle.ogg",
			"music/level/Causeway.ogg",
			"music/level/Club Fight.ogg"
		};
		try {
			AL.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
	}
	
	public void tick(InputHandler input) {
		if(rendered) {
			switch(loadCounter) {
				case 0: ground = loadSound(files[loadCounter], false); break;
				case 1: jetpack = loadSound(files[loadCounter], true); break;
				case 2: jump = loadSound(files[loadCounter], false); break;
				case 3: jetpackGet = loadSound(files[loadCounter], false); break;
				case 4: levelComplete = loadSound(files[loadCounter], false); break;
				case 5: select = loadSound(files[loadCounter], false); break;
				case 6: optionChange = loadSound(files[loadCounter], false); break;
				case 7: pause = loadSound(files[loadCounter], false); break;
				case 8: MusicHandler.songs[0] = loadSound(files[loadCounter], true); break;
				case 9: MusicHandler.songs[1] = loadSound(files[loadCounter], true); break;
				case 10: MusicHandler.songs[2] = loadSound(files[loadCounter], true); break;
				case 11: MusicHandler.songs[3] = loadSound(files[loadCounter], true); break;
				case 12: MusicHandler.songs[4] = loadSound(files[loadCounter], true); break;
				case 13: MusicHandler.songs[5] = loadSound(files[loadCounter], true); break;
				case 14: MusicHandler.songs[6] = loadSound(files[loadCounter], true); break;
				case 15: MusicHandler.songs[7] = loadSound(files[loadCounter], true); break;
				default: main.changeState(new MainMenuState(main));
			}
			rendered = false;
			loadCounter++;
		}
	}
	
	public void render(Screen screen) {
		int color = 0xFF00FF00;
		screen.drawColored(Bitmaps.loading, color, (Config.width - Bitmaps.loading.getWidth()) / 2, (Config.height - Bitmaps.loading.getHeight()) / 2 - 20);
		Font.drawStringColored(files[loadCounter - 1], color, (Config.width - Font.getPhraseWidth(files[loadCounter - 1])) / 2, (Config.height - Bitmaps.font[0].getHeight()) / 2 + 20, screen);
		int barX = (Config.width - 64) / 2;
		int barY = (Config.height - 10) / 2 + 40;
		screen.draw(Bitmaps.progressBorder, barX - 2, barY - 2);
		screen.fillRect(0xFF99E550, barX, barY, (int) ((loadCounter / 16.0) * 64), 2);
		screen.fillRect(0xFF00FF00, barX, barY + 2, (int) ((loadCounter / 16.0) * 64), 8);
		rendered = true;
	}

}
