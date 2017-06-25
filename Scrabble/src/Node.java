import java.util.LinkedHashMap;
import java.util.Set;


public class Node {
	private boolean isEndOfWord;
	LinkedHashMap<Character, Node> branches;
	
	public Node() {
		branches = new LinkedHashMap<Character, Node>();
		isEndOfWord = false;
	}
	
	public Node(boolean isEndOfWord) {
		this();
		this.isEndOfWord = isEndOfWord;
	}
	
	public boolean isTerminal() {
		return branches.keySet().isEmpty();
	}

	public boolean containsWord(String word) {
		if(word.length() == 0) {
			return isEndOfWord;
		} else {
			return branches.containsKey(word.charAt(0)) 
					&& getNode(word.charAt(0)).containsWord(word.substring(1)); 
		}
	}
	
	public boolean isEndOfWord() {
		return isEndOfWord;
	}
	
	public void setIsEndOfWord(boolean isEndOfWord) {
		this.isEndOfWord = isEndOfWord;
	}
	
	public void addBranch(char c) {
		branches.put(c, new Node());
	}
	
	public void addBranch(char c, Node n) {
		branches.put(c, n);
	}
	
	public Set<Character> getChars() {
		return branches.keySet();
	}
	
	public Node getNode(char c) {
		return branches.get(c);
	}
	
	public Node getNode(String s) {
		Node n = branches.get(s.charAt(0));
		if(n == null)
			return null;
		if(s.length() == 1)
			return n;
		return n.getNode(s.substring(1));
	}
	
	public void print() {
		print("");
	}
	
	private void print(String indent) {
		for(char c: this.getChars()) {
			String s = this.getNode(c).isEndOfWord() ? "*" : "";
			System.out.println(indent + c + s);
			this.getNode(c).print(indent + '-');
		}
	}
	
	@Override
	public String toString() {
		return this.toString("-");
	}
	
	private String toString(String indent) {
		String str = "";
		for(char c: this.getChars()) {
			String s = this.getNode(c).isEndOfWord() ? "*" : "";
			str+= indent + c + s + "\n" + this.getNode(c).toString(indent + '-');
		}
		return str;
	}
	
}
