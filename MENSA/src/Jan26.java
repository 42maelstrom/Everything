import java.util.ArrayList;
import java.util.Arrays;


public class Jan26 {
	public static final String DESCRIPTION = "In each identical setup, insert two"
			+ " different letters into the blanks in the first word and two other"
			+ " different letters int othe blanks of the second word to form a phrase"
			+ " defined by a clue in the last column.";
	
	private static final String alph = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	static Dictionary dict = new Dictionary(50);
	public ArrayList<String> puzzles;
	
	/**
	 * Put an underscore where there's a blank.
	 * @param puzzles
	 */
	public Jan26(ArrayList<String> puzzles) {
		this.puzzles = puzzles;
	}
	
	public void solve() {
		for(int i = 0; i < puzzles.size(); i++) {
			System.out.println((i + 1) + ". " + puzzles.get(i) + " " + puzzles.get(i));
			for(int l1 = 0; l1 < 26; l1++) {
				for(int l2 = 0; l2 < 26; l2++) {
					if(l1 != l2) {
						String w1 = puzzles.get(i);
						w1 = replaceFirst(w1, '_', alph.charAt(l1));
						w1 = replaceFirst(w1, '_', alph.charAt(l2));
						if(dict.isWord(w1))
							System.out.println("   " + w1);
					}
				}
			}
		}	
	}
	
	private String replaceFirst(String str, char target, char replacement) {
		int j = str.indexOf(target);
		return str.substring(0, j) + replacement + str.substring(j + 1);
	}
	
	public static void main(String[] args) {
		ArrayList<String> puzzles = new ArrayList<String>(Arrays.asList("B_TTO_", "A_END_", "C_MBA_", "M_PPE_", "W_LLO_", "P_OTO_"));
		new Jan26(puzzles).solve();
	}
}
