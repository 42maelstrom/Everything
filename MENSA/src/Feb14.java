import java.util.ArrayList;


public class Feb14 {
	public static final String DESCRIPTION = "Use the clues on each line to create"
			+ " an anagram of VALENTINE'S DAY. The Firstletter of each word is given"
			+ " and should not be reused.";
	
	private final String source;
	private final String[] puzzles;
	private Dictionary dict = new Dictionary(100);
	
	public Feb14(String source, String[] puzzles) {
		this.source = source;
		this.puzzles = puzzles;
	}
	
	public void solve() {
		ArrayList<String> solutions = new ArrayList<String>();
		
		w1:
		for(String w1: dict.dict) {
			w2:
			for(String w2: dict.dict) {
				if(w1.length() + w2.length() == source.length()) {
					String source2 = source.toLowerCase();

					for(int i = 0; i < w1.length(); i++) {
						if(-1 == source2.indexOf(w1.charAt(i)))
							continue w1;
						
						source2 = source2.substring(0, source2.indexOf(w1.charAt(i)))
								+ source2.substring(source2.indexOf(w1.charAt(i)) + 1);
					}
					
					for(int i = 0; i < w2.length(); i++) {
						if(-1 == source2.indexOf(w2.charAt(i)))
							continue w2;
						
						source2 = source2.substring(0, source2.indexOf(w2.charAt(i)))
								+ source2.substring(source2.indexOf(w2.charAt(i)) + 1);
					}
					
					System.out.println(w1 + " " + w2);
					solutions.add(w1 + " " + w2);
				}
			}
		}
		
		for(String puzzle: puzzles) {
			puzzle = puzzle.toLowerCase();
			System.out.println(puzzle);
			for(String solution: solutions) {
				if(solution.indexOf(' ') == puzzle.indexOf(' ')) {
					if(solution.charAt(0) == puzzle.charAt(0)) {
						if(solution.charAt(solution.indexOf(' ') + 1) 
								== puzzle.charAt(solution.indexOf(' ') + 1)) {
							System.out.println(solution);
						}
					}
				}
			}
		}
	}
	
	private ArrayList<String> getPossibleWords(String outline) {
		ArrayList<String> pWords = new ArrayList<String>();
		
		wordLoop:
		for(String w: dict.dict) {
			if(w.charAt(0) == outline.charAt(0) && w.length() == outline.length()) {
				for(int i = 1; i < w.length(); i++) {
					if(!source.contains(w.substring(i, i+1)))
						continue wordLoop;
				}
				pWords.add(w);
			}
		}
		
		return pWords;
	}
	
	public static void main(String[] args) {
		String[] puzzles = {"A***** N******", "E***** A******", "N*** D********"};
		new Feb14("VALENTINESDAY", puzzles).solve();
	}
}
