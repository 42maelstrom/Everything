
public abstract class TicTacToePlayer {
	public boolean isPlayerOne;
	public abstract Move makeMove(Tile[][] board, Point lastMove);
	
	public TicTacToePlayer(boolean isPlayerOne) {
		this.isPlayerOne = isPlayerOne;
	}
}
