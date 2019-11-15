package BookGang;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;

public class Book {
	
	private String codice;
	private String titolo;
	private String autore;
	private String casaEditrice;
	private String subtitle;
	private String publishedDate;
	private int pageCount;
	private String imageUrl;
	private BigDecimal prezzo;
	private File pdf;
	
	public File getPdf() {
		return pdf;
	}

	public void setPdf(String string) {
		this.pdf = new File(string);
	}

	public String getCodice() {
		return codice;
	}

	public void setCode(String codice) {
		this.codice = codice;
	}

	public String getTitle() {
		return titolo;
	}

	public void setTitle(String titolo) {
		this.titolo = titolo;
	}

	public String getAuthor() {
		return autore;
	}

	public void setAuthor(String autore) {
		this.autore = autore;
	}

	public String getPublisher() {
		return casaEditrice;
	}

	public void setPublisher(String casaEditrice) {
		this.casaEditrice = casaEditrice;
	}

	public BigDecimal getPrice() {
		return prezzo;
	}

	public void setPrice(BigDecimal prezzo) {
		this.prezzo = prezzo;
	}

	public Book(String codice, String titolo, String autore, String casaEditrice, BigDecimal prezzo) {
		super();
		this.codice = codice;
		this.titolo = titolo;
		this.autore = autore;
		this.casaEditrice = casaEditrice;
		this.prezzo = prezzo;
	}
	
	public Book(String titolo, String autore, String casaEditrice) {
		super();
		this.codice = "";
		if(titolo != null) {
			this.titolo = titolo;
		}
		if(autore != null) {
			this.autore = autore;
		}
		if(casaEditrice != null) {
			this.casaEditrice = casaEditrice;
		}
		
		this.prezzo = null;
	}
	
	public static Book addBookToDb(String titolo, String autore, String casaEditrice, BigDecimal prezzo) throws SQLException {
		Db db = Db.getInstance();
		String idString;
		try {
			idString = db.inesertBook(new Book("", titolo, autore, casaEditrice, 
					prezzo));
		} finally {
			db.close();
		}
		
		return new Book(idString, titolo, autore, casaEditrice, prezzo);
	}

	public void printBook() {
		System.out.println("Book code: " + this.codice + " etc...");
	}

	public void openPdf() {
		if (Desktop.isDesktopSupported()) {
		    try {
		    	if (pdf != null) {
		    		Desktop.getDesktop().open(pdf);
		    	} else {
		    		System.out.println("No PDF associated with this book");
		    	}
		        
		    } catch (IOException ex) {
		        // no application registered for PDFs
		    }
		} else {
			System.out.println("We cannot open the PDF file "
					+ "because the "
					+ "appropiate program doesn't seem so be installed");
		}
	}
}
