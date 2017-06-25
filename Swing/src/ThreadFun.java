import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.*;

public class ThreadFun implements ActionListener, Runnable{
	
	JFrame frame;
	JLabel label;
	JPanel panel;
	JTextField input;
	JLabel picLabel;
	
	int numOfSheep;
	
	public ThreadFun() {
		frame = new JFrame("Counting Sheep");
		panel = new JPanel();
		input = new JTextField();
		label = new JLabel();
		
		picLabel = new JLabel("No image found");
		
		try{
			BufferedImage myPicture = ImageIO.read(new File("Sheep.png"));
			picLabel = new JLabel(new ImageIcon(myPicture));
		}catch(Exception e) {
			System.out.println(e);
			System.exit(1);
		}
		
		picLabel.setLocation(20,20);
		picLabel.setSize(100,100);
		picLabel.setVisible(true);
		label.setText("How many sheep?");
		panel.setLayout(null);
		input.setSize(50,30);
		input.setLocation(160,150);
		label.setLocation(10,150);
		label.setSize(190,30);
		panel.add(input); panel.add(label);
		panel.add(picLabel);
		frame.setContentPane(panel);
		frame.setSize(220,220);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
		input.addActionListener(this);
		
		numOfSheep = 0;
	}
	
	public void dispNextSheep() {
		System.out.println("Here comes the next sheep");
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(input)) {
			try{
				int i = Integer.parseInt(input.getText());
				if(i != numOfSheep) {
					System.out.println("Game over. You counted " 
							+ numOfSheep + " sheep successfully. Goodnight!");
					System.exit(0);
				}
			} catch(Exception ex) {
				System.out.println(ex.getMessage());
			}
		}
	}
	
	public void run() {
		System.out.println("Let's fake up my sheep counting program from my"
				+ " calculator!");
		
	}
	
	public static void main(String[] args) {
		ThreadFun tf = new ThreadFun();
		tf.run();
	}

}
