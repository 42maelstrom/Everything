import java.util.ArrayList;
import java.util.Random;

public class TileBag {
	private ArrayList<Tile> tiles;
	private Random rand;
	
	public TileBag(ArrayList<Tile> tiles) {
		this.tiles = tiles;
		rand = new Random();
	}
	
	public static TileBag generateStandardTileBag() {
		ArrayList<Tile> tiles = new ArrayList<Tile>(100);
		String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ*";
		int[] freq = {9, 2, 2, 4, 12, 2, 3, 2, 9, 1, 1, 4, 2, 6, 8, 2, 1, 6, 4, 6,
				4, 2, 2, 1, 2, 1, 2};
		for (int i = 0; i < 27; i++) {
			for (int j = 0; j < freq[i]; j++) {
				tiles.add(new Tile(alphabet.charAt(i)));
			}
		}
		return new TileBag(tiles);
	}
	
	public Tile remove() {
		int randIndex = rand.nextInt(tiles.size());
		return tiles.remove(randIndex);
	}
	
	public ArrayList<Tile> remove(int numOfTiles) {
		ArrayList<Tile> returns = new ArrayList<Tile>(numOfTiles);
		for(int i = 0; i < numOfTiles; i++) {
			returns.add(remove());
		}
		
		return returns;
	}
	
	public boolean isEmpty() {
		return tiles.isEmpty();
	}
	
	public void add(Tile t) {
		tiles.add(t);
	}
	
	public void addAll(ArrayList<Tile> toAdd) {
		tiles.addAll(toAdd);
	}
	
	public int size() {
		return tiles.size();
	}
}
