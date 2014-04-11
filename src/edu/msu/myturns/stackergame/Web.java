package edu.msu.myturns.stackergame;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Xml;

public class Web {
	private static final String SIGNUP_URL = "https://www.cse.msu.edu/~aomatthe/project2/signup.php";
	private static final String LOGIN_URL = "https://www.cse.msu.edu/~aomatthe/project2/login.php";
	private static final String NEWGAME_URL = "https://www.cse.msu.edu/~aomatthe/project2/newgame.php";
	private static final String GETGAME_URL = "https://www.cse.msu.edu/~aomatthe/project2/getgame.php";
	private static final String SENDMOVE_URL = "https://www.cse.msu.edu/~aomatthe/project2/sendmove.php";
	
	public class Move{
		public int number;
		public float x;
		public float y;
		public float weight;
		public String username;
	}
	
	public boolean sendMove(String username, String password, int number, float x, float y, float weight){
		URL url;
		InputStream stream = null;
		try {
			url = new URL(SENDMOVE_URL + "?user=" + username + "&pw=" + password
					+ "&number=" + Integer.toString(number)
					+ "&x=" + Float.toString(x)
					+ "&y=" + Float.toString(y)
					+ "&weight=" + Float.toString(weight));
			HttpURLConnection conn;
			conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("GET");
			conn.setUseCaches(false);
			int responseCode;
			responseCode = conn.getResponseCode();
			if(responseCode != HttpURLConnection.HTTP_OK) {
				return false;
			}
			stream = conn.getInputStream();
			XmlPullParser xml = Xml.newPullParser();
			xml.setInput(stream, "UTF-8");
			xml.nextTag();      // Advance to first tag
			xml.require(XmlPullParser.START_TAG, null, "move");
			String status = xml.getAttributeValue(null, "status");
			if(status.equals("yes")) {
				return true;
			}
			else
				return false;
		} catch (Exception e){
			return false;
		} finally {
			try {
				stream.close();
			} catch (IOException e) {
				return false;
			}
		}
	}
	
	public ArrayList<Move> getGame(String username, String password){
		URL url;
		InputStream stream = null;
		ArrayList<Move> game = new ArrayList<Move>();
		try {
			url = new URL(GETGAME_URL + "?user=" + username + "&pw=" + password);
			HttpURLConnection conn;
			conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("GET");
			conn.setUseCaches(false);
			int responseCode;
			responseCode = conn.getResponseCode();
			if(responseCode != HttpURLConnection.HTTP_OK) {
				return null;
			}
			stream = conn.getInputStream();
			XmlPullParser xml = Xml.newPullParser();
			xml.setInput(stream, "UTF-8");
			xml.nextTag();      // Advance to first tag
			xml.require(XmlPullParser.START_TAG, null, "game");
			String status = xml.getAttributeValue(null, "status");
			if(!status.equals("yes")) 
				return null;
			while(xml.nextTag() == XmlPullParser.START_TAG) {
				if(xml.getName().equals("move")) {
					Move move = new Move();
					move.number = Integer.parseInt(xml.getAttributeValue(null, "number"));
					move.x = Float.parseFloat(xml.getAttributeValue(null, "x"));
					move.y = Float.parseFloat(xml.getAttributeValue(null, "y"));
					move.weight = Float.parseFloat(xml.getAttributeValue(null, "weight"));
					move.username = xml.getAttributeValue(null, "username");				
					game.add(move);
				}
				skipToEndTag(xml);
			}			
		} catch (Exception e){
			return null;
		} finally {
			try {
				stream.close();
			} catch (IOException e) {
				return null;
			}
		}
		return null;
	}
	
	public boolean newGame(String username, String password){
		URL url;
		InputStream stream = null;
		try {
			url = new URL(NEWGAME_URL + "?user=" + username + "&pw=" + password);
			HttpURLConnection conn;
			conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("GET");
			conn.setUseCaches(false);
			int responseCode;
			responseCode = conn.getResponseCode();
			if(responseCode != HttpURLConnection.HTTP_OK) {
				return false;
			}
			stream = conn.getInputStream();
			XmlPullParser xml = Xml.newPullParser();
			xml.setInput(stream, "UTF-8");
			xml.nextTag();      // Advance to first tag
			xml.require(XmlPullParser.START_TAG, null, "newgame");
			String status = xml.getAttributeValue(null, "status");
			if(status.equals("yes")) {
				return true;
			}
			else
				return false;
		} catch (Exception e){
			return false;
		} finally {
			try {
				stream.close();
			} catch (IOException e) {
				return false;
			}
		}
	}
	
	public boolean createUser(String username, String password){
		URL url;
		InputStream stream = null;
		try {
			url = new URL(SIGNUP_URL + "?user=" + username + "&pw=" + password);
			HttpURLConnection conn;
			conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("GET");
			conn.setUseCaches(false);
			int responseCode;
			responseCode = conn.getResponseCode();
			if(responseCode != HttpURLConnection.HTTP_OK) {
				return false;
			}
			stream = conn.getInputStream();
			XmlPullParser xml = Xml.newPullParser();
			xml.setInput(stream, "UTF-8");
			xml.nextTag();      // Advance to first tag
			xml.require(XmlPullParser.START_TAG, null, "create");
			String status = xml.getAttributeValue(null, "status");
			if(status.equals("yes")) {
				return true;
			}
			else
				return false;
		} catch (Exception e){
			return false;
		} finally {
			try {
				stream.close();
			} catch (IOException e) {
				return false;
			}
		}
	}

	public boolean login(String username, String password){
		URL url;
		InputStream stream = null;
		try {
			url = new URL(LOGIN_URL + "?user=" + username + "&pw=" + password);
			HttpURLConnection conn;
			conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("GET");
			conn.setUseCaches(false);
			int responseCode;
			responseCode = conn.getResponseCode();
			if(responseCode != HttpURLConnection.HTTP_OK) {
				return false;
			}
			stream = conn.getInputStream();
			XmlPullParser xml = Xml.newPullParser();
			xml.setInput(stream, "UTF-8");
			xml.nextTag();      // Advance to first tag
			xml.require(XmlPullParser.START_TAG, null, "login");
			String status = xml.getAttributeValue(null, "status");
			if(status.equals("yes")) {
				return true;
			}
			else
				return false;
		} catch (Exception e){
			return false;
		} finally {
			try {
				stream.close();
			} catch (IOException e) {
				return false;
			}
		}
	}
	public static void skipToEndTag(XmlPullParser xml) 
			throws IOException, XmlPullParserException {
		int tag;
		do
		{
			tag = xml.next();
			if(tag == XmlPullParser.START_TAG) {
				// Recurse over any start tag
				skipToEndTag(xml);
			}
		} while(tag != XmlPullParser.END_TAG && 
				tag != XmlPullParser.END_DOCUMENT);
	}
}
