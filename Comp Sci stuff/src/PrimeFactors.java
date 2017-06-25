import java.util.ArrayList;

public class PrimeFactors {
	public static void main( String[] args)
	{
		System.out.println(primeFactors(47));
		System.out.println(primeFactors(24));
		System.out.println(primeFactors(123768));
		System.out.println(primeFactors(81));		
	}
	
	public static ArrayList primeFactors(long endNum){
		
		ArrayList primeFactors = new ArrayList();
		int counter = 2;
		while(counter <= endNum)
		{
		
			while(endNum % counter != 0) //while the number is not divisible by the counter
			{
				counter ++; // add one
			}
		
			primeFactors.add(counter); //hey a number make it here so it must be divisible so add it to the list of divisors
			endNum = endNum / counter; // make the endnumber lower 
		}
		return primeFactors;
	}
}
