import java.util.*;
public class SimonQueue<E> implements APQueue<E>
{
	
	//instance variables
	private ArrayList<E> queue;
	
	//constructors
	public SimonQueue()
	{
		queue = new ArrayList<E>();
	}
	
	public SimonQueue(int length)
	{
		queue = new ArrayList<E>(length);
	}
	
	//methods
	/**
	 * Adds obj at the end of the queue
	 * @param obj object (of type E) to be added to the queue
	 * @return true if the object was added, false otherwise
	 */
	public boolean enqueue(E obj)
	{
		return queue.add(obj);
	}
	
	/**
	 * Removes and returns the object at the start of the queue
	 * @return the object at the start (front) of the queue
	 */
	public E dequeue()
	{
		return queue.remove(0);
	}
	
	/**
	 * Returns (without removing) the object at the start of the queue
	 * @return the object at the start (front) of the queue
	 */
	public E peekFront()
	{
		return queue.get(0);
	}
	
	/**
	 * Returns true if the queue is empty, false otherwise
	 * @return true if queue is empty, false otherwise
	 */
	public boolean isEmpty()
	{
		return queue.isEmpty();
	}
	
	public static void main(String[] args)
	{
		APQueue<String> myQ = new SimonQueue<String>();
		myQ.enqueue("Simon");
		myQ.enqueue("Tommy");
		myQ.enqueue("Grace");
		while(!myQ.isEmpty())
		{
			System.out.println(myQ.dequeue());
		}
	}
}
