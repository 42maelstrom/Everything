import java.util.ArrayList;
import java.util.Arrays;


public class Feb5 {
	public static final String DESCRIPTION = "In each individual row, choose one"
			+ " letter from each box in left-to-right order to spell a five-letter"
			+ " occupation. Similarly, in each column, choose one letter from each"
			+ " box in top-to-bottom order to spell a different five-letter"
			+ " occupation. Cross out all the letters you have chosen. After you have"
			+ " crossed out all 10 occupations, the 25 remaining letters, when read"
			+ " left to right starting at the top, will spell out a proverb related"
			+ " to the theme.";
	
	public String[][] boxes;
	private String[] rowWords, colWords;
	private int rows, cols;
	//list of row/col indeces words placed are only hypothetical and not for sure solutions
	private ArrayList<Integer> hypoRowWords, hypoColWords;
	private boolean isFinalSolutionFound;
	private boolean gotStuck;
	public Dictionary dict;
	
	private ArrayList<String[]> possibleRowSolutions;
	private ArrayList<String[]> possibleColSolutions;
	
	public Feb5(String[][] boxes) {
		dict = new Dictionary(50);
		this.boxes = boxes;
		rows = boxes.length;
		cols = boxes[0].length;
		rowWords = new String[rows];
		colWords = new String[cols];
		hypoRowWords = new ArrayList<Integer>();
		hypoColWords = new ArrayList<Integer>();
		isFinalSolutionFound = false;
		gotStuck = false;
		
		possibleRowSolutions = new ArrayList<String[]>();
		possibleColSolutions = new ArrayList<String[]>();
		
	}
	
	public void solve() {
		solveRecursive();
		
		System.out.println("\n\n\n");
		System.out.println("hi");
		for(int i = 0; i < possibleRowSolutions.size(); i++) {
			String[] rowWords2 = possibleRowSolutions.get(i);
			String[] colWords2 = possibleColSolutions.get(i);
			
			String lettersLeft = "";
			for(int r = 0; r < rows; r++) {
				for(int c = 0; c < cols; c++) {
					char rChar = rowWords2[r].charAt(c);
					char cChar = colWords2[c].charAt(r);
					
					if(boxes[r][c].charAt(0) == boxes[r][c].charAt(1) ) {
						if(boxes[r][c].charAt(1) == boxes[r][c].charAt(2)) {
							//if all three equal each other, just add it.
							lettersLeft += boxes[r][c].charAt(0);
						} else {
							//if one equals another, then make sure the duplicate isn't the left
							if(rChar != cChar)
								lettersLeft += boxes[r][c].charAt(0);
						}
					} else if(boxes[r][c].charAt(0) == boxes[r][c].charAt(2)) {
						if(rChar != cChar)
							lettersLeft += boxes[r][c].charAt(0);
					} else if(boxes[r][c].charAt(1) == boxes[r][c].charAt(2)) {
						if(rChar != cChar)
							lettersLeft += boxes[r][c].charAt(1);
					} else {
			
						for(int j = 0; j < 3; j++) {
						char p = boxes[r][c].charAt(j);
						if(p != rChar && p != cChar)
							lettersLeft += p;
						}
					}
				}//end for each col
			}//end for each row
			
			ArrayList<String> words = makeWords(lettersLeft);
			
			if(words != null) {
				System.out.println("This is a possible solution: ");
				System.out.println(Arrays.toString(rowWords2));
				System.out.println(Arrays.toString(colWords2));

				System.out.println(lettersLeft + "=\n");
				for(String word: words) {
					System.out.print(word + " ");
				}
				System.out.println("\n");
				
			}
		}
	}
//for making words out of anything. This may come in handy.
	private ArrayList<String> makeWords(String s) {
		ArrayList<String> words = new ArrayList<String>();

		if(s.length() == 0) {
			return words;
		}
		
		int i = 1;
		while(i <= s.length()) {
			while(i <= s.length() && !dict.isWord(s.substring(0, i)))
				i++;
			
			if(i == s.length() + 1)
				return null;
			
			String word = s.substring(0, i);
			//System.out.println(word);
			ArrayList<String> restOfWords = makeWords(s.substring(i));
			if(restOfWords != null) {
				restOfWords.add(0, word);
				return restOfWords;
			}
			i++;
		}
		return null;
	}
	
	private void solveRecursive() {
		while(!isFinalSolutionFound) {
			
			doLogicalSolving();
			
			if(isFinalSolutionFound) {
				System.out.println("**possiblesolution**");
				System.out.println(Arrays.toString(rowWords));
				System.out.println(Arrays.toString(colWords));
				possibleRowSolutions.add(rowWords.clone());
				possibleColSolutions.add(colWords.clone());
				return;
			} else if(gotStuck) {
				return;
			} else {
				System.out.println("Nothing more can be determined for sure. Going into hypothethical options");
				//choices for a word that have the fewest possible options
				ArrayList<String> bestHypoOptions = new ArrayList<String>();
				boolean fromCol = false;
				int index = 0;
				
				for(int r = 0; r < rows; r++) {
					if(rowWords[r] == null) {
						ArrayList<String> words = getWordsFromRow(r);
						if(bestHypoOptions.isEmpty() 
								|| words.size() > 1 && words.size() < bestHypoOptions.size()) {
							bestHypoOptions = words;
							index = r;
						}
					}
				}
				
				for(int c = 0; c < cols; c++) {
					if(colWords[c] == null) {
						ArrayList<String> words = getWordsFromCol(c);
						if(bestHypoOptions.isEmpty() 
								|| words.size() > 1 && words.size() < bestHypoOptions.size()) {
							bestHypoOptions = words;
							index = c;
							fromCol = true;
						}
					}
				}
				
				System.out.println("Hypothetical options: " + bestHypoOptions.toString());
				
				//to go back to in case a hypothetical leads to wrong words.
				String[] rowWordsOriginal = rowWords.clone();
				String[] colWordsOriginal = colWords.clone();
				
				for(String potentialFit: bestHypoOptions) {
					rowWords = rowWordsOriginal;
					colWords = colWordsOriginal;
				
					System.out.println("Hypo choice: " + potentialFit);
					
					gotStuck = false;
					isFinalSolutionFound = false;
					
					if(fromCol) {
						colWords[index] = potentialFit;
					} else {
						rowWords[index] = potentialFit;
					}
					
					solveRecursive();
					//System.out.println("Here"); 
				} //end testing each hypothetical option. We know one of them had to work
				//so if neither of them do, there must be no solution.
				
				gotStuck = true;
				return;
			}
		}
	}
	
