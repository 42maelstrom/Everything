import java.util.Random;
import java.util.Scanner;


public class TicTacToeHuman extends TicTacToePlayer {
	
	public TicTacToeHuman(boolean isPlayerOne) {
		super(isPlayerOne);
	}

	@Override
	public Move makeMove(Tile[][] board, Point lastMove) {
		boolean tryAgain = false; 
		do {
			tryAgain = false;
			String s = "";
				System.out.println("Input move, ex. \"X at (1, 1)\"");
				s = new Scanner(System.in).nextLine();
			Tile t = Tile.EMPTY;
			try {
				if(s.charAt(0) == 'X') {
					t = Tile.X;
				} else {
					t = Tile.O;
				}
			} catch (Exception e) {
				System.out.println("Incorrect input");
				tryAgain = true;
			}
			
			int x = Integer.parseInt(s.substring(6,7));
			int y = Integer.parseInt(s.substring(9, 10));
			return new Move(new Point(x, y), t);
		} while(tryAgain);
	}
}
