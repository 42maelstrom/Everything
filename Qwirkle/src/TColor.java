import java.awt.Color;


public enum TColor {
	RED(Color.RED), ORANGE(Color.ORANGE), YELLOW(Color.YELLOW), GREEN(Color.GREEN), BLUE(Color.BLUE), PURPLE(new Color(130, 50, 235));
	
	public Color color;
	
	private TColor(Color color) {
		this.color = color;
	}
	
	public Color getColor() {
		return color;
	}
}
