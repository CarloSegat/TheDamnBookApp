package ui;

import BookGang.Book;
import BookGang.BookApp;
import books.events.CreateBookDetailsViewEvent;
import books.events.SearchCompleteEvent;
import javafx.event.EventType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class BookResult extends GridPane {
	
	public static EventType<CreateBookDetailsViewEvent> MAKE_VIEW = new EventType<>("MAKE_VIEW");
	private BookApp bookApp;
    private Book book;

	public BookResult(Book item, BookApp bookApp) {
		if(item == null || bookApp == null) {
			throw new NullPointerException();
		}
		this.book = item;
		this.bookApp = bookApp;
		this.setStyle("-fx-border-color: #555100;");
		Text text1 = new Text(format(item.getTitle())); 
		text1.setStyle("-fx-font-weight: bold;");
		Text text2;
		text2 = new Text(format(item.getAuthor()));
		Text text3 = new Text(item.getPublisher());       
		this.add(text1, 0, 0);
		this.add(text2, 0, 1);
		this.add(text3, 0, 2);
		this.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
			bookApp.getRoot().getChildren().add(new BookDetail(this.book));
		});
	}
	
	private String format(String title) {
		String t = title.strip();
		String f = title;
		if(title.length() > 32 && t.indexOf(" ", 30) != -1) {
			f = t.substring(0, t.indexOf(" ", 30)) +
				"\n" + 
				t.substring(t.indexOf(" ", 30)+1);
		}
		return f;
	}

}
