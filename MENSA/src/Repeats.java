import java.util.ArrayList;
import java.util.Arrays;


public class Repeats {
	
	//same as March 8
	public static void july15() {
		char[][] letters = { {'W','O','E','W','T','E','O','N'},
												 {'T','N','K','E','R','N','W','T'},
												 {'H','I','S','T','N','N','A','H'},
												 {'U','R','I','S','I','K','S','T'},
												 {'E','T','T','N','E','H','T','O'}};
		int[] enumeration = {3, 6, 2, 3, 8, 2, 4, 4, 4, 4};
		Point start = new Point(7, 0);
		Mar8 m = new Mar8(letters, enumeration, start);
		m.solve();
	}
	
	//same as Feb5
	public static void june17() {
		String[][] boxes = {{"GRA", "BLO", "ACL", "QKD", "SYU"},
				{"IHF", "AAE", "RTL", "UGO", "OHN"},
				{"TAO", "LMH", "FEI", "EMW", "ENR"},
				{"CSS", "RBT", "EIA", "BSR", "NHE"},
				{"YTF", "REI", "NEO", "NTO", "KTL"}};
		
		Feb5 j17 = new Feb5(boxes);
		j17.dict.addWord("yentl");
		j17.dict.addWord("fargo");
		j17.dict.addWord("bambi");
		j17.dict.addWord("alfie");
		j17.dict.addWord("dumbo");
		j17.dict.addWord("shrek");
		j17.solve();
	}
	
	//same as Jan23
	public static void june12() {
			Jan23 j12 = new Jan23("sstavu", "eanipi", "lycsrg");
			j12.dict.addWord("thomas");
			j12.dict.addWord("edison");
			j12.solve();
	}
	
	//same as Jan24
	public static void june10() {
		ArrayList<String> firstWords = new ArrayList<String>(Arrays.asList("ice", "reuse", "swig", "close", "ecru", "swarm"));
		ArrayList<String> secondWords = new ArrayList<String>(Arrays.asList("regal", "archery", "nifty", "chant", "diction", "planed"));

		new Jan24(firstWords, secondWords).solve();
	}
	
	//same as Feb16
	public static void june8() {
			Region r1 = new Region(new Space(0,0), new Space(0, 1), new Space(1, 0),
					new Space(2, 0), new Space(3, 0), new Space(3, 1), new Space(3, 1),
					new Space(4, 0), new Space(4, 1), new Space(4, 2), new Space(5, 0),
					new Space(5,1), new Space(5,2));
			Region r2 = new Region(new Space(1, 1), new Space(2, 1), new Space(2, 2),
					new Space(3, 2), new Space(3, 3), new Space(4, 3), new Space(4, 4));
			Region r3 = new Region(new Space(5, 3), new Space(5, 4), new Space(5, 5),
					new Space(4, 5), new Space(3, 5));
			Region r4 = new Region(new Space(2, 5), new Space(2, 4), new Space(3, 4));
			Region r5 = new Region(new Space(1, 5), new Space(1, 4));
			Region r6 = new Region(new Space(0, 2), new Space(0, 3), new Space(0, 4),
					new Space(0, 5), new Space(1, 2), new Space(1, 3), new Space(2, 3));
			
			new Feb16(r1, r2, r3, r4, r5, r6).solve();
	}
	
	public static void main(String[] args) {
		july15();
	}
}
