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

public class GuiMS {
	
	private static final Color BACKGROUND_COLOR = Color.WHITE;
	private static final Color TEXT_COLOR = Color.GRAY;
	private static final Font TITLE_FONT = new Font("Avenir Next", Font.PLAIN, 36);
	private static final int WIDTH = 500, HEIGHT = 500;
	
	private JFrame frame;
	private JPanel mainPanel;
	private JScrollPane scrollPane;
	private JPanel innerPanel;
	
	private JLabel title;
	private MSButton settings, howToPlay, highScores;
	
	public GuiMS() {
		frame = new JFrame("Minesweeper");
		frame.setSize(WIDTH, HEIGHT);
		frame.setBackground(BACKGROUND_COLOR);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
//		mainPanel = new JPanel();
//		mainPanel.setSize(WIDTH, HEIGHT);
//		mainPanel.setLayout(null);
//		mainPanel.setBackground(BACKGROUND_COLOR);
//		frame.setContentPane(mainPanel);
		
//		title = new JLabel("Minesweeper");
//		title.setFont(TITLE_FONT);
//		title.setForeground(TEXT_COLOR);
//		title.setLocation(25,25);
//		title.setSize(title.getPreferredSize());
//		title.setVisible(true);
//		mainPanel.add(title);
		
		innerPanel = new JPanel();
		innerPanel.setBackground(Color.RED);
		innerPanel.setLocation(0,0);
		innerPanel.setPreferredSize(new Dimension(400,400));
		
		scrollPane = new JScrollPane();
		scrollPane.getViewport().add(innerPanel);
		scrollPane.setBorder(null);
		frame.add(scrollPane);
		
		frame.setVisible(true);
	}
}