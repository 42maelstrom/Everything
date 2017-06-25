import java.util.Random;

/**
 * This class provides a convenient way to test shuffling methods.
 */
public class Shuffler {

	/**
	 * The number of consecutive shuffle steps to be performed in each call
	 * to each sorting procedure.
	 */
	private static final int SHUFFLE_COUNT = 5;
	

	/**
	 * Tests shuffling methods.
	 * @param args is not used.
	 */
	public static void main(String[] args) {
		System.out.println("Results of " + SHUFFLE_COUNT +
								 " consecutive perfect shuffles:");
		int[] values1 = {0, 1, 2, 3};
		for (int j = 1; j <= SHUFFLE_COUNT; j++) {
			perfectShuffle(values1);
			System.out.print("  " + j + ":");
			for (int k = 0; k < values1.length; k++) {
				System.out.print(" " + values1[k]);
			}
			System.out.println();
		}
		System.out.println();

		System.out.println("Results of " + SHUFFLE_COUNT +
								 " consecutive efficient selection shuffles:");
		int[] values2 = {0, 1, 2, 3,4,5,6,7,8};
		for (int j = 1; j <= SHUFFLE_COUNT; j++) {
			selectionShuffle(values2);
			System.out.print("  " + j + ":");
			for (int k = 0; k < values2.length; k++) {
				System.out.print(" " + values2[k]);
			}
			System.out.println();
		}
		System.out.println();
	}


	/**
	 * Apply a "perfect shuffle" to the argument.
	 * The perfect shuffle algorithm splits the deck in half, then interleaves
	 * the cards in one half with the cards in the other.
	 * @param values is an array of integers simulating cards to be shuffled.
	 */
	public static void perfectShuffle(int[] values) {
		int[] shuffled = new int[values.length];
		int k = 0;
		for(int j = 0; j < (values.length + 1) / 2; j++)
		{
			shuffled[k] = values[j];
			k+=2;
		}
		k = 1;
		for(int j = (values.length + 1) / 2; j < values.length; j++)
		{
			shuffled[k] = values[j];
			k+=2;
		}
		
		//copy shuffled to values. for some reason it doesn't work to copy just saying values = shuffled.
		for(int i = 0; i < shuffled.length; i++)
		{
			values[i] = shuffled[i];
		}
	}

	/**
	 * Apply an "efficient selection shuffle" to the argument.
	 * Starting at the last element, swap it with a random element before it (or swap with itself)
	 * @param values is an array of integers simulating cards to be shuffled.
	 */
	public static void selectionShuffle(int[] values) {
		//I'm doing the version that's on the ElevensLab.pdf, not the one in the method description.
		//Because they sound like two different things to me.
		Random randGen = new Random();
		for(int k = values.length - 1; k > 0; k--)
		{
			//random spot before or at this rank
			int r = randGen.nextInt(k+1);
			
			//exchange/swap the two values
			int temp = values[k];
			values[k] = values[r];
			values[r] = temp;
		}
	}
}
