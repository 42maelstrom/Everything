import java.awt.*;
import java.util.*;


public class Prey extends Animal
{
	/**
	*	Constructor creates a Prey with Position 0,0.  Animal
	*	has no cage in which to live.
	*/
	public Prey()
	{
		super();
	}
	
	/**
	*	Constructor creates a Prey in a random empty spot in
	*	the given cage.
	*	@param cage  the cage in which Prey will be created.
	*/
	public Prey(Cage cage)
	{
		super(cage);
	}
	
	/**
	*	Constructor creates a Prey in a random empty spot in
	*	the given cage with the specified Color.
	*	@param cage  the cage in which Prey will be created.
	*	@param color  the color of the Prey
	*/
	public Prey(Cage cage, Color color)
	{
		super(cage, color);
	}
	
	/**
	*	Constructor creates a Prey in the given Position
	*	the given cage with the specified Color.
	*	@param cage  the cage in which Prey will be created.
	*	@param color  the color of the Prey
	*	@param pos	the position of the Prey
	*/
	public Prey(Cage cage, Color color, Position pos)
	{
		super(cage, color, pos);
	}
	
	public String toString()
	{
		return (myPos.toString() + " is an Prey Animal.  ");
	}
}