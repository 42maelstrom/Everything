import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Scanner;

public class BestMoveFinder {
	/*
	 * The board and rack we are finding the best move for. This gets transposed
	 * halfway through via transpose().
	 */
	private BoardAndRack bar;
	
	private Board board;
	
	/*
	 * Because we deal with characters usually, this is faster and easier to
	 * manipulate than the ArrayList<Tile> in the BoardAndRack.
	 */
	private ArrayList<Character> charRack;
	
	/*
	 * for each space on the board, has a string containing characters that, when
	 * played here, form valid down words.
	 */
	private String[][] crossChecks;
	
	/*
	 * Places where we may begin making a move. Are any spaces that are adjacent
	 * to a space with a tile on it.
	 */
	private boolean[][] anchors;
	
	private static final Trie TRIE = ScrabbleGame.TRIE;
	
	private PriorityQueue<PossibleMove> moveList;
	
	// for convenience of referencing
	private int bHeight;
	private int bWidth;
	
	// gets flipped to true halfway through when board is transposed.
	private boolean isTransposed;
	
	/*
	 * to keep track of whether move list has been filled up so when someone calls
	 * getBestMove() or getPossibleMoveList() we don't redo the whole thing over. 
	 */
	private boolean hasRun;
	
	// to help with logging moves in the correct orientation
	private Board untransposedBoard;
	
	public BestMoveFinder(BoardAndRack bar) {

		this.bar = bar;
		this.board = bar.board;
		this.untransposedBoard = bar.board;
		bHeight = bar.board.HEIGHT;
		bWidth = bar.board.WIDTH;

		charRack = new ArrayList<Character>(bar.rack.size());
		for (Tile t: bar.rack) {
			charRack.add(t.c);
		}
	
		moveList = new PriorityQueue<PossibleMove>(10, Collections.reverseOrder());
		isTransposed = false;
		hasRun = false;
	}
	
	private void findBestMove() {
		//bar.print();
		
		generateAllAcrossMoves();
		transpose();
		generateAllAcrossMoves();
		hasRun = true;
	}
	
	public PriorityQueue<PossibleMove> getMoveList() {
		if(!hasRun)
			findBestMove();
		return moveList;
	}
	
	public PossibleMove getBestPossibleMove() {
		if(!hasRun)
			findBestMove();
		
		if(moveList.isEmpty())
			return new PossibleMove(""); //no moves found, pass.
		//System.out.println("BWF idea " + moveList.peek().toString());
		return moveList.peek();
	}
	
	public Move getBestMove() {
		return bar.board.convertToMove(getBestPossibleMove());
	}
	
	private void calculateCrossChecksAndAnchors() {
			
		String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		crossChecks = new String[board.HEIGHT][board.WIDTH];
		anchors = new boolean[board.HEIGHT][board.WIDTH];
		
		for (int x = 0; x < bar.board.WIDTH; x++) {
			for (int y = 0; y < bar.board.HEIGHT; y++) {
				if (isTile(x, y)) {
					continue;
				}
				//if adjacent tile is taken, do some cross-checkin'
				if (   x + 1 != bWidth  && isTile(x + 1, y)
						|| x     != 0       && isTile(x - 1, y) 
				    || y + 1 != bHeight && isTile(x, y + 1)
				    || y     != 0       && isTile(x, y - 1)) {
					String crossCheck = "";
					for (char c: alphabet.toCharArray()) {
						if (isValidCharPlacement(c, x, y))
							crossCheck+=c;
					}
//						System.out.println(new Position(x, y).toString() + crossCheck);
					crossChecks[y][x] = crossCheck;
					anchors[y][x] = true;
				} else {
					crossChecks[y][x] = alphabet;
				}
			}
		}

		if(bar.board.isWholeBoardEmpty()) {
			anchors[bHeight / 2][bWidth / 2] = true;
		}
	}
	
