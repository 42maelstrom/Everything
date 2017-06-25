import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class ScrabbleGame {
	public final ArrayList<Player> players;
	public final int numOfPlayers;
	public Board board;
	public TileBag tileBag;
	public final int tilesPerRack = 7;
	public ArrayList<ArrayList<Tile>> racks;
	public ArrayList<Integer> scores;
	public int numOfPassesInARow;
	public static final Trie TRIE = Trie.makeTrie("twl.txt"); 
	public static boolean print = true;
	static int totalScores = 0;
	static int numGames = 0;
	private String playOutput;
	
	public ScrabbleGame(Player... players) {
		this.players = new ArrayList<Player>();
		for(Player p : players) {
			this.players.add(p);
		}
		
		numOfPlayers = this.players.size();
		this.board = Board.generateStandardBoard();
		this.tileBag = TileBag.generateStandardTileBag();
	}
	
	public ScrabbleGame(ArrayList<Player> players, Board b, TileBag tileBag) {
		this.players = players;
		this.board = b;
		this.tileBag = tileBag;
		numOfPlayers = players.size();
	}
	
	public void playGame() {
		scores = new ArrayList<Integer>(numOfPlayers);
		racks = new ArrayList<ArrayList<Tile>>(numOfPlayers);
		for(int i = 0; i < numOfPlayers; i++) {
			scores.add(0);
			ArrayList<Tile> l = tileBag.remove(tilesPerRack);
			racks.add(l);
		}
		
		numOfPassesInARow = 0;
		
		while(isGameOver() == false) {
			Move m;
			
			for(int playerNum = 1; playerNum <= numOfPlayers; playerNum++) {
				BoardAndRack bar = new BoardAndRack(board, racks.get(playerNum - 1));
				Player p = players.get(playerNum - 1);
				m = p.makeMove(bar);
//				System.out.println(m.toString());

				while (!isValidMove(m)) {
					print = true;
					System.out.println("Invalid move. Try again");
					System.out.println(m.toString());
					new Scanner(System.in).nextLine();
					m = p.makeMove(bar);
					System.out.println(m.toString());
				}
	
				processMove(m, playerNum);
			
				if(isGameOver())
					break;
			}
		}

		endOfGame();
	}
	
	public boolean isValidMove(Move m) {
		if(m.isPass())
			return true;
		if(m.isExchange()) {
			return tileBag.size() >= tilesPerRack;
		} else {
			return board.isValidMove(m);
		}
	}
	
	public void processMove(Move m, int playerNum) {
		//System.out.println(m.toString());
		playOutput = players.get(playerNum - 1).getName();
		if (m.isPass()){
			playOutput += " passed";
			numOfPassesInARow++;
		} else if (m.isExchange()) {
			playOutput += " exchanged " + m.size() + " tiles";
			
			ArrayList<Tile> rack = racks.get(playerNum - 1);
			for(int i = 0; i < m.size(); i++) {
				rack.add(tileBag.remove());
				rack.remove(m.getTiles().get(i));
				tileBag.add(m.getTiles().get(i));
				if(tileBag.isEmpty())
					break;
			}
			
		} else {
			int score = board.score(m);
			PossibleMove pm = board.convertToPossibleMove(m);
			//System.out.println(pm.s);
			playOutput+= " played " + pm.s 
					+ " for " + score + " point";
			if(score > 1)
				playOutput += "s";
			
			scores.set(playerNum - 1, scores.get(playerNum - 1) + score);
			ArrayList<Tile> rack = racks.get(playerNum - 1);
			for(int i = 0; i < m.size(); i++) {
				if(tileBag.isEmpty() == false)
					rack.add(tileBag.remove());
				rack.remove(m.getTiles().get(i));
			}
			board.addMove(m);
		}
		if(print)
			printStuff(playerNum);
	}
	
	private void printStuff(int playerNum) {
		System.out.println(playOutput);
		printScores();
		playerNum++;
		if(playerNum == numOfPlayers + 1)
			playerNum = 1;
		BoardAndRack bar = new BoardAndRack(board, racks.get(playerNum - 1));
		Player p = players.get(playerNum - 1);
		bar.print();
		System.out.println(p.getName() + "'s turn");
	}
	
	private void printScores() {
		for(int i = 0; i < numOfPlayers; i++) {
			System.out.println(players.get(i).name + ": " + scores.get(i));
		}
	}
	
	public boolean isGameOver() {
		if(numOfPassesInARow == 6)
			return true;
		for(ArrayList<Tile> rack: racks) {
			if(rack.isEmpty())
				return true;
		}
		return false;
	}
	
	public void endOfGame() {
		System.out.println();
		System.out.println("Game over");
		
		if(numOfPassesInARow < 6) {
			int lastPlayerNum = 1;
			while(racks.get(lastPlayerNum - 1).isEmpty() == false) {
				lastPlayerNum++;
			}
			
			for(int playerNum = 1; playerNum <= numOfPlayers; playerNum++) {
				if(playerNum != lastPlayerNum) {
					int sumOfRack = 0;
					for(Tile t: racks.get(playerNum - 1)) {
						sumOfRack+=t.value;
					}
					
					scores.set(lastPlayerNum - 1, scores.get(lastPlayerNum - 1) + sumOfRack);
					scores.set(playerNum - 1, scores.get(playerNum - 1) - sumOfRack);
				}
			}
		}
		
		ArrayList<Integer> playerWinners = new ArrayList<Integer>();
		playerWinners.add(1);
		
		int maxScore = scores.get(0);
		for(int playerNum = 2; playerNum <= numOfPlayers; playerNum++) {
			if(scores.get(playerNum - 1) > maxScore) {
				playerWinners.clear();
				playerWinners.add(playerNum);
				maxScore = scores.get(playerNum - 1);
			} else if(scores.get(playerNum - 1) == maxScore) {
				playerWinners.add(playerNum);
			}
		}
		
		System.out.println("Final scores: ");
		for(int playerNum = 1; playerNum <= numOfPlayers; playerNum++) {
			System.out.print(players.get(playerNum - 1).getName() + ": " + scores.get(playerNum - 1));
			totalScores+=scores.get(playerNum - 1);
			numGames++;
			System.out.println();
		}
		
		String playOutput = "Winner: ";
		if(playerWinners.size() > 1)
			playOutput = "Tie between ";
		
		for(int i = 1; i <= numOfPlayers; i++) {
			if(playerWinners.contains(i))
				playOutput += players.get(i - 1).name + ", ";
		}
		playOutput = playOutput.substring(0, playOutput.length() - 2);
		System.out.println(playOutput);
	}

	public static void main(String[] args) {
		
		int numGames = 100;
		int i = 1;
		while(i <= numGames) {
			System.out.println("Game " + i);
			new ScrabbleGame(new ScrabbleBot("Computer"), new ScrabbleBot("Computer")).playGame();
			i++;
			System.out.println(ScrabbleGame.totalScores / ScrabbleGame.numGames);
		}
		
	}
}
