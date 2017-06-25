import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JPanel;

public class BoardPanel extends JPanel {
	private Board board;
	
	private static final Color BACKGROUND_TILE_COLOR = new Color(194, 178, 164);
	private static final Color BOARD_OUTLINE_COLOR = new Color(173, 157, 143);
	
	private static final int TILE_WIDTH = 107;
	private static final int OUTLINE_WIDTH = 15;
	public static final int BOARD_WIDTH = 5*OUTLINE_WIDTH + 4*TILE_WIDTH;
	
	public BoardPanel(Board board) {
		super();
		this.board = board;
		setLocation(0,0);
		setSize(BOARD_WIDTH, BOARD_WIDTH);
	}
	
	public int getXPixelPos(double xPos, double size) {
		return (int) ((xPos + 1)*OUTLINE_WIDTH + xPos*TILE_WIDTH + (TILE_WIDTH - getTilePixelWidth(size)) / 2);
	}
	
	public int getYPixelPos(double yPos, double size) {
		return (int) ((yPos + 1)*OUTLINE_WIDTH + yPos*TILE_WIDTH + (TILE_WIDTH - getTilePixelWidth(size)) / 2);
	}
	
	public int getTilePixelWidth(double size) {
		return (int) (TILE_WIDTH*size);
	}

	public Color getColorForTile(int value) {
		switch(value) {
			case 0:		return BACKGROUND_TILE_COLOR;    
			case 2:		return new Color(233, 221, 209); 
			case 4:		return new Color(235, 218, 188); 
			case 8:		return new Color(237, 162, 102); 
			case 16:	return new Color(240, 129, 80);  
			case 32: 	return new Color(250, 119, 90);  
			case 64: 	return new Color(247, 81, 49);   
			case 128: 	return new Color(239, 198, 95);  
			case 256: 	return new Color(239, 195, 79);  
			case 512: 	return new Color(239, 183, 65);  
			case 1024: 	return new Color(244, 191, 65);  
			case 2048: 	return new Color(244, 188, 49);  
			default: 	return new Color(59 , 58, 51);   
		}
	}
	
	public int getFontSize(Tile tile) {
		int fontSize;
		
		if(tile.value < 99) {
			fontSize = 55;
		} else if(tile.value < 999) {
			fontSize = 45;
		} else if(tile.value < 9999) {
			fontSize = 31;
		} else if(tile.value < 99999) {
			fontSize = 28;
		} else {
			fontSize = 23;
		}
		
		return (int)(fontSize*tile.size);
	}
	
	private void drawTile(Graphics g, Tile tile) {
		int xPos = getXPixelPos(tile.xPos, tile.size);
		int yPos = getYPixelPos(tile.yPos, tile.size);
		int tilePixelWidth = getTilePixelWidth(tile.size);
		
		g.setColor(getColorForTile(tile.value));
		g.fillRoundRect(xPos, yPos, tilePixelWidth, tilePixelWidth, 6, 6);
		
		//for drawing the tile value in the square.
		int fontSize = getFontSize(tile);
		
		g.setFont(new Font("ClearSans-Bold", Font.BOLD, fontSize));	
		
		//color of the number when drawn.
		if(tile.value < 8) {
			g.setColor(new Color(119, 110, 101));
		} else {
			g.setColor(Color.WHITE);
		}
		
		String num = Integer.toString(tile.value);
		
		int sWidth = g.getFontMetrics().stringWidth(num);
		int	sHeight = (int) g.getFont().createGlyphVector(
				g.getFontMetrics().getFontRenderContext(),
				num).getVisualBounds().getHeight();
		
		g.drawString(num, xPos + tilePixelWidth / 2 - sWidth / 2,
				yPos + tilePixelWidth / 2 + sHeight / 2 + 1);
	}
	
	private void drawBackground(Graphics g) {
		g.setColor(BOARD_OUTLINE_COLOR);
		g.fillRoundRect(0, 0, BOARD_WIDTH, BOARD_WIDTH, 14, 14);
		
		for(int r = 0; r < 4; r++) {
			for(int c = 0; c < 4; c++) {
				int xPos = getXPixelPos(c, 1.0);
				int yPos = getYPixelPos(r, 1.0);
	
				g.setColor(BACKGROUND_TILE_COLOR);
				g.fillRoundRect(xPos, yPos, TILE_WIDTH, TILE_WIDTH, 6, 6);
			}
		}
	}
	
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
							 RenderingHints.VALUE_ANTIALIAS_ON);
		drawBackground(g2d);
		
		ArrayList<Tile> tileList = board.getTileList();
		for(int i = 0; i < tileList.size(); i++) {
			drawTile(g2d, tileList.get(i));
		}
	}
}
