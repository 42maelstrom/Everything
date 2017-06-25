import java.util.ArrayList;
import java.util.Arrays;


public class Feb12 {
	
	public static final String DESCRIPTION = "The seven 7-letter words given"
			+ " below can be made by using the individual letters in CHARLES DARWIN,"
			+ " whose birthday is today. Insert those words into the grid, reading"
			+ " across, in such a way that a seven-letter job will be found when you"
			+ " ascend one of the columns from bottom to top. Ten letters have been"
			+ " placed to get you started.";
	
	private String[] words;
	private char[][] board;
	private Dictionary dict = new Dictionary(50);
	
	public Feb12(String[] words, char[][] board) {
		this.words = words;
		this.board = board;
	}
	
	public void solve() {
		for(char[][] board: getPossibleConfigurations()) {
			//printBoard(board);
			for(int c = 0; c < board[0].length; c++) {
				String word = "";
				for(int r = board.length - 1; r >= 0; r--) {
					word+=board[r][c];
				}
				//System.out.println(word);
				
				if(dict.isWord(word)) {
					System.out.println("Possible solution: ");
					printBoard(board);
					System.out.println("Word = " + word);
					System.out.println("(Column " + c + ")");
				}
			}
		}
	}
	
	private ArrayList<char[][]> getPossibleConfigurations() {
		ArrayList<ArrayList<String>> fittingLists = new ArrayList<ArrayList<String>>();
		
		for(int r = 0; r < board.length; r++) {
			ArrayList<String> fittingWords = new ArrayList<String>();
			
			for(String w: words) {
				if(fits(w, board[r]))
					fittingWords.add(w);
			}
			
			fittingLists.add(fittingWords);
		}
		
		return getConfigsRecursive(fittingLists);
	}
	
	private ArrayList<char[][]> getConfigsRecursive(ArrayList<ArrayList<String>> fittingLists) {
		if(fittingLists.size() == 1) {
			ArrayList<char[][]> options = new ArrayList<char[][]>();
			for(int i = 0; i < fittingLists.get(0).size(); i++) {
				char[][] config = new char[1][fittingLists.get(0).get(i).length()];
				config[0] = fittingLists.get(0).get(i).toCharArray();
				options.add(config);
			}
			
			return options;
		}
		
		ArrayList<char[][]> configs = new ArrayList<char[][]>();
		ArrayList<String> pFirstWords = fittingLists.remove(0);
		
		ArrayList<char[][]> lowerConfigs = getConfigsRecursive(fittingLists);

		for(String pFirstWord: pFirstWords) {
			for(char[][] lowerConfig: lowerConfigs) {
				char[][] augmentedConfig = new char[lowerConfig.length + 1][lowerConfig[0].length];
				augmentedConfig[0] = pFirstWord.toCharArray();
				for(int j = 0; j < lowerConfig.length; j++) {
					augmentedConfig[j+1] = lowerConfig[j];
				}
				configs.add(augmentedConfig);
			}
		}
		
		return configs;
	}
	
	private void printConfigs(ArrayList<char[][]> configs) {
		for(int i = 0; i < configs.size(); i++) {
			System.out.println();
			printBoard(configs.get(i));
			System.out.println();
		}
	}
	
	private boolean fits(String word, char[] spaces) {
		for(int i = 0; i < spaces.length; i++) {
			if(spaces[i] != ' ' && word.charAt(i) != spaces[i])
				return false;
		}
			
		return true;
	}
	
	private static void printBoard(char[][] board) {
		for(int r = 0; r < board.length; r++) {
			System.out.println();
			for(int c = 0; c < board[0].length; c++) {
				System.out.print("" + board[r][c] + ' ');
			}
		}
		System.out.println();
	}
	
	public static void main(String[] args) {
		String[] words = {"ARSENIC", "CHARADE", "CRASHED", "DISCERN", "INHALER", "RADICAL", "WARHEAD"};
		char[][] board = { {'C', ' ', 'A', ' ', ' ', ' ',' '} ,
											 {' ', ' ', 'S', ' ', ' ', ' ',' '} ,
											 {' ', 'A', ' ', ' ', ' ', 'A',' '} ,
											 {' ', 'A', ' ', ' ', ' ', 'A',' '} ,
											 {'C', ' ', 'A', ' ', ' ', ' ',' '} ,
											 {' ', ' ', ' ', ' ', ' ', ' ',' '} ,
											 {' ', ' ', 'S', ' ', ' ', ' ',' '} };

		new Feb12(words, board).solve();
	}
}
