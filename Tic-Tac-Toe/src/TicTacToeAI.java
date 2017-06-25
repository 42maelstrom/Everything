import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.jar.Pack200;


public class TicTacToeAI extends TicTacToePlayer {
	private final Random rand;
	private boolean isFirstMove;
	private Tile myMark;
	private Tile theirMark;
	
	public TicTacToeAI(boolean isPlayerOne) {
		super(isPlayerOne);
		rand = new Random();
		isFirstMove = true;
		if(isPlayerOne) {
			myMark = Tile.X;
			theirMark = Tile.O;
		} else {
			myMark = Tile.O;
			theirMark = Tile.X;
		}
	}
	
	/**
	 * If is P1 & is first move, pick a random corner and play
	 * If is P2 & is first move, play center if possible, otherwise random corner.
	 * ELSE:
	 * In order:
	 * 
	 * If I can make a move to win, play it.
	 * If they can make a move and win, block it.
	 * If I can make a fork, do it.
	 * Play a move that forces a win-block in a spot that doesn't create a fork against yourself.
	 * Play randomly.
	 */
	public Move makeMove(Tile[][] board, Point lastMove) {
		if(isFirstMove) {
			isFirstMove = false;
			if(isPlayerOne) {
				//pick a random corner and play
				int xPos = 0 + 2*rand.nextInt(2); //either gives 0 or 2
				int yPos = 0 + 2*rand.nextInt(2);
				
				return new Move(new Point(xPos, yPos), myMark);
			} else {
				//we're player two. If center is taken, take a corner. Otherwise play center.
				if(board[1][1] == Tile.EMPTY) {
					//they didn't play center. play center.
					return new Move(new Point(1, 1), myMark);
				} else {
					//they played center. play corner.
					int xPos = 0 + 2*rand.nextInt(2); //either gives 0 or 2
					int yPos = 0 + 2*rand.nextInt(2);
					
					return new Move(new Point(xPos, yPos), myMark);
				}
			}
		} else {
			/**from here on out, strategy is the same for P1 or P2.**/
			
			/**if I can make a move to win, play it.**/
			Move m = findWinningMove(board);
			if(m != null) {
				System.out.println("Making a win");
				return m;
			}
			
			/**if they can make a move to win, block it.**/
			m = findWinBlockingMove(board);
			if(m != null) {
				System.out.println("Blocking a future win");
				return m;
			}
			
			/**If I can make a fork, do it.**/
			m = findForkingMove(board);
			if(m != null) {
				System.out.println("Making a fork at " + m.p.toString());
				return m;
			}
			
//			/**try to find a move that can give a fork the next move**/
//			m = findFutureForkMove(board);
//			if(m != null) {
//				System.out.println("Future fork move tactics");
//				return m;
//			}
			
			/**make two in a row that doesn't force a fork**/
			m = findForkFreeWinTry(board);
			if(m != null) {
				System.out.println("Getting two in a row without having the resulting win block create a fork for me.");
				return m;
			}
			
			/**play randomly.**/
			System.out.println("Random move");
			ArrayList<Point> emptyPs = new ArrayList<Point>();
			for(int y = 0; y < 3; y++) {
				for(int x = 0; x < 3; x++) {
					if(board[y][x] == Tile.EMPTY)
						emptyPs.add(new Point(x, y));
				}
			}
			
			return new Move(emptyPs.get(rand.nextInt(emptyPs.size())), myMark);
		}
	}
	
	private Move findWinningMove(Tile[][] board) {
		//if two of three in any direction are the same and the third is empty.
		ArrayList<Move> winningMoves = new ArrayList<Move>();
	// look and see if any of the directions from the center point would yield three in a row.
	outer:
		for(int dX = -1; dX < 2; dX++) {
			for(int dY = -1; dY < 2; dY++) {
				if(dX == 0 && dY == 0)
					break outer;
				
				boolean hasEmpty = false;
				Point emptyP = new Point(0, 0);
				int numOfMyMark = 0;
				int x = 1, y = 1;
				
				for(int i = -1; i <= 1; i++) {
					int x2 = x + i*dX;
					int y2 = y + i*dY;
					if(board[y2][x2] == Tile.EMPTY) {
						hasEmpty = true;
						emptyP = new Point(x2, y2);
					} else if(board[y2][x2] == myMark) {
						numOfMyMark++;
					}
				}
				
				if(numOfMyMark == 2 && hasEmpty) {
					winningMoves.add(new Move(emptyP, myMark));
				}
			}
		}
			//try the four cases where the three in a row doesn't use the center square.
			for(int y = 0; y <= 2; y+=2) {
				int x = 1;
				
				boolean hasEmpty = false;
				Point emptyP = new Point(0, 0);
				int numOfMyMark = 0;

				for(int i = -1; i <= 1; i++) {
					int x2 = x + i;
					int y2 = y;
					if(board[y2][x2] == Tile.EMPTY) {
						hasEmpty = true;
						emptyP = new Point(x2, y2);
					} else if(board[y2][x2] == myMark) {
						numOfMyMark++;
					}
				}
				
				if(numOfMyMark == 2 && hasEmpty) {
					winningMoves.add(new Move(emptyP, myMark));
				}
			}
			for(int x = 0; x <= 2; x+=2) {
				int y = 1;
				
				boolean hasEmpty = false;
				Point emptyP = new Point(0, 0);
				int numOfMyMark = 0;

				for(int i = -1; i <= 1; i++) {
					int x2 = x;
					int y2 = y + i;
					if(board[y2][x2] == Tile.EMPTY) {
						hasEmpty = true;
						emptyP = new Point(x2, y2);
					} else if(board[y2][x2] == myMark) {
						numOfMyMark++;
					}
				}
				
				if(numOfMyMark == 2 && hasEmpty) {
					winningMoves.add(new Move(emptyP, myMark));
				}
			}
			
			if(winningMoves.isEmpty()) {
				return null;
			} else {
				return winningMoves.get(rand.nextInt(winningMoves.size()));
			}
	}
	
