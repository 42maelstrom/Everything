public interface APQueue <E>
{
	/**
	 * Adds obj at the end of the queue
	 * @param obj object (of type E) to be added to the queue
	 * @return true if the object was added, false otherwise
	 */
	public boolean enqueue(E obj);
	
	/**
	 * Removes and returns the object at the start of the queue
	 * @return the object at the start (front) of the queue
	 */
	public E dequeue();
	
	/**
	 * Returns (without removing) the object at the start of the queue
	 * @return the object at the start (front) of the queue
	 */
	public E peekFront();
	
	/**
	 * Returns true if the queue is empty, false otherwise
	 * @return true if queue is empty, false otherwise
	 */
	public boolean isEmpty();
}