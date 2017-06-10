package pl.edu.mimuw.pogodynka.network;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pl.edu.mimuw.pogodynka.app.Log;
import pl.edu.mimuw.pogodynka.model.WeatherData;

public class WeatherDataSource2 implements WeatherDataSource {
	private static final String URL = "http://www.meteo.waw.pl/";
	
	@Override
	public WeatherData getData() {
		String temp, press, cloud, windPow, windDir, humid;
		temp = press = cloud = windPow = windDir = humid = "-";
		String httpSource = "";
		
		try{
			httpSource = FromUrlReader.getUrlContent(URL);
		} catch(Exception e) {
			Log.err("WeatherData2 - Couldn't read from url.");
		}
		
		temp = findData(httpSource, "PARAM_0_TA");
		press = findData(httpSource, "PARAM_0_PR");
		windPow = findData(httpSource, "PARAM_0_WV");
		windDir = parseDirectionToDegrees(findData(httpSource, "PARAM_0_WDABBR"));
		humid = findData(httpSource, "PARAM_0_RH");
		
		return new WeatherData(temp, press, cloud, windPow, windDir, humid);
	}
	
	/**
	 * Finds data in httpSource using regex matching
	 * @param httpSource source code from specified url
	 * @param param first part of regex
	 * @return data we were looking for or '-'
	 */
	private String findData(String httpSource, String param) {
		Pattern pattern;
		
		if(param.equals("PARAM_0_WDABBR"))
			pattern = Pattern.compile(param + "\\\">[NSWE]*", Pattern.CASE_INSENSITIVE);
		else
			pattern = Pattern.compile(param + "\\\">[1234567890,]*", Pattern.CASE_INSENSITIVE);
		
		Matcher m = pattern.matcher(httpSource);
		
		if(m.find())
			return m.group(0).substring(param.length() + 2);
		else
			return "-";
	}
	
	private String parseDirectionToDegrees(String direction) {
		int result = 0;
		
		if(direction.charAt(0) == 'N') {
			if(direction.length() > 1) {
				if(direction.charAt(1) == 'W')
					result = 315;
				else
					result = 45;
			}
		}
		
		if(direction.charAt(0) == 'S') {
			result = 180;
			
			if(direction.length() > 1) {
				if(direction.charAt(1) == 'W')
					result += 45;
				else
					result -= 45;
			}
		}
		
		if(direction.charAt(0) == 'W')
			result = 270;
		
		if(direction.charAt(0) == 'E')
			result = 90;
		
		return Integer.toString(result);
	}
	
	public WeatherDataSource2() {}
}
