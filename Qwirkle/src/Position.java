public class Position
{
	private int xPosition;
	private int yPosition;
	
	public Position()
	{
		xPosition = 0;
		yPosition = 0;
	}
	
	public Position(int x, int y)
	{
		xPosition = x;
		yPosition = y;
	}
	
	public int getX()
	{
		return xPosition;
	}
	
	public int getY()
	{
		return yPosition;
	}
	
	public void setPosition(int x, int y)
	{
		xPosition = x;
		yPosition = y;
	}
	
	public void setX(int x){
		xPosition = x;
	}
	
	public void setY(int y){
		yPosition = y;
	}
	
	public boolean equals(Position pos)
	{
		if (pos.getX() == xPosition && pos.getY() == yPosition)
			return true;
		return false;
	}
	
	public String toString()
	{
		return ("(" + xPosition + ", " + yPosition +")");
	}
}