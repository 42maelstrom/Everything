import java.util.ArrayList;
import java.util.Arrays;
import java.util.Queue;
import java.util.Scanner;


public class Feb6 {
	public static final String NAME = "MAGIC SQUARES";
	public static final String DESCRIPTION = "USing the letters in the word MAGIC"
			+ ", place one letter in each cell so that no two identical letters are"
			+ " in the same row horizontally, vertically, or diagonally—not even on"
			+ " a short diagonal. We've started you off with four letters.";
	public static final ArrayList<Class> HELPER_CLASSES = new ArrayList<Class>(Arrays.asList(Space.class));
	
	private char[] letters;
	private char[][] board;
	private int rows, cols;
	private ArrayList<ArrayList<ArrayList<Character>>> pChars;
	private ArrayList<Space> toAdd;
	private int totalAdded;
	
	public Feb6(char[] letters, char[][] board) {
		this.letters = letters;
		this.board = board;
		rows = board.length;
		cols = board[0].length;
		totalAdded = 0;
		
		toAdd = new ArrayList<Space>();
		
		//each spot on the board has an arraylist of possible characters (ex. {'M','A'}) that could go there
		pChars = new ArrayList<ArrayList<ArrayList<Character>>>();
		
		for(int r = 0; r < rows; r++) {
			ArrayList<ArrayList<Character>> row = new ArrayList<ArrayList<Character>>();
			for(int c = 0; c < cols; c++) {
				ArrayList<Character> pLetters = new ArrayList<Character>();
				for(Character ch: letters) 
					pLetters.add(ch);
				
				row.add(pLetters);
				
				if(board[r][c] != ' ')
					toAdd.add(new Space(r, c, board[r][c]));
			}
			pChars.add(row);
		}
		
		
	}
	
	/**
	 * Updates the pChars list to take away new spots
	 */
	private void addLetter(Space s) {
		System.out.println(s.toString());
		
		board[s.r][s.c] = s.ch;
		totalAdded++;
		
		pChars.get(s.r).get(s.c).clear();
		
		for(int dX = -1; dX <= 1; dX++) {
			for(int dY = -1; dY <= 1; dY++) {
				if(dX == 0 && dY == 0)
					continue;
				
				int c2 = s.c, r2 = s.r;
				
				c2 += dX;
				r2 += dY;
				
				//System.out.println("Removing: " + s.toString() + " , dir = (" + dX + ", " + dY + ")");
				while(isInbounds(r2, c2)) {
					//System.out.println(s.toString());
					pChars.get(r2).get(c2).remove((Character)s.ch);
					r2 += dY;
					c2 += dX;
				}
				//System.out.println("Out of bounds : (" + c2 + ", " + r2 + ")");
			}
		}
		
		
	}
	
	private boolean isInbounds(int r, int c) {
		return r >= 0 && r < rows && c >= 0 && c < cols;
	}
	
	public void solve() {
		while(totalAdded < rows*cols) {
			while(!toAdd.isEmpty())
				addLetter(toAdd.remove(0));
			
			//printBoard(pChars);
			//String s = new Scanner(System.in).nextLine();
			for(int r = 0; r < rows; r++) {
				for(int c = 0; c < cols; c++) {
					if(pChars.get(r).get(c).size() == 1)
						toAdd.add(new Space(r, c, pChars.get(r).get(c).get(0)));
				}
			}
		}
		
		for(int r = 0; r < rows; r++) {
			System.out.println();
			for(int c = 0; c < cols; c++) {
				System.out.print(board[r][c] + " ");
			}
		}
	}
	
	private void printBoard(ArrayList<ArrayList<ArrayList<Character>>> a) {
		for(int r = 0; r < rows; r++) {
			System.out.println();
			for(int c = 0; c < cols; c++) {
				System.out.print(a.get(r).get(c).toString() + "  ");
			}
		}
	}
	
	public static void main(String[] args) {
		char[] letters = {'M', 'A', 'G', 'I', 'C'};
		
		char[][] board = { {' ',' ','M',' ',' '},
				 							 {' ',' ',' ',' ',' '},
				 							 {' ',' ','C',' ',' '},
				 							 {'G',' ',' ',' ',' '},
				 							 {' ',' ',' ',' ','A'} };
		
		new Feb6(letters, board).solve();
		
	}
}
