package pl.edu.mimuw.pogodynka.model;

import pl.edu.mimuw.pogodynka.app.Log;

public class WeatherData {
	private String temperature;
	private String pressure;
	private String cloudiness;
	private String windSpeed;
	private String windDirectionInDegrees;
	private String humidity;
	
	public String getTemperature() {
		return temperature;
	}

	public String getPressure() {
		return pressure;
	}

	public String getCloudiness() {
		return cloudiness;
	}

	public String getWindSpeed() {
		return windSpeed;
	}

	public String getWindDirectionInDegrees() {
		return windDirectionInDegrees;
	}

	public String getHumidity() {
		return humidity;
	}
	
	public WeatherData(String tem, String pre, String clou, String wis, String wid, String hum) {
		temperature = tem;
		pressure = pre + " hPa";
		cloudiness = clou + " %";
		windSpeed = wis + " m/s";
		windDirectionInDegrees = wid;
		humidity = hum + " %";
		
		Log.info(this.toString());
	}
	
	@Override
	public String toString() {
		return "Temperature: " + temperature + 
				", Pressure: " + pressure +
				", Cloudiness: " + cloudiness +
				", WindPower: " + windSpeed +
				", WindDirection: " + windDirectionInDegrees + 
				", Humidity: " + humidity;
	}
}
