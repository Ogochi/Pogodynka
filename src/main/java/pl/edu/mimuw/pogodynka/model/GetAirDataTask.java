package pl.edu.mimuw.pogodynka.model;

import javafx.concurrent.Task;
import pl.edu.mimuw.pogodynka.network.AirConditionDataSource;

public class GetAirDataTask extends Task<AirConditionData> {
	private AirConditionDataSource source;

	@Override
	protected AirConditionData call() throws Exception {
		return source.getData();
	}
	
	public GetAirDataTask(AirConditionDataSource wds) {
		this.source = wds;
	}
}
