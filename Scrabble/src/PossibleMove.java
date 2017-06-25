public class PossibleMove implements Comparable<PossibleMove> {
	String s;
	int x;
	int y;
	int score;
	boolean isHorizontal;
	boolean isExchange;
	boolean isPass;
	
	/**
	 * Creates a Move. 
	 * @param s - The word played, including tiles that are already on the board.
	 * @param x - the x position of the leftmost tile
	 * @param y - the y position of the topmost tile
	 * @param score - the score this move gets.
	 * @param isHorizontal - whether the move is horizontal or vertical.
	 */
	public PossibleMove(String s, int x, int y, int score, boolean isHorizontal) {
		this.s = s;
		this.x = x;
		this.y = y;
		this.score = score;
		this.isHorizontal = isHorizontal;
		isExchange = false;
		isPass = false;
	}
	
	public PossibleMove(String s, int x, int y, boolean isHorizontal) {
		this(s, x, y, 0, isHorizontal);
	}
	
	public PossibleMove(String s) {
		this.s = s;
		this.score = 0;
		isPass = s.isEmpty();
		isExchange = !isPass;
	}
			
	@Override
	public int compareTo(PossibleMove pm) {
		return Integer.valueOf(score).compareTo(Integer.valueOf(pm.score));
	}
	
	@Override
	public String toString() {
		if(isPass)
			return "pass";
		if(isExchange)
			return "exchange: " + s;
		return score + ": " + s + " at " + new Position(x, y).toString() 
				+ (isHorizontal ? "H" : "V");
	}
}
	