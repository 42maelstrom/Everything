public class FindTheLargest 
{
	public static void main(String[] args)
	{
		// Creates an array of ten ints and fills it with
		// random values from 1 to 100
		int[] data = new int[10];
		for(int x=0; x<data.length; x++)
		{
			data[x] = (int)(Math.random()*100-150);
		}
		
		// Prints out the array
		for(int x=0; x<data.length; x++)
		{
			System.out.print(data[x]+" ");
		}
		System.out.println("\n\n");
		
		System.out.println(largest(data));
		
	}
	
	public static int largest(int[] myArray)
	{
		int a = myArray[0];
		for(int i = 1; i < myArray.length; i++)
		{
			a = Math.max(a, myArray[i]);
		}
		return a;
	}
}