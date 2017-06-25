public class ReverseTheArray 
{
	public static void main(String[] args)
	{
		int length = (int)(Math.random()*10)+10;
		
		int[] numbers = new int[length];
		
		for(int i=0; i<length; i++)
		{
			numbers[i] = (int)(Math.random()*90+10);
		}
		
		System.out.println("Original Order:");
		for(int temp : numbers)
			System.out.print(temp+" ");
		reverse(numbers,0,length-1);
		System.out.println("\nReversed Order:");
		for(int temp : numbers)
			System.out.print(temp+" ");
		System.out.println("\nLargest = "  + findTheLargest(numbers, 0));
	}
	
	// Write code so that reverse is a recursive method that
	// reverses the array data from index start to index end
	public static void reverse(int[] data, int start, int end)
	{
		if(start >= end) //stop reversing once you get past halfway
			return;
		
		int temp = data[start]; //swap start and end
		data[start] = data[end];
		data[end] = temp;
		
		reverse(data, start + 1, end -1);
	}
	
	// Write code so that findTheLargest is a recursive method
	// that finds the largest element of the array data from
	// index to the end of the array
	public static int findTheLargest(int[] data, int index)
	{
		if(index == data.length - 1) //base case.
			return data[index];
		
		return Math.max(data[index], findTheLargest(data, index+1));

	}
}