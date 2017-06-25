import java.util.ArrayList;

public class Move {

	private ArrayList<Position> positions;
	private ArrayList<Tile> tiles;
	private int points;
	
	public Move()
	{
		points = 0;
	}
	
	public Move(int pointValue)
	{
		points = pointValue;
	}
	
	public Move(ArrayList<Position> positions)
	{
		this.positions = positions;
		tiles = new ArrayList<Tile>(positions.size());
	}
	
	public Move(ArrayList<Position> positions, ArrayList<Tile> tiles)
	{
		this.positions = positions;
		this.tiles = tiles;
	}
	
	public ArrayList<Position> getPositions()
	{
		return positions;
	}
	
	public ArrayList<Tile> getTiles()
	{
		return tiles;
	}
	
	public int getPoints()
	{
		return points;
	}
	
	public void setPoints(int pointValue)
	{
		points = pointValue;
	}
	
	public int length()
	{
		if(positions == null)
			return 0;
		return positions.size();
	}
	
	public String toString()
	{
		return "Positions: " + positions.toString() + "\n" + "Tiles: " + tiles.toString();
	}
}
