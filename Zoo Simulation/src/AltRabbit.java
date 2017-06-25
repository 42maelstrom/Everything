import java.util.Random;
import java.awt.Color;

public class AltRabbit extends Prey 
{
	private boolean isFemale;
	private static Random generator = new Random();
	private static double fertility = .02;
	
	public AltRabbit(Cage cage)
	{
		super(cage);
		if(generator.nextInt(2)==0)
			isFemale = true;
		else
			isFemale = false;
	}
	
	public AltRabbit(Cage cage, Color col)
	{
		super(cage, col);
		if(generator.nextInt(2)==0)
			isFemale = true;
		else
			isFemale = false;
	}
	
	public AltRabbit(Cage cage, Color col, Position pos)
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
		if(isFemale && check < fertility)
			haveChildren();
		return super.act();
	}
	
	public String toString()
	{
		if(isFemale)
		{
			return (myPos.toString() + " is a female AltRabbit.  ");
		}else{
			return (myPos.toString() + " is a male AltRabbit.  ");
		}
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
					AltRabbit baby = new AltRabbit(myCage, Color.ORANGE, pos);
					myCage.addAnimal(baby);
					n++;
					possible[index] = null;
				}	
			}
		}
	}
}