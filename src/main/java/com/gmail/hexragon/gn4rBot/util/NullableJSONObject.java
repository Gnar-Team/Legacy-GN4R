package com.gmail.hexragon.gn4rBot.util;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * If value is null/not found, instead of throwing an exception,
 * just return null instead.
 */
public class NullableJSONObject extends JSONObject
{
    public NullableJSONObject()
    {
        super();
    }
    
    public NullableJSONObject(String content)
    {
        super(content);
    }
    
    @Override
    public Object get(String key) throws JSONException
    {
        try
        {
            return super.get(key);
        }
        catch (JSONException e)
        {
            return null;
        }
    }
}
