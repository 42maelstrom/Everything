import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import BreezyGUI.*;

public class Jukebox extends GBFrame implements ActionListener
{
	private Label label;
	private Button button;
	private Choice menu;
	String[] choiceNames = {"Song 1","Song 2", "Song 3"};
	boolean isPlayButton;
	TestBeat beat;
	
   	public Jukebox()
   	{
   		isPlayButton = true;
   		label = addLabel("Select a song: ",2,1,1,1);
   		button = addButton("Play",3,1,5,5);
   		menu = addChoice(2,2,1,1);
     	for(String temp: choiceNames)
     		menu.addItem(temp);
     	beat = new TestBeat();
     	
   	}

   	public void buttonClicked (Button buttonObj)
   	{
   		if(buttonObj == button) {
   			if(isPlayButton){
   				button.setLabel("Stop");
   				int songNumber = menu.getSelectedIndex();
   				if(songNumber == 0)
   					beat.testBeat1();
   				if(songNumber == 1)
   					beat.testBeat2();
   				if(songNumber == 2)
   					beat.testBeat3();
   				
   			} else {
   				beat.stop();
   				button.setLabel("Play");
   			}
   			isPlayButton = !isPlayButton;
   		}
   	}
   	
   	
   	public static void main(String[] args)
	{
		Frame frm = new Jukebox();    
		frm.setSize (230, 100);  
		frm.setTitle("Jukebox");
		frm.setVisible (true);    
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println(e.getActionCommand());
	}
      	
 }