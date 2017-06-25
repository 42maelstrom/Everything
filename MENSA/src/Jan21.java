import java.util.ArrayList;
import java.util.Arrays;

public class Jan21 {
	
	public static final String NAME = "TEN TO FIVE";
	public static final String DESCRIPTION =
			"Starting on a letter in the longest row, spell out a "
			+ "ten-letter word going from letter to adjacent letter—horizontally,"
			+ " vertically, or diagonally—as you use every letter in the grid one."
			+ " Then rearrange that ten-letter world into two five-letter words that"
			+ " begin and end with the letters shown in the rows at the bottom.";
	public static final ArrayList<Class> helperClasses =
			new ArrayList<Class>(Arrays.asList(AnagramFinder.class));

	public String letters;
	
	public Jan21(String letters) {
		 this.letters = letters;
	}
	
	public void solve() {
		ArrayList<String> anagrams = AnagramFinder.findAnagrams(letters);
		for(String s: anagrams) {
			if(s.length() == 10 && "raoca".contains(s.substring(0, 1)))
				System.out.println(s);
			if(s.length() == 5 && s.charAt(0) == 'm' && s.charAt(4) == 'c')
				System.out.println(s);
			if(s.length() == 5 && s.charAt(0) == 'a' && s.charAt(4) == 'r')
				System.out.println(s);
		}
	}
	
	public static void main(String[] args) {
		new Jan21("raocagirdm").solve();
	}
}
