import java.util.ArrayList;

public class WordCounter {
	
	private String text;
	private ArrayList<String> words;
	
	public WordCounter(String inputText) {
		
		text = inputText.toLowerCase();
		words = new ArrayList<String>();
		
		String letters = "abcdefghijklmnopqrstuvwxyz";
		int i = 0;
		while(i < text.length()) {
			//find the beginning of the first word
			while(i < text.length() && letters.contains(text.substring(i, i+1)) == false) {
				i++;
			}
			if(i >= text.length())
				return;
			String word = text.substring(i,i+1);
			i++;
			//find the length of the word found
			while(i < text.length() && letters.contains(text.substring(i, i+1))) {
				word += text.substring(i,i+1);
				i++;
			}
			//end of word. add to arraylist.
			words.add(word);
			i++;
		}		
	}
	
	/**
	 * Returns the text that this WordCounter is based off of.
	 */
	public String getText() {
		return text;
	}
	
	/**
	 * Returns an ArrayList of words from the text, in order.
	 */
	public ArrayList<String> getWords() {
		return words;
	}
	
	/**
	 * Returns the number of words in this text.
	 * @return
	 */
	public int getNumOfWords() {
		return words.size();
	}
	
	/**
	 * Returns the longest word in this text. If there
	 * are two words of the same length, this method will
	 * return the word that occurs earlier in the text.
	 * @return
	 */
	public String getLongestWord() {
		String longest = words.get(0);
		for(String word: words) {
			if(word.length() > longest.length())
				longest = word;
		}
		return longest;
	}
	
	/**
	 * Returns the average word length for words in this text.
	 * @return
	 */
	public double getAvgWordLength() {
		int sumOfLengths = 0;
		for(String word: words) {
			sumOfLengths+=word.length();
		}
		return (double)sumOfLengths / words.size();
	}
}
