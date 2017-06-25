import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class Glossary {
	private static final ArrayList<String> dict;
	
	static {
		dict = new ArrayList<String>();
		
		try {
			BufferedReader bf = new BufferedReader(new FileReader("WordList_35.txt"));
			String line;
			
			while((line = bf.readLine()) != null) {
				dict.add(line);
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static ArrayList<String> uncommonWordsIn(String text) {
		ArrayList<String> uncommons = new ArrayList<String>();
		ArrayList<String> textWords = getWords(text);
		textWords.removeAll(dict);
		return textWords;
	}
	
	private static ArrayList<String> getWords(String text) {
		ArrayList<String> words = new ArrayList<String>();
		int i = 0;
		while(i < text.length()) {
			int j = text.indexOf(' ', i);
			if(j == -1)
				return words;
			String word = text.substring(i, j).toLowerCase();
			System.out.println(word);
			
			boolean isAllowed = true;
			
			String allowed = "abcdefghijklmnopqrstuvwxyz'-";
			for(int k = 0; k < word.length() - 1; k++) {
				if(allowed.indexOf(word.charAt(k)) == -1) {
						isAllowed = false;
						break;
				}
			}
			
			if(allowed.indexOf(word.charAt(word.length() - 1)) == -1) 
				word = word.substring(0, word.length() - 1);
			
			if(isAllowed)
				words.add(word);
			
			i = j + 1;
		}
		return words;
	}
	
	private static boolean containsNonLetters(String word) {
		String allowed = "abcdefghijklmnopqrstuvwxyz'-";
		for(int i = 0; i < word.length() - 1; i++) {
			if(allowed.indexOf(word.charAt(i)) == -1)
					return true;
		}
		if(allowed.indexOf(word.charAt(word.length() - 1)) == -1) 
			return false;
		return false;
	}
	
	public static void main(String[] args) {
		System.out.println(Glossary.uncommonWordsIn(new Scanner(System.in).nextLine()));
	}
}
