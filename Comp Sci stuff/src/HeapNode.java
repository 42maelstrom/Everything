public class HeapNode<E>
{
	private E data;
	private HeapNode<E> left;
	private HeapNode<E> right;
	private boolean addLeft;
	
	public HeapNode(E obj)
	{
		data = obj;
		left = null;
		right = null;
		addLeft = true;
	}
	
	public E getData()
	{
		return data;
	}
	
	public HeapNode<E> getLeft()
	{
		return left;
	}
	
	public HeapNode<E> getRight()
	{
		return right;
	}
	
	public void setData(E obj)
	{
		data = obj;
	}
	
	public void setLeft(HeapNode<E> next)
	{
		left = next;
	}
	
	public void setRight(HeapNode<E> next)
	{
		right = next;
	}
	
	public boolean goLeft()
	{
		return addLeft;
	}
	
	public void flipDirection()
	{
		addLeft = !addLeft;
	}
	
	public String toString() {
		return getData().toString();
	}	
}