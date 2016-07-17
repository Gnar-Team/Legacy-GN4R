package com.gmail.hexragon.gn4rBot.util;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MediaCache
{
	private FileIOManager fileIOManager;


	private Map<String, MediaSet> imageCache;

	private Map<String, MediaSet> gifCache;

	private Map<String, MediaSet> vineCache;

	public class MediaSet
	{
		private final String name;
		private final String extension;

		private MediaSet(String name, String extension)
		{
			this.name = name;
			this.extension = extension;
		}

		public String getName()
		{
			return name;
		}

		public String getExtension()
		{
			return extension;
		}
	}

	public MediaCache()
	{
		imageCache = new LinkedHashMap<>();
		gifCache = new LinkedHashMap<>();
		vineCache = new LinkedHashMap<>();

		fileIOManager = new FileIOManager(new File("_DATA/images/imageCache.txt"));
	}

	public void loadFromFile()
	{
		List<String> entries = fileIOManager.readList();

		entries.stream()
				.filter(s -> !s.isEmpty())
				.map(s -> s.split("!="))
				.forEach(strings -> cacheImage(strings[0], strings[1], strings[2]));
	}

	public void storeToFile()
	{
		List<String> entries = new ArrayList<>();

		entries.addAll(imageCache.entrySet().stream()
				.map(entry -> String.format("%s!=%s!=%s", entry.getKey(), entry.getValue().getName(), entry.getValue().getExtension()))
				.collect(Collectors.toList()));

		entries.add("");

		entries.addAll(gifCache.entrySet().stream()
				.map(entry -> String.format("%s!=%s!=%s", entry.getKey(), entry.getValue().getName(), entry.getValue().getExtension()))
				.collect(Collectors.toList()));

		entries.add("");

		entries.addAll(vineCache.entrySet().stream()
				.map(entry -> String.format("%s!=%s!=%s", entry.getKey(), entry.getValue().getName(), entry.getValue().getExtension()))
				.collect(Collectors.toList()));

		fileIOManager.writeFile(entries);

//		//TESTING
//		JSONObject obj = new JSONObject();
//		obj.put("wow.lol", imageCache);
//
//
////		//NOTE: you need to create base folder before server
////
////
//		FileReadingUtils.writeToFile(obj.toString(4), "_DATA/lmao.txt", false);
//
//		JSONObject obj2 = new JSONObject(FileReadingUtils.fileToString("_DATA/lmao.txt"));
//
//		//noinspection unchecked
//		Iterator<?> it = ((JSONObject) obj2.get("wow.lol")).keys();
//
//		while (it.hasNext())
//		{
//			String key = (String) it.next();
//			System.out.println(key);
//		}

	}

	public synchronized boolean cacheImage(String id, String url, String extension)
	{
		switch(extension.toLowerCase())
		{
			case "gif":
				gifCache.put(id, new MediaSet(url, extension));
				return true;

			case "vine":
				vineCache.put(id, new MediaSet(url, extension));
				return true;

			case "jpg":
			case "jpeg":
			case "png":
				imageCache.put(id, new MediaSet(url, extension));
				return true;

			default:
				throw new IllegalArgumentException("Extension '"+extension+"' is invalid.");
		}
	}

	public Map<String, MediaSet> getImageCache() {
		return imageCache;
	}

	public Map<String, MediaSet> getGifCache() {
		return gifCache;
	}

	public Map<String, MediaSet> getVineCache() {
		return vineCache;
	}
}
