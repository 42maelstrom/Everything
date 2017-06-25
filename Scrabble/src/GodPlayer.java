import java.util.Scanner;

public class GodPlayer extends Player {
	private Scanner s;
	
	public GodPlayer(String name) {
		super(name);
		s = new Scanner(System.in);
	}
	
	public Move makeMove(BoardAndRack bar) {
		System.out.println("Input word including tiles on board, ex. \"DOG\"");
		String word = getNextLine();
		
		System.out.println("Input starting position (leftmost or topmost) including tiles on board ex. \"(1,3)\"");
		Position p = new Position(getNextLine());
		
		System.out.println("Is horizontal move? Y/N ex. \"Y\"");
		boolean isHorizontal = s.nextLine().equals("Y");
		
		Move m = bar.board.convertToMove(new PossibleMove(
				word, p.x, p.y,isHorizontal));
		System.out.println(m.toString());
		return m;
	}
	
	private String getNextLine() {
		return s.nextLine();
	}
}
