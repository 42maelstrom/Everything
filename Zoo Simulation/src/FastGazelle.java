
import java.awt.*;

public class FastGazelle extends SmartGazelle
{
	/**
	*	Constructor creates a FastGazelle with Position 0,0.  Animal
	*	has no cage in which to live.
	*/
	public FastGazelle()
	{
		super();
	}
	
	/**
	*	Constructor creates a FastGazelle in a random empty spot in
	*	the given cage.
	*	@param cage  the cage in which Gazelle will be created.
	*/
	public FastGazelle(Cage cage)
	{
		super(cage);
	}
	
	/**
	*	Constructor creates a FastGazelle in a random empty spot in
	*	the given cage with the specified Color 
	*	@param cage  the cage in which Gazelle will be created.
	*	@param color  the color of the Gazelle
	*/
	public FastGazelle(Cage cage, Color color)
	{
		super(cage, color);
	}
	
	/**
	*	Constructor creates a FastGazelle in the given Position
	*	the given cage with the specified Color.
	*	@param cage  the cage in which Gazelle will be created.
	*	@param color  the color of the Gazelle
	*	@param pos	the position of the Gazelle
	*/
	public FastGazelle(Cage cage, Color color, Position pos)
	{
		super(cage, color, pos);
	}
	
	public boolean act()
	{
		super.act();
		return super.act();
	}
	
	public String toString()
	{
		return (myPos.toString() + " is a Fast Gazelle.  ");
	}
	
	/**
	*	Method returns the String form of the Animal's
	*	species, in this case "Fast Gazelle"
	*	@return	the String "Fast Gazelle"
	*/
	public String getSpecies()
	{
		return "Fast Gazelle";
	}
}
