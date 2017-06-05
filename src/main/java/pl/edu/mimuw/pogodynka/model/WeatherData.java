package pl.edu.mimuw.pogodynka.model;

import pl.edu.mimuw.pogodynka.app.Log;

public class WeatherData {
	private final String temperature;
	private final String pressure;
	private final String cloudiness;
	private final String windPower;
	private final String windDirectionInDegrees;
	private final String humidity;
	
	public String getTemperature() {
		return temperature;
	}
	
	public String getPressure() {
		return pressure;
	}
	
	public String getCloudiness() {
		return cloudiness;
	}
	
	public String getWindPower() {
		return windPower;
	}
	
	public String getWindDirection() {
		return windDirectionInDegrees;
	}
	
	public String getHumidity() {
		return humidity;
	}
	
	public WeatherData(String tem, String pre, String clou, String wip, String wid, String hum) {
		temperature = tem;
		pressure = pre + " hPa";
		cloudiness = clou + " %";
		windPower = wip + " m/s";
		windDirectionInDegrees = wid;
		humidity = hum + " %";
		
		Log.info(this.toString());
	}
	
	@Override
	public String toString() {
		return "Temperature: " + temperature + 
				", Pressure: " + pressure +
				", Cloudiness: " + cloudiness +
				", WindPower: " + windPower +
				", WindDirection: " + windDirectionInDegrees + 
				", Humidity: " + humidity;
	}
}
