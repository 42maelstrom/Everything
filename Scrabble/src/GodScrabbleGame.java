public class GodScrabbleGame {
	public Player[] players;
	public Board board;
	public int[] scores;
	public int playerTurn;
	
	public GodScrabbleGame(Board b, Player ... players) {
		this.players = players;
		this.board = b;
	}
	
	public void playGame() {
		scores = new int[players.length];
		playerTurn = 1;
		
		while (true) {
			Move m;
			
			for(int playerNum = 1; playerNum <= players.length; playerNum++) {
				boolean doAgain = false;
				do {
					printSituation();
					m = players[playerNum - 1].makeMove(new BoardAndRack(board));
					if(!isValidMove(m)) {
						doAgain = true;
						System.out.println("Invalid move, try again");
					}
				} while (doAgain);
			
				processMove(m, playerNum);
			}
		}
	}

	public boolean isValidMove(Move m) {
		return board.isValidMove(m);
	}
	
	public void printSituation() {
		board.printBoard();
		for(int i = 0; i < players.length; i++)
			System.out.println("P" + (i + 1) + ": " + scores[i]);
		System.out.println("Player " + playerTurn + " turn");
	}
	
	public void processMove(Move m, int playerNum) {
		scores[playerNum - 1] += board.score(m);
		board.addMove(m);
		playerTurn = playerTurn % players.length + 1;
	}
	
	public static void main(String[] args) {
		new GodScrabbleGame(Board.generateStandardBoard(), new GodPlayer("God"), new GodPlayer("God")).playGame();
	}
}
