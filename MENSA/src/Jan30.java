import java.util.ArrayList;
import java.util.HashMap;


public class Jan30 {
	public static final String NAME = "FOUR OF A KIND";
	public static final String DESCRIPTION = "Complete the short paragraph below"
			+ " by filling in the blanks with four 4-letter words that each differ"
			+ " by one letter. The changed letter will be in the same position in"
			+ " each word, as in CHEF, CHEZ, CHER, CHEW";
	Dictionary dict = new Dictionary(50);
	
	public void solve() {
		
		ArrayList<String> words = new ArrayList<String>();
		for(String w: dict.dict) {
			if(w.length() == 4)
				words.add(w);
		}
		
		for(String w: words) {
			ArrayList<ArrayList<String>> indexNeighbors = new ArrayList<ArrayList<String>>();
			
			for(int i = 0; i < 4; i++) {
				indexNeighbors.add(new ArrayList<String>());
			}
			
			for(int i = 0; i < words.size(); i++) {
				int index = spotChangeIndex(w, words.get(i));
				if(index != -1) {
					indexNeighbors.get(index).add(words.get(i));
				}
			}
			
			for(ArrayList<String> neighbors: indexNeighbors) {
				if(neighbors.size() >= 4) {
					System.out.println(w + ": " + neighbors.toString());
				}
			}
		}
	}
	
	private static int spotChangeIndex(String w1, String w2) {
		if(w1.equals(w2)) 
			return -1;
		
		boolean wasOneMistake = false;
		int mistakeIndex = 0;
		
		for(int i = 0; i < w1.length(); i++) {
			if(!(w1.charAt(i) == w2.charAt(i))) {
				if(wasOneMistake)
					return -1;
				wasOneMistake = true;
				mistakeIndex = i;
			}
		}
		
		return mistakeIndex;
	}
	
	public static void main(String[] args) {
		new Jan30().solve();
	}
}
