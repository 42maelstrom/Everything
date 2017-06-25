import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.MenuBar;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.plaf.basic.BasicBorders.SplitPaneBorder;
import javax.swing.text.html.parser.Parser;

public class SoundPlayer extends JFrame {
	JPanel myPanel;
	JButton myButton;
	JLabel label;
	JComboBox menu;
	TestBeat currentSong;
	boolean isPlayButton;

	public SoundPlayer() {
		JFrame frame = new JFrame("Soundbox");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		
		currentSong = new TestBeat();

		myPanel = new JPanel();
		myPanel.setOpaque(true);
		myPanel.setBackground(Color.ORANGE);
		myPanel.setLayout(null);

		myButton = new JButton("Play");
		isPlayButton = true;
		myButton.setSize(90, 60);
		myButton.setLocation(120, 90);

		String[] menuItems = {"Song 1", "Song 2","Song 3"};

		menu = new JComboBox(menuItems);
		menu.setSelectedIndex(0);
		menu.setSize(100, 100);
		menu.setLocation(200, 0);

		label = new JLabel("Select a song:", JLabel.CENTER);
		label.setSize(100, 30);
		label.setLocation(30, 34);

		myPanel.add(myButton);
		myPanel.add(label);
		myPanel.add(menu);

		frame.setSize(330, 200);

		frame.setContentPane(myPanel);
		frame.setVisible(true);

		myButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (myButton == e.getSource()) {
					if (isPlayButton == true) {

						if (menu.getSelectedIndex() == 0)
							currentSong.testBeat3();
						if (menu.getSelectedIndex() == 1)
							currentSong.testBeat1();
						if(menu.getSelectedIndex() == 2)
							currentSong.testBeat3();
						
						myButton.setText("Stop");
						
					} else {
						myButton.setText("Play");
						currentSong.stop();
					}
					isPlayButton = !isPlayButton;
				}
			}
		});
	}

	public static void main(String[] args) {
		SoundPlayer sp = new SoundPlayer();
	}
}