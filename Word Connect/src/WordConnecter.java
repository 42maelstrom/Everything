import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;


public class WordConnecter {
	public final WordMap wm;
	
	private Word w1;
	private Word w2;
	private ArrayList<Word> used1, used2;
	private ArrayList<ArrayList<Word>> rounds1, rounds2;
	private int dist1, dist2;
	
	public WordConnecter(WordMap wm) {
		this.wm = wm;
	}
	
	public ArrayList<Word> weightedConnect(String word1, String word2) {
		//if either of them is not a word, that's bad
		if(!wm.isWord(word1) || !wm.isWord(word2))
			throw new IllegalArgumentException();

		w1 = wm.get(word1.toLowerCase());
		w2 = wm.get(word2.toLowerCase());
		
		//if word1 is the same as word2: trivial!
		ArrayList<Word> path = new ArrayList<Word>();
		path.add(w1);
		if(w1.equals(w2))
			return path;
		
		Word currentWord = w1;
		ArrayList<Word> shortestPath = new ArrayList<Word>();
		shortestPath.add(w1);
		currentWord.setShortestPath(shortestPath);
		currentWord.setVisited(true);
		ArrayList<Word> tentativeWords = new ArrayList<Word>();
		
		/*
		 * Starting at current word, whose shortestPath is finalized, update
		 * all of its neighbors' tentative distances. Then go to list of tentative
		 * distances and find the word with the smallest tentative distance and
		 * set it as the new finalized current word. (Dijkstra's algorithm)
		 */
		while(!currentWord.equals(w2)) {
			//System.out.println(currentWord + ": " + currentWord.getNeighbors().toString());
			for(Word neighbor: currentWord.getNeighbors()) {
				//if it hasn't been visited, you have to update its tentative weight
				if(!neighbor.isVisited()) {
					if(!neighbor.isTentative()) {
						neighbor.setTentative(true);
						shortestPath = new ArrayList<Word>();
						shortestPath.addAll(currentWord.getShortestPath());
						shortestPath.add(neighbor);
						neighbor.setShortestPath(shortestPath);
						neighbor.setShortestPathWeight(currentWord.getShortestPathWeight() + neighbor.getWeight());
						tentativeWords.add(neighbor);
					} else {
						/*if it has been visited, check to see that this new path
						 *weight is smallest than the previous path. If so, change it
						 */
						int newShortestPathWeight = currentWord.getShortestPathWeight() + neighbor.getWeight();
						if(newShortestPathWeight < neighbor.getShortestPathWeight()) {
							shortestPath = new ArrayList<Word>();
							shortestPath.addAll(currentWord.getShortestPath());
							shortestPath.add(neighbor);
							neighbor.setShortestPath(shortestPath);
							neighbor.setShortestPathWeight(currentWord.getShortestPathWeight() + neighbor.getWeight());
						}
					}
				}
			}
			//System.out.println(tentativeWords.size());
			//if tentativeWords is empty, we're out of moves and there is no path!
			if(tentativeWords.isEmpty()) {
				wm.reset();
				return new ArrayList<Word>();
			}
			
			//find the closest word in the tentative word list. This is our new
			//finalized word/currentWord!
			Word closestWord = tentativeWords.get(0);
			
			for(int i = 1; i < tentativeWords.size(); i++) {
				if(tentativeWords.get(i).getShortestPathWeight() < closestWord.getShortestPathWeight()) {
					closestWord = tentativeWords.get(i);
				}
			}
			
			closestWord.setVisited(true);
			tentativeWords.remove(closestWord);
			currentWord = closestWord;
			//System.out.println("Final: " + currentWord + ": " + currentWord.getShortestPathWeight());
		}
		shortestPath = currentWord.getShortestPath();
		wm.reset();
		return shortestPath;
	}
	
