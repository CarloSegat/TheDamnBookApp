package ui;

import java.util.ArrayList;
import java.util.Map;

import com.google.gson.Gson;

import BookGang.Book;
import BookGang.BookApp;
import books.events.CreateBookDetailsViewEvent;
import books.events.SearchCompleteEvent;
import google.books.Api;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ParallelTransition;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.event.EventType;
import javafx.geometry.Bounds;
import javafx.geometry.Point3D;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.util.Duration;

public class SearchWidget extends GridPane {
	
	private TextField textField = new TextField();
	private Button button1 = new Button("Title"); 
    private Button button2 = new Button("Author"); 
    private Button button3 = new Button("Publisher"); 
	private ArrayList<Book> books;
	private boolean rotated = false;
	private double pivotX;
	private double pivotY;
	public static EventType<SearchCompleteEvent> SEARCH_DONE = new EventType<>("SEARCH_DONE");
	final Rotate rotationTransform = new Rotate(0, 0, 1);
	private BookApp mainApp;
	
	public SearchWidget(BookApp bookApp) {

		this.mainApp = bookApp;
		
		this.setOnKeyPressed(event -> {
			if (event.getCode() == KeyCode.ENTER){
				performSearch();
			} else {
				System.out.println("KEY PRESED ON SERCH WIDGET");
			}
		});
		
		this.addEventHandler(SEARCH_DONE, e -> {
        	this.animateSearchWidget();
        	mainApp.setResultSection(new ResultSection(this.getBooks(), this.mainApp));
        	mainApp.getRoot().getChildren().add(mainApp.getResultSection());
        	e.consume();
        });
		
		this.addEventHandler(KeyEvent.ANY, e -> {
        	if(e.getCode() != KeyCode.ENTER && this.isRotated()) {
        		this.resetPosition();
        		if(mainApp.getResultSection() != null) {
        			mainApp.getResultSection().animateDisappear();
        			mainApp.setResultSection(null);
        		}
        	}
        	e.consume();
        });
		
		this.addEventFilter(MouseEvent.MOUSE_CLICKED, 
				// For some reason event handler wont work here
        		e -> {
        			if(this.isRotated()) {
                		this.resetPosition();
                		if(mainApp.getResultSection() != null) {
                			mainApp.getResultSection().animateDisappear();
                			mainApp.setResultSection(null);
                		}
                	}
                });
	    
	    GridPane gridPaneButtons = new GridPane();    
	    gridPaneButtons.add(button1, 0, 1);
	    gridPaneButtons.add(button2, 1, 1);
	    gridPaneButtons.add(button3, 2, 1);
	    
	    GridPane gridPane = new GridPane();    
	    gridPane.add(textField, 0, 0);
	    gridPane.add(gridPaneButtons, 0, 1);
	    
	    this.setTranslateX(BookApp.WIDTH / 1.85);
	    this.setTranslateY(BookApp.HEIGHT / 4.1);
	    getChildren().addAll(gridPane);
	    Bounds localBounds = this.localToScene(this.getBoundsInLocal());
	    pivotX = localBounds.getMinX() - this.getTranslateX();
	    pivotY = localBounds.getMinY() - this.getTranslateY();
	    this.addEventHandler(MouseEvent.ANY, e -> {
	    	System.out.println("ganggggggggg");
	    });
	}

	private void performSearch() {
		String a = Api.callApi(textField.getText());
		this.books = Api.getBooksFromRequest(a);
		this.fireEvent(new SearchCompleteEvent(SEARCH_DONE));
	}
	
	public void animateSearchWidget() {
		
		 this.getTransforms().clear();
		 this.getTransforms().add(rotationTransform);
		 
		 rotationTransform.pivotXProperty().set(pivotX);
		 rotationTransform.pivotYProperty().set(pivotY);
		 
		 final Timeline rotationAnimation = new Timeline();
		    rotationAnimation.getKeyFrames()
		      .add(
		        new KeyFrame(
		         BookApp.SEARCH_ANIMATION_DURATION,
		          new KeyValue(
		            rotationTransform.angleProperty(),
		            -90
		          ),
		          new KeyValue(this.translateXProperty(), 800),
		          new KeyValue(this.translateYProperty(), 211)
		        )
		      );
	    rotationAnimation.setCycleCount(1);
	    rotationAnimation.setOnFinished(e -> {
	    	this.rotated = true;
	    });
	    rotationAnimation.play();
		
	}

	public ArrayList<Book> getBooks() {
		return books;
	}

	public boolean isRotated() {
		return rotated;
	}

	public void resetPosition() {
		
		 this.getTransforms().add(rotationTransform);
		 rotationTransform.pivotXProperty().set(pivotX);
		 rotationTransform.pivotYProperty().set(pivotY);
		 
		 final Timeline rotationAnimation = new Timeline();
		    rotationAnimation.getKeyFrames()
		      .add(
		        new KeyFrame(
		        		Duration.millis(300),
		          new KeyValue(
		            rotationTransform.angleProperty(),
		            0
		          ),
		          new KeyValue(this.translateXProperty(), 864.86),
		          new KeyValue(this.translateYProperty(), 268.29)
		        )
		      );
	    rotationAnimation.setCycleCount(1);
	    rotationAnimation.play();
		this.rotated = false;
		
		rotationAnimation.setOnFinished(
				e -> {
					this.getTransforms().clear();
					this.toFront();
					textField.appendText("");
				}	
		);
	}
}
