
public class Move {
	Point p;
	Tile t;
	
	public Move(Point p, Tile t) {
		this.p = p;
		this.t = t;
	}
	
	@Override
	public String toString() {
		return (t == Tile.X ? "X" : "O" ) + " " + p.toString(); 
	}
	
}
