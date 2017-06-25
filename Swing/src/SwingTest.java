import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.*;

public class SwingTest extends JFrame{

	public static void main(String[] args)
	{
		JFrame hi = new SwingTest();
		hi.setVisible(true);
	}
	
	public SwingTest()
	{
		DrawPanel dpnl = new DrawPanel();
		add(dpnl);
		
		setSize(250, 200);
		setTitle("Hi");
		
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JLabel jlb = new JLabel("Testing...");
		JButton butt = new JButton("Here's a button");
		
		butt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				System.exit(0);
			}
		});
		
		createLayout(butt);
	}
	
	
	private void createLayout(JComponent...arg) {
		Container pane = getContentPane();
		GroupLayout gl = new GroupLayout(pane);
		pane.setLayout(gl);
		
		gl.setAutoCreateContainerGaps(true);
		
		gl.setHorizontalGroup(gl.createSequentialGroup()
				.addComponent(arg[0])
		);
		
		gl.setVerticalGroup(gl.createSequentialGroup()
				.addComponent(arg[0])
		);		
		
	}
}
