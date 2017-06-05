package pl.edu.mimuw.pogodynka.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

public class FromUrlReader {
	private static String readAll(Reader reader) throws IOException {
	    StringBuilder stringBuilder = new StringBuilder();
	    int cp;
	    
	    while ((cp = reader.read()) != -1) {
	    	stringBuilder.append((char) cp);
	    }
	    
	    return stringBuilder.toString();
	}

	public static String getUrlContent(String url) throws IOException {
	    InputStream inputStream = new URL(url).openStream();
	    
	    try {
		    BufferedReader bufferReader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
		    String text = readAll(bufferReader);
		    return text;
	    } finally {
	    	inputStream.close();
	    }
	}
}
