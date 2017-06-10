package pl.edu.mimuw.pogodynka.app;

import java.io.IOException;

import com.jfoenix.controls.JFXDecorator;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import pl.edu.mimuw.pogodynka.controller.PogodynkaAppController;

public class AppMain extends Application {
	private static final String FXML_MAIN_FORM_TEMPLATE = "/fxml/Pogodynka-main.fxml";
	//private static final String FXML_CLOSE_DIALOG_TEMPLATE = "/fxml/close-dialog.fxml";
	
	private static final String FONT_CSS = "/css/jfoenix-fonts.css";
	private static final String MATERIAL_CSS = "/css/jfoenix-design.css";
	private static final String POGODYNKA_CSS = "/css/Pogodynka.css";
	
	private static final String LOGO = "/icons/earth-globe.png";
	
	private Stage mainStage;
	
	private static void setupTextRendering() {
		System.setProperty("prism.text", "t2k");
		System.setProperty("prism.lcdtext", "true");
	}

	@Override
	public void start(Stage primaryStage) throws IOException {
		Log.info("Starting Pogodynka application...");
		
		mainStage = primaryStage;

		FXMLLoader loader = new FXMLLoader(getClass().getResource(FXML_MAIN_FORM_TEMPLATE));
		loader.setController(new PogodynkaAppController());
		Parent root = loader.load();
			
		JFXDecorator decorator = new JFXDecorator(primaryStage, root, false, false, true);
		decorator.setOnCloseButtonAction(this::onClose);
		
		Scene scene = new Scene(decorator);
		scene.setFill(null);
		
		scene.getStylesheets().addAll(AppMain.class.getResource(FONT_CSS).toExternalForm(),
				AppMain.class.getResource(MATERIAL_CSS).toExternalForm(),
				AppMain.class.getResource(POGODYNKA_CSS).toExternalForm());
		
		Log.info("Application's up and running!");
		
		primaryStage.setScene(scene);

		primaryStage.setWidth(450);
		primaryStage.setHeight(300);
		primaryStage.setResizable(false);
		
		primaryStage.getIcons().add(new Image(LOGO));

		primaryStage.show();
	}
	
	private void onClose() {
		Log.info("Closing Application!");
		
		AppMain.this.mainStage.close();
		
		System.exit(0);
	}

	public static void main(String[] args) {
		setupTextRendering();

		Platform.setImplicitExit(true);
		
		launch(args);
	}
}
