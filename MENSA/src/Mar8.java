import java.util.ArrayList;
import java.util.Arrays;


public class Mar8 {
	public static final String DESCRIPTION = "The end of aquip by author Isaac"
			+ " Asimov appears in the grid. To spell it out, start on the circled"
			+ " \"G\" and go from letter to adjacent letter as you move up, down, or"
			+ " across, but never diagonally. Every letter in the grid will be used"
			+ " exactly once. The enumeration (the number of letters in the words)"
			+ " is (5 9 2 5 2 2 3 2). The quip begins: \"Those people who think they"
			+ " know everything are a ... \"";
	
	public static final ArrayList<Class> HELPER_CLASSES = new ArrayList<Class>(Arrays.asList(Point.class));
	
	char[][] letters;
	int[] enumeration;
	int[] cumSumEnum;
	Point start;
	Dictionary dict = new Dictionary(50);
	
	public Mar8(char[][] letters, int[] enumeration, Point start) {
		dict.addWord("isnt");
		this.letters = letters;
		this.enumeration = enumeration;
		this.start = start;
		
		cumSumEnum = new int[enumeration.length];
		cumSumEnum[0] = enumeration[0];
		
		for(int i = 1; i < cumSumEnum.length; i++) {
			cumSumEnum[i] = cumSumEnum[i - 1] + enumeration[i];
		}
	}
	
	public void solve() {
		boolean[][] isUsed = new boolean[letters.length][letters[0].length];
		isUsed[start.y][start.x] = true;
		
		System.out.println(recursiveSolve(Character.toString(letters[start.y][start.x]), 0, start, isUsed, 1));
	}
	
	private String recursiveSolve(String soFar, int wordCount, Point currentP, boolean[][] isUsed, int letterCount) {
		if(letterCount == letters.length*letters[0].length)
			return Character.toString(letters[currentP.y][currentP.x]);
		
		for(int i = 0; i < 4; i++) {
			//to go through all four directions not diagonal
			int dx = i >= 2 ? 0 : 2*i - 1;
			int dy = i <  2 ? 0 : 2*i - 5;
			int newX = currentP.x + dx;
			int newY = currentP.y + dy;
			
			Point newP = new Point(newX, newY);
			//System.out.println(soFar + " " + newP.toString());
			
			if(newX < 0 || newX >= letters[0].length || newY < 0 || newY >= letters.length) {
				//System.out.println("out");
				continue;
			}
			
			//System.out.print(letters[newY][newX] + " " + newP.toString() + " ");
			
			if(isUsed[newY][newX]) {
				//System.out.println("used");
				continue;
			}
			
			//System.out.println("sweet");

			
			int newWordCount = wordCount;
			if(cumSumEnum[wordCount] == letterCount + 1) {
				newWordCount++;
				String word = soFar.substring(1 + soFar.length() - enumeration[wordCount]) + letters[newP.y][newP.x];
				if(!dict.isWord(word))
					continue;
				System.out.println(word);
			}
			
			boolean[][] newIsUsed = new boolean[isUsed.length][isUsed[0].length];
			
			for(int r = 0; r < isUsed.length; r++) {
				for(int c = 0; c < isUsed[0].length; c++) {
					newIsUsed[r][c] = isUsed[r][c];
				}
			}
			
			newIsUsed[newP.y][newP.x] = true;
			
			String solution = recursiveSolve(soFar + letters[newP.y][newP.x], newWordCount, newP, newIsUsed, letterCount + 1);
			
			if(solution != null)
				return letters[currentP.y][currentP.x] + solution;
		}
		
		return null;
	}
	
	public static void main(String[] args) {
		char[][] letters = { {'T', 'O', 'T', 'H', 'O', 'O'},
												 {'E', 'A', 'Y', 'E', 'S', 'D'},
												 {'C', 'N', 'O', 'O', 'F', 'O'},
												 {'R', 'E', 'N', 'N', 'U', 'H'},
												 {'G', 'A', 'T', 'A', 'S', 'W'} };
		Point start = new Point(0, 4);
		int[] enumeration = {5, 9, 2, 5, 2, 2, 3, 2};
		
		new Mar8(letters, enumeration, start).solve();
	}
}
