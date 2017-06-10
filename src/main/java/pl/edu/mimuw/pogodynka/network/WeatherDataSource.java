package pl.edu.mimuw.pogodynka.network;

import pl.edu.mimuw.pogodynka.model.WeatherData;

public interface WeatherDataSource {
	/**
	 * Returns WeatherData object that holds information about weather
	 * @return WeatherData for specific town using data from url specified in the task
	 */
	public WeatherData getData();
}
