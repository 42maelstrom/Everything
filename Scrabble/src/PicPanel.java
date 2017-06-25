import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class PicPanel extends JPanel {
	Image img;
	
	public PicPanel(Image img) {
		this.img = img;
	}
	
	public void paintComponent(Graphics g) {
		//this way it fills in the background color!
		super.paintComponent(g);
		g.drawImage(img, 0, 0, this);	
	}
	
	public static void makePanel(File f) {
		BufferedImage img = null;
		try {
			img = ImageIO.read(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		JFrame fr = new JFrame();
		JPanel picPanel = new PicPanel(img);
		fr.add(picPanel);
		fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fr.setSize(img.getWidth(), 25 + img.getHeight());
		fr.setVisible(true);
	}
}
