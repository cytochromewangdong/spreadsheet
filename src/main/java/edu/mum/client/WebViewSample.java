package edu.mum.client;

import java.util.stream.Collectors;

import edu.mum.spreadsheet.SpreadSheet;
import edu.mum.spreadsheet.StatusChangeListener;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker.State;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import netscape.javascript.JSObject;

public class WebViewSample extends Application {

	private Scene scene;

	@Override
	public void start(Stage stage) {
		// create scene
		stage.setTitle("Web View");
		scene = new Scene(new Browser(), 750, 500, Color.web("#666970"));
		stage.setScene(scene);
		// apply CSS style
//		scene.getStylesheets().add("webviewsample/BrowserToolbar.css");
		// show stage
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}

class Browser extends Region implements StatusChangeListener<SpreadSheet> {

	private HBox toolBar;
	private static String[] imageFiles = new String[] { "product.png", "blog.png", "documentation.png", "partners.png",
			"help.png" };
	private static String[] captions = new String[] { "Refresh" };
	private static String[] urls = new String[] { 
			WebViewSample.class.getResource("help.html").toExternalForm() };
	final ImageView selectedImage = new ImageView();
	final Hyperlink[] hpls = new Hyperlink[captions.length];
	final Image[] images = new Image[imageFiles.length];
	final WebView browser = new WebView();
	final WebEngine webEngine = browser.getEngine();
	final Button showPrevDoc = new Button("Toggle Previous Docs");
	private boolean needDocumentationButton = false;
	SpreadSheet sheet = new SpreadSheet();

	public Browser() {

		Director director = new Director();
		director.fillSampleSheet(sheet);
		// apply the styles
		getStyleClass().add("browser");
		sheet.registerListener(this);
		for (int i = 0; i < captions.length; i++) {
			// create hyperlinks
			Hyperlink hpl = hpls[i] = new Hyperlink(captions[i]);
			Image image = images[i] = new Image(getClass().getResourceAsStream(imageFiles[i]));
			hpl.setGraphic(new ImageView(image));
			final String url = urls[i];
			final boolean addButton = (hpl.getText().equals("Documentation"));

			// process event
			hpl.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent e) {
					needDocumentationButton = addButton;
					webEngine.load(url);
				}
			});
		}

		// create the toolbar
		toolBar = new HBox();
		toolBar.setAlignment(Pos.CENTER);
		toolBar.getStyleClass().add("browser-toolbar");
		toolBar.getChildren().addAll(hpls);
		toolBar.getChildren().add(createSpacer());

//		// set action for the button
//		showPrevDoc.setOnAction(new EventHandler() {
//			@Override
//			public void handle(Event t) {
//				webEngine.executeScript("toggleDisplay('PrevRel')");
//			}
//		});

		// process page loading
		webEngine.getLoadWorker().stateProperty().addListener(new ChangeListener<State>() {
			@Override
			public void changed(ObservableValue<? extends State> ov, State oldState, State newState) {
				toolBar.getChildren().remove(showPrevDoc);
				if (newState == State.SUCCEEDED) {
					JSObject win = (JSObject) webEngine.executeScript("window");
//					win.setMember("app", new JavaApp());
					win.setMember("app", sheet);
					webEngine.executeScript("start()");
					if (needDocumentationButton) {
						toolBar.getChildren().add(showPrevDoc);
					}
				}
			}
		});

		// load the home page
		webEngine.load(WebViewSample.class.getResource("help.html").toExternalForm());

		// add components
		getChildren().add(toolBar);
		getChildren().add(browser);
	}

	// JavaScript interface object
	public class JavaApp {

		public void exit(JSObject o) {
			System.out.println(o.getMember("test"));
			Platform.exit();
		}
	}

	private Node createSpacer() {
		Region spacer = new Region();
		HBox.setHgrow(spacer, Priority.ALWAYS);
		return spacer;
	}

	@Override
	protected void layoutChildren() {
		double w = getWidth();
		double h = getHeight();
		double tbHeight = toolBar.prefHeight(w);
		layoutInArea(browser, 0, 0, w, h - tbHeight, 0, HPos.CENTER, VPos.CENTER);
		layoutInArea(toolBar, 0, h - tbHeight, w, tbHeight, 0, HPos.CENTER, VPos.CENTER);
	}

	@Override
	protected double computePrefWidth(double height) {
		return 750;
	}

	@Override
	protected double computePrefHeight(double width) {
		return 600;
	}

	@Override
	public void onChange(edu.mum.spreadsheet.observer.Event<SpreadSheet> event) {
		String idx = "[" + event.getParam().getChangedCellList().stream().map(c -> c.getRow() + "," + c.getColumn())
				.collect(Collectors.joining(",")) + "]";

		webEngine.executeScript("callback(" + idx + ")");
	}

}
