import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


public class Mar5 {
	public static final String NAME = "PLAYTIME";
	public static final String DESCRIPTION = "Start at the top and choose one"
			+ " letter from each successive row to create five 5-letter things that"
			+ " people play. You may jump to any column as you go down. there is"
			+ " never a need to rearrange the letters, and every letter will be used"
			+ " exactly once. What are the five words?";
	
	char[][] grid;
	Dictionary dict = new Dictionary(50);
	
	public Mar5(char[][] grid) {
		this.grid = grid;
	}
	
	public void solve() {
		ArrayList<String> pastAnswers = new ArrayList<String>();
		for(ArrayList<String> answer: getAnswers(grid)) {
			String pastAnswer = "";
			for(String w: answer) {
				pastAnswer+=w + " ";
			}
			if(!pastAnswers.contains(pastAnswer)) {
				System.out.println(pastAnswer);
				pastAnswers.add(pastAnswer);
			}
		}
	}
	
	
	//get the possibilities for the first word
	//for each first word, if it's a word,
	//find the solution for the rest of the grid
	//if the solution exists, return it.
	//otherwise, keep trying first words.
	public ArrayList<ArrayList<String>> getAnswers(char[][] grid) {
		ArrayList<ArrayList<String>> answers = new ArrayList<ArrayList<String>>();
		
		ArrayList<String> firstWords = getPossibleFirstWords(grid);
		for(String w: firstWords) {
			if(dict.isWord(w)) {
				if(grid[0].length == 1) {
					ArrayList<String> answer = new ArrayList<String>();
					answer.add(w);
					answers.add(answer);
				} else {
					char[][] newGrid = new char[grid.length][grid[0].length - 1];
					for(int j = 0; j < grid.length; j++) {
						int count = 0;
						for(int k = 0; k < grid[0].length; k++) {
							if(grid[j][k] != w.charAt(j) || count != k) {
								newGrid[j][count] = grid[j][k];
								count++;
							}
						}
					}
					
					ArrayList<ArrayList<String>> lowerAnswers = getAnswers(newGrid);
					if(lowerAnswers != null) {
						for(ArrayList<String> lowerAnswer: lowerAnswers) {
							ArrayList<String> answer = new ArrayList<String>();
							answer.add(w);
							answer.addAll(lowerAnswer);
							answers.add(answer);
						}
					}//end if lowerAnswes != null
				}// end else
			}//end if first word is a word
		}//for each first word
		
		if(answers.isEmpty())
			return null;

		return answers;
	}
	
	private ArrayList<String> getPossibleFirstWords(char[][] grid) {
		ArrayList<String> firstWords = new ArrayList<String>();
		
		if(grid.length == 1) {
			for(char c: grid[0]) {
				firstWords.add("" + c);
			}
			return firstWords;
		}
		
		String firstLetter = "" + grid[0][0];
		char[][] lowerGrid = new char[grid.length - 1][grid[0].length];
		for(int i = 1; i < grid.length; i++) {
			lowerGrid[i - 1] = grid[i];
		}
		
		ArrayList<String> possibleLowerFirstWords = getAllPossibleWords(lowerGrid);
		for(String lowerBit: possibleLowerFirstWords) {
				firstWords.add("" + firstLetter + lowerBit);
		}
		return firstWords;
	}
	
	private ArrayList<String> getAllPossibleWords(char[][] grid ) {
		ArrayList<String> firstWords = new ArrayList<String>();
		
		if(grid.length == 1) {
			for(char c: grid[0]) {
				firstWords.add("" + c);
			}
			return firstWords;
		}
		
		String firstLetter = "" + grid[0][0];
		char[][] lowerGrid = new char[grid.length - 1][grid[0].length];
		for(int i = 1; i < grid.length; i++) {
			lowerGrid[i - 1] = grid[i];
		}
		
		ArrayList<String> possibleLowerFirstWords = getAllPossibleWords(lowerGrid);
		for(String lowerBit: possibleLowerFirstWords) {
			for(int i = 0; i < grid[0].length; i++) {
				firstWords.add("" + grid[0][i] + lowerBit);
			}
		}
		return firstWords;
	}
	                                                               
	
	
	
	public static void main(String[] args) {
		char[][] grid = { {'B', 'C', 'C', 'D', 'P'},
											{'A', 'I', 'I', 'H', 'A'},
											{'E', 'R', 'T', 'A', 'N'},
											{'N', 'G', 'T', 'S', 'C'},
											{'S', 'H', 'O', 'O', 'S'} };
		new Mar5(grid).solve();
	}
}
