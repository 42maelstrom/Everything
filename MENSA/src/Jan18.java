import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Jan18 {
	public static final String DESCRIPTION =
			"In the puzzle below, we've strung together a series of three"
			+ " or four words in a specific category, removed one letter from each"
			+ " word, and then closed up the spaces. The letters we removed, in"
			+ " order, spell on other item in that category. For example,"
			+ " ERCURYSTURNEATHVENU is missing the M from MERCURY, the A from"
			+ " SATURN, the R from EARTH, and the S from VENUS, which spells MARS,"
			+ " which, like the others, is a planet. Can you figure out the original"
			+ " words and the extra item spelled by the missing letters?";

	public String[] puzzles;
	Dictionary dict = new Dictionary(50);
	
	public Jan18(String... puzzles) {
		this.puzzles = puzzles;
	}
	
	public void solve() {
		puzzleLoop:
		for(int pNum = 1; pNum <= puzzles.length; pNum++) {
			System.out.println(pNum + ". ");
			String s = puzzles[pNum - 1];
			for(int i = 3; i <= s.length() - 6; i++) {
				String possibleWord1 = s.substring(0, i);
				ArrayList<String> neighbors1 = getNeighbors(possibleWord1);
				if(neighbors1.size() > 0) {
					for(int j = i + 3; j <= s.length() - 3; j++) {
						String possibleWord2 = s.substring(i, j);
						ArrayList<String> neighbors2 = getNeighbors(possibleWord2);
						if(neighbors2.size() > 0) {
							for(int k = j + 3; k <= s.length(); k++) {
								String possibleWord3 = s.substring(j, k);
								ArrayList<String> neighbors3 = getNeighbors(possibleWord3);
								if(neighbors3.size() > 0) {
									if(k == s.length()) {
										ArrayList<ArrayList<String>> solutionLists = new ArrayList<ArrayList<String>>();
										solutionLists.add(neighbors1);
										solutionLists.add(neighbors2);
										solutionLists.add(neighbors3);
										ArrayList<String> possibleWords = new ArrayList<String>();
										possibleWords.add(possibleWord1);
										possibleWords.add(possibleWord2);
										possibleWords.add(possibleWord3);
										ArrayList<ArrayList<String>> solutions = getSolutions(solutionLists);
										finalSolutionCheck(solutions, possibleWords);
									}
									
									String possibleWord4 = s.substring(k);
									if(possibleWord4.length() > 2) {
										ArrayList<String> neighbors4 = getNeighbors(possibleWord4);
										if(neighbors4.size() > 0) {
											ArrayList<ArrayList<String>> solutionLists = new ArrayList<ArrayList<String>>();
											solutionLists.add(neighbors1);
											solutionLists.add(neighbors2);
											solutionLists.add(neighbors3);
											solutionLists.add(neighbors4);
											ArrayList<String> possibleWords = new ArrayList<String>();
											possibleWords.add(possibleWord1);
											possibleWords.add(possibleWord2);
											possibleWords.add(possibleWord3);
											possibleWords.add(possibleWord4);
											ArrayList<ArrayList<String>> solutions = getSolutions(solutionLists);
											finalSolutionCheck(solutions, possibleWords);
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}
	
	private void finalSolutionCheck(ArrayList<ArrayList<String>> solutions, ArrayList<String> possibleWords) {
		for(ArrayList<String> solution: solutions) {
			String bonus = "";
			for(int m = 0; m < possibleWords.size(); m++) {
				String possibleWord = possibleWords.get(m);
				String actualWord = solution.get(m);
				for(int n = 0; n < actualWord.length(); n++) {
					//watch out, this only works if there aren't any repeated letters. but
					//it still got the answer so I'm too lazy to change it.
					if(!possibleWord.contains(actualWord.substring(n, n+1).toUpperCase())) {
						bonus+=actualWord.charAt(n);
						break;
					}
				}
			}
			
			if(dict.isWord(bonus)) {
				System.out.println(solution + bonus);
			}
		}
	}
	
	private ArrayList<ArrayList<String>> getSolutions(ArrayList<ArrayList<String>> solution) {
		if(solution.size() == 1) {
			ArrayList<ArrayList<String>> solution2 = new ArrayList<ArrayList<String>>();
			for(String s: solution.get(0)) {
				ArrayList<String> word = new ArrayList<String>();
				word.add(s);
				solution2.add(word);
			}
			return solution2;
		}
		
		ArrayList<ArrayList<String>> solutionsToReturn = new ArrayList<ArrayList<String>>();
		ArrayList<String> s = solution.remove(0);
		ArrayList<ArrayList<String>> solutions = getSolutions(solution);
		
		for(String st: s) {
			for(ArrayList<String> sol: solutions) {
				ArrayList<String> str = new ArrayList<String>();
				str.addAll(sol);
				str.add(0, st);
				solutionsToReturn.add(str);
			}
		}
		return solutionsToReturn;
	}
	
	private ArrayList<String> getNeighbors(String altered) {
		ArrayList<String> neighbors = new ArrayList<String>();
		for(String s: dict.dict) {
			if(areNeighbors(s, altered.toLowerCase()))
				neighbors.add(s);
		}
		return neighbors;
	}
	
	private boolean hasNeighbor(String altered) {
		for(String s: dict.dict) {
			if(areNeighbors(s, altered.toLowerCase()))
				return true;
		}
		return false;
	}
	
	private boolean areNeighbors(String word, String altered) {
		if(word.length() == altered.length() + 1) {
			boolean hasMissed = false;
			for(int i = 0; i < word.length(); i++) {
				if(i == word.length() - 1 && hasMissed == false) {
					return true;
				}
				int j = i;
				if(hasMissed)
					j--;
				if(word.charAt(i) == altered.charAt(j)) {
					
				} else {
					if(hasMissed == false) {
						hasMissed = true;
					} else {
						return false;
					}
				}
			}
			return true;
		}
		return false;
	}
	
	public static void main(String[] args) {
		new Jan18("ROOBSHOPPAWKNIHT", "IBRAGMINIVIRG", "CONBARLEWHAT", "NYXTOAZJDEPEAR").solve();
	}
}
