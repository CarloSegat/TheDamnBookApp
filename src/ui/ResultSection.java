package ui;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import BookGang.Book;
import BookGang.BookApp;
import books.events.CreateBookDetailsViewEvent;
import books.events.SearchCompleteEvent;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.event.EventType;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class ResultSection extends ScrollPane {
	
	private BookApp ba;

	public ResultSection(ArrayList<Book> books, BookApp ba) {
		
		this.ba = ba;
    	int outerY = 0;
    	GridPane outer = new GridPane();
    	for(Book item: books) {
    		outer.add(new BookResult(item, ba), 0, ++outerY);
    	}
      	
    	ColumnConstraints columnConstraints = new ColumnConstraints();
    	columnConstraints.setHgrow(Priority.NEVER);
    	columnConstraints.setPercentWidth(100.00);

    	
    	RowConstraints rowConstraints = new RowConstraints();
    	rowConstraints.setVgrow(Priority.NEVER);
    	
    	
    	outer.getRowConstraints().add(rowConstraints);
    	outer .getColumnConstraints().add(columnConstraints);
    	this.setContent(outer);
    	this.setPrefHeight(580);
    	this.setPrefWidth(300);
    	//this.pannableProperty().set(true);
    	//this.add(outer, 0, 0);
    	this.setTranslateX(BookApp.WIDTH / 2.6);
    	this.setTranslateY(BookApp.HEIGHT / 21);
    	this.animateAppear();
	}
		
	public void animateAppear() {
		 TranslateTransition translateRotation = new TranslateTransition();
		  translateRotation.setDuration(BookApp.SEARCH_ANIMATION_DURATION);
		  translateRotation.setNode(this); 
		  translateRotation.setByY(0);
		  translateRotation.setByX(250);
		  translateRotation.setCycleCount(1);
		  
		  FadeTransition ft = new FadeTransition(Duration.millis(3000));
		  ft.setDuration(BookApp.SEARCH_ANIMATION_DURATION);
		  ft.setNode(this);
		  ft.setFromValue(0);
		  ft.setToValue(1);
		  ft.setCycleCount(1);
		  
		  ParallelTransition pt = new ParallelTransition(ft, translateRotation);
		  pt.setAutoReverse(false);
		  pt.setCycleCount(1);
		  pt.play();
	}
	
	private String pad(String s) {
		if(s != null && 40 - s.length() > 0) {
			return " ".repeat((40 - s.length()));
		}
		return " ";
	}

	public void animateDisappear() {
		TranslateTransition translateRotation = new TranslateTransition();
		  translateRotation.setDuration(Duration.millis(400));
		  translateRotation.setNode(this); 
		  translateRotation.setByY(0);
		  translateRotation.setByX(-250);
		  translateRotation.setCycleCount(1);
		  
		  FadeTransition ft = new FadeTransition(Duration.millis(3000));
		  ft.setDuration(Duration.millis(400));
		  ft.setNode(this);
		  ft.setFromValue(1);
		  ft.setToValue(0);
		  ft.setCycleCount(1);
		  
		  ParallelTransition pt = new ParallelTransition(ft, translateRotation);
		  pt.setAutoReverse(false);
		  pt.setCycleCount(1);
		  pt.play();

	}
}
