package google.books;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Map;

import javax.imageio.ImageIO;

import com.google.gson.Gson;

import BookGang.Book;

public class Api {
	
	private static final String startUrl = "https://www.googleapis.com/books/v1/volumes?q=";
	private static String key = null;
	private static String maxResults = "&startIndex=0&maxResults=20";
	private static Gson g = new Gson();
	
	static {
		String envKey= System.getenv("GOOGLE_KEY");
		if(envKey == null) {
			
		}
		key = "&key=" + envKey;
	}
	
	public static String callApi(String query) {
		try {
			URL url = new URL(startUrl + query + maxResults +
					"&characterEncoding=utf8" + key );
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");
			conn.setRequestProperty("Encoding", "UTF-8");
			
			
			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(
					new InputStreamReader(conn.getInputStream(), "utf-8")
			);

			String output = "";
			String allString = "";
			while ((output = br.readLine()) != null) {
				allString = allString.concat(output);
			}
			
			conn.disconnect();
			return allString;

		  } catch (MalformedURLException e) {

			e.printStackTrace();

		  } catch (IOException e) {

			e.printStackTrace();

		  }
		return null;

	}
	
	public static void getBookImage(String urlString) throws IOException {
		String path = "C:\\image.jpg";
		URL url = new URL("http://images.anandtech.com/doci/5434/X79%20Extreme9Box_575px.jpg");
		BufferedImage image = ImageIO.read(url);
		ImageIO.write(image, "jpg", new File(path));
	}
	
	public static void saveStream( String mURL, String ofile ) throws Exception {
	    InputStream in = null;
	    FileOutputStream out = null;
	    try {
	        URL url = new URL(mURL);
	        URLConnection urlConn = url.openConnection();
	        in = urlConn.getInputStream();
	       
	        out = new FileOutputStream(new String(ofile));
	        int c;
	        byte[] b = new byte[1024];
	        while ((c = in.read(b)) != -1)
	            out.write(b, 0, c);
	    } finally {
	        if (in != null)
	            in.close();
	        if (out != null)
	            out.close();
	    }
	}

	public static ArrayList<Book> getBooksFromRequest(String a) {
		Map p = g.fromJson(a, Map.class);
		ArrayList<Map> bookStrings =  (ArrayList<Map>) p.get("items");
		ArrayList<Book> books = new ArrayList<Book>();
		for(Map map : bookStrings) {
			Map infoMap = (Map) map.get("volumeInfo");
			ArrayList<String> authors = (ArrayList<String>) 
					infoMap.get("authors");
			if(authors == null) {
				authors = new ArrayList<String>();
			}
			String sub = (String) infoMap.get("subtitle");
			String pubDate = (String) infoMap.get("publishedDate");
			Double pageCount = (Double) infoMap.get("pageCount");
			String title = (String) infoMap.get("title");
			String publisher = (String) infoMap.get("publis 	her");
			books.add(new Book(title, String.join(" ", authors), publisher));
		}
		return books;
	}
}
