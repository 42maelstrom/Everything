
public class Feb15 {
	public static final String NAME = "PRESIDENT AND ACCOUNTED FOR";
	public static final String DESCRIPTION = "Change one letter in each word below"
		+ " to uncover a five-letter last name of a U.S. president. For example,"
		+ " change the R in GRAYEST to H to get HAYES.";
	
	private String[] puzzles;
	
	public Feb15(String... puzzles) {
		this.puzzles = puzzles;
	}
	
	public void solve() {
		for(String puzzle: puzzles) {
			System.out.print(puzzle + ": ");
			for(int i = 0; i < puzzle.length() - 1; i++) {
				for(int j = i + 1; j < puzzle.length(); j++) {
					String sub = puzzle.substring(i, j);
					for(String president: TxtParser.parseFile("Presidents.txt")) {
						if(areNeighbors(president.toLowerCase(), sub.toLowerCase()))
							System.out.print(president + " ");
					}
				}
			}
			System.out.println();
		}
	}
	
	private static void findNeighborsOfPresidents() {
		Dictionary dict = new Dictionary(50);
		for(String p: TxtParser.parseFile("Presidents.txt")) {
			System.out.print(p + ": ");
			
			for(String w: dict.dict) {
				for(int i = 0; i < w.length(); i++) {
					for(int j = i + 1; j < w.length(); j++) {
						String sub = w.substring(i, j);
							if(areNeighbors(sub.toLowerCase(), p.toLowerCase()))
								System.out.print(w + " ");
					}
				}
			}
			
			System.out.println();
		}
	}
	
	private static boolean areNeighbors(String s1, String s2) {
		if(s1.length() != s2.length())
			return false;
		
		int numDiff = 0;
		for(int i = 0; i < s1.length(); i++) {
			if(s1.charAt(i) != s2.charAt(i))
				numDiff++;
		}
		return 1 == numDiff;
	}
	
	public static void main(String[] args) {
		//new Feb15("Fragrance", "Dioramas","Intolerable","Unironic","Upsadaisy").solve();
		Feb15.findNeighborsOfPresidents();
	}
}
