import java.util.ArrayList;
import java.util.Scanner;

public class Jan23 {
	
	public static final String NAME = "MADE IN THE SHADE";
	public static final String DESCRIPTION =
			"Fill in the blanks so that each column spells out a common "
			+ "five-letter word and the two shaded rows spell out a two-word phrase "
			+ "that identifies some people who work at a school.";

	public String firstLetters;
	public String middleLetters;
	public String finalLetters;
	private static String alphabet = "abcdefghijklmnopqrstuvwxyz";
	static Dictionary dict = new Dictionary(50);
	
	public Jan23(String firstLetters, String middleLetters, String finalLetters) {
		this.firstLetters = firstLetters;
		this.middleLetters = middleLetters;
		this.finalLetters = finalLetters;
	}
	
	public void solve() {
		ArrayList<ArrayList<String>> possibilities = new ArrayList<ArrayList<String>>();
		for(int i = 0; i < 6; i++) {
			possibilities.add(new ArrayList<String>());
			for(int l1 = 0; l1 < 26; l1++) {
				for(int l2 = 0; l2 < 26; l2++) {
					String word = firstLetters.substring(i, i+1) 
							+ alphabet.substring(l1, l1+1) + middleLetters.substring(i, i+1) 
							+ alphabet.substring(l2, l2+1) + finalLetters.substring(i, i+1);
					if(dict.isWord(word))
						possibilities.get(i).add(word);
				}
			}
		}
		
		System.out.println(possibilities.toString());
		System.out.println(makeCharList(possibilities));
		ArrayList<String> possibleFinalStrings = getPossibleFinalStrings(makeCharList(possibilities));
		//System.out.println(possibleFinalStrings.toString());
		System.out.println(possibleFinalStrings.size());
		
		for(String s: possibleFinalStrings) {
			String w1 = s.substring(0, 6);
			String w2 = s.substring(6);
			//System.out.println(w1 + ", " + w2);
			if(dict.isWord(w1) && dict.isWord(w2)) {
				System.out.println("SUCCESS");
			  System.out.println(w1 + ", " + w2);
				String st = new Scanner(System.in).nextLine();
			}
		}
	}
	
	//it can only be two words in the phrase
	private static ArrayList<String> makeWords2(String s) {
		int i = 2;
		while(i <= s.length()) {
			while(i <= s.length() && !dict.isWord(s.substring(0, i)))
				i++;
			
			if(i == s.length() + 1) {
				return null;
			} else if(dict.isWord(s.substring(i))) {
				ArrayList<String> words = new ArrayList<String>();
				words.add(s.substring(0, i));
				words.add(s.substring(i));
				return words;
			}
			
			i++;
		}
		return null;
	}
	
	//for making words out of anything. This may come in handy.
	private static ArrayList<String> makeWords(String s) {
		ArrayList<String> words = new ArrayList<String>();

		if(s.length() == 0) {
			return words;
		}
		
		int i = 1;
		while(i <= s.length()) {
			while(i <= s.length() && !dict.isWord(s.substring(0, i)))
				i++;
			
			if(i == s.length() + 1)
				return null;
			
			String word = s.substring(0, i);
			System.out.println(word);
			ArrayList<String> restOfWords = makeWords(s.substring(i));
			if(restOfWords != null) {
				restOfWords.add(0, word);
				return restOfWords;
			}
			i++;
		}
		return null;
	}
	
	private static ArrayList<String> getPossibleFinalStrings(ArrayList<ArrayList<Character>> chars) {
		if(chars.size() == 1) {
			ArrayList<String> pFinalStrings = new ArrayList<String>();
			for(Character c: chars.get(0)) {
				pFinalStrings.add("" + c);
			}
				
			return pFinalStrings;
		}
		
		ArrayList<String> pFinalStrings = new ArrayList<String>();
		ArrayList<Character> toTackOn = chars.remove(0);
		ArrayList<String> partlyMade = getPossibleFinalStrings(chars);
		
		for(Character c: toTackOn) {
			for(String missingAC: partlyMade) {
				String completer = c + missingAC;
				pFinalStrings.add(completer);
			}
		}
		return pFinalStrings;
	}
	
	private static ArrayList<ArrayList<Character>> makeCharList(
			ArrayList<ArrayList<String>> possibilities) {
		ArrayList<ArrayList<Character>> chars = new ArrayList<ArrayList<Character>>();
		for (int i = 0; i < 12; i++) {
			chars.add(new ArrayList<Character>());
		}

		int i = 0;
		for (ArrayList<String> list : possibilities) {
			ArrayList<Character> char1 = new ArrayList<Character>();
			ArrayList<Character> charN = new ArrayList<Character>();
			for (String s : list) {
				if (!char1.contains(s.charAt(1)))
					char1.add(s.charAt(1));
				if (!charN.contains(s.charAt(3)))
					charN.add(s.charAt(3));
			}
			chars.set(i, char1);
			chars.set(i + 6, charN);
			i++;
		}
		return chars;
	}
	
	public static void main(String[] args) {
		new Jan23("skiaju", "ozphih", "eoydyr").solve();
	}
}
