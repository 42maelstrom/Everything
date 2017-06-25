/**
 * Represents the movement of a tile on the board.
 * Used for animating the motion of the tiles.
 */
public class Movement {
	public Tile tile;
	final boolean mergesAtEnd;
	final Position finalPos;
	final double xMotion;
	final double yMotion;
	final double dX, dY;
	final int finalVal;

	public Movement(Tile tile, boolean mergesAtEnd, Position finalPos, int finalVal) {
		this.tile = tile;
		this.mergesAtEnd = mergesAtEnd;
		this.finalPos = finalPos;
		this.finalVal = finalVal;
		xMotion = finalPos.c - tile.xPos;
		yMotion = finalPos.r - tile.yPos;
		dX = xMotion / (double)Gui2048.MoveAnimation.NUM_OF_SLIDING_FRAMES;
		dY = yMotion / (double)Gui2048.MoveAnimation.NUM_OF_SLIDING_FRAMES;
	}
	
	public void shiftTileOneFrame() {
		tile.xPos += dX;
		tile.yPos += dY;
	}
	
}
