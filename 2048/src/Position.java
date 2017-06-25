
public class Position {
	
	public int r, c;
	
	public Position(int r, int c) {
		this.r = r;
		this.c = c;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(! (obj instanceof Position))
			return false;
		Position other = (Position) obj;
		return r == other.r && c == other.c;
	}
	
	@Override
	public String toString() {
		return "(" + r + ", " + c + ")";
	}
}
