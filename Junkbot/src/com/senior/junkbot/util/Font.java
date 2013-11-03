package com.senior.junkbot.util;

import bitmaps.Bitmaps;

import com.doobs.java2d.gfx.Bitmap;
import com.doobs.java2d.gfx.Screen;

public class Font {
	public static void drawString(String input, int xo, int yo, Screen screen) {
		drawStringColored(input, 0xFFFFFFFF, xo, yo, screen);
	}
	
	public static void drawStringColored(String input, int color, int xo, int yo, Screen screen) {
		Bitmap[] fontSheet = Bitmaps.font;
		for(int i = 0; i < input.length(); i++) {
			if(input.charAt(i) == 32) continue;
			screen.drawColored(fontSheet[parseCharToIndex(input.charAt(i))], color, xo + i * fontSheet[0].getWidth(), yo);
		}
	}
	
	public static void drawFixedDigitNumber(int input, int color, int numOfDigits, int xo, int yo, Screen screen) {
		int numLength = String.valueOf(input).length();
				
		String addon = "";
		for(int i = 0; i < numOfDigits - numLength; i++) {
			addon += "0";
		}
		
		drawStringColored(addon + input, color, xo, yo, screen);
	}
	
	public static int parseCharToIndex(char input) {
		int temp = (int) input;
		if(temp > 32 && temp < 97)
			return temp - 33;
		else if(temp > 96 && temp < 123)
			return temp - 65;
		else
			return 0;
	}
	
	public static char parseIndexToChar(int index) {
		if(index >= 0 && index <= 25)
			return (char) (97 + index);
		else if(index > 25 && index < 36)
			return (char) (48 + index - 26);
		else if(index == 36)
			return '-';
		else
			return ' ';
	}
	
	public static int getPhraseWidth(String phrase) {
		return phrase.length() * Bitmaps.font[0].getWidth();
	}
	
	/*public static void main(String[] args) {
		for(int i = 32; i < 126; i++) {
			System.out.print(i + " : " + (char) i);
		}
	}*/
}
