import java.util.ArrayList;
import java.util.Random;


public class Jan31 {
	public static final String NAME = "DICE BUCKET CHALLENGE";
	public static final String DESCRIPTION = "Joe has a bucket containing 28 "
			+ "dice. He has one red die, two orange dice, three yellow dice, four"
			+ " green dice, five blue dice, six purple dice, and seven white dice."
			+ " He draws the dice out of the bucket one at a time without"
			+ " replacement, and without looking at their colors. What is the"
			+ " minimum number of dice that Joe must draw to guarantee that he has"
			+ " removed at least four dice of the same color?";
	
	private static final int MIN_OF_EACH_COLOR = 4;
	private static final int NUM_OF_TRIALS = 1000;
	private final int[] numOfEachColor;
	private final ArrayList<Integer> bag;
	private final ArrayList<Integer> zeros;
	
	public Jan31(int ... numOfEachColor) {
		this.numOfEachColor = numOfEachColor;
		this.bag = new ArrayList<Integer>();
		this.zeros = new ArrayList<Integer>();
		
		for(int i = 0; i < numOfEachColor.length; i++) {
			zeros.add(0);
			for(int j = 0; j < numOfEachColor[i]; j++) {
				bag.add(i);
			}
		}
	}
	
	public void solve() {
		int maxDrawsTaken = 0;
		
		for(int trial = 0; trial < NUM_OF_TRIALS; trial++) {
			int drawsTaken = findDrawsTaken();
			
			if(drawsTaken > maxDrawsTaken) {
				System.out.println(drawsTaken);
				maxDrawsTaken = drawsTaken;
			}
		}
	}
	
	private int findDrawsTaken() {
		ArrayList<Integer> bag2 = new ArrayList<Integer>();
		for(int i: bag)
			bag2.add(i);
		
		ArrayList<Integer> numTakenOfEachColor = new ArrayList<Integer>();
		for(int i = 0; i < numOfEachColor.length; i++)
			numTakenOfEachColor.add(0);
		
		int drawsTaken = 0;
		Random rand = new Random();
		while(!numTakenOfEachColor.contains(4)) {
			int randIndex = rand.nextInt(bag2.size());
			int color = bag2.remove(randIndex);
			numTakenOfEachColor.set(color, numTakenOfEachColor.get(color) + 1);
			drawsTaken++;
		}
		
		return drawsTaken;
	}
	
	public static void main(String[] args) {
		new Jan31(1, 2, 3, 4, 5, 6, 7).solve();
	}
}
