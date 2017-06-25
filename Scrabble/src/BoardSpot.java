
public class BoardSpot {
	public Tile tile;
	public int wordMultiplier;
	public int letterMultiplier;
	
	public BoardSpot(int wordMultiplier, int letterMultiplier) {
		this.wordMultiplier = wordMultiplier;
		this.letterMultiplier = letterMultiplier;
	}
	
	public BoardSpot(Tile t) {
		this.wordMultiplier = 1;
		this.letterMultiplier = 1;
		this.tile = t;
	}
	
	public BoardSpot() {
		this.wordMultiplier = 1;
		this.letterMultiplier = 1;
		tile = null;
	}
	
	public BoardSpot(int wordMultiplier, int letterMultiplier, Tile t) {
		this.wordMultiplier = wordMultiplier;
		this.letterMultiplier = letterMultiplier;
		this.tile = t;
	}
	
	public BoardSpot copy() {
		return new BoardSpot(wordMultiplier, letterMultiplier, tile.copy());
	}
	
	public boolean isTile() {
		return tile != null;
	}
	
	public String toString() {
		if(tile == null) {
			if(wordMultiplier == 1) {
				if(letterMultiplier == 1) {
					return " ";
				} else {
					return Character.toString("-=+????????".charAt(letterMultiplier - 1));
				}
			} else {
				return Integer.toString(wordMultiplier);
			}
		} else {
			return tile.toString();
		}
	}
}
