package pl.edu.mimuw.pogodynka.controller;

import java.util.Calendar;

import org.kordamp.ikonli.fontawesome.FontAwesome;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.weathericons.WeatherIcons;

import com.jfoenix.controls.JFXRadioButton;

import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import pl.edu.mimuw.pogodynka.app.Log;
import pl.edu.mimuw.pogodynka.model.AirConditionData;
import pl.edu.mimuw.pogodynka.model.GetAirDataTask;
import pl.edu.mimuw.pogodynka.model.GetWeatherDataTask;
import pl.edu.mimuw.pogodynka.model.Source;
import pl.edu.mimuw.pogodynka.model.WeatherData;
import pl.edu.mimuw.pogodynka.network.AirConditionDataSource;
import pl.edu.mimuw.pogodynka.network.WeatherDataSource1;
import pl.edu.mimuw.pogodynka.network.WeatherDataSource2;

public class PogodynkaAppController {
	private Source source = Source.OPENWEATHER;
	
	private final AirConditionDataSource airSource = new AirConditionDataSource();
	private final WeatherDataSource1 weatherSource1 = new WeatherDataSource1();
	private final WeatherDataSource2 weatherSource2 = new WeatherDataSource2();
	
	private final int periodOfAutoRefreshing = 180000; // 3 minutes
	
	private StackPane placeForWindDirectionIcon;
	
	final ToggleGroup group = new ToggleGroup();
	
	@FXML
	private Label lastUpdate;
	
	@FXML
	private Button refreshButton;
	
	@FXML
	private JFXRadioButton openWeatherRadioButton;
	
	@FXML
	private JFXRadioButton meteoRadioButton;
	
	@FXML
	private Label windSpeedAndDirectionLabel;
	
	@FXML
	private Label humidityLabel;
	
	@FXML
	private Label temperatureLabel;
	
	@FXML
	private Label cloudinessLabel;
	
	@FXML
	private Label pressureLabel;
	
	@FXML
	private Label pm2_5LevelLabel;
	
	@FXML
	private Label pm10LevelLabel;
	
	@FXML
	private Label directionIconLabel;
	
	@FXML
	private Label l1, l2, l3, l4 ,l5, l6;
	
	@FXML
	private void initialize() { 
		setIcons();
		
		manageRadioButtons();
		
		refreshButton.setOnMouseClicked((MouseEvent e) -> {
			Log.info("Updating weather info.");
			
			updatingWeather();
		});
		
		updateWeatherPeriodically();
	}
	
	/**
	 * Updates weather periodically.
	 * Period is specified in 'periodOfAutoRefreshing'
	 */
	private void updateWeatherPeriodically() {
		Task<Integer> updateWeather = new Task<Integer>() {
			@Override
			protected Integer call() throws Exception {
				while(true) {
					updatingWeather();
					
					Thread.sleep(periodOfAutoRefreshing);
				}
			}
		};
		
		startTask(updateWeather);
	}
	
	private void updatingWeather() {
		Task<WeatherData> weatherTask;
		Task<AirConditionData> airTask = new GetAirDataTask(airSource);
		
		if(source == Source.OPENWEATHER)
			weatherTask = new GetWeatherDataTask(weatherSource1);
		else
			weatherTask = new GetWeatherDataTask(weatherSource2);
		
		weatherTask.setOnSucceeded((WorkerStateEvent e) -> {
			temperatureLabel.setText(weatherTask.getValue().getTemperature());
			humidityLabel.setText(weatherTask.getValue().getHumidity());
			cloudinessLabel.setText(weatherTask.getValue().getCloudiness());
			pressureLabel.setText(weatherTask.getValue().getPressure());
			windSpeedAndDirectionLabel.setText(weatherTask.getValue().getWindSpeed());
			
			if(!weatherTask.getValue().getWindDirectionInDegrees().equals("-"))
				setWindDirection(Integer.parseInt(weatherTask.getValue().getWindDirectionInDegrees()));
			else
				placeForWindDirectionIcon.visibleProperty().set(false);
			
			updateTime();
		});
		
		airTask.setOnSucceeded((WorkerStateEvent e) -> {
			pm2_5LevelLabel.setText(airTask.getValue().getPM2_5());
			pm10LevelLabel.setText(airTask.getValue().getPM10());
			
			updateTime();
		});
		
		startTask(weatherTask);
		startTask(airTask);
	}
	
	@SuppressWarnings("rawtypes")
	private void startTask(Task t) {
		Thread th = new Thread(t);
        th.setDaemon(true);
        
        th.start();
	}
	
	private void updateTime() {
		int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
		int minutes = Calendar.getInstance().get(Calendar.MINUTE);
		int seconds = Calendar.getInstance().get(Calendar.SECOND);
		
		lastUpdate.setText("Last update: " + Integer.toString(hour) + ":" + 
						   Integer.toString(minutes) + ":" + Integer.toString(seconds));
	}
	
	private void manageRadioButtons() {
		openWeatherRadioButton.setToggleGroup(group);
		meteoRadioButton.setToggleGroup(group);
		
		openWeatherRadioButton.setSelected(true);
		
		openWeatherRadioButton.setOnAction((ActionEvent e) -> {
			source = Source.OPENWEATHER;
		});
		
		meteoRadioButton.setOnAction((ActionEvent e) -> {
			source = Source.METEO;
		});
	}
	
	private void setWindDirection(int degrees) {
		directionIconLabel.rotateProperty().set(degrees);
		
		placeForWindDirectionIcon.visibleProperty().set(true);
	}
	
	private void setIcons() {
		StackPane sp = new StackPane();
		sp.getChildren().add(new FontIcon( WeatherIcons.THERMOMETER));
		l1.setGraphic(sp); l1.setScaleX(2.5); l1.setScaleY(2.5);
		
		sp = new StackPane();
		sp.getChildren().add(new FontIcon( WeatherIcons.findByDescription("wi-barometer")));
		l2.setGraphic(sp); l2.setScaleX(2.5); l2.setScaleY(2.5);
		
		sp = new StackPane();
		sp.getChildren().add(new FontIcon( WeatherIcons.findByDescription("wi-cloudy")));
		l3.setGraphic(sp); l3.setScaleX(2.5); l3.setScaleY(2.5);
		
		sp = new StackPane();
		sp.getChildren().add(new FontIcon( WeatherIcons.findByDescription("wi-strong-wind")));
		l4.setGraphic(sp); l4.setScaleX(2.5); l4.setScaleY(2.5);
		
		sp = new StackPane();
		sp.getChildren().add(new FontIcon( WeatherIcons.findByDescription("wi-humidity")));
		l5.setGraphic(sp); l5.setScaleX(2.5); l5.setScaleY(2.5);
		
		sp = new StackPane();
		sp.getChildren().add(new FontIcon( WeatherIcons.findByDescription("wi-celsius")));
		l6.setGraphic(sp); l6.setScaleX(2.5); l6.setScaleY(2.5);
		
		sp = new StackPane();
		sp.getChildren().add(new FontIcon(FontAwesome.findByDescription("fa-refresh")));
		refreshButton.setGraphic(sp); refreshButton.setScaleX(2.5); refreshButton.setScaleY(2.5);
		
		sp = new StackPane();
		placeForWindDirectionIcon = sp;
		sp.getChildren().add(new FontIcon(WeatherIcons.findByDescription("wi-direction-up")));
		sp.visibleProperty().set(false);
		directionIconLabel.setGraphic(sp); directionIconLabel.setScaleX(2.5); directionIconLabel.setScaleY(2.5);
	}
}
