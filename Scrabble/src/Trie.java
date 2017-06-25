import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;


public class Trie {
	Node rootNode;
	private static final String WORD_LIST_NAME = "twl.txt";
	
	public Trie(Node root) {
		this.rootNode = root;
	}
	
	public boolean isWord(String word) {
		return rootNode.containsWord(word);
	}
	
	public static Trie makeTrie(String filePath) {	
		
		ArrayList<String> wordList = new ArrayList<String>();
		
		try {
			BufferedReader bf = new BufferedReader(new FileReader(WORD_LIST_NAME));
			String line;
	
			while ((line = bf.readLine()) != null) {
					wordList.add(line.toUpperCase());
			}
			
			bf.close();
			
		} catch (IOException e) {
			System.out.println("Error loading wordlist file:");
			System.out.println(e.getMessage());
		}
		
		return new Trie(makeNode(wordList));
	}
	
	private static Node makeNode(ArrayList<String> wordList) {
		int size = wordList.size();
		wordList.add("*");
		Node n = new Node();
		char c;
		int i = 0;
		ArrayList<String> subWordList = new ArrayList<String>();
		boolean isSubEndOfWord;
		Node baby;
		
		// for each first letter
		while (i < size) {
			isSubEndOfWord = false;
			c = wordList.get(i).charAt(0);
			
			//for each last chunk of the word, add to a sublist
			do {
				String s = wordList.get(i).substring(1);
				if(s.isEmpty() == false) {
					subWordList.add(s);
				} else {
					isSubEndOfWord = true;
				}
				i++;
			} while (wordList.get(i).charAt(0) == c);
			
			//subwordList from this iteration's first letter
			if(subWordList.isEmpty() == false) {
				baby = makeNode(subWordList);
				baby.setIsEndOfWord(isSubEndOfWord);
				n.addBranch(c, baby);
			} else {
				n.addBranch(c, new Node(true));
			}
			subWordList.clear();
		}
		
		return n;
	}
	
	public static void main(String[] args) {
		Trie t = makeTrie("sowpods.txt");
		System.out.println(t.rootNode.getNode("DOG").toString());
	}
	
}
