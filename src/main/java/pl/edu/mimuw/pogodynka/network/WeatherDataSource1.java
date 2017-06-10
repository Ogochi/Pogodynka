package pl.edu.mimuw.pogodynka.network;

import com.google.gson.JsonObject;

import pl.edu.mimuw.pogodynka.app.Log;
import pl.edu.mimuw.pogodynka.model.WeatherData;

public class WeatherDataSource1 implements WeatherDataSource {
	private static final String URL = "http://api.openweathermap.org/data/2.5/weather?q=Warsaw,pl"
									+ "&appid=8c26b4a938a0ffaffbcf33f2f877ab94";
	/**
	 * Returns WeatherData object that holds information about weather
	 * @return WeatherData for specific town using data from 'second' url specified in the task
	 */
	@Override
	public WeatherData getData() {
		String temp, press, cloud, windPow, windDir, humid;
		temp = press = cloud = windPow = windDir = humid = "-";
		String httpSource = "";
		
		try{
			httpSource = FromUrlReader.getUrlContent(URL);
		} catch(Exception e) {
			Log.err("WeatherData1 - Couldn't read from url.");
		}
		
		if(httpSource.length() != 0) {
			JsonObject mainData = JsonHelper.asJsonObject(httpSource).get("main").getAsJsonObject();
			JsonObject windData = JsonHelper.asJsonObject(httpSource).get("wind").getAsJsonObject();
			
			temp = Double.toString(mainData.get("temp").getAsDouble() - 273.15);
			press = mainData.get("pressure").getAsString();
			humid = mainData.get("humidity").getAsString();
			windPow = windData.get("speed").getAsString();
			windDir = windData.get("deg").getAsString();
			cloud = JsonHelper.asJsonObject(httpSource).get("clouds").getAsJsonObject().get("all").getAsString();
		}
		
		return new WeatherData(temp, press, cloud, windPow, windDir, humid);
	}
	
	public WeatherDataSource1() {}
}
