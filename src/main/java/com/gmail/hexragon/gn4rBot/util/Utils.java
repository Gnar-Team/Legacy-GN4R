package com.gmail.hexragon.gn4rBot.util;

import org.json.JSONException;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class Utils
{
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

	public static JSONObject readJsonFromUrl(String url) throws JSONException
	{
		try (InputStream is = new URL(url).openStream())
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
}
