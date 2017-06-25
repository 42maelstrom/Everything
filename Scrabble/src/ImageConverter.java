import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class ImageConverter {
	
	public static BoardAndRack convertImageFromFile(String filePath)
			throws IOException {
		return convertImage(loadImage(filePath));
	}

	public static BoardAndRack convertImage(BufferedImage img) {
		ArrayList<Tile> rack = convertRack(img);
		Board board = convertBoard(img);
		
		return new BoardAndRack(board, rack);
	}

	private static ArrayList<Tile> convertRack(BufferedImage img) {
		ArrayList<Tile> rack = new ArrayList<Tile>(7);
		img = img.getSubimage(12, 940, img.getWidth() - 15, 90);
		
		int dY = 10;
		int dX = 0;

		ArrayList<BufferedImage> tiles = new ArrayList<BufferedImage>();
		
		while (dX < img.getWidth()) {
			while (dX < img.getWidth() 
					&& new Color(img.getRGB(dX, dY)).getRed() < 215) {
				dX++;
			}
			
			if (dX < img.getWidth() == true) {
				tiles.add(img.getSubimage(dX, 0, 82, 82));
			}
			
			while (dX < img.getWidth() 
					&& new Color(img.getRGB(dX, dY)).getRed() >= 215) {
				dX++;
			}
		}

		for(BufferedImage tile: tiles) {
			rack.add(new Tile(findMatch(tile)));
		}
		
		return rack;
	}
	
	private static Board convertBoard(BufferedImage img) {
		img = img.getSubimage(10, 263, img.getWidth() - 10, img.getHeight() - 484);
		
		Board b = Board.generateStandardBoard();
		
		int[] xStarts = {2, 43, 85, 126, 167, 209, 250, 291, 332, 374, 415, 456,
				             498, 539, 580};
		int[] yStarts = xStarts;
	
		BufferedImage tile;
		int avgRed, avgGreen, avgBlue;
		Color c;
		final int NUM_PIXELS = 112;
		int blankCount;
		boolean isBlank;
		
		for(int y = 0; y < 15; y++) {
			for(int x = 0; x < 15; x++) {
				blankCount = 0;
				tile = img.getSubimage(xStarts[x], yStarts[y], 38, 38);
				avgRed = 0; avgGreen = 0; avgBlue = 0;
	
				for(int dX = 30; dX <= 36; dX++) {
					for(int dY = 6; dY <= 21; dY++) {
						c = new Color(tile.getRGB(dX, dY));
						avgRed+=c.getRed();
						avgGreen+=c.getGreen();
						avgBlue+=c.getBlue();
					}
				}
				
				avgRed/=NUM_PIXELS;
				avgGreen/=NUM_PIXELS;
				avgBlue/=NUM_PIXELS;
				
				//if this is a tile!
						//for normal colored tiles
				if((239 < avgRed && 242 > avgRed 
						&& 207 < avgGreen && 215 > avgGreen
						&& 164 < avgBlue &&  173 > avgBlue)
						//for yellow colored tiles (were just played)
						|| (246 < avgRed && 250 > avgRed 
						&& 222 < avgGreen && 228 > avgGreen 
						&& 140 < avgBlue && 150 > avgBlue)) {
					int black = new Color(0, 0, 0).getRGB();
					
					dX: //loop to find and erase red pixels
					for(int dX = 2; dX < tile.getWidth(); dX++) {
						for(int dY = 2; dY < tile.getHeight() - 4; dY++) {
							c = new Color(tile.getRGB(dX, dY));
							if(c.getRed() == c.getGreen() && c.getGreen() == c.getBlue()
									&& c.getRed() < 80)
								break dX;
							
							if(c.getGreen() < 50 && c.getRed() > 150) {//red letters = blank tile probs
								tile.setRGB(dX, dY, black);
								blankCount++;
							}
						}//end dY
					}//end dX
					isBlank = blankCount > 10;
					b.addTile(x, y, new Tile(findMatch(tile), isBlank));
				}//end if tile
			} // end for x
		}//end for y
		
		return b;
	}

	private static char findMatch(BufferedImage tile) {
		boolean isBigLetter = false;
		if(tile.getWidth() > 70)
			isBigLetter = true;
		
		String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		if(isBigLetter)
			alphabet+="*";
		
		int misses;
		int minMisses = Integer.MAX_VALUE;
		boolean tileHit, testHit;
		Color tileC, testC;
		BufferedImage test = null;
		int bestIndex = 0;
		charactersearch:
		for(int i = 0; i < alphabet.length(); i++) {
			String letter = alphabet.substring(i, i+1);
			try {
				if(isBigLetter) {
					test = ImageIO.read(new File("BigLetters/" + letter + ".png"));
				} else {
					test = ImageIO.read(new File("Letters/" + letter + ".png"));
				}
			} catch (IOException e) {
				System.out.println("Could not read file");
				System.exit(0);
			}
			
			misses = 0;
			for(int dX = 0; dX < tile.getWidth() - 5; dX++) {
				for(int dY = 0; dY < tile.getWidth() - 5; dY++) {
					tileC = new Color(tile.getRGB(dX, dY));
					testC = new Color(test.getRGB(dX, dY));
					tileHit = tileC.getRed() < 100;
					testHit = testC.getRed() < 100;
					
					if(tileHit ^ testHit) {
						misses++;
						if(misses > minMisses)
							continue charactersearch;
					}
				}// dY
			}// dX
			
			//made it, so misses must be <= min
			minMisses = misses;
			bestIndex = i;
		}
		
		if(isBigLetter) {
			return alphabet.charAt(bestIndex);
		} else if(bestIndex == alphabet.indexOf('N')) {
			int dX = 1;
			int dY = 17;
			int numHits = 0;
			
			while(dX < tile.getWidth()) {
				while(dX < tile.getWidth()
						  && new Color(tile.getRGB(dX, dY)).getRed() > 100) {
					dX++;
				}
				
				if(dX < tile.getWidth())
					numHits++;
				
				while(dX < tile.getWidth()
						  && new Color(tile.getRGB(dX, dY)).getRed() < 100) {
					dX++;
				}
			}
			
			if(numHits == 1) {
				return 'H';
			} else {
				return 'N';
			}
		} else if(bestIndex == alphabet.indexOf('F')
					    || bestIndex == alphabet.indexOf('E')) {
			int dX = 15;
			int dY = 2;
			int numHits = 0;
			
			
			while(dY < tile.getHeight() - 2) {
				while(dY < tile.getHeight() - 2
						  && new Color(tile.getRGB(dX, dY)).getRed() > 100) {
					dY++;
				}
				
				if(dY < tile.getHeight() - 2)
					numHits++;
				
				while(dY < tile.getHeight() - 2
						  && new Color(tile.getRGB(dX, dY)).getRed() < 100) {
					dY++;
				}
			}
			
			if(numHits == 3) {
				return 'E';
			} else {
				return 'F';
			}
		} else if (bestIndex == alphabet.indexOf('Q')) {
			int dY = 28;
			int numHits = 0;
		
			for (int dX = 25; dX < tile.getWidth(); dX++) {
				if (new Color(tile.getRGB(dX, dY)).getRed() < 200) {
					numHits++;
					
					while (new Color(tile.getRGB(dX, dY)).getRed() < 200) {
						dX++;
					}
					
					dX--;
				}
			}
	
			if (numHits == 1) {
				return 'O';
			} else {
				return 'Q';
			}
		} else if (bestIndex == alphabet.indexOf('D')
				      || bestIndex == alphabet.indexOf('O')) {
			int dX = 6;
			int dY = 1;
			
			while(new Color(tile.getRGB(dX, dY)).getRed() > 100) {
				dY++;
			}
	
			int count = 0;
			while(new Color(tile.getRGB(dX, dY)).getRed() < 100) {
				dY++;
				count++;
			}
			
			if (count > 20) {
				return 'D';
			} else {
				return 'O'; 
			}
		} else {
			return alphabet.charAt(bestIndex);
		}
	}
	
	private static void displayImage(BufferedImage img) {
		JFrame fr = new JFrame();
		JPanel picPanel = new PicPanel(img);
		fr.add(picPanel);
		fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fr.setSize(img.getWidth(), 25 + img.getHeight());
		fr.setVisible(true);
	}
	
	private static void pause() {
		new Scanner(System.in).nextLine();
	}

	private static BufferedImage loadImage(String filePath) throws IOException {
		BufferedImage img = null;
		img = ImageIO.read(new File(filePath));
		
		return img;
	}
}