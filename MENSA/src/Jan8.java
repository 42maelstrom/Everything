import java.util.ArrayList;
import java.util.Arrays;


public class Jan8{
	public static final String DESCRIPTION = 
			"Identify the images, which represent ten 4-letter words."
			+ " Then starting with the hair-grooming tool, form a ten-step word"
			+ " ladder in which two adjacent letters change at each step while"
			+ " the other two letters remain the same.";

	public ArrayList<String> words;
	public String start;
	
	public Jan8(String start, ArrayList<String> otherWords) {
		this.words = otherWords;
		this.start = start;
	}
	
	public void solve() {
		ArrayList<String> ladder = formLadder(words, start);
		for(String w: ladder)
			System.out.println(w);
	}
	
	private ArrayList<String> formLadder(ArrayList<String> words, String start) {
		if(words.isEmpty()) {
			ArrayList<String> ladder = new ArrayList<String>();
			ladder.add(start);
			return ladder;
		}
		for(String w: words) {
			if(areNeighbors(start, w)) {
				ArrayList<String> newWords = new ArrayList<String>();
				newWords.addAll(words);
				newWords.remove(w);
				ArrayList<String> ladder = formLadder(newWords, w);
				if(ladder != null) {
					ladder.add(0, start);
					return ladder;
				}
			}
		}
		return null;
	}
	
	private static boolean areNeighbors(String s1, String s2) {
		int numSame = 0;
		for(int i = 0; i < 4; i++) {
			if(s1.charAt(i) == s2.charAt(i))
				numSame++;
		}
		return 2 == numSame;
	}
	
	public static void main(String[] args) {
		ArrayList<String> words = new ArrayList<String>(Arrays.asList("HARP", "CAKE", "WOLF", "LAMP"
				, "CONE", "LAVA", "HALF", "CALF", "HUMP"));
		new Jan8("COMB", words).solve();
	}
}
