import java.util.ArrayList;
import java.util.Arrays;


public class Feb19 {
	public static final String NAME = "SNOW REMOVAL";
	public static final String DESCRIPTION = "The letters S-N-O-W have been"
			+ " removed from all of the words, names, and phrases below, with any"
			+ " resulting spaces closed up. The letters S-N-O-W always appear in"
			+ " left-to-right order, although not neessarily consecutively. What"
			+ " are these words and phrases?";
	
	private String[] puzzles;
	private String insert;
	private Dictionary dict = new Dictionary(50);
	
	public Feb19(String[] puzzles, String insert) {
		this.puzzles = puzzles;
		this.insert = insert;
		dict.addWord("eisenhower");
	}
	
	public void solve() {
		for(String p: puzzles) {
			System.out.println(p);
			for(String s: getPossibleInsertions(p, insert)) {
				ArrayList<String> words = makeWords(s);
				if(words != null) {
					for(int i = 0; i < words.size(); i++) {
						System.out.print("\t" + words.get(i) + " ");
					}
					System.out.println();
				}
			}
			System.out.println();
		}
	}
	
	public static ArrayList<String> getPossibleInsertions(String source, String insert) {
		if(source.length() == 0)
			return new ArrayList<String>(Arrays.asList(insert));
		
		if(insert.length() == 0)
			return new ArrayList<String>(Arrays.asList(source));
		
		ArrayList<String> insertions = new ArrayList<String>();
		
		for(int i = 0; i <= source.length(); i++) {
			String firstPart = source.substring(0, i) + insert.charAt(0);
			ArrayList<String> lowerInsertions = getPossibleInsertions(source.substring(i), insert.substring(1));
			
			for(String lInsert: lowerInsertions) {
				insertions.add(firstPart + lInsert);
			}
		}
		
		return insertions;
	}
	

//for making words out of anything. This may come in handy.
//tries to break up the string into separate words that are real words.
//returns the words in a list if possible, null otherwise.
	private ArrayList<String> makeWords(String s) {
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
			//System.out.println(word);
			ArrayList<String> restOfWords = makeWords(s.substring(i));
			if(restOfWords != null) {
				restOfWords.add(0, word);
				return restOfWords;
			}
			i++;
		}
		return null;
	}
	
	public static void main(String[] args) {
		String[] puzzles = {"DRESIGGN", "TAYIGPER", "UPADDNS", "PRIOERFAR", "HATYTN", "ROEWID", "EIEHER", "JUT"};
		new Feb19(puzzles, "SNOW").solve();
	}
}
