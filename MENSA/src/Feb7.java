import java.util.ArrayList;
import java.util.Arrays;


public class Feb7 {
	public static final String DESCRIPTION = "An investment firm wants the phone"
			+ " number 438-7424 because of the seven-letter phrase that the number"
			+ " spells. What's the phrase?";
	
	public static final ArrayList<Class> HELPER_CLASSES = new ArrayList<Class>(
			Arrays.asList(PhoneNumberWords.class));
	
	public final String phoneNumber;
	
	public Feb7(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public void solve() {
		PhoneNumberWords.printPossibleWords2(phoneNumber);
	}
	
	public static void main(String[] args) {
		new Feb7("4387424").solve();
	}
	
}
