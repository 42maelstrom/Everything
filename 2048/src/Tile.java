
public class Tile {
	int value;
	double size;
	double xPos;
	double yPos;
	
	public Tile(int value, double row, double column) {
		this.value = value;
		size = 1.0;
		this.xPos = column;
		this.yPos = row;
	}
	
	public Tile(int value, Position pos) {
		this.value = value;
		size = 1.0;
		xPos = pos.c;
		yPos = pos.r;
	}
	
	public String toString() {
		return value + " at " + new Position((int)yPos, (int)xPos).toString();
	}

}
