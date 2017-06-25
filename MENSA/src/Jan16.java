import java.util.Arrays;

public class Jan16 {
	public static final String DESCRIPTION =
			"Fill in the blanks with spelled-out numbers to make the"
			+ " statement true.";

	public final String text;
	private final char[] letters;
	private int[] numOfLetters;
	
	static final String[] SPELLED_OUT_NUMBERS = {"ZERO", "ONE", "TWO", "THREE",
		"FOUR", "FIVE", "SIX", "SEVEN", "EIGHT", "NINE", "TEN", "ELEVEN", "TWELVE",
		"THIRTEEN", "FOURTEEN", "FIFTEEN", "SIXTEEN", "SEVENTEEN", "EIGHTEEN",
		"NINETEEN", "TWENTY"};
	
	final int MAX_OF_ONE_LETTER;
	
	public Jan16(String text, char[] letters) {
		this.text = text;
		this.letters = letters;
		numOfLetters = new int[letters.length];
		MAX_OF_ONE_LETTER = 20; 
	}

	public void solve() {
		boolean keepGoing = true;
		
		while(keepGoing == true) {
			keepGoing = addOne(numOfLetters);
			int[] numOfLetters2 = new int[letters.length];
			String fullString = text;
			
			for(int num: numOfLetters)
				fullString+=SPELLED_OUT_NUMBERS[num];
			
	
			for(int i = 0; i < fullString.length(); i++) {
				for(int c = 0; c < letters.length; c++) {
					if(fullString.charAt(i) == letters[c])
						numOfLetters2[c]++;
				}
			}
		
		//	System.out.println(fullString + Arrays.toString(numOfLetters2));

			boolean isRight = true;
			for(int i = 0; i < numOfLetters.length; i++) {
				if(numOfLetters2[i] != numOfLetters[i]) {
					isRight = false;
					break;
				}
			}
			
			if(isRight) {
				String output = "";
				
				for(int i = 0; i < letters.length; i++)
					output+= numOfLetters[i] + " " + Character.toString(letters[i]) + "'S, ";
				
				System.out.println(output);
				return;
			}
		}
	}

	private boolean addOne(int[] array) {
		for(int i = 0; i < array.length; i++) {
			array[i]++;
			if(array[i] > this.MAX_OF_ONE_LETTER) {
				array[i] = 0;
			} else {
				return true;
			}
		}
		return false;
	}
	
	public static void main(String[] args) {
		char[] c = {'N', 'S', 'E'};
		new Jan16("THIS PICTURE FRAME CONTAINS EXACTLY N'S,S'S, AND E'S", c).solve();
	}
}
