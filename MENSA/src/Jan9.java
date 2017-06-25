import java.util.ArrayList;
import java.util.Arrays;



public class Jan9 {
	public static final String	NAME = "BY GUM!";
	public static final String DESCRIPTION = "A gumball machine contains 12 yellow, 11 green, 7 orange,"
				+ " and 13 red gumballs. How many more red gumballs must be loaded into"
				+ " the machine so that 2/3 of the gumballs in it are red?";
	public static final	ArrayList<Class> HELPER_CLASSES = new ArrayList<Class>(Arrays.asList(Fraction.class));

	private int RED;
	private final int YELLOW;
	private final int ORANGE;
	private final int GREEN;
	private int NUM_ADDED = 0;

	public Jan9(int rED, int yELLOW, int oRANGE, int gREEN) {
		RED = rED;
		YELLOW = yELLOW;
		ORANGE = oRANGE;
		GREEN = gREEN;
	}	
	
	public void solve() {
		Fraction redProportion = new Fraction(RED + NUM_ADDED, RED + NUM_ADDED + YELLOW + ORANGE + GREEN);
		do {
			NUM_ADDED++;
			redProportion = new Fraction(RED + NUM_ADDED, RED + NUM_ADDED + YELLOW + ORANGE + GREEN);
			redProportion.simplify();
		} while(!redProportion.equals(new Fraction(2,3)));
		System.out.println(NUM_ADDED);
	}
	
	public static void main(String[] args) {
		new Jan9(13, 12, 7, 13).solve();
	}
}
