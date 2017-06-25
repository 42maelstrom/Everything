import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;


public class Dictionary {
	public static ArrayList<String> dict = getDict("Dictionary.txt");
	
	public static boolean isWord(String word) {
		return dict.contains(word.toLowerCase());
	}
	
	public static ArrayList<String> getDict(String fileName) {
		ArrayList<String> dict = new ArrayList<String>();
		BufferedReader br;
		
		try{
			br = new BufferedReader(new FileReader(fileName)); 
			String line;
			while((line = br.readLine() ) != null) {
				dict.add(line);
			}
			br.close();
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return dict;
	}
	
	public static void addWord(String word) {
		dict.add(word);
	}
}
