import java.util.ArrayList;
import java.util.Collections;

public class QwirkleGame {

	private ArrayList<Tile> tileBag;
	private Board board;
	
	public QwirkleGame(int numberOfPlayers) {
		tileBag = new ArrayList<Tile>(108);
		for(TColor color: TColor.values()) {
			for(TShape shape: TShape.values()) {
				for(int i = 0; i < 3; i++)
					tileBag.add(new Tile(color, shape));
			}
		}
		Collections.shuffle(tileBag);
		board = new Board();
	}
	
	public Board getBoard() {
		return board;
	}
	
	public int tilesLeft()
	{
		return tileBag.size();
	}
	
	public Tile getTileFromBag()
	{
		return tileBag.remove(0);
	}
}
