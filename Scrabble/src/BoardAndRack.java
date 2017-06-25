import java.util.ArrayList;


public class BoardAndRack {
	public Board board;
	public ArrayList<Tile> rack;
	
	public BoardAndRack(Board board, ArrayList<Tile> rack) {
		this.board = board;
		this.rack = rack;
	}
	
	public BoardAndRack(Board board) {
		this.board = board;
		this.rack = new ArrayList<Tile>();
	}
	
	public void print() {
		board.printBoard();
		String rackstr = "";
		System.out.print("Rack: ");
		for(Tile t: rack) 
			rackstr+=t.c;
		if(rackstr.length() == 0) {
			System.out.println("Empty rack");
		} else {
			System.out.println(rackstr);
		}
	}
	
}
