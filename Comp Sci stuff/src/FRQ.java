import java.awt.List;
import java.util.ArrayList;


public class FRQ {
	public static String scrambleWord(String word) {
		for(int i = 0; i < word.length() - 1; i++) {
			if(word.substring(i, i + 1).equals("A") && (word.substring(i + 1, i + 				2).equals("A") == false)) {
				word = word.substring(0, i) + word.substring(i + 1, i + 2) + 				"A" + word.substring(i + 2, word.length() - 1);
				i++;
			}
		}	
		return word;
	}

	public static void scrambleOrRemove(ArrayList<String> wordList) {
		int i = 0;
		for(String word: wordList) {
			if(word.equals(scrambleWord(word))) {
				wordList.remove(word);
			} else {
				wordList.remove(i);
				wordList.add(i, scrambleWord(word));
			}

			i++;
		}
	}
	
	public static void main(String[] args) {
		String test = "TAN";
		System.out.println(scrambleWord(test));
	}
}
