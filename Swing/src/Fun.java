import java.awt.Color;
import java.awt.Font;

import javax.swing.*;


public class Fun extends JFrame{
	
	public Fun() {
		JPanel myPanel = new JPanel();
		myPanel.setOpaque(true);
		myPanel.setBackground(Color.ORANGE);
		myPanel.setLayout(null);

		JButton b1 = new JButton();
		Font f2 = new Font("Monaco", Font.PLAIN, 16);
		b1.setFont(f2);
		b1.setText("First button");
		myPanel.add(b1);
		b1.setToolTipText("This should show up when you mouse over me");
		b1.setBackground(Color.CYAN);
		b1.setSize(100,100);
		b1.setLocation(10,10);		
			
		this.add(myPanel);
		this.setTitle("Fun is fun");
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setBackground(Color.DARK_GRAY);
		
		
	}
	
	public static void main(String[] args) {
		JFrame f = new Fun();
		f.setSize(500,500);
		f.setVisible(true);
	}
}
