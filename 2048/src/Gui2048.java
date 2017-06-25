import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.*;

public class Gui2048 implements KeyListener, ActionListener {

	// the color of any text
	private static final Color TEXT_GRAY = new Color(99, 90, 82);
	// the color of the background of the window.
	private static final Color BACKGROUND_TAN = new Color(248, 247, 235);

	private static final int SIDE_PADDING = 30;
	private static final int VERT_PADDING = 30;

	private static final int BOARD_WIDTH = 503;

	private static final int WIDTH = SIDE_PADDING * 2 + BOARD_WIDTH;
	private static final int HEIGHT = 215 + BOARD_WIDTH + 2 * VERT_PADDING;

	private JFrame frame; // frame of the whole thing
	private JScrollPane scrollPane; // scroll pane has displayPanel inside
	private JPanel displayPanel; // displayPanel contains all the actual
									// components
	private BoardPanel boardPanel;
	private Board board;

	private JLabel title, subtitle, bold, simon;
	private T2048Button newGameButton;
	private T2048Button tryAgainButton;
	private T2048Button keepPlayingButton;
	private ScoreLabel scoreLabel;
	private ScoreLabel bestLabel;
	private Thread animation;
	private JPanel coverPanel; //goes over the screen when game is over or won.
	private boolean got2048Before = false;
	private boolean gamePaused = false;
	//the floating number that fades above the score label.
	private JLabel scoreFloater;
	
	public Gui2048() {
		//because there are special fonts not on the computer to add
		addFonts();

		frame = new JFrame("2048 by Simon");
		frame.addKeyListener(this);
		frame.setSize(WIDTH, HEIGHT);
		frame.setBackground(BACKGROUND_TAN);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		displayPanel = new JPanel();
		displayPanel.setSize(WIDTH, HEIGHT);
		displayPanel.setPreferredSize(new Dimension(displayPanel.getWidth(),
				displayPanel.getHeight() - 22));
		displayPanel.setLayout(null);
		displayPanel.setBackground(BACKGROUND_TAN);

		scrollPane = new JScrollPane();
		scrollPane.getViewport().add(displayPanel);
		scrollPane.setBorder(null);
		frame.add(scrollPane);

		title = new JLabel("2048");
		title.setFont(new Font("ClearSans-Bold", Font.BOLD, 80));
		title.setForeground(TEXT_GRAY);
		title.setLocation(SIDE_PADDING, VERT_PADDING - 8);
		title.setSize(title.getPreferredSize());
		displayPanel.add(title);

		subtitle = new JLabel("Join the numbers and get to the ");
		subtitle.setFont(new Font("ClearSans-Medium", Font.PLAIN, 18));
		subtitle.setForeground(TEXT_GRAY);
		subtitle.setLocation(title.getLocation().x, title.getLocation().y + 120);
		subtitle.setSize(subtitle.getPreferredSize());
		displayPanel.add(subtitle);

		bold = new JLabel("2048 tile!");
		bold.setFont(new Font("ClearSans-Bold", Font.BOLD, 18));
		bold.setForeground(TEXT_GRAY);
		bold.setLocation(
				subtitle.getLocation().x
						+ subtitle.getFontMetrics(subtitle.getFont())
								.stringWidth(subtitle.getText()),
				subtitle.getLocation().y);
		bold.setSize(bold.getPreferredSize());
		displayPanel.add(bold);
		
		bestLabel = new ScoreLabel();
		bestLabel.setText("BEST");
		bestLabel.setLocation(WIDTH - SIDE_PADDING - bestLabel.getWidth(),
				VERT_PADDING);
		displayPanel.add(bestLabel);

		scoreLabel = new ScoreLabel();
		scoreLabel.setText("SCORE");
		scoreLabel.setLocation(
				bestLabel.getLocation().x - 5 - scoreLabel.getWidth(),
				VERT_PADDING);
		displayPanel.add(scoreLabel);

		newGameButton = new T2048Button("New Game");
		newGameButton.setLocation(
				WIDTH - SIDE_PADDING - newGameButton.getWidth(),
				bestLabel.getLocation().y + 105);
		newGameButton.addActionListener(this);
		displayPanel.add(newGameButton);

		board = new Board();
		boardPanel = new BoardPanel(board);
		boardPanel.setLocation(SIDE_PADDING, VERT_PADDING + 187);
		displayPanel.add(boardPanel);
		
		scoreFloater = new JLabel();
		scoreFloater.setVisible(false);
		scoreFloater.setFont(new Font("ClearSans-Bold", Font.BOLD, 25));
		scoreFloater.setForeground(TEXT_GRAY);
		displayPanel.add(scoreFloater);
		displayPanel.setComponentZOrder(scoreFloater, 0);

		simon = new JLabel("by Simon");
		simon.setFont(new Font("ClearSans-Bold", Font.BOLD, 18));
		simon.setForeground(TEXT_GRAY);
		simon.setSize(simon.getFontMetrics(simon.getFont()).stringWidth(simon.getText()),
					  simon.getFontMetrics(simon.getFont()).getHeight());
		
		simon.setLocation(title.getLocation().x + title.getWidth() + 10, 
						  title.getLocation().y + title.getHeight() - 2*simon.getHeight() + 5);
		displayPanel.add(simon);
		
		frame.setVisible(true);
		
		animation = new Thread(new MoveAnimation());
		animation.start();
	}

