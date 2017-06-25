import java.awt.Color;


public class Tile {
	private final TColor color;
	private final TShape shape;
	
	public static final Tile RED_SQUARE = new Tile(TColor.RED, TShape.SQUARE);
	public static final Tile RED_CIRCLE = new Tile(TColor.RED, TShape.CIRCLE);
	public static final Tile RED_TRIANGLE = new Tile(TColor.RED, TShape.TRIANGLE);
	public static final Tile RED_CLOVER = new Tile(TColor.RED, TShape.CLOVER);
	public static final Tile RED_STAR_4 = new Tile(TColor.RED, TShape.STAR_4);
	public static final Tile RED_STAR_8 = new Tile(TColor.RED, TShape.STAR_8);
	
	public static final Tile ORANGE_SQUARE = new Tile(TColor.ORANGE, TShape.SQUARE);
	public static final Tile ORANGE_CIRCLE = new Tile(TColor.ORANGE, TShape.CIRCLE);
	public static final Tile ORANGE_TRIANGLE = new Tile(TColor.ORANGE, TShape.TRIANGLE);
	public static final Tile ORANGE_CLOVER = new Tile(TColor.ORANGE, TShape.CLOVER);
	public static final Tile ORANGE_STAR_4 = new Tile(TColor.ORANGE, TShape.STAR_4);
	public static final Tile ORANGE_STAR_8 = new Tile(TColor.ORANGE, TShape.STAR_8);
	
	public static final Tile YELLOW_SQUARE = new Tile(TColor.YELLOW, TShape.SQUARE);
	public static final Tile YELLOW_CIRCLE = new Tile(TColor.YELLOW, TShape.CIRCLE);
	public static final Tile YELLOW_TRIANGLE = new Tile(TColor.YELLOW, TShape.TRIANGLE);
	public static final Tile YELLOW_CLOVER = new Tile(TColor.YELLOW, TShape.CLOVER);
	public static final Tile YELLOW_STAR_4 = new Tile(TColor.YELLOW, TShape.STAR_4);
	public static final Tile YELLOW_STAR_8 = new Tile(TColor.YELLOW, TShape.STAR_8);
	
	public static final Tile GREEN_SQUARE = new Tile(TColor.GREEN, TShape.SQUARE);
	public static final Tile GREEN_CIRCLE = new Tile(TColor.GREEN, TShape.CIRCLE);
	public static final Tile GREEN_TRIANGLE = new Tile(TColor.GREEN, TShape.TRIANGLE);
	public static final Tile GREEN_CLOVER = new Tile(TColor.GREEN, TShape.CLOVER);
	public static final Tile GREEN_STAR_4 = new Tile(TColor.GREEN, TShape.STAR_4);
	public static final Tile GREEN_STAR_8 = new Tile(TColor.GREEN, TShape.STAR_8);
	
	public static final Tile BLUE_SQUARE = new Tile(TColor.BLUE, TShape.SQUARE);
	public static final Tile BLUE_CIRCLE = new Tile(TColor.BLUE, TShape.CIRCLE);
	public static final Tile BLUE_TRIANGLE = new Tile(TColor.BLUE, TShape.TRIANGLE);
	public static final Tile BLUE_CLOVER = new Tile(TColor.BLUE, TShape.CLOVER);
	public static final Tile BLUE_STAR_4 = new Tile(TColor.BLUE, TShape.STAR_4);
	public static final Tile BLUE_STAR_8 = new Tile(TColor.BLUE, TShape.STAR_8);
	
	public static final Tile PURPLE_SQUARE = new Tile(TColor.PURPLE, TShape.SQUARE);
	public static final Tile PURPLE_CIRCLE = new Tile(TColor.PURPLE, TShape.CIRCLE);
	public static final Tile PURPLE_TRIANGLE = new Tile(TColor.PURPLE, TShape.TRIANGLE);
	public static final Tile PURPLE_CLOVER = new Tile(TColor.PURPLE, TShape.CLOVER);
	public static final Tile PURPLE_STAR_4 = new Tile(TColor.PURPLE, TShape.STAR_4);
	public static final Tile PURPLE_STAR_8 = new Tile(TColor.PURPLE, TShape.STAR_8);

	public Tile(TColor color, TShape shape)
	{
		this.color = color;
		this.shape = shape;
	}
	
	public TColor getTColor()
	{
		return color;
	}
	
	public Color getColor() {
		return color.getColor();
	}
	
	public TShape getShape()
	{
		return shape;
	}
	
	public String toString()
	{
		String color;
		if(this.color == TColor.RED){
			color = "Red";
		}else if(this.color == TColor.ORANGE) {
			color = "Orange";
		}else if(this.color == TColor.YELLOW) {
			color = "Yellow";
		}else if(this.color == TColor.GREEN) {
			color = "Green";
		}else if(this.color == TColor.BLUE) {
			color = "Blue";
		}else{
			color = "Purple";
		}
		String shape;
		if(this.shape == TShape.SQUARE) {
			shape = "Square";
		}else if(this.shape == TShape.CIRCLE) {
			shape = "Circle";
		}else if(this.shape == TShape.TRIANGLE) {
			shape = "Triangle";
		}else if(this.shape == TShape.CLOVER) {
			shape = "Clover";
		}else if(this.shape == TShape.STAR_4) {
			shape = "Four-pointed star";
		}else{
			shape = "Eight-pointed star";
		}
		return color + " " + shape;
	}
	
}