import java.util.ArrayList;

public class Jan14 {
	
	public static final String NAME = "TAKE TWO";
	public static final String DESCRIPTION =
			"Choose two consonants and repeat them as many times as "
			+ "needed to complete this crisscross puzzle. All vowels, including"
			+ " Y's, have already been placed. All words are common, uncapitalized,"
			+ " unhyphenated English words.";
	
	public final String[] words;
	Dictionary dict = new Dictionary(50);
	
	public Jan14(String[] words) {
		this.words = words;
	}
	
	public void solve() {
		String consonants = "bcdfghjklmnpqrstvwxz";
		String solution = "";
		int solutionMistakes = words.length;
		
		for(int i = 0; i < consonants.length(); i++) {
			for(int j = 0; j < consonants.length(); j++) {
				if(i == j)
					continue;
				
				String c1 = consonants.substring(i, i+1);
				String c2 = consonants.substring(j, j+1);
				int mistakes = 0;
				int k = 0;
				while(mistakes < solutionMistakes && k < words.length) {
					ArrayList<String> possibleStrings = getPossibleStrings(words[k], c1, c2);
					boolean hasWord = false;
					
					for(String s: possibleStrings) {
						if(dict.isWord(s))
							hasWord = true;
					}
					
					if(!hasWord)
						mistakes++;
					
					k++;
				}
				
				if(mistakes < solutionMistakes) {
					solution = c1 + c2;
					solutionMistakes = mistakes;
				}
			}
		}
		
		System.out.println(solution);
	}
	
	private static ArrayList<String> getPossibleStrings(String word, String c1, String c2) {
		ArrayList<String> possibleStrings = new ArrayList<String>();
		
		if(word.length() == 0) {
			possibleStrings.add("");
			return possibleStrings;
		}
		
		String l1 = word.substring(0, 1);
		if(!l1.equals("*")) {
			for(String w: getPossibleStrings(word.substring(1), c1, c2)) {
				possibleStrings.add(l1 + w);
			}
		} else {
			for(String w: getPossibleStrings(word.substring(1), c1, c2)) {
				possibleStrings.add(c1 + w);
				possibleStrings.add(c2 + w);
			}
		}
		
		return possibleStrings;
	}
	
	public static void main(String[] args) {
		String[] words = {"*A**OO", "*AA", "A**O", "A*OE*A", "*EE", "IA**", "*A*Y",
				"*IO*E", "*O*", "*I", "O*OE", "*OO*", "I**I*E", "I**UE", "E*U", "*A**O",
				"*IO", "*EA*", "*I*", "*O**", "*Y", "E**", "*IO"};
		new Jan14(words).solve();	
	}
}
