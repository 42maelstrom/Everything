import java.awt.Color;
import java.awt.Graphics;

import javax.swing.*;


public class BoardPanel extends JPanel {
	private Board board;
	private static final double scale = 100;
	
	public BoardPanel(Board board) {
		this.board = board;
		this.setSize(500,500);
	}
	
	public void paintComponent(Graphics g) {
		drawTile(Tile.RED_CIRCLE, 100, 100, g);
	}
	
	/**
	 * Draws the tile in the given location
	 * @param tile - The tile to be drawn
	 * @param xPos - The x coordinate of the upper left corner of the tile
	 * @param yPos - the y coordinate of the upper left corner of the tile
	 */
	public static void drawTile(Tile tile, int xPos, int yPos, Graphics g) {
	
		g.setColor(Color.BLACK);
		g.fillRect(xPos, yPos, (int)scale, (int)scale);
		
		Color c = tile.getColor();
		g.setColor(c);
		
		if(tile.getShape().equals(TShape.CIRCLE)) {
			double radius = scale/2.25;
			int xCenter = xPos + scale / 2, yCenter = (int)(yPos + scale/2)
			g.fillOval((int)(xPos + scale/10), (int)(yPos + scale/10), (int)(scale), (int)(scale));
		}
		
		if(tile.getShape().equals(TShape.CLOVER)
			
	}
	
	public static void main(String[] args) {
		JFrame frm = new JFrame();
		frm.setContentPane(new BoardPanel(new Board()));
		frm.setDefaultCloseOperation(frm.EXIT_ON_CLOSE);
		frm.setSize(frm.getContentPane().getSize());
		frm.setVisible(true);
	}
}
