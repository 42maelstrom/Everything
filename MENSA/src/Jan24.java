import java.util.ArrayList;
import java.util.Arrays;

public class Jan24 {
	public static final String NAME = "CUT AND PASTE";
	
	public static final String DESCRIPTION = 
			"Cut one letter from each word and \"paste\" the remaining"
			+ " letters together to spell out a new word. For example, in SPY + THORN,"
			+ " cut the S and the R to get PYTHON.";

	public ArrayList<String> firstWords;
	public ArrayList<String> secondWords;
	static Dictionary dict = new Dictionary(50);
	
	public Jan24(ArrayList<String> firstWords, ArrayList<String> secondWords) {
		this.firstWords = firstWords;
		this.secondWords = secondWords;
	}
	
	public void solve() {
		outer:
		for(int i = 0; i < firstWords.size(); i++) {
			String w1 = firstWords.get(i);
			String w2 = secondWords.get(i);
			for(int k = 0; k < w1.length(); k++) {
				for(int j = 0; j < w2.length(); j++) {
					String w1new = w1.substring(0, k) + w1.substring(k + 1);
					String w2new = w2.substring(0, j) + w2.substring(j + 1);
					//System.out.println(w1new + ", " + w2new);
					String newWord = w1new + w2new;
					if(dict.isWord(newWord)) {
						System.out.println(newWord);
						continue outer;
					}
				}
			}
		}
	}
	
	public static void main(String[] args) {
		ArrayList<String> firstWords = new ArrayList<String>(Arrays.asList("clan", "pear", "uncle", "pores", "contra", "quick"));
		ArrayList<String> secondWords = new ArrayList<String>(Arrays.asList("nervy", "ligament", "retain", "ague", "infer", "hie"));

		new Jan24(firstWords, secondWords).solve();
	}
}
