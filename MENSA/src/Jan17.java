import java.util.ArrayList;

public class Jan17 {
	public static final String NAME = "ENCRYPTED STATE";
	public static final String DESCRIPTION =
			"The word WHIZGIG can be encoded into the name of a U.S."
			+ " state by replacing each letter with a different letter, but where"
			+ " repeated letters are assigned the same replacement letter, as in a"
			+ " cryptogram. What is this state?";
	
	private static final ArrayList<String> states = TxtParser.parseFile("States.txt");
	public final String key;
	
	
	public Jan17(String key) {
		this.key = key;
	}
	
	public void solve() {
		outer:
		for(String s: states) {
			s = s.toUpperCase();
			if(s.length() == 7) {
				for(int i = 0; i < s.length() - 1; i++) {
					for(int j = i + 1; j < s.length(); j++) {
						boolean sMatches = s.charAt(i) == s.charAt(j);
						boolean keyMatches = key.charAt(i) == key.charAt(j);
						
						if(sMatches && !keyMatches)
							continue outer;
						if(keyMatches && !sMatches)
							continue outer;
					}
				}
				System.out.println(s);
				return;
			}
		}
	}
	
	public static void main(String[] args) {
		new Jan17("WHIZGIG").solve();
	}
}
