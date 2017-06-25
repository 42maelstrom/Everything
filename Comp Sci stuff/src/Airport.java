import java.util.*;

public class Airport
{
	private ArrayList<Flight> allFlights;
	
	// Methods
	
	/*  Part A:
	*	Precondition: 
	*	Postcondition: returns the longest Flight (in minutes) in
	*                  allFlights or null is allFlights is empty
	*/
	public Flight getLongestFlight()
	{
		if(allFlights.isEmpty())
		{
			return null;
		}
		Flight longest = allFlights.get(0);
		for(Flight temp: allFlights)
		{
			if(temp.getFlightTime() > longest.getFlightTime())
				longest = temp;
		}
		return longest;
	}
	
	// Things to watch for:
	// Check the length first thing, if it is zero you have to return null there
	// You don't need or want an ArrayList
	// Remember to return a Flight, not an int
	
	
	/*  Part B:
	*	Precondition:
	*	Postcondition: returns an ArrayList<Flight> of all flights
	*                  longer than (in minutes) the given flight 
	*                  length 'time'
	*/
	public ArrayList<Flight> getAllFlightsLongerThan(int time)
	{
		ArrayList<Flight> longerFlights = new ArrayList<Flight>();
		for(Flight temp: allFlights)
		{
			if(temp.getFlightTime() > time)
				longerFlights.add(temp);
		}
		return longerFlights;
	}
	
	
	/*  Part C:
	*	Precondition: 
	*	Postcondition: returns the shortest Flight (in minutes)
	*                  going to the given destination, returns
	*                  null if no flights are going to that 
	*                  destination
	*/
	public Flight getShortestFlightTo(Location loc)
	{
		Flight shortest = allFlights.get(0);
		
		for(Flight temp: allFlights)
		{
			if(temp.getDestinationPoint().equals(loc))
			{
				if(temp.getFlightTime() < shortest.getFlightTime() || !shortest.getDestinationPoint().equals(loc))
				{
					shortest = temp;
				}
			}
		}
		return shortest;
	}
	
}
