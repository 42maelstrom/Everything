import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;


public class PhoneNumberWords {
	
	final static int MIN_WORD_LENGTH = 2;
	
	/**
	 * This one instead converts each word in the dictionary into it's number
	 * form and sees if this number is in the given phone number anywhere.
	 * Right now this one doesn't work for using 1 as I or 0 as O.... I figure 
	 * that's kind of cheating to make words anyway...
	 */
	public static void printPossibleWords(String number) {
		String[] keypad = { "o", "il", "abc", "def", "ghi", "jkl", "mno", "pqrs","tuv", "wxyz" };
		//					 0    11	222	   333	  444    555    666    7777   888    9999
		ArrayList<String> letters = new ArrayList<String>(7);
		for (int i = 0; i < number.length(); i++) {
			letters.add(keypad[Integer.parseInt(number.substring(i, i + 1))]);
		}
		
		//list of all English words within range of MIN_WORD_LENGTH through 7 letters
		ArrayList<String> dict = new ArrayList<String>();
		BufferedReader br;
		
		try{
			br = new BufferedReader(new FileReader("Dictionary.txt")); 
			String line;
			while((line = br.readLine() ) != null) {
				if(line.length() >= MIN_WORD_LENGTH && line.length() <= number.length())
					dict.add(line);
			}
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		//maps a letter to the number on the keypad. Ex: (a, 2);
		HashMap<String, String> letterToNum = new HashMap<String, String>(26);
		for(int i = 0; i < 26; i++) {
			String letter = "abcdefghijklmnopqrstuvwxyz".substring(i, i+1);
			int num = 2;
			while(keypad[num].indexOf(letter) == -1) {
				num++;
			}
			letterToNum.put(letter, Integer.toString(num));
		}
		
		//maps the found word to the index (or multiple indeces) in the phone 
		//number where it begins.
		HashMap<String, ArrayList<Integer>> foundWords = new HashMap<String, ArrayList<Integer>>();
		
		for(String word: dict) {
			String numberForm = "";
			
			for(int i = 0; i < word.length(); i++)
				numberForm += letterToNum.get(word.substring(i, i+1));
			
			int index = -1;
			//in case there's more than one place where this word can be found!
			do {
				index = number.indexOf(numberForm, index + 1);
				if(index != -1) {
					if(foundWords.containsKey(word) == false) {
						ArrayList<Integer> l = new ArrayList<Integer>();
						l.add(index);
						foundWords.put(word, l);
					} else {
						foundWords.get(word).add(index);
					}
				}
			} while(index != -1);
		}
		
		if(foundWords.keySet().size() == 0) {
			System.out.println("0 words found.");
		} else if(foundWords.keySet().size() == 1) {
			System.out.println("1 word found:");
		} else {
			System.out.println(foundWords.keySet().size() + " words found: \n");
		}
		
		for(String word: foundWords.keySet()) {
			ArrayList<Integer> indeces = foundWords.get(word);
			for(int index: indeces) {
				System.out.println(word + ": " + number.substring(0, index) + 
					word.toUpperCase() + number.substring(index + word.length()));
			}
		}
	}

	/**
	 * Make all of the permutations with the letters, then check and see if each
	 * is in the dictionary.
	 * This is infinitely slower and only should be used if you want to see it 
	 * with 1 and 0 turning into I, L, and O.
	 */
	public static void printPossibleWords2(String number) {
		String[] keypad = { "o", "il", "abc", "def", "ghi", "jkl", "mno", "pqrs","tuv", "wxyz" };
		//					 0    11	222	   333	  444    555    666    7777   888    9999
		ArrayList<String> letters = new ArrayList<String>(7);
		for (int i = 0; i < number.length(); i++) {
			letters.add(keypad[Integer.parseInt(number.substring(i, i + 1))]);
		}
		ArrayList<String> s = new ArrayList<String>(1);
		s.add("");
		
		ArrayList<String> perms = makePerms(s, letters);
		
		//list of all English words within range of MIN_WORD_LENGTH through 7 letters
		ArrayList<String> dict = new ArrayList<String>();
		BufferedReader br;
		
		try{
			br = new BufferedReader(new FileReader("Dictionary.txt")); 
			String line;
			while((line = br.readLine() ) != null) {
				if(line.length() >= MIN_WORD_LENGTH && line.length() <= number.length())
					dict.add(line);
			}
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		System.out.println("\n# of total letter permutations: " + perms.size());
		System.out.println("\nFinding words in permutations...");
		
		//maps a String subword to an ArrayList<String> of all of the letter permutations who have that combination.
		HashMap<String, ArrayList<String>> map = new HashMap<String, ArrayList<String>>();
	
		for(String perm: perms) {
			for(String word: dict) {
				if(perm.contains(word)) {
					if(map.containsKey(word)) {
						map.get(word).add(perm);
					} else {
						ArrayList<String> list = new ArrayList<String>(1);
						list.add(perm);
						map.put(word, list);
					}
				}
			}
		}
		
		System.out.println("Search completed.\n");
		if(map.size() == 1) {
			System.out.println("1 word found");
		} else {
		System.out.println(map.size() + " words found: \n");
		}
		
		for(String subWord: map.keySet()) {
			int index = map.get(subWord).get(0).indexOf(subWord);
			System.out.println(subWord + " (" + map.get(subWord).size() + ") "
							   + number.substring(0, index) + subWord.toUpperCase() +
							     number.substring(index + subWord.length()));
		}
		
		System.out.println();
		
//		for(String subWord: map.keySet()) {
//			int index = map.get(subWord).get(0).indexOf(subWord);
//			System.out.println(subWord + " (" + map.get(subWord).size() + ") "
//							   + number.substring(0, index) + subWord.toUpperCase() 
//							   + number.substring(index + subWord.length()));
//			for(String word: map.get(subWord)) {
//				System.out.println("\t" + word); 
//			}
//		}
	}
	
	/**
	 * These strings returned are all the possible 7-letter arrangements of the
	 * phone number. It does this recursively.
	 */
	private static ArrayList<String> makePerms(ArrayList<String> perms, ArrayList<String> letters) {

		if (letters.isEmpty())
			return perms;

		ArrayList<String> newPerms = new ArrayList<String>(perms.size()
				* letters.get(0).length());
		for (int i = 0; i < perms.size(); i++) {
			for (int j = 0; j < letters.get(0).length(); j++) {
				newPerms.add(perms.get(i)  + letters.get(0).charAt(j));
			}
		}

		letters.remove(0);
		return makePerms(newPerms, letters);
	}
	
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Input phone number with no spaces. Ex: 6590736");
		String number = scanner.nextLine();
		printPossibleWords(number);
	}
	
}
