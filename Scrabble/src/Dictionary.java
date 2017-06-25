import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class Dictionary {
	public static final ArrayList<String> WORD_LIST;
	private static final String WORD_LIST_NAME = "twl.txt";
	
	static {
		WORD_LIST = new ArrayList<String>();
		
		try {
			BufferedReader bf = new BufferedReader(new FileReader(WORD_LIST_NAME));
			String line;
	
			while ((line = bf.readLine()) != null) {
					WORD_LIST.add(line.toUpperCase());
			}
			
			bf.close();
			
		} catch (IOException e) {
			System.out.println("Error loading wordlist file:");
			System.out.println(e.getMessage());
		}
	}
	
	public static boolean isWord(String word) {
		return WORD_LIST.contains(word);
	}
}
