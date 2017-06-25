import java.util.ArrayList;
import java.util.Arrays;


public class Feb8 {
	public static final String DESCRIPTION = "Insert the given letters, one per"
			+ " cell, to complete a word square containing ten different words;:"
			+ " five reading across and five erading down. We've placed all the"
			+ " vowels to get you started.";
	
	private char[][] board;
	private ArrayList<Character> consonants;
	private Dictionary dictionary;
	private int size;
	private ArrayList<String> dict;
	private boolean[] isSolvedRow, isSolvedCol;
	
	private boolean isFinalSolutionFound = false;
	
	public Feb8(char[][] board, char[] consonants) {
		this.board = board;
		this.consonants = new ArrayList<Character>(consonants.length);
		size = board.length;
		for(char ch: consonants) {
			this.consonants.add(ch);
		}
		
		dictionary = new Dictionary(70);
		
		dict = new ArrayList<String>();
		
		String allowedLetters = "AEIOUY";
		for(char ch: consonants) {
				allowedLetters+= ch;
		}
		
		allowedLetters = allowedLetters.toLowerCase();
		
		outer:
		for(String word: dictionary.dict) {
			if(word.length() == size) {
				for(int i = 0; i < size; i++) {
					if(-1 == allowedLetters.indexOf(word.charAt(i)))
							continue outer;
				}
				dict.add(word);
			}
		}
		
		isSolvedRow = new boolean[size];
		isSolvedCol = new boolean[size];
		
	}
	
	public void solve() {
		doLogicalSolving();
		for(int r = 0; r < size; r++) {
			System.out.println();
			for(int c = 0; c < size; c++) {
				System.out.print(Character.toString(board[r][c]).toUpperCase() + ' ');
			}
		}
	}
	
	private void doLogicalSolving() {
		boolean didChange = true;
		
		while(didChange) {
			didChange = false;
			
			for(int r = 0; r < size; r++) {
				if(!isSolvedRow[r]) {
					ArrayList<String> pWords = getPossibleFits(board[r]);
					System.out.println(pWords);
					if(pWords.size() == 1) {
						String w = pWords.get(0);
						System.out.println("SUCCESS: " + w);
						isSolvedRow[r] = true;
						didChange = true;
						
						for(int i = 0; i < size; i++) {
							//if you put down a new consonant that wasn't already there,
							//take it out of the sack
							if(board[r][i] == ' ')
								consonants.remove((Character)w.charAt(i));
							
							board[r][i] = w.charAt(i);
							
						}
					}
				}
			}
			
			for(int c = 0; c < size; c++) {
				if(!isSolvedCol[c]) {
					char[] spaces = new char[size];
					for(int i = 0; i < size; i++) {
						spaces[i] = board[i][c];
					}
		
					ArrayList<String> pWords = getPossibleFits(spaces);
					System.out.println(pWords);
					if(pWords.size() == 1) {
						String w = pWords.get(0);
						System.out.println("SUCCESS: " + w);
						isSolvedCol[c] = true;
						didChange = true;
						
						for(int i = 0; i < size; i++) {
							//if you put down a new consonant that wasn't already there,
							//take it out of the sack
							if(board[i][c] == ' ')
								consonants.remove((Character)w.charAt(i));
							
							board[i][c] = w.charAt(i);
						}
					}
				}
			}
		}
	}
	
	private ArrayList<String> getPossibleFits(char[] spaces) {
		//System.out.println(Arrays.toString(spaces));
		//System.out.println("consonants " + consonants);
		ArrayList<String> possibleFits = new ArrayList<String>();
		
		ArrayList<Character> cons;
		
		outer:
		for(String w: dict) {
			cons = (ArrayList<Character>)consonants.clone();
			
			for(int i = 0; i < size; i++) {
				boolean isVowel = -1 != "aeiouy".indexOf(w.charAt(i));
				boolean isBlankSpace = spaces[i] == ' ';
				
				if(isVowel) {
					if(w.charAt(i) != spaces[i])
						continue outer;
				} else {
					//it's a consonant
					if(isBlankSpace) {
						if(!cons.contains((Character)w.charAt(i)))
							continue outer;
					} else {
						cons.remove((Character)w.charAt(i));
						if(w.charAt(i) != spaces[i])
							continue outer;
					}
				}
			}
			possibleFits.add(w);
		}
	
		return possibleFits;
	}
	
	private void solveRecursive() {
		doLogicalSolving();
	}
	
	public static void main(String[] args) {
		char[][] board = { {' ', ' ', ' ', 'a', ' '},
											 {' ', ' ', 'o', ' ', 'o'},
											 {'o', 'a', ' ', 'e', ' '},
											 {'o', ' ', 'o', ' ', 'e'},
											 {' ', 'e', ' ', ' ', 'y'}, };
		char[] consonants = {'c', 'd', 'm', 'm', 'n', 'n', 'n', 'p', 'r', 'r', 'r', 's', 't', 'z'};

		new Feb8(board, consonants).solve();
	}
}
