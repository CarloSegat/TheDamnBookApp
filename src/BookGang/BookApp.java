package BookGang;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Map;

import com.google.gson.Gson;

import google.books.Api;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.EventType;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import ui.ResultSection;
import ui.SearchWidget;

public class BookApp extends Application {

    public static final double HEIGHT = 1100;
	public static final double WIDTH = 1600;
	public static final Duration SEARCH_ANIMATION_DURATION = Duration.millis(800);
	private SearchWidget searchWidget = new SearchWidget(this);
	private ResultSection resultSection;
	private static Group root;
	
	public static Group getRoot() {
		return root;
	}

	public ResultSection getResultSection() {
		return resultSection;
	}

	public void setResultSection(ResultSection resultSection) {
		this.resultSection = resultSection;
	}

	public static EventType<InputEvent> inputEvent = new EventType<>("InputEvent");
	
	@Override
    public void init() throws Exception {
        super.init();
        System.out.println("Inside init() method! Perform necessary initializations here.");
    }

    @Override
    public void start(Stage stagetage) throws Exception {
    	
    	ImageView imageView = makeBackgroundImageLogo();  
        root = new Group(imageView);  
        
        root.getChildren().add(searchWidget);
        Scene scene = new Scene(root, 1200, 900);  
        searchWidget.toFront();
        stagetage.setTitle("Hello World Application");
        stagetage.setScene(scene);
        stagetage.show();
        
    }

	private ImageView makeBackgroundImageLogo() throws FileNotFoundException {
		Image image = new Image(new FileInputStream("C:\\Users\\Utente\\eclipse-workspace\\BooksGang\\assets\\facebook_profile_image.png"));  
        
        ImageView imageView = new ImageView(image); 
        
        imageView.setX(0); 
        imageView.setY(0); 
        
        imageView.setFitHeight(HEIGHT); 
        imageView.setFitWidth(WIDTH); 
        
        imageView.setPreserveRatio(true);
		return imageView;
	}

	@Override
    public void stop() throws Exception {
        super.stop();
        System.out.println("Inside stop() method! Destroy resources. Perform Cleanup.");
    }

    public static void main(String[] args) throws Exception {
//    	Api.saveStream("http://books.google.com/books/content?id=ewHadgmYVVgC&printsec=fron"
//    			+ "tcover&img=1&zoom=3&edge=curl&imgtk=AFLRE72L8WyvbWL5Hv_oaMehdVNjgqeC8PT3"
//    			+ "PGirtB06kJxbuea6O_ItUmS-aD-K_iB1IkPnrHOu0SmZ3c_phDMoY6_E9kpFHJj2UlxvCvd"
//    			+ "JXs4tZIYuskiv92e3ZgIQrWi7WXNGDZ5c&source=gbs_api",
//    			"assets/image2.png");
        launch(args);
    }
    
    private String pad(String s) {
		if(s != null && 50 - s.length() > 0) {
			return " ".repeat((50 - s.length()));
		}
		return "";
	}
}