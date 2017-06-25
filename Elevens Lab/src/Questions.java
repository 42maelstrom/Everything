
public class Questions {
/**
	1. A deck is essentially a collection of cards.
	2. The deck contains 6 cards.
	3. Here is how ranks and suits would be initialized: 
		String[] ranks = {2,3,4,5,6,7,8,9,10,11,12,13,14};
		String[] suits = {Spades, Hearts, Diamonds, Clubs};
		int[] pointValues = {2,3,4,5,6,7,8,9,10,10,10,10,11};
	4. The order of the suits does not matter, because the same pointValues and ranks will be created for each suit no matter what. The order of the ranks and pointValues does matter, however. Each element in ranks and pointValues must align with each other, that is, cards will be created giving the card with the first rank from ranks the first pointValue from pointValues.

	1.   import java.util.Random; 
		public static String flip()
		{
			String flip = "heads";
			Random randGen = new Random();
			if(randGen.nextInt(3) == 2)
				flip = "tails";
			return flip;
		}

	2. 
		public static boolean arePermutations(int[] array1, int[] array2)
		{
			for(int i = 0; i < array1.length; i++)
			{
				boolean array2containsThisIndex = false;
				for(int j = 0; j < array1.length; j++)
				{
					if(array2[j] == array1[i])
					{
						array2containsThisIndex = true;
						break;
					}
				}
				if(array2containsThisIndex == false)
					return false;
			}
			return true;
		}

	3. 

	1,2,3,4
	4,2,3,1
	4,3,2,1
	4,3,2,1

	First, 4 and 1 are swapped. First random integer = 0.
	Next, 3 and 2 are swapped. Second random integer = 1.
	Lastly, 3 swaps with itself. Third random integer = 1. 
	   **/
}
