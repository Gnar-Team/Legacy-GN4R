package com.gmail.hexragon.gn4rBot.util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class MediaCache
{
	private final FileIOManager fileIOManager = new FileIOManager("_DATA/images/imageCache.txt");
	
	private final HashMap<String, File> imgFileCache;
	
	private final HashMap<String, String> imgCache;
	private final HashMap<String, String> gifCache;
	private final HashMap<String, String> vineCache;
	
	public MediaCache()
	{
		this.imgFileCache = new HashMap<>();
		
		this.imgCache = new LinkedHashMap<>();
		this.gifCache = new LinkedHashMap<>();
		this.vineCache = new LinkedHashMap<>();
		
		load();
		//store();
	}
	
	public synchronized void cacheImage(String id, String url, String extension)
	{
		switch (extension)
		{
			case "gif":
				gifCache.put(id, url);
				return;
			case "vine":
				vineCache.put(id, url);
				return;
			case "jpg":
			case "png":
			{
				try
				{
					File file = new File("_DATA/images/pics/" + id + "." + extension);
					
					if (!file.exists())
					{
						BufferedImage img = ImageIO.read(new URL(url));
						ImageIO.write(img, extension, file);
					}
					
					imgFileCache.put(id, file);
					imgCache.put(id, url);
					return;
				}
				catch (Exception e)
				{
					System.out.println("Was not able to load image " + id);
					e.printStackTrace();
				}
				
			}
			default:
				System.out.println("Was not able to load media id=" + id + " url=" + url + " extension=" + extension);
		}
	}
	
	public void store()
	{
		List<String> entries = new ArrayList<>();
		
		entries.addAll(imgCache.entrySet().stream()
				.map(entry -> String.format("%s!=%s!=%s", entry.getKey(), entry.getValue(), entry.getValue().substring(entry.getValue().lastIndexOf('.') + 1)))
				.collect(Collectors.toList()));
		
		entries.add("");
		
		entries.addAll(gifCache.entrySet().stream()
				.map(entry -> String.format("%s!=%s!=%s", entry.getKey(), entry.getValue(), "gif"))
				.collect(Collectors.toList()));
		
		entries.add("");
		
		entries.addAll(vineCache.entrySet().stream()
				.map(entry -> String.format("%s!=%s!=%s", entry.getKey(), entry.getValue(), "vine"))
				.collect(Collectors.toList()));
		
		fileIOManager.writeFile(entries);
	}
	
	public synchronized void load()
	{
		List<String> entries = fileIOManager.readList();
		
		entries.stream().filter(s -> !s.isEmpty()).map(s -> s.split("!=")).forEach(args -> cacheImage(args[0], args[1], args[2]));
	}
	
	public HashMap<String, File> getImgFileCache()
	{
		return imgFileCache;
	}
	
	public HashMap<String, String> getGifCache()
	{
		return gifCache;
	}
	
	public HashMap<String, String> getImgCache()
	{
		return imgCache;
	}
	
	public HashMap<String, String> getVineCache()
	{
		return vineCache;
	}
	
	public Map<String, String> getURLCaches()
	{
		Map<String, String> urlCaches = new HashMap<>();
		urlCaches.putAll(imgCache);
		urlCaches.putAll(gifCache);
		urlCaches.putAll(vineCache);
		return urlCaches;
	}
}
