public class Point {
	int x, y;
	
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	@Override
	public boolean equals(Object other){
	    if (other == null) return false;
	    if (other == this) return true;
	    if (!(other instanceof Point))return false;
	    Point otherP = (Point)other;
	    return this.x == otherP.x && this.y == otherP.y;
	}
	
	public Point move(int x, int y) {
		return new Point(this.x + x, this.y + y);
	}
	
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
}