	public ArrayList<Word> unweightedConnect(String word1, String word2) {
		if(!wm.isWord(word1) || !wm.isWord(word2))
			throw new IllegalArgumentException();
		
		w1 = wm.get(word1.toLowerCase());
		w2 = wm.get(word2.toLowerCase());
		
		ArrayList<Word> path = new ArrayList<Word>();
		path.add(w1);
		if(w1.equals(w2))
			return path;

		rounds1 = new ArrayList<ArrayList<Word>>();
		rounds2 = new ArrayList<ArrayList<Word>>(); 
		
		used1= new ArrayList<Word>();
		used1.add(w1);
		used2= new ArrayList<Word>();
		used2.add(w2);
		
		ArrayList<Word> first = new ArrayList<Word>();
		first.add(w1);
		rounds1.add(first);
		
		ArrayList<Word> last = new ArrayList<Word>();
		last.add(w2);
		rounds2.add(last);
		
		dist1 = 0;
		dist2 = 0;
		
		boolean isOverlap = false;
		Word connection = wm.get(wm.WORD_MAP.keySet().iterator().next());
		
		while(!isOverlap) {
			//if one side has a bunch more mapped out neighbors than the other side,
			//then only extend that side to make it catch up.
			if(used1.size() * 3 < used2.size()) {
				if(!extendSide1()) 	//the method returns boolean on whether it found something
					return new ArrayList<Word>();			//so stop the program if it got to a dead end.
			} else if(used2.size() * 3 < used1.size()) {
				if(!extendSide2())
					return new ArrayList<Word>();	
			} else {
				if(!extendSide1())
					return new ArrayList<Word>();
				if(!extendSide2())
					return new ArrayList<Word>();
			}
		
			for(Word word: used1) {
				if(used2.contains(word)) {
					connection = word;
					isOverlap = true;
					break;
				}
			}
		}
		
		//a connection ist gemacht worden!
		
		path = new ArrayList<Word>();
		path.add(connection);
		Word currentWord = connection;
		
		//find the neighbor of connection closest to w1 repeated till you get to w1
		//this makes sure it's the absolute smallest path
		
		while(!currentWord.equals(w1)) {
			for(int round = 0; round < dist1; round++) {
				int i = 0;
				while(i < rounds1.get(round).size() && 
						!rounds1.get(round).get(i).isNeighbor(currentWord)) {
					i++;
				}
				
				if(i < rounds1.get(round).size()) {
					currentWord = rounds1.get(round).get(i);
					path.add(0, currentWord);
					break;
				}
			}
		}
		
		currentWord = connection;
		
		while(!currentWord.equals(w2)) {
			for(int round = 0; round < dist2; round++) {
				int i = 0;
				while(i < rounds2.get(round).size() && 
						!rounds2.get(round).get(i).isNeighbor(currentWord)) {
					i++;
				}
				
				if(i < rounds2.get(round).size()) {
					currentWord = rounds2.get(round).get(i);
					path.add(currentWord);
					break;
				}
			}
		}
		
		return path;

	}
	
	private boolean extendSide1() {
		dist1++;
		ArrayList<Word> next1 = new ArrayList<Word>();
		
		for(Word word: rounds1.get(dist1 - 1)) {
			for(Word neighbor: word.getNeighbors()) {
				if(!used1.contains(neighbor)) {
					next1.add(neighbor);
					used1.add(neighbor);
				}
			}
		}
		
		if(next1.size() == 0) {
			System.out.println("\nRats! No connection possible.");
			System.out.println("\"" + w1 + "\" has too few neighbors: ");
			System.out.println(used1);
			System.out.println();
			return false;
		}
			
		rounds1.add(next1);
		//System.out.println("1: " + next1);
		return true;
	}
	
	private boolean extendSide2() {
		dist2++;
		ArrayList<Word> next2 = new ArrayList<Word>();
		
		for(Word word: rounds2.get(dist2 - 1)) {
			for(Word neighbor: word.getNeighbors()) {
				if(!used2.contains(neighbor)) {
					next2.add(neighbor);
					used2.add(neighbor);
				}
			}
		}
		
		if(next2.size() == 0) {
			System.out.println("\nRats! No connection possible.");
			System.out.println("\"" + w2 + "\" has too few neighbors: ");
			System.out.println(used2);
			System.out.println();
			return false;
		}
			
		rounds2.add(next2);
		//System.out.println("2: " + next2);
		return true;
	}
	
	public ArrayList<Word> pathToEasyWord(String word) {
		if(!wm.isWord(word))
			throw new IllegalArgumentException();
		
		w1 = wm.get(word.toLowerCase());
			
		ArrayList<Word> usedWords = new ArrayList<Word>();
		usedWords.add(w1);
		return pathToEasyWord(w1, usedWords);
	}
	
	private ArrayList<Word> pathToEasyWord(Word word, ArrayList<Word> usedWords) {
		ArrayList<Word> neighbors = word.getNeighbors();
		
		for(Word neighbor: neighbors)  {
			ArrayList<Word> easyPath = new ArrayList<Word>();
			easyPath.add(word);
			
			if(!usedWords.contains(neighbor)) {
				if(neighbor.toString().length() < 5) {
					//System.out.println(neighbor);
					easyPath.add(neighbor);
					return easyPath;
				} else if(neighbor.getNeighbors().size() > 1) { //bound to have one neighbor because it's a neighbor to the current word
					//System.out.println(neighbor);
					usedWords.add(neighbor);
					easyPath.addAll(pathToEasyWord(neighbor, usedWords));
					if(easyPath.size() > 1) //if it didn't get anywhere, it returns an empty list.
						return easyPath;
				}
			}
		}
		//System.out.println("Dead end");
		return new ArrayList<Word>();
	}	
	
	public static void main(String[] args) {
		WordConnecter wc = new WordConnecter(new WordMap("WordMapFile.txt"));
		Scanner s = new Scanner(System.in);
		while(true) {
			System.out.println("Word 1:");
			String start = s.nextLine();
			System.out.println("Word 2:");
			String goal = s.nextLine();
			try {
				System.out.println("Connection using fewest words possible:\t" + wc.unweightedConnect(start, goal));
				System.out.println("Weighted to prefer common words:\t" + wc.weightedConnect(start, goal));
				System.out.println();
			} catch(Exception e) {
				if(!wc.wm.isWord(start)) {
					System.out.println("Sorry, " +"\'" + start + "\' is not a word.");
				} else {
					System.out.println("Sorry, " + "\'" + goal + "\' is not a word.");
				}
				System.out.println("Try again!");
				System.out.println();
			}
		}
	}
}
