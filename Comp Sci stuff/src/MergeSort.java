import java.util.Random;

public class MergeSort {

	public static void main(String[] args) {
		int[] data = new int[15];
		Random randGen = new Random();
		
		System.out.println("Original order");
		
		for(int i = 0; i < data.length; i++)
		{
			data[i] = 1 + randGen.nextInt(99);
			System.out.print(data[i] + " ");
		}
		System.out.println();
		
		mergeSort(data);
		
		System.out.println("Sorted order");
		for(int i: data)
			System.out.print(i + " ");
	}
	
	public static void mergeSort(int[] data) {
		mSort(data, 0, data.length - 1);
	}
	
	private static void mSort(int[] data, int low, int high) {
		//if low is not lower, must be a segment of length one and is already sorted.
		if(low < high) {
			int middle = (low + high) / 2;
			mSort(data, low, middle);
			mSort(data, middle + 1, high);
			merge(data, low, middle, high);
		}
	}
	
	private static void merge(int[] data, int low, int middle, int high) {
		//make helper array containing that section's numbers.
		int[] helper = new int[data.length];
		for(int i = low; i <= high; i++) {
			helper[i] = data[i];
		}
		
		int k = middle + 1; //this keeps track of current element from 'high' being looked at
		int j = low; //this keeps track of current element from 'low' being looked at
		int i = low; //this keeps track of current index being copied into
		
		//going through low and high. compare two, and
		while(j <= middle && k <= high) {
			if(helper[j] < helper[k]) {
				data[i] = helper[j];
				j++;
			} else {
				data[i] = helper[k];
				k++;
			}
			i++;
		}
		
		 // Now either the left side or right side has run out of elements.
		//if the left side ran out, the right side's elements will already
		//be in order and in place, and this loop will not run.
		//if the right side ran out, this loop finishes copying the left side elements in.
	    while (j <= middle) {
	      data[i] = helper[j];
	      j++;
	      i++;
	    }
	}
}
