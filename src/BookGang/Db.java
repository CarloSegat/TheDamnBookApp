package BookGang;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

public class Db {
	
    private static Connection connect = null;
    private static Statement statement = null;
    private static PreparedStatement preparedStatement = null;
    private static ResultSet resultSet = null;
    
    private static final Db SINGLE_INSTANCE = new Db();
    
    static {
    	try {
			SINGLE_INSTANCE.init();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    private Db() {}
    
    public static Db getInstance() {
    	return SINGLE_INSTANCE;
    }
    
    private static void init() throws SQLException {
    	if (connect == null) {
    		connect = DriverManager
                    .getConnection("jdbc:mysql://localhost/carlodb?"
                    		+ "noDatetimeStringSync=true&serverTimezone=UTC"
                            + "&user=carlo&password=my_password");
    	}
    }
    
    public Book getBookById(int id) throws SQLException {
    	statement = connect.createStatement();
    	resultSet = statement
                .executeQuery("select * "
                		+ "from carlodb.books"
                		+ "where id=" + id);
    	return convertResultSetToBook(resultSet);
    }

    private Book convertResultSetToBook(ResultSet resultSet) throws SQLException {
    	String id = resultSet.getString("id");
    	String title = resultSet.getString("title");
        String author = resultSet.getString("author");
        String publisher = resultSet.getString("publisher");
        BigDecimal price = new BigDecimal(resultSet.getDouble("price"));
        return new Book(id, title, author, publisher, price);
	}
    

	public String inesertBook(Book book) throws SQLException {
		// return id 
        
        preparedStatement = connect
                .prepareStatement("insert into carlodb.books values (default, ?, ?, ?, ?)", 
                		Statement.RETURN_GENERATED_KEYS);
    
        preparedStatement.setString(1, book.getTitle());
        preparedStatement.setString(2, book.getAuthor());
        preparedStatement.setString(3, book.getPublisher());
        preparedStatement.setDouble(4, book.getPrice().doubleValue());
        
        int affectedRows = preparedStatement.executeUpdate();

        if (affectedRows == 0) {
            throw new SQLException("Creating user failed, no rows affected.");
        }

        try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                return generatedKeys.getString(1);
            }
            else {
                throw new SQLException("Creating user failed, no ID obtained.");
            }
        }
    }

    private void printResultSet(ResultSet resultSet) throws SQLException {
        // ResultSet is initially before the first data set
        while (resultSet.next()) {
            String title = resultSet.getString("title");
            String author = resultSet.getString("author");
            String publisher = resultSet.getString("publisher");
            BigDecimal price = new BigDecimal(resultSet.getDouble("price"));
            System.out.println("Title: " + title);
            System.out.println("author: " + author);
            System.out.println("publisher: " + publisher);
            System.out.println("price: " + price);
        }
    }

    // You need to close the resultSet
    public void close() {
        try {
            if (resultSet != null) {
                resultSet.close();
            }

            if (statement != null) {
                statement.close();
            }

            if (connect != null) {
                connect.close();
            }
        } catch (Exception e) {
        }
    }

}
