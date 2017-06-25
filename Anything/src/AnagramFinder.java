import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class AnagramFinder {
	
	private static ArrayList<String> wordList = getWordList("Dictionary.txt");
	
	private static int MIN_WORD_LENGTH = 4;
	
	public static ArrayList<String> oldFindAnagrams(String word) {
		word = word.toLowerCase();
		ArrayList<String> anagrams = new ArrayList<String>();
		
		for(String anagram: getAllArrangements(word)) {
			if(wordList.contains(anagram) && !anagrams.contains(anagram))
				anagrams.add(anagram);
		}
		
		return anagrams;
	}

	private static ArrayList<String> getWordList(String filePath) {
		
		ArrayList<String> wordList = new ArrayList<String>();
		
		try {
			BufferedReader bf = new BufferedReader(new FileReader(filePath));
			String line;
	
			while((line = bf.readLine()) != null) {
				if(line.length() >= MIN_WORD_LENGTH)
					wordList.add(line);
			}
			
			bf.close();
			
		} catch(IOException e) {
			System.out.println("Error loading word list file:");
			System.out.println(e.getMessage());
			System.out.println("Program ended.");
			System.exit(0);
		}
		
		return wordList;
	}
	
	private static ArrayList<String> getAllArrangements(String word) {
		ArrayList<String> arrangements = new ArrayList<String>();
		
		//go through all of the possible combinations of the string, and add all of
		for(String combo: getCombinations(word)) 
				arrangements.addAll(getPermutations(combo));	
		
		return arrangements;
	}
	
	private static ArrayList<String> getCombinations(String word) {
		ArrayList<String> substrings = new ArrayList<String>();
		
		if(word.length() == 1) {
			substrings.add(word);
			return substrings;
		}
		
		for(int i = 0; i < word.length(); i++) {
			String firstLetter = word.substring(i,  i + 1);
			substrings.add(firstLetter);
			for(String s: getCombinations(word.substring(i + 1))) 
				substrings.add(firstLetter + s);
		}
		
		return substrings;
	}
	
	private static ArrayList<String> getPermutations(String word) {
		ArrayList<String> perms = new ArrayList<String>();
		//start with the base character
		perms.add(word.substring(0, 1));
		
		//for each remaining letter, make a permutation with that 
		//letter inserted into each possible position
		for(int i = 1; i < word.length(); i++) {
			//character to add in each position
			String charToAdd = word.substring(i, i+1);
			//new list of perms created from list of old perms
			ArrayList<String> newPerms = new ArrayList<String>(perms.size() * (i+1));
			for(String perm: perms) {
				//for each position, insert the letter.
				for(int pos = 0; pos < i+1; pos++)
					newPerms.add(perm.substring(0,pos) + charToAdd + perm.substring(pos)); 
			}
			perms = newPerms;
		}
		
		return perms;
	}
	
	public static ArrayList<String> findAnagrams(String word) {
		ArrayList<String> anagrams = new ArrayList<String>();
	
		outer:
		for(String w: wordList) {
			if(w.length() < MIN_WORD_LENGTH)
				continue;
			
			int blanks = 0;
			for(int i = 0; i < word.length(); i++) {
				if(word.charAt(i) == '*')
					blanks++;
			}
			
			int misfits = 0; //basically number of times a blank has been used
		
			String word2 = word;
			for(int i = 0; i < w.length(); i++) {
				int j = word2.indexOf(w.charAt(i));
				if(j == -1) {
					misfits++;
					if(misfits > blanks)
						continue outer;
				} else {
					//take out the character where the match was
					word2 = word2.substring(0, j) + word2.substring(j + 1); //brisk
				}
			}
			anagrams.add(w);
		}
		return anagrams;
	}
	
	public static String getScrabbleRack() {
		String alphabet = "abcdefghijklmnopqrstuvwxyz*";
		int[] freq = {9,2,2,4,12,2,3,2,9,1,1,4,2,6,8,2,1,6,4,6,4,2,2,1,2,1,2};
		ArrayList<Character> bag = new ArrayList<Character>(98);
		int k = 0;
		for(int i = 0; i < 26; i++) {
			for(int j = 0; j < freq[i]; j++) {
				bag.add(alphabet.charAt(i));
			}
		}
		
		String rack = "";
		Random rand = new Random();
		for(int i = 0; i < 7; i++) {
			rack+=bag.get(rand.nextInt(bag.size()));
		}
		
		return rack;
	}
	
	public static void printAnagrams(String word) {
		ArrayList<String> anagrams = findAnagrams(word);
		System.out.println("Anagrams with min word length of " + MIN_WORD_LENGTH + ":");
		
		for(int i = 0; i < anagrams.size() - 1; i++) {
			for(int j = i + 1; j < anagrams.size(); j++) {
				if(anagrams.get(i).length() < anagrams.get(j).length()) {
					String temp  = anagrams.get(i);
					anagrams.set(i, anagrams.get(j));
					anagrams.set(j, temp);
				}
			}
		}
		
		for(String s: anagrams) {
			System.out.println(s);
		}
	}
	
	public static void findWordWithMostAnagrams() {
		String maxWord;
		int max = -1;
		
		for(int i = 0; i < wordList.size(); i++) {
			String word = wordList.get(i);
			MIN_WORD_LENGTH = word.length();
			ArrayList<String> anagrams = findAnagrams(word);
			int numOfAnagrams = anagrams.size();
			
			if(numOfAnagrams >= max) {
				maxWord = word;
				max = numOfAnagrams;
				System.out.println(word + ": " + numOfAnagrams + ": " + anagrams.toString());
			}
		}
	}
	
	public static void main(String[] args) {
		MIN_WORD_LENGTH = 5;
		System.out.println(findAnagrams("shomrer").toString());
	}
}
