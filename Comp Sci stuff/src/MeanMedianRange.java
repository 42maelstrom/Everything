/************************************************
 * 
 * Class: MeanMedianRange
 * Author: Greg King
 * Date: September 16, 2013
 * 
 * This program computes and displays the mean,
 * median and range of a randomly generated 
 * set of data.
 *
 * Date			Activity
 * 9/16/13		Main coding
 * 
 ************************************************/
import java.util.ArrayList;

public class MeanMedianRange 
{
	public static void main(String[] args)
	{
		// Create an array and fill it with random values
		int[] data = new int[20];
		for(int n=0; n<data.length; n++)
		{
			data[n] = (int)(Math.random()*100+1);
		}
		
		// Print out the array
		for(int n=0; n<data.length; n++)
		{
			System.out.print(data[n]+"\t");
			if(n%5==4)
				System.out.println();
		}
		
		// Put code here to find and print out
		// the mean, median and range of the 
		// values in data
		
		//Mean
		double sum = 0;
		for(int temp: data)
		{
			sum+= temp;
		}
		double mean = sum / data.length;
		
		//Median
		for(int i = 0; i < data.length - 1; i++)   //sort the array from smallest to largest
		{
			for(int j = i + 1; j < data.length; j++)
			{
				if(data[j] < data[i])
				{
					int temp = data[i];
					data[i] = data[j];
					data[j] = temp;
				}
			}
		}
		double median;
		if(data.length % 2 == 0)
		{
			median = (double)(data[data.length / 2] + data[data.length / 2 - 1] ) / 2; 
			//median is the sum of the two middle elements, divided by two
		}else{
			median = data[data.length / 2];
			//median is the middle element
		}
		
		//Range
		int min = data[0];
		int max = data[0];
		
		for(int i = 1; i < data.length; i++) //find the min and the max
		{
			if(data[i] < min)
				min = data[i];
			if(data[i] > max)
				max = data[i];
		}
		int range = max - min;
		
		//Mode
		int count, highestCount;
		highestCount = 1;
		ArrayList<Integer> modes = new ArrayList<Integer>();
		for(int temp: data)
		{
			if(!modes.contains(temp)) // only do this if this number hasn't already been found as a mode.
			{						  // Otherwise, it will show up twice in the mode ArrayList.
				count = 0;
				for(int temp2: data) //after this loop has run, count is the number of times temp appears in the array
				{
					if(temp == temp2)
						count++;
				}
				if(count == highestCount) // if temp appears as many times as the other mode(s)
					modes.add(temp);      // ..add it!
				if(count > highestCount)  // if temp appears more times...
				{
					modes.clear();		  // empty the list of modes, because they aren't the mode anymore!
					modes.add(temp);      // add the one and only true mode (for now)
					highestCount = count; // now to be a mode, you have to reach what that guy did.
				}
			}	
		}
		
		System.out.println("  The mean is: " + mean);
		System.out.println("The median is: " + median);
		System.out.println(" The range is: " + range);
		System.out.print  ("The modes are: ");
		
		for(int i = 0; i < modes.size(); i++)
		{
			System.out.print(modes.get(i) + " ");
		}
	}

}