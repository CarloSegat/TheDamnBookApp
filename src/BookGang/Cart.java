package BookGang;

public class Cart {
	
	private final int maxNumberItems = 3;
	private Book[] items = new Book[3];
	private int pos = 0;
	
	public void addItem(Book book) {
		if(book != null && pos != this.maxNumberItems) {
			this.items[pos] = book;
			this.pos++;
		}
	}
	
	public void removeItem() { 
		if(pos != 0) {
			this.items[--pos] = null;
		}
	}
	
	public void viewContent() {
		for(Book b : items) {
			if(b != null)
			b.printBook();
		}
	}
	
	// modifcare
	
}
