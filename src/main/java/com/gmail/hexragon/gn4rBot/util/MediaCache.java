package com.gmail.hexragon.gn4rBot.util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.util.HashMap;

public class MediaCache
{
	public static HashMap<String, File> imageCache = new HashMap<>();
	public static HashMap<String, String> gifCache = new HashMap<>();
	public static HashMap<String, String> vineCache = new HashMap<>();

	public synchronized static void cacheImage(String url, String extension, String name) {
		if (extension.equalsIgnoreCase("gif")) {
			gifCache.put(name, url);
			return;
		}
		if(extension.equalsIgnoreCase("vine")) {
			vineCache.put(name, url);
			return;
		}
		try {
			File imgf = new File("_DATA/images/pics/" + name + "." + extension);
			BufferedImage img = ImageIO.read(new URL(url));
			ImageIO.write(img, extension, imgf);
			imageCache.put(name, imgf);
		} catch (Exception e) {
			System.out.println("Was not able to load image " + name);
			e.printStackTrace();
		}
	}

	public synchronized void cacheImages() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File("_DATA/images/imageCache.txt")));
			String line = "";
			while ((line = br.readLine()) != null) {
				try {
					String[] another = line.split("!=");
					cacheImage(another[1], another[2], another[0]);

				} catch (Exception e) {
					continue;
				}
			}
			br.close();
		} catch (Exception e) {
		}
	}
}
