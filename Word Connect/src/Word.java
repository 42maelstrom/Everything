import java.util.ArrayList;


public class Word {
	private final String WORD;
	private final int WEIGHT;
	private boolean isVisited = false;
	private boolean isTentative = false;
	private ArrayList<Word> neighbors;
	private ArrayList<Word> shortestPath;
	private int shortestPathWeight = 0;
	
	public Word(String word, int weight) {
		this.WORD = word;
		this.WEIGHT = weight;
	}

	public void setNeighbors(ArrayList<Word> neighbors) {
		this.neighbors = neighbors;
	}

	public void setVisited(boolean isVisited) {                 
		this.isVisited = isVisited;                             
	}                                                           
	                                                            
	public void setTentative(boolean isTentative) {             
		this.isTentative = isTentative;                         
	}                                                           
	                                                            
	public void setShortestPath(ArrayList<Word> shortestPath) { 
		this.shortestPath = shortestPath;                       
	}                                                           
	                                                            
	public void setShortestPathWeight(int shortestPathWeight) { 
		this.shortestPathWeight = shortestPathWeight;           
	}                                                           
	
	public String toString() {
		return WORD;
	}
	
	public boolean equals(Word word) {
		return this.WORD.equals(word.WORD);
	}
	
	public boolean isNeighbor(Word word) {
		return neighbors.contains(word);
	}

	public int getWeight() {
		return WEIGHT;
	}

	public boolean isVisited() {
		return isVisited;
	}

	public boolean isTentative() {
		return isTentative;
	}

	public ArrayList<Word> getNeighbors() {
		return neighbors;
	}

	public ArrayList<Word> getShortestPath() {
		return shortestPath;
	}

	public int getShortestPathWeight() {
		return shortestPathWeight;
	}

}
