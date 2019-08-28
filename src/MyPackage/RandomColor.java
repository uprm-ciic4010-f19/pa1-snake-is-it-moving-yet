package MyPackage;

import java.awt.Color;
import java.util.Random;

public class RandomColor{
	
	static Random rand = new Random();
	static float red = rand.nextFloat();
	static float green = rand.nextFloat();
	static float blue = rand.nextFloat();
	public static Color randColor = new Color(red, green, blue);
	
}