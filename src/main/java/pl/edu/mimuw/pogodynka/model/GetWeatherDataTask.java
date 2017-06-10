package pl.edu.mimuw.pogodynka.model;

import javafx.concurrent.Task;
import pl.edu.mimuw.pogodynka.network.WeatherDataSource;

public class GetWeatherDataTask extends Task<WeatherData> {
	private WeatherDataSource source;

	@Override
	protected WeatherData call() throws Exception {
		return source.getData();
	}
	
	public GetWeatherDataTask(WeatherDataSource wds) {
		this.source = wds;
	}
}
