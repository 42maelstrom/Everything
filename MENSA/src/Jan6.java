import java.util.ArrayList;
import java.util.Arrays;

public class Jan6 {
	public static final String DESCRIPTION = "Reconstruct the end of a quip and"
			+ " the name of the person to whom the quip is attributed by placing"
			+ " the three-letter chunks in the correct order. To start you off,"
			+ " we've placed the first chunk and cross it off the list. The quip"
			+ " begins: \"the trouble with stealing quotes off the internet is"
			+ " that you...\"";
	
	public ArrayList<String> chunks;
	public ArrayList<Integer> wordLengths;
	String given;
	ArrayList<String> dict = new Dictionary(50).dict;
	
	public Jan6(ArrayList<String> chunks, ArrayList<Integer> wordLengths, String given) {
		this.chunks = chunks;
		this.wordLengths = wordLengths;
		this.given = given;
	}
	
	public void solve() {
		String solution = solvePart(this.chunks, this.wordLengths, this.given);
		int i = 0;
		for(int length: wordLengths) {
			System.out.print(solution.substring(i, i + length) + " ");
			i = i + length;
		}
	}
	
	private String solvePart(ArrayList<String> chunks, ArrayList<Integer> wordLengths, String given) {
		if(chunks.size() == 0)
			return "";
		
		for(String s: chunks) {
			String current = given + s;
			if(current.length() < wordLengths.get(0)) {
				ArrayList<String> newChunks = new ArrayList<String>();
				newChunks.addAll(chunks);
				newChunks.remove(s);
				
				ArrayList<Integer> newLengths = new ArrayList<Integer>();
				newLengths.addAll(wordLengths);
				
				String newGiven = current;
				String solve = solvePart(newChunks, newLengths, newGiven);
				if(solve != null) {
					return solve;
				} else {
					continue;
				}
			} else {
				String w1 = current.substring(0, wordLengths.get(0));
				String newGiven = current.substring(wordLengths.get(0));

				if(dict.contains(w1)) {
					ArrayList<String> newChunks = new ArrayList<String>();
					newChunks.addAll(chunks);
					newChunks.remove(s);
				
					ArrayList<Integer> newLengths = new ArrayList<Integer>();
					newLengths.addAll(wordLengths);
					newLengths.remove(wordLengths.get(0));
				
					String solve = solvePart(newChunks, newLengths, newGiven);
					if(solve != null) {
						return w1 + solve;
					} else {
						continue;
					}
				} else {
					continue;
				}
			}
		}
		return null;
	}
	
	public static void main(String[] args) {
		ArrayList<Integer> wordLengths = new ArrayList<Integer>(Arrays.asList(5, 4, 2, 4, 3, 7, 7, 7));
		ArrayList<String> chunks = new ArrayList<String>(Arrays.asList("aml", "are", "eab", "erk", "gen", "hey", "ift", "inc", "now", "oln", "rah", "uin"));
		String given = "nev";
		
		new Jan6(chunks, wordLengths, given).solve();
	}
}