	private void generateAllAcrossMoves() {
		calculateCrossChecksAndAnchors();
		
		//for each anchor point
		for(int x = 0; x < bar.board.WIDTH; x++) {
			for(int y = 0; y < bar.board.HEIGHT; y++) {
				if(!isAnchor(x, y))
					continue;
//					System.out.println("Anchor: " + new Position(x, y).toString());
				generateAllMovesFromAnchor(x, y);
			}
		}
	}
	
	private void generateAllMovesFromAnchor(int x, int y) {
		if(x != 0 && isTile(x - 1, y)) {
			int x2 = x - 1;
			String s = "" + getChar(x2, y);
			Node n = TRIE.rootNode;
			while (x2 != 0 && isTile(x2 - 1, y)) {
				x2--;
				char c = getChar(x2, y);
				s = c + s;
			}
			n = n.getNode(s);
			if(n != null)
				extendRight(s, n, x, y, true);
		} else {
			int x2 = x;
			int limit = 0;
			while (x2 != 0 && !isAnchor(x2 - 1, y)) {
				x2--;
				limit++;
			}
			leftPart("", TRIE.rootNode, x, y, limit);
		}
	}
	
	private void leftPart(String partialWord, Node n, int x, int y, int limit) {
		//	System.out.println("LP: " + partialWord + " " + n.getChars().size() + " " + new Position(x, y).toString() + " " + limit);
		extendRight(partialWord, n, x, y, true);
		
		if (limit > 0) {
			for(char c: n.getChars()) {
				if(charRack.contains(c)) {
					charRack.remove(Character.valueOf(c));
					Node n2 = n.getNode(c);
					
					leftPart(partialWord + c, n2, x, y, limit - 1);
					
					charRack.add(c);
				}
				
				if (charRack.contains('*')){
					charRack.remove(Character.valueOf('*'));
					Node n2 = n.getNode(c);
					
					leftPart(partialWord + Character.toLowerCase(c), n2, x, y, limit - 1);
					charRack.add('*');
				}
			}
		}
	}
	
	private void extendRight(String partialWord, Node n, int x, int y,
			boolean isFirstTime) {
	//		System.out.println("ER: " + partialWord + " " 
		//			+ new Position(x, y).toString() + " " + isFirstTime); 
		if(!isTile(x, y)) {
			//have to place anchor square tile if it's first time. 
			if(n.isEndOfWord() && !isFirstTime) {
				logMove(partialWord, x - partialWord.length(), y);
			}
			
			if(n.isTerminal() || x + 1 == bWidth)
				return;
			
			for(char c: n.getChars()) {
				if(charRack.contains(c) && crossChecks[y][x].contains(c + "")) {
					charRack.remove(Character.valueOf(c));
					extendRight(partialWord + c, n.getNode(c), x + 1, y, false);
					charRack.add(c);
				}
				
				if(charRack.contains('*') && crossChecks[y][x].contains(c + "")) {
					charRack.remove(Character.valueOf('*'));
					extendRight(partialWord + Character.toLowerCase(c), n.getNode(c), x + 1, y, false);
					charRack.add('*');
				}
			}
		} else {
			if(x + 1 == bWidth)
				return;
			
			char c = getChar(x, y);
			if(n.getChars().contains(c)) {
				extendRight(partialWord + c, n.getNode(c), x + 1, y, false); 
			}
		}
	}
	
	private void logMove(String word, int x, int y) {
		//	System.out.println("***" + word + " " + new Position(x, y).toString() + " " + isTransposed);
		int score = score(word, x, y);
		PossibleMove pm;
		if(isTransposed) {
			pm = new PossibleMove(word, y, x, score, false);
		} else {
			pm = new PossibleMove(word, x, y, score, true);
		}
		moveList.add(pm);
	}
	
