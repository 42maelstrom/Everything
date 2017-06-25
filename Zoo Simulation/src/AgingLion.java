import java.awt.Color;


public class AgingLion extends Lion{
	
	private int age;

	/**
	*	Constructor creates an Aging Lion with Position 0,0.  Animal
	*	has no cage in which to live.
	*/
	public AgingLion()
	{
		super();
		age = 0;
	}
	
	/**
	*	Constructor creates an Aging Lion in a random empty spot in
	*	the given cage.
	*	@param cage  the cage in which lion will be created.
	*/
	public AgingLion(Cage cage)
	{
		super(cage);
		age = 0;
	}
	
	/**
	*	Constructor creates an Aging Lion in a random empty spot in
	*	the given cage with the specified Color.
	*	@param cage  the cage in which lion will be created.
	*	@param color  the color of the lion
	*/
	public AgingLion(Cage cage, Color color)
	{
		super(cage, color);
		age = 0;
	}
	
	/**
	*	Constructor creates an Aging Lion in the given Position
	*	the given cage with the specified Color.
	*	@param cage  the cage in which lion will be created.
	*	@param color  the color of the lion
	*	@param pos	the position of the lion
	*/
	public AgingLion(Cage cage, Color color, Position pos)
	{
		super(cage, color, pos);
		age = 0;
	}
	
	public boolean act()
	{
		age++;
		if(age == 25)
			myColor = Color.green;
		if(age == 50)
			myColor = Color.blue;
		if(age == 75)
			myColor = Color.black;
		
		return super.act();
	}
	
	public String toString()
	{
		return (myPos.toString() + " is an Aging Lion.  ");
	}
	
	/**
	*	Method returns the String form of the Animal's
	*	species, in this case "Lion"
	*	@return	the String "Lion"
	*/
	public String getSpecies()
	{
		return "Aging Lion";
	}
}
