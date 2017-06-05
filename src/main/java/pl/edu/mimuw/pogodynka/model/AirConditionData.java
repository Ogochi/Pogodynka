package pl.edu.mimuw.pogodynka.model;

import pl.edu.mimuw.pogodynka.app.Log;

public class AirConditionData {
	private final String PM2_5;
	
	private final String PM10;
	
	public AirConditionData(double pm2_5, double pm10) {
		if(pm2_5 == -1)
			this.PM2_5 = "-";
		else {
			pm2_5 = Math.round(pm2_5 * 100000) / 100000.0;
			this.PM2_5 = Double.toString(pm2_5);
		}
		
		if(pm10 == -1)
			this.PM10 = "-";
		else {
			pm10 = Math.round(pm10 * 100000) / 100000.0;
			this.PM10 = Double.toString(pm10);
		}
		
		Log.info(this.toString());
	}

	public String getPM2_5() {
		return PM2_5;
	}
	
	public String getPM10() {
		return PM10;
	}
	
	@Override
	public String toString() {
		return "PM2_5: " + PM2_5 + ", PM10: " + PM10;
	}
}
