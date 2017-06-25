import java.util.Scanner;


public class TicTacToeGame {
	TicTacToePlayer p1;
	TicTacToePlayer p2;
	Tile[][] board;
	boolean didP1Win;
	int movesPlayed;
	
	public TicTacToeGame(TicTacToePlayer p1, TicTacToePlayer p2) {
		this.p1 = p1;
		this.p2 = p2;
		movesPlayed = 0;
		board = new Tile[3][3];
		for(int y = 0; y < 3; y++) {
			for(int x = 0; x < 3; x++) {
				board[y][x] = Tile.EMPTY;
			}
		}
		didP1Win = false;
	}
	
	public void play() {
		printBoard();
		pause();
		Move p2Move = new Move(new Point(4, 4), Tile.X);
		while(!didSomeoneWin() && movesPlayed != 9) {
			Move p1Move = p1.makeMove(board, movesPlayed == 0 ? null : p2Move.p);
			addMove(p1Move);
			
			printBoard();
			pause();
			
			if(!didSomeoneWin() && movesPlayed != 9) {
				didP1Win = true;
				p2Move = p2.makeMove(board, p1Move.p);
				addMove(p2Move);
				
				printBoard();
				pause();
			}
		}
		
		if(movesPlayed == 9) {
			System.out.println("Tie");
		} else  {
			System.out.println("NOT A TIE!");
			String s = new Scanner(System.in).nextLine();
			if(didP1Win) {
				System.out.println("P1 wins");
			} else {
				System.out.println("P2 wins");
			}
		}
	}
	
	public void addMove(Move m) {
		board[m.p.y][m.p.x] = m.t;
		movesPlayed++;
	}
	
	public boolean didSomeoneWin() {
		// look and see if any of the 8 directions from the center point yield three in a row.
		if(board[1][1] != Tile.EMPTY) {
			outer:
			for(int dX = -1; dX < 2; dX++) {
				for(int dY = -1; dY < 2; dY++) {
					if(dX == 0 && dY == 0)
						break outer;
					
					if(	board[1 + dY][1 + dX] == board[1][1]
				     && board[1 - dY][1 - dX] == board[1][1]) {
						System.out.println("hi" + dX + ", " + dY);
						return true;
					}
				}
			}
		}
		
		//try the four cases where the three in a row doesn't use the center square.
		for(int y = 0; y <= 2; y+=2) {
			if(board[y][0] == Tile.EMPTY)
				continue;
			if(	board[y][0] == board[y][1]
			 && board[y][0] == board[y][2]) {
				System.out.println("hey");
				return true;
			}
		}
		
		for(int x = 0; x <= 2; x+=2) {
			if(board[0][x] == Tile.EMPTY)
				continue;
			if( board[0][x] == board[1][x]
			 && board[0][x] == board[2][x])
			 	return true;
		}
		
		return false;
	}
	
	public void printBoard() {
		for(int y = 0; y < 3; y++) {
			System.out.println();
			for(int x = 0; x < 3; x++) {
				System.out.print((board[y][x] == Tile.EMPTY ? "_" : board[y][x].toString()) + " ");
			}
		}
		System.out.println();
		System.out.println();
	}
	
	public void pause() {
		String s = new Scanner(System.in).nextLine();
	}
	
	public static void main(String[] args) {
		while(true) {
			TicTacToeGame tttg = new TicTacToeGame(new TicTacToeAI(true), new TommyAI(false));
			tttg.play();
		}
	}
	
}
