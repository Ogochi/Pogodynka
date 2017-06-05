package pl.edu.mimuw.pogodynka.network;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import pl.edu.mimuw.pogodynka.app.Log;
import pl.edu.mimuw.pogodynka.model.AirConditionData;

public class AirConditionDataSource {
	private static final String URL = "http://powietrze.gios.gov.pl/pjp/current/getAQIDetailsList?param=AQI";

	private static final String CITY_NAME = "Warszawa";
	
	/**
	 * Returns AirConditionData object that holds average PM2.5 and PM10
	 * @return AirConditionData for specific town using data from url
	 */
	public AirConditionData getData() {
		String httpSource = "";
		double pm2_5Level = 0, pm10Level = 0;
		int numberOfStationsWithPm2_5Data = -1, numberOfStationsWithPm10Data = -1;
		
		try{
			httpSource = FromUrlReader.getUrlContent(URL);
		} catch(Exception e) {
			Log.err("AirConditionData - Couldn't read from url.");
		}
		
		if(httpSource.length() != 0) {
			for(JsonElement e : JsonHelper.asJsonArray(httpSource)) {
				JsonObject jo = e.getAsJsonObject();
				
				if(jo.get("stationName").getAsString().contains(CITY_NAME) &&
				   jo.get("values").getAsJsonObject().has("PM10")) {
						pm10Level += jo.get("values").getAsJsonObject().get("PM10").getAsDouble();
						numberOfStationsWithPm10Data++;	
				}
				
				if(e.getAsJsonObject().get("stationName").getAsString().contains(CITY_NAME) &&
				   e.getAsJsonObject().get("values").getAsJsonObject().has("PM2.5")) {
						pm2_5Level += jo.get("values").getAsJsonObject().get("PM2.5").getAsDouble();
						numberOfStationsWithPm2_5Data++;	
				}
			}
		}
		
		return new AirConditionData(numberOfStationsWithPm2_5Data != -1 ? 
									pm2_5Level / (numberOfStationsWithPm2_5Data + 1) : -1,
									numberOfStationsWithPm10Data != -1 ? 
									pm10Level / (numberOfStationsWithPm10Data + 1) : -1);
	}
	
	public  AirConditionDataSource() {}
}
