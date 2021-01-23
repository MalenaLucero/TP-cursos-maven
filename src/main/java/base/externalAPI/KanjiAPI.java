package base.externalAPI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import base.model.Kanji;

public class KanjiAPI {
	private static HttpURLConnection connection;
	
	public static Kanji getKanjiData(String kanjiToSearch) {
		BufferedReader reader;
		String line;
		StringBuffer responseContent = new StringBuffer();
		Kanji kanji = null;
		try {
			URL url = new URL("https://kanjiapi.dev/v1/kanji/" + kanjiToSearch);
			try {
				connection = (HttpURLConnection) url.openConnection();
				connection.setRequestMethod("GET");
				connection.setConnectTimeout(5000);
				connection.setReadTimeout(5000);
				int status = connection.getResponseCode();
				if(status > 299) {
					reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
					while((line = reader.readLine()) != null) {
						responseContent.append(line);
					}
					reader.close();
				} else {
					reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
					while((line = reader.readLine()) != null) {
						responseContent.append(line);
					}
					reader.close();
				}
				kanji = parse(responseContent.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} finally {
			connection.disconnect();
		}
		return kanji;
	}
	
	public static Kanji parse(String responseBody) {
		Kanji kanjiModel = null;
		try{
			JSONObject kanjiObject = new JSONObject(responseBody);
			String kanji = kanjiObject.getString("kanji");
			JSONArray meaningsJSON = new JSONArray(kanjiObject.getJSONArray("meanings"));
			List<String> meanings = new ArrayList<String>();
			for(int i = 0; i < meaningsJSON.length(); i++) {
				meanings.add(meaningsJSON.getString(i));
			}
			kanjiModel = new Kanji(kanji, meanings);
		} catch(Exception e) {
			System.err.println("Kanji no encontrado");
		}
		return kanjiModel;
	}
}
