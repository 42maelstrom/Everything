import java.util.ArrayList;

public class Move {
		private final ArrayList<Tile> tiles;
		private final ArrayList<Position> positions;
		private boolean isExchange;
		private boolean isPass;
		private int size;
		
		public Move(ArrayList<Tile> tiles, ArrayList<Position> positions) {
			this.tiles = tiles;
			this.positions = positions;
			size = tiles.size();
			isExchange = false;
			isPass = false;
		} 
		
		/**
		 * A pass
		 */
		public Move() {
			tiles = null;
			positions = null;
			isExchange = false;
			isPass = true;
		}
		
		/**
		 * An exchange
		 */
		public Move(ArrayList<Tile> tiles) {
			this.tiles = tiles;
			positions = new ArrayList<Position>();
			isExchange = true;
			isPass = false;
		}
		
		public ArrayList<Tile> getTiles() {
			return tiles;
		}
		
		public ArrayList<Position> getPositions() {
			return positions;
		}
		
		public boolean isExchange() {
			return isExchange;
		}
		
		public boolean isPass() {
			return isPass;
		}
		
		public int size() {
			return size;
		}
		
		public String toString() {
			return tiles.toString() + " at " + positions.toString();
		}
	}