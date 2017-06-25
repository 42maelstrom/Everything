import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.*;


public class ScoreLabel extends JLabel {
	
	private static final Color BACKGROUND_COLOR = new Color(173, 157, 143);
	private static final Color TEXT_COLOR = new Color(234, 222, 209);
	private static final Font LABEL_FONT = new Font("ClearSans-Bold", Font.BOLD, 13);
	private static final Font NUMBER_FONT = new Font("ClearSans-Bold", Font.BOLD, 25);
	
	private int score;
	private static final int HEIGHT = 56;
	
	public ScoreLabel() {
		super();
		setBackground(BACKGROUND_COLOR);
		setForeground(TEXT_COLOR);
		setScore(0);
		setSize(getWidth(), HEIGHT);
	}
	
	@Override
	public Dimension getSize() {
		return new Dimension(getWidth(), HEIGHT);
	}
	
	@Override
	public Dimension getPreferredSize() {
		return getSize();
	}
	
	public void increaseScore(int scoreIncrease) {
		setScore(score + scoreIncrease);
	}
	
	public void setScore(int score) {
		this.score = score;
		updateWidth();
	}
	
	public int getScore() {
		return score;
	}
	
	private void updateWidth() {
		int padding = 25;
		int sWidth = getFontMetrics(NUMBER_FONT).stringWidth(Integer.toString(score));
		setSize(2*padding + sWidth, HEIGHT);
	}

	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(
				RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		
		g2d.setColor(getBackground());
		g2d.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 6, 6);
		
		g2d.setFont(LABEL_FONT);
		g2d.setColor(getForeground());
		int sWidth = g.getFontMetrics().stringWidth(getText());
		g2d.drawString(getText(), getWidth() / 2 - sWidth / 2, 22);
		
		g2d.setFont(NUMBER_FONT);
		g2d.setColor(Color.WHITE);
		sWidth = g2d.getFontMetrics().stringWidth(Integer.toString(score));
		g2d.drawString(Integer.toString(score), getWidth() / 2 - sWidth / 2, 48);
	}
}
