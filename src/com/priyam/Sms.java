package com.priyam;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class Sms {
	public static void main(String[] args) {
		try {
		String recipient = "+919674355906";
		String message = " Greetings from Mr. Gupta! Have a nice day!";
		String username = "admin";
		String password = "admin123";
		String originator = "+441234567";
		String requestUrl  = "http://127.0.0.1:9876/api?action=sendmessage&" +
		 "username=" + URLEncoder.encode(username, "UTF-8") +
		 "&password=" + URLEncoder.encode(password, "UTF-8") +
		 "&recipient=" + URLEncoder.encode(recipient, "UTF-8") +
		 "&messagetype=SMS:TEXT" +
		 "&messagedata=" + URLEncoder.encode(message, "UTF-8") +
		 "&originator=" + URLEncoder.encode(originator, "UTF-8") +
		 "&serviceprovider=HTTPServer2" +
		 "&responseformat=html";
		URL url = new URL(requestUrl);
		HttpURLConnection uc = (HttpURLConnection)url.openConnection();
		System.out.println(uc.getResponseMessage());
		uc.disconnect();
		} catch(Exception ex) {
		System.out.println(ex.getMessage());
		}
		}
}