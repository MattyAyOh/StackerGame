package edu.msu.myturns.stackergame;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Xml;

public class Web {
	private static final String SIGNUP_URL = "https://www.cse.msu.edu/~aomatthe/project2/signup.php";
	private static final String LOGIN_URL = "https://www.cse.msu.edu/~aomatthe/project2/login.php";
	
	public boolean createUser(String username, String password){
		URL url;
		try {
			url = new URL(SIGNUP_URL + "?user=" + username + "&pw=" + password);
		} catch (MalformedURLException e) {
			return false;
		}

		HttpURLConnection conn;
		try {
			conn = (HttpURLConnection) url.openConnection();
		} catch (IOException e) {
			return false;
		}

		conn.setDoOutput(true);
		try {
			conn.setRequestMethod("GET");
		} catch (ProtocolException e) {
			return false;
		}
		conn.setUseCaches(false);


		int responseCode;
		try {
			responseCode = conn.getResponseCode();
		} catch (IOException e) {
			return false;
		}
		if(responseCode != HttpURLConnection.HTTP_OK) {
			return false;
		}
		InputStream stream;
		try {
			stream = conn.getInputStream();
		} catch (IOException e) {
			return false;
		}

		try {
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
			// We are done
		} catch(XmlPullParserException ex) {
			return false;
		} catch(IOException ex) {
			return false;
		} finally {
			try {
				stream.close();
			} catch(IOException ex) {
				return false;
			}
		}

		return true;
	}

	public boolean login(String username, String password){
		URL url;
		try {
			url = new URL(LOGIN_URL + "?user=" + username + "&pw=" + password);
		} catch (MalformedURLException e) {
			return false;
		}

		HttpURLConnection conn;
		try {
			conn = (HttpURLConnection) url.openConnection();
		} catch (IOException e) {
			return false;
		}

		conn.setDoOutput(true);
		try {
			conn.setRequestMethod("GET");
		} catch (ProtocolException e) {
			return false;
		}
		conn.setUseCaches(false);


		int responseCode;
		try {
			responseCode = conn.getResponseCode();
		} catch (IOException e) {
			return false;
		}
		if(responseCode != HttpURLConnection.HTTP_OK) {
			return false;
		}
		InputStream stream;
		try {
			stream = conn.getInputStream();
		} catch (IOException e) {
			return false;
		}

		try {
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
		
		} catch(XmlPullParserException ex) {
			return false;
		} catch(IOException ex) {
			return false;
		} finally {
			try {
				stream.close();
			} catch(IOException ex) {
				return false;
			}
		}

		return true;
	}
}
