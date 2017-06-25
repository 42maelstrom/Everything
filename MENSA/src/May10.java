public class May10 {
	public static final String NAME = "TAKE TWO";
	public static final String DESCRIPTION = "Choose two consonants and repeat"
			+ " them as many times as needed to complete this crisscross puzzle. All"
			+ " vowels, including Y's, have already been placed. All words are common,"
			+ " uncapitalized, unhyphenated English words.";
	
	public static void main(String[] args) {
		String[] words = {"*E*O**", "O*IO*E", "AE*IA*", "*EE*", "EA**IE*", "*UE",
				"EE*", "*U*E", "*EA*", "*EA*", "A**U*E", "O*A*", "O*EO","*OI*","*AU*E*",
				"A**U*E","*OA*"};
		new Jan14(words).solve();
	}
}
