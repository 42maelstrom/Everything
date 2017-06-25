import java.util.ArrayList;

public class NBDig {
	/**
	 * A number is digisible iff:
	 * -The number is greater than 10
	 * -The number has no repeated digits
	 * -The number is divisible by each digit
	 */
	public static ArrayList<Long> getAllDigisibles() {
		long i = 10;
		ArrayList<Long> digisibles = new ArrayList<Long>();

		outer: 
		while(i < 10000000)
		{
			i++;
			
			//This loop gets all of the individual digits in the number. Also
			//quits if any repeated digits are found.
			ArrayList<Long> digits = new ArrayList<Long>(9);
			long j = i;
			while(j > 0)
			{
				long digit = j % 10;
				
				if(digits.contains(digit))
					continue outer;

				digits.add(digit);
				j/=10;
			}
			
			if(digits.contains(0L) == false) {
				for(Long digit: digits)
					if(0 != i % digit)
						continue outer;
			
				digisibles.add(i);
			}
		}	
		return digisibles;
	}
	
	/**
	 * Here repeated digits are okay
	 */
	public static ArrayList<Long> getAllDigisibles2() {
		long i = 10;
		ArrayList<Long> digisibles = new ArrayList<Long>();

		outer: 
		while(i < 10000000)
		{
			i++;
			
			//This loop gets all of the individual digits in the number. Also
			//quits if any repeated digits are found.
			ArrayList<Long> digits = new ArrayList<Long>(9);
			long j = i;
			while(j > 0)
			{
				long digit = j % 10;
				
				digits.add(digit);
				j/=10;
			}
			
			if(digits.contains(0L) == false) {
				for(Long digit: digits)
					if(0 != i % digit)
						continue outer;
			
				digisibles.add(i);
			}
		}	
		return digisibles;
	}
	
	public static void main(String[] args)
	{
		/*
		 * Why aren't there any 4's in digisible numbers past 91476?
		 * - 6 digits. 
		 * - Already can't have a five, so digits available are 1,2,3,6,7,8,9 to fill five slots
		 * - it's not something to do with another digit, I think. Because in all of the five digit
		 *  numbers which are digisible and include a 4, it has every other number (besides 5 of course)
		 *  
		 *  
		 *  [24, 48, 124, 184, 248, 264, 324, 384, 412, 432, 624, 648, 784, 824, 864, 1248, 1764, 1824,
		 *   2184, 2364, 2436, 3264, 3492, 3624, 3648, 3864, 3924, 4128, 4172, 4236, 4368, 4392, 4632,
		 *   4872, 4896, 4932, 4968, 6324, 6384, 6432, 6984, 8496, 9324, 9432, 9648, 9864, 12384,
		 *   12648, 12864, 13248, 13824, 14328, 14728, 14832, 16248, 16824, 17248, 18264, 18432,
		 *   18624, 21384, 21648, 21784, 21864, 23184, 24168, 24816, 26184, 27384, 28416, 31248,
		 *   31824, 32184, 34128, 38472, 41328, 41832, 42168, 42816, 43128, 43176, 46128, 46872,
		 *   48216, 48312, 61248, 61824, 62184, 64128, 72184, 73164, 73248, 73416, 78624, 81264,
		 *   81432, 81624, 82416, 84216, 84312, 84672, 91476
		 *  
		 *  Number of digisibles containing the digit: 
		 *  1: 449
		 *  2: 483
		 *  3: 436
		 *  4: 338 
		 *  5: 11
		 *  6: 386
		 *  7: 187
		 *  8: 458
		 *  9: 263
		 *  
		 */
		
		//new EasyWriter("digisibles2.csv").print(getAllDigisibles2().toString().replaceAll("\\s+", "").substring(1).replaceFirst("]", ""));
	}
}	
