package base.model;

import java.util.List;

public class Kanji {
	private String kanji;
	private List<String> meanings;
	
	public Kanji(String kanji, List<String> meanings) {
		this.kanji = kanji;
		this.meanings = meanings;
	}
	
	public String getMeanings() {
		return createMeaningsString();
	}
	
	public String toString() {
		String meaningsString = createMeaningsString();
		return String.format("Kanji: %s - Significado: %s", kanji, meaningsString);
	}
	
	private String createMeaningsString() {
		String meaningsString = "";
		for(String meaning: meanings) {
			meaningsString += ", " + meaning;
		}
		return meaningsString.substring(2);
	}
}
