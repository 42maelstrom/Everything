import java.util.ArrayList;
import java.util.Arrays;


public class May12 {
	public static void main(String[] args) {
		ArrayList<Integer> wordLengths = new ArrayList<Integer>(Arrays.asList(4, 4, 3, 5, 5, 3, 2, 3, 4, 9));
		ArrayList<String> chunks = new ArrayList<String>(Arrays.asList("ame", "ane", "epl", "hes", "hew", "hol", "ket", "nce", "oft", "out", "sta", "sub", "yma"));
		String given = "the";
		
		new Jan6(chunks, wordLengths, given).solve();
	}
}