	/**
	 * Returns the score for making this hypothetical word.
	 * The move is assumed to be horizontal, inbounds. Tiles that are already on 
	 * the board are included in the word, so it is assumed these match up as well
	 *  
	 * @param word - the word to be played. includes tiles that are already on
	 * the board.
	 * @param x - the x position of the leftmost tile in the word
	 * @param y - the y position of the tiles
	 * @return
	 */
	private int score(String word, int x, int y) {
		int totalVertScore = 0;
		int horizScore = 0;
		int wordMultiplier = 1;
		int minX = x;
		int numNewTiles = 0;
		
		while(minX != 0 && isTile(minX - 1, y)) {
			minX--;
		}
		
		int maxX = minX + word.length() - 1;
		
		for(x = minX; x <= maxX; x++) {
			if(isTile(x, y)) {
				horizScore+=board.getTile(x, y).value;
			} else {
				numNewTiles++;
				char c = word.charAt(x - minX);
				int val = Tile.getValue(word.charAt(x - minX));
				int wmHere = board.getWordMultiplier(x, y);
				int lmHere = board.getLetterMultiplier(x, y);
				
				val*=lmHere;
				horizScore+=val;
				wordMultiplier*=wmHere;
				
				int vertScore = val;
				int y2 = y;
				
				while (y2 > 0 && isTile(x, y2 - 1)) {
					y2--;
					vertScore+=board.getTile(x, y2).value;
				}
				
				int y3 = y;
				while(y3 < bHeight - 1 && isTile(x, y3 + 1)) {
					y3++;
					vertScore+=board.getTile(x, y3).value;
				}
				
				vertScore*=wmHere;
				boolean isVertMove = y3 != y || y2 != y;
				if(isVertMove) {
					totalVertScore+=vertScore;
				}
			}
		}
		
		horizScore*=wordMultiplier;
		if(numNewTiles >= 7)
			return totalVertScore + horizScore + 50;
		
		return totalVertScore + horizScore;
	}
	
	/**
	 * Is only invalid if this character creates a down word that is illegal.
	 */
	private boolean isValidCharPlacement(char c, int x, int y) {
		String word = String.valueOf(c);
		int y2 = y;
		while (y2 != 0 && isTile(x, y2 - 1)) {
			y2--;
			word = getChar(x, y2) + word;
		}
		
		y2 = y;
		while (y2 + 1 != bHeight && isTile(x, y2 + 1)) {
			y2++;
			word = word + getChar(x, y2);
		}
		
		if(word.length() > 1 && !TRIE.isWord(word))
			return false;
		return true;
	}
	
	private boolean isTile(int x, int y) {
		return board.isTile(x, y);
	}
	
	private boolean isAnchor(int x, int y) {
		return anchors[y][x] == true;
	}
	
	private char getChar(int x, int y) {
		return board.getChar(x, y);
	}
	
	private void transpose() {
		BoardSpot[][] bs = new BoardSpot[bar.board.WIDTH][bar.board.HEIGHT];
	
		for(int x = 0; x < bs[0].length; x++) {
			for(int y = 0; y < bs.length; y++) {
				bs[y][x] = board.getBoardSpot(y, x);  
			}
		}
		
		ArrayList<Tile> rack = bar.rack;
		
		Board b = new Board(bs);
		bar = new BoardAndRack(b, rack);
		board = b;
		
		int h = bHeight;
		bHeight = bWidth;
		bWidth = h;
		
		isTransposed = true;
	}
	
	public static void main(String[] args) {
		try {
			BoardAndRack bar = ImageConverter.convertImageFromFile("Boards/IMG_0307.png");
			bar.print();
			PriorityQueue<PossibleMove> bms = new BestMoveFinder(bar).getMoveList();
			while (bms.isEmpty() == false) {
				PossibleMove m = bms.remove();
				System.out.println(m.toString());
			}
			
			Move bm = new BestMoveFinder(bar).getBestMove();
			System.out.println(bm.toString());
			bar.board.addMove(bm);
			bar.print();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
