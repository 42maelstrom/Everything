public class ScrabbleBot extends Player {
	public ScrabbleBot(String name) {
		super(name);
	}
	
	public Move makeMove(BoardAndRack bar) {
		return new BestMoveFinder(bar).getBestMove();
	}
}
