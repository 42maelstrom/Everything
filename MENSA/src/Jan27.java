import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Jan27 {
	public static final String NAME = "JOHNSON & JOHNSON & JOHNSON";
	public static final String DESCRIPTION = "Many famous people have the last "
			+ "name Johnson. In each row below, we've interwoven (without scrambling)"
			+ " the first names of three famous Johnsons so that each name still"
			+ " reads from left to right. Can you identify all nine Johnsons,"
			+ " three per row?";
	
	private static final ArrayList<String> NAMES = getNames("Names.txt");
	public final String[] puzzles;
	
	public Jan27(String... puzzles) {
		this.puzzles = puzzles;
	}
	
	private static ArrayList<String> getNames(String fileName) {
		ArrayList<String> dict = new ArrayList<String>();
		BufferedReader br;
		
		try{
			br = new BufferedReader(new FileReader(fileName)); 
			String line;
			while((line = br.readLine() ) != null) {
				dict.add(line.toUpperCase());
			}
			br.close();
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return dict;
	}
	
	public void solve() {
		for(int pNum = 0; pNum < puzzles.length; pNum++) {
			System.out.print(pNum + ". ");
			ArrayList<String> solution = getSolution(puzzles[pNum]);
			if(solution == null)
				System.out.print("Could not find solution.");
			System.out.println();
		}
	}
	
	private ArrayList<String> getSolution(String puzzle) {
		HashMap<String, ArrayList<ArrayList<Integer>>> possibleNames = new HashMap<String, ArrayList<ArrayList<Integer>>>();
	
		outer:
		for(String name: NAMES) {
			ArrayList<ArrayList<Integer>> usedIndeces = getPossibleArrangements(name, puzzle);
			if(usedIndeces.size() != 0)
				possibleNames.put(name, usedIndeces);
		}
		
		//now we have a list of names that could be interwoven. Let's find a trio
		//that doesn't intersect with each other.
		
		name1:
		for(String name1: possibleNames.keySet()) {
			name2:
			for(String name2: possibleNames.keySet()) {
				if(name1.equals(name2))
					continue name2;
				
				ArrayList<ArrayList<Integer>> possibleIndexPairs = getPossibleIndexPairs(possibleNames.get(name1), possibleNames.get(name2));
				if(possibleIndexPairs.isEmpty())
					continue name2;
				
				String name3 = "";
				for(ArrayList<Integer> indeces: possibleIndexPairs) {
					for(int i = 0; i < puzzle.length(); i++) {
						if(!indeces.contains(i))
							name3+=puzzle.charAt(i);
					}
					
					if(NAMES.contains(name3)) {
						System.out.println(name1 + " " + name2 + " " + name3);
						ArrayList<String> solution = new ArrayList<String>(Arrays.asList(name1, name2, name3));
						return solution;
					}
				}
			}
		}
		
		return null;
	}
	
	/**
	 * Returns a list of indeces that are all unique representing the ways the two
	 * lists of indeces can be mashed without intersecting indeces from each list
	 */
	public static ArrayList<ArrayList<Integer>> getPossibleIndexPairs(ArrayList<ArrayList<Integer>> l1, ArrayList<ArrayList<Integer>> l2) {
		ArrayList<ArrayList<Integer>> possibleIndexPairs = new ArrayList<ArrayList<Integer>>();
		
		for(ArrayList<Integer> indeces1: l1) {
			indeces2:
			for(ArrayList<Integer> indeces2: l2) {
				for(int index: indeces1) {
					if(indeces2.contains(index))
						continue indeces2;
				}
				//now we know that the two don't intersect.
				ArrayList<Integer> possiblePair = new ArrayList<Integer>();
				possiblePair.addAll(indeces1);
				possiblePair.addAll(indeces2);
				possibleIndexPairs.add(possiblePair);
			}
		}
		
		return possibleIndexPairs;
	}
	
	private static ArrayList<ArrayList<Integer>> getPossibleArrangements(String name, String puzzle) {
		ArrayList<ArrayList<Integer>> indeces = new ArrayList<ArrayList<Integer>>();
		
		for(int i = 0; i < name.length(); i++) {
			indeces.add(new ArrayList<Integer>());
			int lastIndex = -1;
			do {
				lastIndex = puzzle.indexOf(name.charAt(i), lastIndex + 1);
				if(lastIndex != -1)
					indeces.get(i).add(lastIndex);
			} while(lastIndex != -1);
		}
		
		ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
		for(int index: indeces.get(0)) {
			ArrayList<Integer> newList = new ArrayList<Integer>();
			newList.add(index);
			result.add(newList);
		}
		
		for(int i = 1; i < name.length(); i++) {
			ArrayList<ArrayList<Integer>> newResult = new ArrayList<ArrayList<Integer>>();
			for(ArrayList<Integer> wIndeces: result) {
				int lastIndex = wIndeces.get(wIndeces.size() - 1);
				//if there is a way to put the next ones on, do them. Otherwise remove them.
				for(int nextIndex: indeces.get(i)) {
					if(nextIndex > lastIndex) {
						ArrayList<Integer> newList = new ArrayList<Integer>();
						newList.addAll(wIndeces);
						newList.add(nextIndex);
						newResult.add(newList);
					}
				}//end for each possible next index
			}//end for each previous arrangement of indeces for the previous letters
			
			result = newResult;
		}
		
		return result;
	}
	
	
	public static void main(String[] args) {
		new Jan27("SAANMDUDEWARLYNEEW", "VIMARHOGGIIWNCIAARD", "PLYHINBLDOIETPSENY").solve();
	}
}
