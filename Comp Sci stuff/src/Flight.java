public class Flight
{
	// The class Flight has the following methods and contructors
	// which you should assume work as specified.
	
	// Constructors
	/*
	*	Precondition: dep and des are not null
	*   Postcondition: departure, destination and flightTime
	*                  are all properly initialized with 
	*                  departure = dep, destination = des 
	*                  and the correct flight time between them
	*/
	public Flight(Location dep, Location des) {}
	
	// Methods
	/*
	*	Precondition:
	*	Postcondition: returns the departure Location
	*/
	public Location getDeparturePoint() { return new Location();}
	
	/*
	*	Precondition:
	*	Postcondition: returns the destination Location
	*/
	public Location getDestinationPoint() {return new Location(); }
	
	/*
	*	Precondition:
	*	Postcondition: returns the flight time
	*/
	public int getFlightTime() { return 0;}
	
	/*
	*	Precondition:
	*	Postcondition: returns the (unique) flight number
	*/
	public String getFlightNumber() { return "";}
}
