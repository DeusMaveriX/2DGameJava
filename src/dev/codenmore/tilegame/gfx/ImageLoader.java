package dev.codenmore.tilegame.gfx;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageLoader
{
	//string path = location of image
	public static BufferedImage loadImage(String path)
	{
		try
		{
			return ImageIO.read(ImageLoader.class.getResource(path));
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		}
		return null;
	}
}
