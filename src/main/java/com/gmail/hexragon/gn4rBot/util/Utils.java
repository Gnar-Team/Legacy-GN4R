package com.gmail.hexragon.gn4rBot.util;

import com.gmail.hexragon.gn4rBot.GnarBot;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class Utils
{

    public static JSONObject information;

    static
    {
        TrustManager[] trustAllCerts = new TrustManager[]
                {
                        new X509TrustManager()
                        {
                            public java.security.cert.X509Certificate[] getAcceptedIssuers()
                            {
                                return null;
                            }
                            
                            public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType)
                            {}
                            
                            public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType)
                            {}
                        }
                };
        
        try
        {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        }
        catch (Exception ignore) {}
    }
    
    public static String exceptionToString(Exception e)
    {
        StringBuilder errBuilder = new StringBuilder();
        
        Arrays.stream(e.getStackTrace())
                .forEach(ste -> errBuilder.append("  ").append(ste.toString()).append("\n"));
        
        return String.format("```xl\n%s%s```", e.toString(), errBuilder.toString());
    }
    
    private static String readAll(Reader rd) throws IOException
    {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1)
        {
            sb.append((char) cp);
        }
        return sb.toString();
    }
    
    public static JSONObject readJsonFromUrl(String url)
    {
        URLConnection uCon;
        try
        {
            uCon = new URL(url).openConnection();
            uCon.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
            uCon.connect();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
        
        try (InputStream is = uCon.getInputStream())
        {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            String jsonText = readAll(rd);
            return new JSONObject(jsonText);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public static void startLeagueChampInfo() {
        JSONObject jso = Utils.readJsonFromUrl("https://global.api.pvp.net/api/lol/static-data/na/v1.2/champion?champData=all&api_key=" + GnarBot.getAuthTokens().get("leauge"));

        System.out.println(jso.toString());

        information = (JSONObject) jso.get("data");

        System.out.println("League Champion Info Loaded");
        System.out.println(information.toString());
    }
}
