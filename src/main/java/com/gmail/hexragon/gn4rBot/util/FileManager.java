package com.gmail.hexragon.gn4rBot.util;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class FileManager
{
    private final File file;
    
    public FileManager(File file)
    {
        this.file = file;
    }
    
    public FileManager(String path)
    {
        this.file = new File(path);
    }
    
    public String readText()
    {
        return StringUtils.join(readLines(), "\n");
    }
    
    public List<String> readLines()
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
    
    public synchronized void writeText(List<String> list)
    {
        writeText(StringUtils.join(list, "\n"));
    }
    
    public synchronized void writeText(String content)
    {
        writeText(content, false);
    }
    
    public synchronized void writeText(List<String> list, boolean append)
    {
        writeText(StringUtils.join(list, "\n"), append);
    }
    
    public synchronized void writeText(String content, boolean append)
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
    
    public boolean createIfNotFound()
    {
        if (!file.exists())
        {
            try
            {
                return file.createNewFile();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        return false;
    }
    
    public File getFile()
    {
        return file;
    }
}
