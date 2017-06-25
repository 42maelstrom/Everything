import java.util.Random;
import java.awt.Color;

public class Rabbit extends Prey 
{
	private boolean isFemale;
	private static Random generator = new Random();
	private static double fertility = .02;
	
	public Rabbit(Cage cage)
	{
		super(cage);
		if(generator.nextInt(2)==0)
			isFemale = true;
		else
			isFemale = false;
	}
	
	public Rabbit(Cage cage, Color col)
	{
		super(cage, col);
		if(generator.nextInt(2)==0)
			isFemale = true;
		else
			isFemale = false;
	}
	
	public Rabbit(Cage cage, Color col, Position pos)
	{
		super(cage, col, pos);
		if(generator.nextInt(2)==0)
			isFemale = true;
		else
			isFemale = false;
	}
	
	public boolean act()
	{
		double check = generator.nextDouble();
		if(check < fertility)
			haveChildren();
		return super.act();
	}
	
	public void haveChildren()
	{
		//System.out.print("Kids");
		Position[] possible = myCage.emptyNeighbors(myPos);
		if(possible.length > 0)
		{
			int numKids = generator.nextInt(possible.length);
			//System.out.println(" - " + numKids);
			for(int n=0; n<numKids; )
			{
				int index = generator.nextInt(possible.length);
				Position pos = possible[index];
				if(pos != null)
				{
					Rabbit baby = new Rabbit(myCage, Color.ORANGE, pos);
					myCage.addAnimal(baby);
					n++;
					possible[index] = null;
				}	
			}
		}
	}
}