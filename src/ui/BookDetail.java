package ui;

import BookGang.Book;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class BookDetail extends GridPane {

	public BookDetail(Book book) {
		this.setStyle("-fx-border-color: #555100;"); 
		this.add(new Text(book.getAuthor()), 0, 0);
		this.add(new Text("gang"), 0, 0);
		this.add(new Text("gang"), 0, 0);
	}
	
}
