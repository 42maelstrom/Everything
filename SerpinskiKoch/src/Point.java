public class Point {
	double x, y;
	
	public Point(double x, double y) {
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
	
	public Point move(double x, double y) {
		return new Point(this.x + x, this.y + y);
	}
	
	public int getIntX() {
		return (int)Math.round(x);
	}
	
	public int getIntY() {
		return (int)Math.round(y);
	}
	
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
}