	private Move findWinBlockingMove(Tile[][] board) {
		//I'm so lazy, wow.
		Tile t = myMark;
		myMark = theirMark;
		theirMark = t;
		Move m = findWinningMove(board);
		t = myMark;
		myMark = theirMark;
		theirMark = t;
		if(m != null)
			m.t = myMark;
		return m;
	}
	
	private Move findForkingMove(Tile[][] board) {
		ArrayList<Move> forkMoves = new ArrayList<Move>();
		//for each potential move, see if it makes a fork.
		//that is, see if there are two winning moves afterwards.
		for(int y = 0; y < 3; y++) {
			for(int x = 0; x < 3; x++) {
				if(board[y][x] == Tile.EMPTY) {
					Tile[][] newBoard = new Tile[3][3];
					for(int y3 = 0; y3 < 3; y3++) {
						for(int x3 = 0; x3 < 3; x3++) {
							newBoard[y3][x3] = board[y3][x3];
						}
					}
					newBoard[y][x] = myMark;
					//wow, I am really REALLY lazy. see if it can find a winning move twice -
					// - even after theoretically filling in the first winning move with a theirMark.
					Move m = findWinningMove(newBoard);
					
					if(m != null) {
						newBoard[m.p.y][m.p.x] = theirMark;
						m = findWinningMove(newBoard);
						if(m != null) {
							forkMoves.add(new Move(new Point(x, y), myMark));
						}
					}
				}
			}
		}
		if(forkMoves.isEmpty()) {
			return null;
		} else {
			return forkMoves.get(rand.nextInt(forkMoves.size()));
		}
	}
	
	private Move findForkFreeWinTry(Tile[][] board) {
		ArrayList<Move> moves = new ArrayList<Move>();
		
		for(int y = 0; y < 3; y++) {
			for(int x = 0; x < 3; x++) {
				if(board[y][x] == Tile.EMPTY) {
					Tile[][] newBoard = new Tile[3][3];
					for(int y3 = 0; y3 < 3; y3++) {
						for(int x3 = 0; x3 < 3; x3++) {
							newBoard[y3][x3] = board[y3][x3];
						}
					}
					newBoard[y][x] = myMark;
					
					Move winMove = findWinningMove(newBoard);
					if(winMove != null) {
						//we know this isn't a fork move, otherwise it would have already been found
						//as a result, there should only be one win-blocking move now.
						Tile t = myMark;
						myMark = theirMark;
						theirMark = t;
						Move winBlock = findWinBlockingMove(newBoard);
						t = myMark;
						myMark = theirMark;
						theirMark = t;
						
						newBoard[winBlock.p.y][winBlock.p.x] = theirMark;
						
						t = myMark;
						myMark = theirMark;
						theirMark = t;
						Move m = findWinningMove(newBoard);
						t = myMark;
						myMark = theirMark;
						theirMark = t;
						
						if(m != null) {
							newBoard[m.p.y][m.p.x] = myMark;
							t = myMark;
							myMark = theirMark;
							theirMark = t;
							m = findWinningMove(newBoard);
							t = myMark;
							myMark = theirMark;
							theirMark = t;
							if(m != null) {
								
							} else {
								moves.add(new Move(new Point(x, y), myMark));
							}
						} else {
							moves.add(new Move(new Point(x, y), myMark));
						}
						
					}
				}
			}
		}
		if(moves.isEmpty()) {
			return null;
		} else {
			return moves.get(rand.nextInt(moves.size()));
		}
	}
}
