import java.util.ArrayList;

public class MaxHeap<E>  
{
	private HeapNode<E> root;
	private int size;
	
	public MaxHeap ()
	{
		root = null;
		size = 0;
	}
	
	// precondition: none
	// postcondition: adds obj to the Heap
	public void add(E obj) 
	{	
		if(size == 0)
		{
			root = new HeapNode<E>(obj);
			size = 1;
		} else {
			
			HeapNode<E> currentNode = root;
			
			Comparable object = (Comparable)obj;
			Comparable currentData;
			
			while(currentNode != null) {
				currentData = (Comparable)(currentNode.getData());
				//compare the two objects
				if(object.compareTo(currentData) > 0) { //if object is greater, swap the two
					E temp = obj;
					object = currentData; //now currentNote's root is going down the tree
					obj = currentNode.getData();
					currentNode.setData(temp); //replace root of currentNode with obj
				}
				
				if(currentNode.goLeft() == true) {
					if(currentNode.getLeft() == null) {
						currentNode.setLeft(new HeapNode<E>(obj));
						currentNode.flipDirection();
						size++;
						return;
					} else {
						currentNode.flipDirection();
						currentNode = currentNode.getLeft();
					}
				} else {	
					if(currentNode.getRight() == null ){
						currentNode.setRight(new HeapNode<E>(obj));
						currentNode.flipDirection();
						size++;
						return;
					} else {
						currentNode.flipDirection();
						currentNode = currentNode.getRight();
					}
				}
			}
		}
	}

	// precondition: none
	// postcondition: removes and returns the largest element in the Heap
	//				  while preserving the Heap structure
	public E remove() 
	{
		size--;
		E max = root.getData();
		if(root.getLeft() == null && root.getRight() == null) {
			root = null;
		} else if(root.getLeft() == null) {
			promote(root.getRight(), root, false);
		} else if(root.getRight() == null) {
			promote(root.getLeft(), root, true);
		} else {
			Comparable leftData = (Comparable)root.getLeft().getData();
			Comparable rightData = (Comparable)root.getRight().getData();
			
			if(leftData.compareTo(rightData) > 0)
			{
				promote(root.getLeft(), root, true);
			} else {
				promote(root.getRight(), root, false);
			}
		}
		
		return max;
		
	}
	
	//given that prev is going to be replaced with current's data, recursively go down to make current have data.
	private void promote(HeapNode<E> current, HeapNode<E> prev, boolean cameFromLeft) {
		
		prev.setData(current.getData()); //put current's data into previous
		
		//now what will go in for current's data spot?
		
		if(current.getLeft() == null && current.getRight() == null) {
			//nothing will go here. current needs to be made null
			if(cameFromLeft == true){
				prev.setLeft(null);				
			} else {
				prev.setRight(null);
			}
			return;
		} else if(current.getLeft() == null) {
			//current has one child: the right child so you know it's going to be the one promoted.
			promote(current.getRight(), current, false);
		} else if(current.getRight() == null) {
			promote(current.getLeft(), current, true);
		} else {
			Comparable leftData = (Comparable)current.getLeft().getData();
			Comparable rightData = (Comparable)current.getRight().getData();
			
			if(leftData.compareTo(rightData) > 0)
			{
				promote(current.getLeft(), current, true);
			} else {
				promote(current.getRight(), current, false);
			}
		}
	}

	// precondition: none
	// postcondition: returns the current size of the Heap
	public int size() 
	{
		return size;
	}
	
	public static void main(String[] args)
	{
		// Creates and prints out an array of integers
		Integer[] numbers = {3, 32, 45, -12, 17, 18, 29};
		for(Integer temp : numbers)
			System.out.print(temp+"\t");
		System.out.println();
		
		// Creates a heap
		MaxHeap<Integer> heap = new MaxHeap<Integer>();
		
		// Adds the elements of array to the heap
		for(Integer temp : numbers) {
			heap.add(temp);
		}

		// Pulls the elements of the heap out and puts
		// them back in the array
		int index = 0;
		while(heap.size() > 0)
		{
			numbers[index] = heap.remove();
			index++;
		}
		
		// Prints out the array, it should now be sorted
		for(Integer temp : numbers)
			System.out.print(temp+"\t");
		System.out.println();
			
	}

}