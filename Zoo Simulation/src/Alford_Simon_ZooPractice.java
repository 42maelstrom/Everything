import java.util.ArrayList;


//public class Alford_Simon_ZooPractice 
//{
//	public ArrayList<Prey> findNonRabbitPrey()
//	{
//		ArrayList<Prey> nonRabbits = new ArrayList<Prey>();
//		
//		for(int y=0; y<myCage.getMax_Y(); y++)
//		{
//			for(int x=0; x<myCage.getMax_X(); x++)
//			{
//				if(myPos.distanceTo(new Position(x,y)) <= visualRange)
//				{
//					if(myCage.animalAt(x,y) instanceof Prey && !(myCage.animalAt(x, y) instanceof Rabbit));
//					{
//						nonRabbits.add((Prey)myCage.animalAt(x, y));
//					}
//				}	
//			}
//		}
//		
//		return nonRabbits;	
//	}
//	
//	public Prey findFarthestPreyICanSee()
//	{
//		Prey farthestPrey = null;
//		double farthestPreyDist = 0;
//		
//		for(int y=0; y<myCage.getMax_Y(); y++)
//		{
//			for(int x=0; x<myCage.getMax_X(); x++)
//			{
//				if(isSomethingICanEat(myCage.animalAt(x,y)) == true)
//				{
//					if(myPos.distanceTo(new Position(x,y)) <= visualRange)
//					{
//						if(myPos.distanceTo(new Position(x,y)) > farthestPreyDist)
//						{
//							farthestPrey = (Prey)myCage.animalAt(x,y);
//							farthestPreyDist = myPos.distanceTo(new Position(x,y));
//						}
//					}
//				}
//			}
//		}
//		
//		return farthestPrey;
//	}
//	
//}
