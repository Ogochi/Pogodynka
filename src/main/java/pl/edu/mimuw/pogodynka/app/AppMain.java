package pl.edu.mimuw.pogodynka.app;

import pl.edu.mimuw.pogodynka.network.AirConditionDataSource;
import pl.edu.mimuw.pogodynka.network.WeatherDataSource1;
import pl.edu.mimuw.pogodynka.network.WeatherDataSource2;

public class AppMain {

	public static void main(String[] args) {
		AirConditionDataSource acds = new AirConditionDataSource();
		WeatherDataSource1 wds = new WeatherDataSource1();
		WeatherDataSource2 wds2 = new WeatherDataSource2();
		
		System.out.println(acds.getData());
		System.out.println(wds.getData());
		System.out.println(wds2.getData());
		
	}
}
