/**
 * Trying to make a maxheap again by myself. Also trying out the other version (nonrecursive) for remove() method.
 * @author 16alford_simon
 *
 */
public class MaxHeap2<E> {

	int size;
	HeapNode<E> root;
	
	public MaxHeap2() {
		size = 0;
		root = null;
	}
	
	public int size() {
		return size;
	}
	
	public void add(E obj) {
		
		if(size == 0) {
			root = new HeapNode<E>(obj);
			size++;
			return;
		}
		
		size++;
		HeapNode<E> currentNode = root;
		boolean keepGoing = true;
		
		while(keepGoing == true) {
			Comparable objData = (Comparable)obj;
			Comparable currentData = (Comparable)currentNode.getData();
			
			if(objData.compareTo(currentData) > 0) {
				E temp = currentNode.getData();
				currentNode.setData(obj);
				obj = temp;
			}
			
			if(currentNode.goLeft() == true) {
				//System.out.println("left");
				currentNode.flipDirection();
				if(currentNode.getLeft() == null)
				{
					currentNode.setLeft(new HeapNode<E>(obj));
					keepGoing = false;
				} else {
					currentNode = currentNode.getLeft();
				}
			} else {
				//System.out.println("right");
				currentNode.flipDirection();
				if(currentNode.getRight() == null) {
					currentNode.setRight(new HeapNode<E>(obj));
					keepGoing = false;
				} else {
					currentNode = currentNode.getRight();
				}
			}
		}
		return;
		
	}
	
	public E remove() {
		
		if(size == 1) {
			size = 0;
			E max = root.getData();
			root = null;
			return max;
		}
		
		size--;
		E max = root.getData();
		root.setData(null);
		
		HeapNode<E> currentNode = root;
		HeapNode<E> lastNode = root;
		
		E toMoveUp = null;
		boolean keepGoing = true;
		
		while(keepGoing == true) {
			if(currentNode.goLeft() == false) {
				if(currentNode.getLeft() == null)
				{
					toMoveUp = currentNode.getData();
					keepGoing = false;
					if(lastNode.goLeft() == true) {
						lastNode.setLeft(null);
					} else {
						lastNode.setRight(null);
					}
				} else {
					lastNode = currentNode;
					currentNode = currentNode.getLeft();
				}
			} else {
				currentNode.flipDirection();
				if(currentNode.getRight() == null) {
					toMoveUp = currentNode.getData();
					keepGoing = false;
					if(lastNode.goLeft() == true) {
						lastNode.setLeft(null);
					} else {
						lastNode.setRight(null);
					}
				} else {
					lastNode = currentNode;
					currentNode = currentNode.getRight();
				}
			}
		}
		
		//System.out.println(toMoveUp.toString());
		
		currentNode = root;
		root.setData(toMoveUp);
		keepGoing = true;
		
		while(keepGoing == true) {
			HeapNode<E> leftNode = currentNode.getLeft();
			HeapNode<E> rightNode = currentNode.getRight();
			
			if(leftNode == null && rightNode == null) {
				keepGoing = false;
			} else if(leftNode == null) {
				Comparable currentData = (Comparable)currentNode.getData();
				Comparable rightData = (Comparable)rightNode.getData();
				
				if(rightData.compareTo(currentData) > 0) {
					E temp =  currentNode.getData();
					currentNode.setData(currentNode.getRight().getData());
					currentNode.getRight().setData(temp);
				}
				
				keepGoing = false;
			} else if(rightNode == null) {
				Comparable currentData = (Comparable)currentNode.getData();
				Comparable leftData = (Comparable)leftNode.getData();
				
				if(leftData.compareTo(currentData) > 0) {
					E temp =  currentNode.getData();
					currentNode.setData(currentNode.getLeft().getData());
					currentNode.getLeft().setData(temp);
				}
				
				keepGoing = false;
			} else {
				Comparable currentData = (Comparable)currentNode.getData();
				Comparable leftData = (Comparable)leftNode.getData();
				Comparable rightData = (Comparable)rightNode.getData();
				
				if(leftData.compareTo(rightData) > 0) {
					if(leftData.compareTo(currentData) > 0) {
						E temp =  currentNode.getData();
						currentNode.setData(currentNode.getLeft().getData());
						currentNode.getLeft().setData(temp);
					}
					currentNode = currentNode.getLeft();
					
				} else {
					if(rightData.compareTo(currentData) > 0) {
						E temp =  currentNode.getData();
						currentNode.setData(currentNode.getRight().getData());
						currentNode.getRight().setData(temp);
					}
					currentNode = currentNode.getRight();					
				}
			}
		}
		return max;
	}

	
	public static void main(String[] args) {
		final int size = 100;
		MaxHeap2<Integer> heap = new MaxHeap2<Integer>();
		for(int i = 0; i < size; i++){
			heap.add(i);
		}
		while(heap.size() > 0) {
			heap.remove();
		}
	}
}
