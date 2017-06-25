import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.*;


public class T2048Button extends JButton {
	private static final Color BACKGROUND_COLOR = new Color(124, 103, 83);
	private static final Color TEXT_COLOR = new Color(249, 246, 242);
	
	public T2048Button(String text) {
		super(text);
		setSize(136, 46);
		setCursor(new Cursor(Cursor.HAND_CURSOR));
		setFocusable(false);
		setForeground(TEXT_COLOR);
		setBackground(BACKGROUND_COLOR);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(
				RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		
		g2d.setColor(getBackground());
		g2d.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 6, 6);
		g2d.setColor(getForeground());
		g2d.setFont(new Font("ClearSans-Bold", Font.BOLD, 18));
		g2d.drawString(getText(), getWidth() / 2 -
				g.getFontMetrics().stringWidth(getText()) / 2, 29);
	}
}
