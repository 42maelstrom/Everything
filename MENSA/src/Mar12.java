import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;


public class Mar12 {
	public static final String DESCRIPTION = "Use the same set of four letters "
			+ "(in a different order each time) to fill in the blanks to form six"
			+ " uncapitalized English words.";
	
	String[] puzzles;
	Dictionary dict = new Dictionary(50);
	HashMap<String, ArrayList<Integer>> charMap;
	
	public Mar12(String... puzzles) {
		this.puzzles = puzzles;
		for(int j = 0; j < puzzles.length; j++) {
			int n = 1;
			for(int i = 0; i < puzzles[j].length(); i++) {
				if(puzzles[j].charAt(i) == '_') {
					puzzles[j] = puzzles[j].substring(0, i) + n + puzzles[j].substring(i + 1);
					n++;
				}
			}
		}
		charMap = new HashMap<String, ArrayList<Integer>>();
	}
	
	public void solve() {
		char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
		for(char c1: alphabet) {
			for(char c2: alphabet) {
				System.out.println("" + c2);
				for(char c3: alphabet) {
					for(char c4: alphabet) {
						for(int i = 0; i < puzzles.length; i++) {
							String p = puzzles[i];
							p = p.replace('1', c1);
							p = p.replace('2', c2);
							p = p.replace('3', c3);
							p = p.replace('4', c4);
							if(dict.isWord(p)) {
								char[] chars = {c1, c2, c3, c4};
								Arrays.sort(chars);
								String chars2 = String.copyValueOf(chars);
								if(charMap.containsKey(chars2)) {
									charMap.get(chars2).add(i);
									if(charMap.get(chars2).size() == 6) {
										System.out.println(chars2);
									}
								} else {
									charMap.put(chars2, new ArrayList<Integer>(Arrays.asList(i)));
								}
							}
						}
					}
				}
			}
		}
		
	}
	
	public static void main(String[] args) {
		new Mar12("BAN__U_TC_", "_LA_MA_E_","H__E_LIN_","S__SC_A_ER","_A__WA_","____TON").solve();
	}
}
