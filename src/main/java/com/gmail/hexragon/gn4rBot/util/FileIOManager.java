package com.gmail.hexragon.gn4rBot.util;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class FileIOManager
{
	private final File file;

	public FileIOManager(File file)
	{
		this.file = file;
	}

	public FileIOManager(String path)
	{
		this.file = new File(path);
	}

	public String readString()
	{
		return StringUtils.join(readList(), "\n");
	}

	public List<String> readList()
	{
		try
		{
			return Files.readAllLines(file.toPath());
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public synchronized void writeFile(List<String> list)
	{
		writeFile(StringUtils.join(list, "\n"));
	}

	public synchronized void writeFile(String content)
	{
		writeFile(content, false);
	}

	public synchronized void writeFile(List<String> list, boolean append)
	{
		writeFile(StringUtils.join(list, "\n"), append);
	}

	public synchronized void writeFile(String content, boolean append)
	{
		FileWriter writer = null;

		try
		{
			writer = new FileWriter(file, append);

			writer.write(content);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if (writer != null) writer.close();
			}
			catch (IOException ex)
			{
				ex.printStackTrace();
			}
		}
	}
}
