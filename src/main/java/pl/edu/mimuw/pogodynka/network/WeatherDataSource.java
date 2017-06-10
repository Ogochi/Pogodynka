package pl.edu.mimuw.pogodynka.network;

import pl.edu.mimuw.pogodynka.model.WeatherData;

public interface WeatherDataSource {
	public WeatherData getData();
}