	private void addFonts() {
		try {
			for (File file : new File("ClearSans_TTF").listFiles()) {
				GraphicsEnvironment ge = GraphicsEnvironment
						.getLocalGraphicsEnvironment();
				ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, file));
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}
	}
	
	private void newGame() {
		gamePaused = false;
		got2048Before = false;
		
		if(coverPanel != null)
			displayPanel.remove(coverPanel);
		displayPanel.remove(boardPanel);

		board = new Board();

		boardPanel = new BoardPanel(board);
		boardPanel.setLocation(SIDE_PADDING, VERT_PADDING + 187);
		displayPanel.add(boardPanel);

		scoreLabel.setScore(0);
		scoreLabel.setLocation(
				bestLabel.getLocation().x - 4 - scoreLabel.getWidth(),
				VERT_PADDING);
		displayPanel.repaint();
		
		animation = new Thread(new MoveAnimation());
		animation.start();
	}
	
	public void keyPressed(KeyEvent k) {
		if(!gamePaused) {
			// Stop the animation if a previous move is still animating.
			animation.interrupt();
			
			/* This way we make sure the animation ends properly before we go on.
			 * Otherwise some whacky things happen with tileList not being
			 * properly used.
			 */ 
			try {
				animation.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			if (k.getKeyCode() == KeyEvent.VK_UP) {
				board.moveUp();
			} else if (k.getKeyCode() == KeyEvent.VK_DOWN) {
				board.moveDown();
			} else if (k.getKeyCode() == KeyEvent.VK_LEFT) {
				board.moveLeft();
			} else if (k.getKeyCode() == KeyEvent.VK_RIGHT) {
				board.moveRight();
			} else {
				return;
			}
			
			if(board.boardChanged()) {
				animation = new Thread(new MoveAnimation());
				animation.start();
			}
			
		}
	}

	public void keyReleased(KeyEvent arg0) {

	}

	public void keyTyped(KeyEvent arg0) {

	}

	public void actionPerformed(ActionEvent a) {
		if (a.getSource().equals(newGameButton)) {
			newGame();
		} else if(a.getSource().equals(tryAgainButton)) {
			newGame();
		} else if(a.getSource().equals(keepPlayingButton)) {
			gamePaused = false;
			displayPanel.remove(coverPanel);
			displayPanel.repaint();
		}
	}

	public class MoveAnimation implements Runnable {

		ArrayList<Tile> tileList;
		ArrayList<Movement> movements;
		ArrayList<Movement> merges;
		int scoreIncrease;
		ArrayList<Tile> newTiles;

		static final int NUM_OF_SLIDING_FRAMES = 20;
		static final int SLIDING_DELAY = 4;

		static final int NUM_OF_MERGING_FRAMES = 8;
		static final int MERGING_DELAY = 15;
		static final double MAX_MERGE_SIZE = 1.24;
		static final double NEW_TILE_SIZE_INCREASE = 1.0 / (double)NUM_OF_MERGING_FRAMES;
		
		static final int NUM_OF_FLOATER_FRAMES = 10;
		static final int FLOATER_DELAY = 15;
		int frame = 0;
		boolean finishedDrawingBoard = false;
		
		public MoveAnimation() {
			tileList = board.getTileList();
			movements = board.getMovements();
			scoreIncrease = board.getScoreIncrease();
			newTiles = board.getNewTiles();

			merges = new ArrayList<Movement>(8);
			for (Movement m : movements) {
				if (m.mergesAtEnd)
					merges.add(m);
			}
		}

		@Override
		public void run() {
			updateScoreLabels();

			try {
				animateSliding();
				animateMerges();
				finishFloater();
			} catch (InterruptedException e) {
				if(!finishedDrawingBoard) 
					drawFinalBoard();
			} finally {
				scoreFloater.setVisible(false);
				
				if(board.isGameOver())
					gameOver();
				if(board.got2048Tile())
					got2048Tile();
			}
		}
		
		private void updateScoreLabels() {
			if (scoreIncrease != 0) {
				
				if (scoreLabel.getScore() + scoreIncrease > bestLabel.getScore()) {
					bestLabel.setScore(scoreLabel.getScore() + scoreIncrease);
					bestLabel.setLocation(
							WIDTH - SIDE_PADDING - bestLabel.getWidth(),
							VERT_PADDING);
					bestLabel.repaint();
				}
				
				scoreLabel.increaseScore(scoreIncrease);
				
				scoreLabel.setLocation(bestLabel.getLocation().x - 4
						- scoreLabel.getWidth(), VERT_PADDING);
				
				scoreFloater.setVisible(true);
				scoreFloater.setText("+" + scoreIncrease);
				int sWidth = scoreFloater.getFontMetrics(scoreFloater.getFont())
						.stringWidth(scoreFloater.getText());
				scoreFloater.setSize(sWidth + 20, scoreFloater.getFontMetrics(
						scoreFloater.getFont()).getHeight());
				scoreFloater.setLocation(
						scoreLabel.getLocation().x + scoreLabel.getWidth() / 2
						- sWidth / 2,
						scoreLabel.getLocation().y + 18);
				
				scoreLabel.repaint();
				scoreFloater.repaint();
			} else {
				scoreFloater.setVisible(false);
			}
		}

		/**
		 * Animates the sliding.
		 * @throws InterruptedException - if Mr. Player makes a new move.
		 */
		private void animateSliding() throws InterruptedException {
			for (int i = 0; i < NUM_OF_SLIDING_FRAMES - 1; i++) {
				for (Movement m : movements) {
					m.shiftTileOneFrame();
				}
				Color fg = scoreFloater.getForeground();
				scoreFloater.setForeground(new Color(
						fg.getRed(), fg.getGreen(), fg.getBlue(), getFloaterAlpha(frame)));
				scoreFloater.setLocation(scoreFloater.getLocation().x, scoreFloater.getLocation().y - 1);
				scoreFloater.repaint();
				boardPanel.repaint();
				Thread.sleep(SLIDING_DELAY);
				frame++;
			}

			/*
			 * for the last frame. This is to fix the bug where tiles get off by
			 * one pixel due to rounding error in shifting each frame.
			 */
			for (Movement m : movements) {
				m.tile.xPos = m.finalPos.c;
				m.tile.yPos = m.finalPos.r;
			}
			boardPanel.repaint();
		}

		/**
		 * Animates the merges and the new tile added animation.
		 * @throws InterruptedException - if Mr. Player makes a new move.
		 */
		private void animateMerges() throws InterruptedException {
			for (Movement m : merges) {
				m.tile.value *= 2;
			}

			for(Tile newTile: newTiles) {
				board.getTileList().add(newTile);
			}
			
			boardPanel.repaint();
			
			for (int i = 0; i < NUM_OF_MERGING_FRAMES; i++) {
				for (Movement m : merges)
					m.tile.size = mergeSize(i);
				
				for(Tile newTile: newTiles)
					newTile.size += NEW_TILE_SIZE_INCREASE;
				
				Color fg = scoreFloater.getForeground();
				scoreFloater.setForeground(new Color(
						fg.getRed(), fg.getGreen(), fg.getBlue(), getFloaterAlpha(frame)));
				scoreFloater.setLocation(scoreFloater.getLocation().x, scoreFloater.getLocation().y - 2);
				
				scoreFloater.repaint();
				boardPanel.repaint();
				Thread.sleep(MERGING_DELAY);
				frame++;
			}
		}
		
		private void finishFloater() throws InterruptedException {
			finishedDrawingBoard = true;
			for(int i = 0; i < NUM_OF_FLOATER_FRAMES; i++) {
				Color fg = scoreFloater.getForeground();
				scoreFloater.setForeground(new Color(
						fg.getRed(), fg.getGreen(), fg.getBlue(), getFloaterAlpha(frame)));
				scoreFloater.setLocation(scoreFloater.getLocation().x, scoreFloater.getLocation().y - 2);
				
				scoreFloater.repaint();
				Thread.sleep(FLOATER_DELAY);
				frame++;
			}
		}
		
		/**
		 * Gives the size of the merging-animating tile given the frame it's on
		 * @param frameNumber - the frame of the animation
		 * @return - the size of the tile.
		 */
		private double mergeSize(int frameNumber) {
			double w = NUM_OF_MERGING_FRAMES - 1;
			double x = frameNumber;
			double a = 2*(MAX_MERGE_SIZE - 1) / w;
			
			return 1.0 + a*(.5*w - Math.abs(x - .5*w));
		}
		
		private int getFloaterAlpha(int frame) {
			final int TOTAL_FRAMES = NUM_OF_SLIDING_FRAMES + NUM_OF_MERGING_FRAMES + NUM_OF_FLOATER_FRAMES;
			final double D_ALPHA = 255 / (double)TOTAL_FRAMES;
			return Math.max(0, 250 - (int)(frame*D_ALPHA));
		}
		
		private void drawFinalBoard() {
			for (Movement m : movements) {
				m.tile.xPos = m.finalPos.c;
				m.tile.yPos = m.finalPos.r;
				m.tile.value = m.finalVal;
				m.tile.size = 1.0;
			}

			boardPanel.repaint();
		}
		
		private void gameOver() {
			
			gamePaused = true;
			
			//delay before game over screen shows up.
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {

			}
			
			coverPanel = new JPanel();
			coverPanel.setLocation(boardPanel.getLocation());
			coverPanel.setSize(boardPanel.getSize());
			coverPanel.setLayout(null);
			final Color coverColor = new Color(250, 240, 230);
			coverPanel.setBackground(new Color(coverColor.getRed(),
											   coverColor.getBlue(),
											   coverColor.getGreen(),
											   0));
			
			JLabel gameOverLabel = new JLabel("Game over!");
			gameOverLabel.setForeground(TEXT_GRAY);
			gameOverLabel.setFont(new Font("ClearSans-Bold", Font.BOLD, 60));
			gameOverLabel.setSize(340, 100);
			gameOverLabel.setHorizontalAlignment(SwingConstants.CENTER);
			gameOverLabel.setLocation(coverPanel.getWidth() / 2 - gameOverLabel.getWidth() / 2,
									  coverPanel.getHeight() / 3);
			coverPanel.add(gameOverLabel);
			
			tryAgainButton = new T2048Button("Try again");
			tryAgainButton.setLocation(coverPanel.getWidth() / 2 - tryAgainButton.getWidth() / 2,
									   coverPanel.getHeight() / 2 + tryAgainButton.getHeight() / 2);
			tryAgainButton.addActionListener(Gui2048.this);
			coverPanel.add(tryAgainButton);
			
			displayPanel.add(coverPanel);
			displayPanel.setComponentZOrder(coverPanel, 0);
			displayPanel.repaint();
			
			Color background = tryAgainButton.getBackground();
			Color foreground = tryAgainButton.getForeground();
			
			for(int alpha = 0; alpha < 140; alpha++) {
				alpha++;
				coverPanel.setBackground(new Color(coverColor.getRed(),
						   coverColor.getBlue(),
						   coverColor.getGreen(),
						   alpha));
				gameOverLabel.setForeground(new Color(TEXT_GRAY.getRed(),
													  TEXT_GRAY.getGreen(),
													  TEXT_GRAY.getBlue(),
													  alpha + 255 - 140));
				tryAgainButton.setBackground(new Color(background.getRed(),
													   background.getGreen(),
													   background.getBlue(),
													   alpha + 255 - 140));
				tryAgainButton.setForeground(new Color(foreground.getRed(),
						   foreground.getGreen(),
						   foreground.getBlue(),
						   alpha + 255 - 140));
				
				displayPanel.repaint();
				try {
					Thread.sleep(6);
				} catch (InterruptedException e) {

				}
			}
		}
		
		private void got2048Tile() {
			if(!got2048Before) {
				got2048Before = true;
				gamePaused = true;
				
				coverPanel = new JPanel();
				coverPanel.setLocation(boardPanel.getLocation());
				coverPanel.setSize(boardPanel.getSize());
				coverPanel.setLayout(null);
				Color c2048 = boardPanel.getColorForTile(2048);
				coverPanel.setBackground(new Color(
						c2048.getRed(),
						c2048.getGreen(),
						c2048.getBlue(), 
						100));
				
				JLabel youWinLabel = new JLabel("You win!");
				youWinLabel.setForeground(Color.WHITE);
				youWinLabel.setFont(new Font("ClearSans-Bold", Font.BOLD, 60));
				youWinLabel.setSize(340, 100);
				youWinLabel.setHorizontalAlignment(SwingConstants.CENTER);
				youWinLabel.setLocation(coverPanel.getWidth() / 2 - youWinLabel.getWidth() / 2,
										  coverPanel.getHeight() / 2 - youWinLabel.getHeight() / 2);
				coverPanel.add(youWinLabel);
			
				tryAgainButton = new T2048Button("Try again");
				tryAgainButton.addActionListener(Gui2048.this);
				coverPanel.add(tryAgainButton);
				
				keepPlayingButton = new T2048Button("Keep playing");
				keepPlayingButton.addActionListener(Gui2048.this);
				coverPanel.add(keepPlayingButton);
				
				int bHeight = youWinLabel.getLocation().y + 100;
				int gap = 15;
				int bTotalWidth = tryAgainButton.getWidth() + keepPlayingButton.getWidth() + gap;
				
				tryAgainButton.setLocation(coverPanel.getWidth() / 2 - bTotalWidth / 2, bHeight);
				keepPlayingButton.setLocation(tryAgainButton.getLocation().x + tryAgainButton.getWidth() + gap, bHeight);
				
				displayPanel.add(coverPanel);
				displayPanel.setComponentZOrder(coverPanel, 0);
				displayPanel.repaint();
			}
		}
	}
}