	private void doLogicalSolving() {
		boolean didChange = true;
		
		while(didChange) {
			didChange = false;
			System.out.println("\nnew round\n");
			
			isFinalSolutionFound = true;
			//rows
			for(int r = 0; r < rows; r++) {
				if(rowWords[r] == null) {
					//a word still hasn't been filled in, so we're not done.
					isFinalSolutionFound = false;
					
					ArrayList<String> pWords = getWordsFromRow(r);
					System.out.println("r" + r + pWords.toString());
					
					if(pWords.isEmpty()) {
						gotStuck = true;
						System.out.println("Got stuck");
						return;
					}
					
					if(pWords.size() == 1) {
						rowWords[r] = pWords.get(0);
						System.out.println("SUCCESS: R" + r + ": " + rowWords[r]);
						didChange = true;
					}
				}
			}
			
			//columns
			for(int c = 0; c < cols; c++) {
				if(colWords[c] == null) {
					//a word still hasn't been filled in, so we're not done.
					
					isFinalSolutionFound = false;
					ArrayList<String> pWords = getWordsFromCol(c);
					System.out.println("c" + c + pWords.toString());
					
					if(pWords.isEmpty()) {
						gotStuck = true;
						System.out.println("Got stuck");
						return;
					}
					
					if(pWords.size() == 1) {
						colWords[c] = pWords.get(0);
						System.out.println("SUCCESS: C" + c + ": " + colWords[c]);
						didChange = true;
					}
				}
			}
		}
		
	}
	
	private ArrayList<String> getWordsFromRow(int r) {
		ArrayList<String> bits = new ArrayList<String>(cols);
		for(int i = 0; i < cols; i++) {
			String toAdd = boxes[r][i];
			//if a word was already made going in the perpendicular direction,
			//don't let that letter be used
			if(colWords[i] != null) {
				int indexMatch = toAdd.indexOf(colWords[i].charAt(r));
				toAdd = toAdd.substring(0, indexMatch) + toAdd.substring(indexMatch + 1);
			}

			bits.add(toAdd);
		}
		
		System.out.println("r" + r + bits.toString());
		
		ArrayList<String> perms = getPossiblePerms(bits);
		ArrayList<String> words = new ArrayList<String>();
		
		for(String perm: perms) {
			if(dict.isWord(perm) && !words.contains(perm))
				words.add(perm);
		}
		
		return words;
	}
	
	private ArrayList<String> getWordsFromCol(int c) {
		ArrayList<String> bits = new ArrayList<String>(cols);
		for(int i = 0; i < rows; i++) {
			String toAdd = boxes[i][c];
			//if a word was already made going in the perpendicular direction,
			//don't let that letter be used
			if(rowWords[i] != null) {
				int indexMatch = toAdd.indexOf(rowWords[i].charAt(c));
				toAdd = toAdd.substring(0, indexMatch) + toAdd.substring(indexMatch + 1);
			}

			bits.add(toAdd);
		}
		
		System.out.println("c" + c + bits.toString());
		
		
		ArrayList<String> perms = getPossiblePerms(bits);
		ArrayList<String> words = new ArrayList<String>();
		
		for(String perm: perms) {
			if(dict.isWord(perm) && !words.contains(perm))
				words.add(perm);
		}
		
		return words;
	}
	
	private static ArrayList<String> getPossiblePerms(ArrayList<String> bits) {
		if(bits.size() == 1) {
			ArrayList<String> pFinalStrings = new ArrayList<String>();
			for(int i = 0; i < bits.get(0).length(); i++) {
				pFinalStrings.add(bits.get(0).substring(i, i+1));
			}
				
			return pFinalStrings;
		}
		
		ArrayList<String> pFinalStrings = new ArrayList<String>();
		String toTackOn = bits.remove(0);
		ArrayList<String> partlyMade = getPossiblePerms(bits);
		
		for(int i = 0; i < toTackOn.length(); i++) {
			for(String missingAC: partlyMade) {
				String completer = toTackOn.charAt(i) + missingAC;
				pFinalStrings.add(completer);
			}
		}
		
		return pFinalStrings;
	}
	
	public static void main(String[] args) {
		String[][] boxes = {{"NJA", "PBU", "ARG", "SRD", "MWE"},
												{"OUA", "CIR", "UKT", "MOA", "RAA"},
												{"NMD", "LBO", "ADL", "EBA", "MSL"},
												{"BGE", "SOA", "KHR", "BEI", "ROS"},
												{"CET", "TLO", "ODE", "IRL", "KNS"}};
		
		new Feb5(boxes).solve();
	}
}
