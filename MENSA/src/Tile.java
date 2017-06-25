import java.util.ArrayList;


public class Tile {
	ArrayList<Point> points;
	ArrayList<Point> dots;
	
	public Tile(ArrayList<Point> points, ArrayList<Point> dots) {
		this.points = points;
		this.dots = dots;
	}
	
	public static ArrayList<Tile> makeAllTileTypesAtPoint(int x, int y) {
		ArrayList<Tile> tiles = new ArrayList<Tile>();
		for(Direction dir: Direction.values()) {
			tiles.add(makeITile(x, y, dir));
			tiles.add(makeLTile(x, y, dir));
		}
		return tiles;
	}
	
	public static Tile makeITile(int x, int y, Direction dir) {
		ArrayList<Point> points = new ArrayList<Point>();
		points.add(new Point(x, y));
		
		ArrayList<Point> dots = new ArrayList<Point>();
		dots.add(new Point(x, y));
		
		switch(dir) {
		case LEFT:
			points.add(new Point(x - 1, y));
			points.add(new Point(x - 2, y));
			dots.add(new Point(x - 2, y));
			break;
		case RIGHT:
			points.add(new Point(x + 1, y));
			points.add(new Point(x + 2, y));
			dots.add(new Point(x + 2, y));
			break;
		case UP:
			points.add(new Point(x, y - 1));
			points.add(new Point(x, y - 2));
			dots.add(new Point(x, y - 2));
			break;
		case DOWN:
			points.add(new Point(x, y + 1));
			points.add(new Point(x, y + 2));
			dots.add(new Point(x, y + 2));
			break;
		default:
			System.out.println("This shouldn't happen");
		}
		
		return new Tile(points, dots);
	}
	
	public static Tile makeLTile(int x, int y, Direction dir) {
		ArrayList<Point> points = new ArrayList<Point>();
		points.add(new Point(x, y));
		
		ArrayList<Point> dots = new ArrayList<Point>();
		dots.add(new Point(x, y));
		
		switch(dir) {
		case LEFT:
			points.add(new Point(x - 1, y));
			points.add(new Point(x, y - 1));
			break;
		case RIGHT:
			points.add(new Point(x + 1, y));
			points.add(new Point(x, y + 1));
			break;
		case UP:
			points.add(new Point(x + 1, y));
			points.add(new Point(x, y - 1));
			break;
		case DOWN:
			points.add(new Point(x - 1, y));
			points.add(new Point(x, y + 1));
			break;
		default:
			System.out.println("This shouldn't happen");
		}
		
		return new Tile(points, dots);
	}
	
	@Override
	public boolean equals(Object other){
	    if (other == null) return false;
	    if (other == this) return true;
	    if (!(other instanceof Tile))return false;
	    Tile otherT = (Tile)other;
	    return this.points.equals(otherT.points) && this.dots.equals(otherT.dots);
	}
}
