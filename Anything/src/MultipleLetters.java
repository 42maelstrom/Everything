import java.util.ArrayList;

public class MultipleLetters {
	static String alpha = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toLowerCase();
	public static void go() {
		ArrayList<ArrayList<String>> words=  new ArrayList<ArrayList<String>>();
		for(int i = 0; i < 26; i++) {
			words.add(new ArrayList<String>());
			for(String word: Dictionary.dict) {
				if(getNumOfOccurences(word, alpha.charAt(i)) > 2)
					words.get(i).add(word);
			}
			System.out.println(String.valueOf(alpha.charAt(i)) + " (" + words.size() + "): " + words.get(i).toString());
		}
	}
	
	public static int getNumOfOccurences(String str, char c) {
		int n = 0;
		for(int i = 0; i < str.length(); i++) {
			if(str.charAt(i) == c)
				n++;
		}
		return n;
	}
	
	public static void main(String[] args) {
		go();
	}
}
