import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Board {
	private BoardSpot[][] board;
	public final int WIDTH, HEIGHT;
	public boolean isWholeBoardEmpty;
	
	public Board(BoardSpot[][] board) {
		this.board = board;
		HEIGHT = board.length;
		WIDTH = board[0].length;
		
		isWholeBoardEmpty = true;
		outer:
		for(BoardSpot[] array: board) {
			for(BoardSpot bs: array) {
				if(bs != null && bs.isTile()) {
					isWholeBoardEmpty = false;
					break outer;
				}
			}
		}
	}
	
	public Board(int[][] board) {
		HEIGHT = board.length;
		WIDTH = board[0].length;
		BoardSpot[][] realBoard = new BoardSpot[HEIGHT][WIDTH];
		for(int x = 0; x < WIDTH; x++) {
			for(int y = 0; y < HEIGHT; y++) {
				BoardSpot bs = new BoardSpot();
				if(board[y][x] < -1) {
					bs.letterMultiplier = board[y][x]*-1;
				} else if(board[y][x] > 1) {
					bs.wordMultiplier = board[y][x];
				}
				realBoard[y][x] = bs;
			}
		}
		this.board = realBoard;
		isWholeBoardEmpty = true;
	}
	
	public boolean isWholeBoardEmpty() {
		return isWholeBoardEmpty;
	}
	
	public void addTile(int x, int y, Tile t) {
		isWholeBoardEmpty = false;
		board[y][x].tile = t;
	}
	
	public int getWordMultiplier(int x, int y) {
		return board[y][x].wordMultiplier;
	}
	
	public int getLetterMultiplier(int x, int y) {
		return board[y][x].letterMultiplier;
	}
	
	public char getChar(int x, int y) {
		return board[y][x].tile.c;
	}
	
	public Tile getTile(int x, int y) {
		return board[y][x].tile;
	}
	
	public BoardSpot getBoardSpot(int x, int y) {
		return board[y][x];
	}
	
	public boolean isTile(int x, int y) {
		return board[y][x].isTile();
	}
	
	/**
	 * Converts a Move into an Move based of this board.
	 * Assumed Move is inbounds and etc.
	 */
	public Move convertToMove(PossibleMove pm) {
		if(pm == null)
			return null;
		if(pm.isExchange) {
			ArrayList<Tile> tiles = new ArrayList<Tile>(pm.s.length());
			for(char c: pm.s.toCharArray())
				tiles.add(new Tile(c));
			return new Move(tiles);
		} else if(pm.isPass) {
			return new Move();
		}
		
		String word = pm.s;
		int x = pm.x;
		int y = pm.y;
		
		ArrayList<Tile> tiles = new ArrayList<Tile>(word.length());
		ArrayList<Position> positions = new ArrayList<Position>(word.length());
		for(int i = 0; i < word.length(); i++) {
			int dX = pm.isHorizontal ? 1 : 0;
			int dY = pm.isHorizontal ? 0 : 1;
			
			if(!isTile(x + i*dX, y + i*dY)) {
				tiles.add(new Tile(word.charAt(i)));
				positions.add(new Position(
						x + i*dX, y + i*dY));
			}
		}
		return new Move(tiles, positions);
	}
	
	/**
	 * Assumes it's a valid move and all
	 * @param m
	 * @return
	 */
	public PossibleMove convertToPossibleMove(Move m) {
		if(m.isPass())
			return new PossibleMove("");
		if(m.isExchange()) {
			String s = "";
			for(Tile t: m.getTiles()) {
				s+=t.c;
			}
			return new PossibleMove(s);
		}
		
		boolean isVerticalMove = false;
		String s = "";
		int startX;
		int startY;
		
		int score = 0;
		if(m.size() == 1 || m.getPositions().get(0).x == m.getPositions().get(1).x)
			isVerticalMove = true;
		
		if(isVerticalMove) {
			//go up and down from one of the positions until you get to empty space. 
			//this is the main word
			int x = m.getPositions().get(0).x;
			int maxY = m.getPositions().get(0).y;
			int minY = maxY;
			
			while(minY > 0 && (board[minY - 1][x].isTile() || m.getPositions().contains(
					new Position(x, minY - 1)))) {
				minY--;
			}
			
			while(maxY < HEIGHT - 1 && (board[maxY + 1][x].isTile() || m.getPositions().contains(
					new Position(x, maxY + 1)))) {
				maxY++;
			}
			
			for(int y = minY; y <= maxY; y++) {
				if(isTile(x, y)) {
					s+=board[y][x].tile.c;
				} else {
					int i = m.getPositions().indexOf(new Position(x, y));
					s+= m.getTiles().get(i);
				}
			}
			startX = x;
			startY = minY;
		} else {
			//go left and right from one of the positions until you get to empty space. 
			//this is the main word
			int y = m.getPositions().get(0).y;
			int maxX = m.getPositions().get(0).x;
			int minX = maxX;
			
			while(minX > 0 && (board[y][minX - 1].isTile() || m.getPositions().contains(
					new Position(minX - 1, y)))) {
				minX--;
			}
			
			while(maxX < WIDTH - 1 && (board[y][maxX + 1].isTile() || m.getPositions().contains(
					new Position(maxX + 1, y)))) {
				maxX++;
			}
			
			for(int x = minX; x <= maxX; x++) {
				if(isTile(x, y)) {
					s+=board[y][x].tile.c;
				} else {
					int i = m.getPositions().indexOf(new Position(x, y));
					s+= m.getTiles().get(i);
				}
			}
			startX = minX;
			startY = y;
		}
		
		return new PossibleMove(s, startX, startY, 0, !isVerticalMove);
	}

	public void printBoard() {
		for(int i = 0; i < WIDTH + 2; i++)
			System.out.print("-");
		System.out.println();
		
		for(int y = 0; y < HEIGHT; y++) {
			System.out.print("|");
			for(int x = 0; x < WIDTH; x++) {
				System.out.print(board[y][x].toString());
			}
			System.out.println("|");
		}
		
		for(int i = 0; i < WIDTH + 2; i++)
			System.out.print("-");
		System.out.println();
	}

	public boolean isValidMove(Move m) {
		boolean areXsEqual = true;
		boolean areYsEqual = true;
		
		int x1 = m.getPositions().get(0).x;
		int y1 = m.getPositions().get(0).y;
		
		for(Position p: m.getPositions()) {
			if(p.x != x1)
				areXsEqual = false;
			if(p.y != y1)
				areYsEqual = false;
			
			if(p.y < 0 || p.y >= HEIGHT && p.x < 0 || p.x >= WIDTH || board[p.y][p.x].isTile())
				return false;
		}
		
		if(areXsEqual == false && areYsEqual == false)
			return false;
		
		if(isWholeBoardEmpty()) {
			if(!m.getPositions().contains(new Position(WIDTH / 2, HEIGHT / 2)))
					return false;
		}
		if(areXsEqual) {
			//is a vertical move
			//find min and max
			int minY = m.getPositions().get(0).y;
			int maxY = m.getPositions().get(0).y;
			
			for(Position p: m.getPositions()) {
				if(p.y > maxY) {
					maxY = p.y;
				} else if(p.y < minY) {
					minY = p.y;
				}
			}
			/*
			 * make sure all tiles in between are already on the board or 
			 * and the difference between min and max is number of new tiles + number of tiles
			 * in between already on the board
			 */
			int numOldTiles = 0; 
			for(int y = minY; y <= maxY; y++) {
				if(board[y][x1].isTile()) {
					numOldTiles++;
				} else {
					if(!m.getPositions().contains(new Position(x1, y)))
						return false;
				}
			}
			
			if(maxY - minY != numOldTiles + m.size() - 1) 
				return false;
			boolean isGood = false;
			//looking for connections to words
			if(minY > 0 && (board[minY - 1][x1].isTile()))
				isGood = true;
			if(maxY < HEIGHT - 1 && (board[maxY + 1][x1].isTile()))
				isGood = true;
			for(int y = minY; y <= maxY; y++) {
				if(x1 > 0 && (board[y][x1 - 1].isTile()))
					isGood = true;
				if(x1 < WIDTH - 1 && (board[y][x1 + 1].isTile()))
					isGood = true;
			}
			
			if(!isWholeBoardEmpty() && isGood == false) {
				return false;
			}
			
			String word = this.convertToPossibleMove(m).s;
			
			if(word.length() > 1 && !ScrabbleGame.TRIE.isWord(word.toUpperCase()))
				return false;
			
			//now check the horizontal words and make sure they're good
			for(int i = 0; i < m.size(); i++) {
				char c = m.getTiles().get(i).c;
				Position p = m.getPositions().get(i);
				int y = p.y;
				int minX = p.x;
				int maxX = p.x;
				while(minX > 0 && isTile(minX - 1, y)) {
					minX--;
				}
				while(maxX + 1 < WIDTH && isTile(maxX + 1, y)) {
					maxX++;
				}
				word = "";
				
				//only applies for words greater than one letter
				if (minX == maxX) {
					continue;
				}
				
				for(int x = minX; x <= maxX; x++) {
					if(x == p.x)
						word+=c;
					else {
						word+=board[y][x].tile.c;
					}
				}
				System.out.println("hi5");
				if(!ScrabbleGame.TRIE.isWord(word.toUpperCase())) {
					return false;
				}
			}
			
			return true;
			
		} else if(areYsEqual) {
			//is a horizontal move
			//find min and max
			int minX = m.getPositions().get(0).x;
			int maxX = m.getPositions().get(0).x;
			
			for(Position p: m.getPositions()) {
				if(p.x > maxX) {
					maxX = p.x;
				} else if(p.x < minX) {
					minX = p.x;
				}
			}
			/*
			 * make sure all tiles in between are already on the board or 
			 * and the difference between min and max is number of new tiles + number of tiles
			 * in between already on the board
			 */
			int numOldTiles = 0; 
			for(int x = minX; x <= maxX; x++) {
				if(board[y1][x].isTile()) {
					numOldTiles++;
				} else {
					if(!m.getPositions().contains(new Position(x, y1)))
						return false;
				}
			}
			
			if(maxX - minX != numOldTiles + m.size() - 1) 
				return false;
			
			boolean isGood = false;
			//looking for connections to words.
			if(minX > 0 && board[y1][minX - 1].isTile()) {
				isGood = true; 
			} else if(maxX < WIDTH - 1 && board[y1][maxX + 1].isTile()) {
				isGood = true;
			} else {
				for(int x = minX; x <= maxX; x++) {
					if(y1 > 0 && (board[y1 - 1][x].isTile())) {
						isGood = true;
					} else if(y1 < HEIGHT - 1 && (board[y1 + 1][x].isTile())) {
						isGood = true;
					}
				}
			}
			
			if(isGood == false && !isWholeBoardEmpty())
				return false;

			String word = this.convertToPossibleMove(m).s;
			//System.out.println("valid ? " + word);
			if(word.length() > 1 && !ScrabbleGame.TRIE.isWord(word.toUpperCase()))
				return false;
			// now go through and make sure each vertical word is a word
			for(int i = 0; i < m.size(); i++) {
				char c = m.getTiles().get(i).c;
				Position p = m.getPositions().get(i);
				int x = p.x;
				int minY = p.y;
				int maxY = p.y;
				while(minY > 0 && isTile(x, minY - 1)) {
					minY--;
				}
				while(maxY + 1 < HEIGHT && isTile(x, maxY + 1)) {
					maxY++;
				}
				word = "";
				//only applies for words greater than one letter
				if (minY == maxY) {
					continue;
				}
				
				for(int y = minY; y <= maxY; y++) {
					if(y == p.y)
						word+=c;
					else {
						word+=board[y][x].tile.c;
					}
				}
				if(!ScrabbleGame.TRIE.isWord(word.toUpperCase())) {
					return false;
				}
			}
			
			return true;
		}
		
		System.out.println("You should never get here");
		return true;
	}
	
	public void addMove(Move m) {
		for(int i = 0; i < m.size(); i++) {
			Position p = m.getPositions().get(i);
			Tile t = m.getTiles().get(i);
			board[p.y][p.x].tile = t;
		}
		isWholeBoardEmpty = false;
	}

	public int score(Move m) {
		boolean isVerticalMove = false;
		int score = 0;
		if(m.size() == 1 || m.getPositions().get(0).x == m.getPositions().get(1).x)
			isVerticalMove = true;
		
		if(isVerticalMove) {
			//go up and down from one of the positions until you get to empty space. 
			//this is the main word
			int x = m.getPositions().get(0).x;
			int maxY = m.getPositions().get(0).y;
			int minY = maxY;
			
			while(minY > 0 && (board[minY - 1][x].isTile() || m.getPositions().contains(
					new Position(x, minY - 1)))) {
				minY--;
			}
			
			while(maxY < HEIGHT - 1 && (board[maxY + 1][x].isTile() || m.getPositions().contains(
					new Position(x, maxY + 1)))) {
				maxY++;
			}
			
			score+= scoreVertical(minY, maxY, x, m);
			
			for(int i = 0; i < m.size(); i++) {
				Position p = m.getPositions().get(i);
				Tile t = m.getTiles().get(i);
				//go left and right from one of the positions until you get to empty space. 
				int y = p.y;
				int maxX = p.x;
				int minX = maxX;
				
				while(minX > 0 && board[y][minX - 1].isTile()) {
					minX--;
				}
				
				while(maxX < WIDTH - 1 && board[y][maxX + 1].isTile()) {
					maxX++;
				}
				
				if(minX != maxX) {
					ArrayList<Position> positions = new ArrayList<Position>(1);
					positions.add(p);
					ArrayList<Tile> tiles = new ArrayList<Tile>(1);
					tiles.add(t);
					score+=scoreHorizontal(minX, maxX, y, new Move(tiles, positions));
				}
			}
		} else {
			//go left and right from one of the positions until you get to empty space. 
			//this is the main word
			int y = m.getPositions().get(0).y;
			int maxX = m.getPositions().get(0).x;
			int minX = maxX;
			
			while(minX > 0 && (board[y][minX - 1].isTile() || m.getPositions().contains(
					new Position(minX - 1, y)))) {
				minX--;
			}
			
			while(maxX < WIDTH - 1 && (board[y][maxX + 1].isTile() || m.getPositions().contains(
					new Position(maxX + 1, y)))) {
				maxX++;
			}
			
			score+= scoreHorizontal(minX, maxX, y, m);
			
			for(int i = 0; i < m.size(); i++) {
				Position p = m.getPositions().get(i);
				Tile t = m.getTiles().get(i);
				//go up and down from one of the positions until you get to empty space. 
				int x = p.x;
				int maxY = p.y;
				int minY = maxY;
				
				while(minY > 0 && board[minY - 1][x].isTile()) {
					minY--;
				}
				
				while(maxY < HEIGHT - 1 && board[maxY + 1][x].isTile()) {
					maxY++;
				}
				
				if(minY != maxY) {
					ArrayList<Position> positions = new ArrayList<Position>(1);
					positions.add(p);
					ArrayList<Tile> tiles = new ArrayList<Tile>(1);
					tiles.add(t);
					score+=scoreVertical(minY, maxY, x, new Move(tiles, positions));
				}
			}
		}
		
		if(m.size() >= 7)
			score+=50;
		
		return score;
	}

	private int scoreVertical(int minY, int maxY, int x, Move newTiles) {
		int score = 0;
		int wordMultiplier = 1;
		
		for(int y = minY; y <= maxY; y++) {
			if(board[y][x].isTile()) {
				score+=board[y][x].tile.value;
			} else {
				int i = newTiles.getPositions().indexOf(new Position(x, y));
				int val = newTiles.getTiles().get(i).value;
				val*=board[y][x].letterMultiplier;
				score+=val;
				wordMultiplier*=board[y][x].wordMultiplier;
			}
		}
	
		return score*wordMultiplier;
	}
	
	private int scoreHorizontal(int minX, int maxX, int y, Move newTiles) {
		int score = 0;
		int wordMultiplier = 1;
		
		for(int x = minX; x <= maxX; x++) {
			if(board[y][x].isTile()) {
				score+=board[y][x].tile.value;
			} else {
				int i = newTiles.getPositions().indexOf(new Position(x, y));
				int val = newTiles.getTiles().get(i).value;
				val*=board[y][x].letterMultiplier;
				score+=val;
				wordMultiplier*=board[y][x].wordMultiplier;
			}
		}
		return score*wordMultiplier;
	}
	
	public static Board generateStandardBoard() {
		BoardSpot[][] board = new BoardSpot[15][15];
		for(int x = 0; x <= 7; x++) {
			for(int y = 0; y <= 7; y++) {
				int wm= 1;
				int lm= 1;
				
				if(x % 7 == 0 && y % 7 == 0)
					wm = 3;
				if(x == y && x > 0)
					wm = 2;
				if((x - 1) % 4 == 0 && (y - 1) % 4 == 0 && x*y != 1) {
					lm = 3;
					wm = 1;
				} else if(x == 0 && y == 3 || y == 0 && x == 3 ||
									x == 2 && y == 6 || x == 6 && y == 2 ||
									x == 7 && y == 3 || x == 3 && y == 7 ||
									x == y && x == 6) {
					lm = 2;
					wm = 1;
				}
				
				board[x][y] = new BoardSpot(wm, lm);
				board[14 - x][y] = new BoardSpot(wm, lm);
				board[x][14 - y] = new BoardSpot(wm, lm);
				board[14 - x][14 - y] = new BoardSpot(wm, lm);
			}			
		}
		
		return new Board(board);
	}
	
	public static void main(String[] args) {
		System.out.println(ScrabbleGame.TRIE.isWord("VET"));
	}
}
