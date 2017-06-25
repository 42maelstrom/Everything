import java.util.*;

public class GasStation {
	
//	private double pricePerGallon;
//	private ArrayList<GasPump> pumps;
//	private int numPumps;
//	
//	// Write the following methods
//	
//		//  precondition: pumps is not empty
//		// postcondition: returns the total number of cash collected at all
//		//		of the pumps at this GasStation
//		public double getTotalCashCollected()
//		{
//			double total = 0;
//			for(GasPump temp: pumps)
//			{
//				total += temp.getCashCollected();
//			}
//			return total;
//		}
//		
//		//  precondition: pump with given id is in pumps
//		// postcondition: the sale s is processed for the pump with the given
//		//		id and the cash collected for that sale is returned (the cash
//		//		collected for that sale is the difference in total cash 
//		//		collected at that pump before and after the sale is processed
//		public double processThisSale(Sale s, String id)
//		{
//			for(int i = 0; i < pumps.size(); i++)
//			{
//				if(pumps.get(i).getID().equals(id))
//				{
//					double j = pumps.get(i).getCashCollected();
//					pumps.get(i).processSale(s);
//					return pumps.get(i).getCashCollected() - j;
//				}
//			}
//		}
//		
//		//  precondition: 
//		// postcondition: resets the given pump and returns true if the pump
//		//		with that id is in the GasStation, returns false otherwise
//		public boolean resetThePump(String id)
//		{ 
//			for(GasPump temp: pumps)
//			{
//				if(temp.getID().equals(id))
//				{
//					temp.resetPump();
//					return true;
//				}
//			}
//			return false;
//		}
//		
//		//  precondition: pumps is not empty
//		// postcondition: returns an ArrayList<GasPump> containing all of the 
//		//     pumps at this GasStation that have zero dollars in sales
//		public ArrayList<GasPump> getPumpsWithNoSales()
//		{
//			ArrayList<GasPump> noSalePumps = new ArrayList<GasPump>();
//			for(GasPump temp: pumps)
//			{
//				if(temp.getCashCollected() == 0)
//					noSalePumps.add(temp);
//			}
//			return noSalePumps;
//			
//		}
		
}
