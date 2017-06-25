import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;


public class Jan4 {
	public static final String NAME = "IN-CAR";
	public static final String DESCRIPTION = "Find the words in the crossword puzzle."
			+ " There are so many. Whenever there is a car symbol, it means there is the"
			+ " letters C-A-R there. The remaining letters will spell out three more"
			+ " words which have a theme I forget.";

	public final String[] grid;
	Dictionary dict = new Dictionary(50);
	
	public Jan4(String[] grid) {
		this.grid = grid;
		dict.addWord("medicare");
		dict.addWord("applecart");
		dict.addWord("carpool");
	}
	
	private static String reverse(String s) {
		String reverse = "";
		for(int i = s.length() - 1; i >= 0; i--)
			reverse+=s.charAt(i);
		return reverse;
	}
	
	public void solve() {
		
		ArrayList<String> allLines = new ArrayList<String>();
		ArrayList<ArrayList<Integer>> positionsList = new ArrayList<ArrayList<Integer>>();
		
		ArrayList<String> horiz = new ArrayList<String>();
		for(int i = 0; i < grid.length; i++) {
			horiz.add(grid[i]);
			
			ArrayList<Integer> positions = new ArrayList<Integer>();
			for(int n = 10*i; n <= 10*i + 10; n++)
				positions.add(n);
				
			positionsList.add(positions);
		}
		
		ArrayList<String> vert = new ArrayList<String>();
		for(int i = 0; i < grid[0].length(); i++) {
			String s = "";
			ArrayList<Integer> positions = new ArrayList<Integer>();
			for(int j = 0; j < grid.length; j++) {
				s+=grid[j].charAt(i);
				positions.add(10*j + i);
			}
				
			vert.add(s);
			positionsList.add(positions);
		}

		ArrayList<String> diag1 = new ArrayList<String>();
		for(int i = 0; i < grid[0].length(); i++) {
			String s = "", s2 = "";
			ArrayList<Integer> positions = new ArrayList<Integer>();
			ArrayList<Integer> positions2 = new ArrayList<Integer>();
			
			for(int j = 0; j < grid[0].length() - i; j++) {
				s+=grid[j].charAt(i + j);
				s2+=grid[i + j].charAt(j);
				positions.add(10*j + i + j);
				positions2.add(10*(i + j) + j);
			}
			
			diag1.add(s);
			if(i > 0)
				diag1.add(s2);
			
			positionsList.add(positions);
			if(i > 0)
				positionsList.add(positions2);
		}
		
		ArrayList<String> diag2 = new ArrayList<String>();
		for(int i = grid[0].length() - 1; i >= 0; i--) {
			String s = "", s2 = "";
			ArrayList<Integer> positions = new ArrayList<Integer>();
			ArrayList<Integer> positions2 = new ArrayList<Integer>();
			
			for(int j = 0; j <= i; j++) {
				s+=grid[j].charAt(i - j);
				s2+=grid[grid[0].length() - i - 1 + j].charAt(grid[0].length() - j - 1);
				positions.add(10*j + i - j);
				positions2.add(10*(grid[0].length() - i - 1 + j) + grid[0].length() - j - 1);
			}
			
			diag2.add(s);
			if(i != grid[0].length() - 1)
				diag2.add(s2);
			
			positionsList.add(positions);
			if(i != grid[0].length() - 1)
				positionsList.add(positions2);
		}
		
		String allLetters = "";
		for(String s: grid) 
			allLetters+=s;
	
		allLines.addAll(horiz);
		allLines.addAll(vert);
		allLines.addAll(diag1);
		allLines.addAll(diag2);
		
		System.out.println(allLines.toString());
		
		for(int i = 0; i < allLines.size(); i++) {
			for(int j = 0; j < allLines.get(i).length() - 1; j++) {
				for(int k = j + 1; k < allLines.get(i).length() + 1; k++) {
					String word = allLines.get(i).substring(j, k);
					if(word.contains("*") && word.length() > 3) {
						word = word.replace("*", "car");
						if(dict.isWord(word)) {
							System.out.println(word);
							ArrayList<Integer> positions = positionsList.get(i);
							for(int l = j; l < k; l++)
								allLetters = allLetters.substring(0, positions.get(l)) + " " + allLetters.substring(positions.get(l) + 1);
						}
						
						String word2 = reverse(allLines.get(i).substring(j, k)).replace("*", "car");
						if(dict.isWord(word2)) {
							System.out.println(word2);
							ArrayList<Integer> positions = positionsList.get(i);
							for(int l = j; l < k; l++) 
								allLetters = allLetters.substring(0, positions.get(l)) + " " + allLetters.substring(positions.get(l) + 1);
						}
					}
				}
			}
		}
		
		allLetters = allLetters.replace(" ", "");
		System.out.println(allLetters);
		for(int i = 0; i < allLetters.length() - 1; i++) {
			for(int j = i + 1; j <= allLetters.length(); j++) {
				String word = allLetters.substring(i, j);
				if(word.contains("*") && word.length() > 3) {
					word = word.replace("*", "car");
					if(dict.isWord(word))
						System.out.println(word);
				}
			}
		}
	}

	public static void main(String[] args) {
		String[] grid = { "vi*ious*rw", 
			"ebnpo*ibou",
			"*ai*oterse",
			"ic**coco*g",
			"d*at*elppa",
			"eags*arlei",
			"mtussefanr",
			"*ma*oniet*",
			"am*icature",
			"e*diologyl"};
			
			new Jan4(grid).solve();
	}
}
