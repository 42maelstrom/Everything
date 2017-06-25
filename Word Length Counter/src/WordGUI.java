import java.awt.*;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import BreezyGUI.*;

public class WordGUI extends GBFrame {

	private Button countWords;
	private Button importText;
 
	private Label l1;
   	private Label l2;
   	private Label l3;
   	
   	private IntegerField numOfWords;
   	private TextField longestWord;
   	private IntegerField avgWordLength;
   	
   	private TextArea inputArea;
 	
   	public WordGUI( )
   	{
   		countWords = addButton("Count Words",1,1,1,1);
   		importText = addButton("Import .txt file",1,3,1,1);

   		l1 = addLabel("Number of words:",2,1,1,1);
   		l2 = addLabel("Longest word:",3,1,1,1);
   		l3 = addLabel("Average word length:",4,1,1,1);
   		
   		numOfWords = addIntegerField(0,2,2,1,1);
   		longestWord = addTextField("",3,2,1,1);
   		avgWordLength = addIntegerField(0,4,2,1,1);
   		
   		inputArea = addTextArea("Input text here",2,3,2,4);
   		

   	}

   
   	/**
   	 * This method is called every time one of the buttons in our 
   	 * GUI frame is clicked. The specific button being clicked is 
   	 * passed to the method as the object "buttonObj."
   	 */
   	public void buttonClicked (Button buttonObj)
   	{
      	if(buttonObj == countWords) {
      		countWords(inputArea.getText());
      	} else if(buttonObj == importText) {
      		JFileChooser jfc = new JFileChooser();
      		FileNameExtensionFilter filter = new FileNameExtensionFilter(".txt Files", "txt");
      		jfc.setFileFilter(filter);   
      		int returnVal = jfc.showOpenDialog(null);
      		if(returnVal == JFileChooser.APPROVE_OPTION) {
      			try{
      				String text = "";
      				BufferedReader br = new BufferedReader(new FileReader(jfc.getSelectedFile().getAbsolutePath()));
      				int numOfLines = 0;
      				while(br.readLine() != null)
      					numOfLines++;
      				br.close();
      		
      				BufferedReader br2 = new BufferedReader(new FileReader(jfc.getSelectedFile().getAbsolutePath()));
      				
      				for(int i = 0; i < numOfLines; i++)
      					text+=br2.readLine();
      				br2.close();

      				inputArea.setText(text);
      				countWords(text);
      				
      			}catch(IOException e){
      				inputArea.setText("Error importing file. \nIt's probably not a .txt file. \nMy apoligies.");
      			}
      		}
      	}
   	}
   	
   	/**
   	 * Makes a WordCounter object and puts in the values into the GUI
   	 * @param text - the text to be analyzed
   	 */
   	public void countWords(String text){
   		WordCounter wc = new WordCounter(text);
  		numOfWords.setText(Integer.toString(wc.getNumOfWords()));
  		longestWord.setText(wc.getLongestWord());
  		String avgStr = Double.toString(wc.getAvgWordLength());
  		if(avgStr.length() > 3)
  			avgStr = avgStr.substring(0,4);
  		avgWordLength.setText(avgStr);
   	}

   	
   	public static void main (String[] args)
	{
		//Instantiate the GUI part
		Frame frm = new WordGUI();    
		//Set the application's window width and height in pixels
		frm.setSize (620, 240);  
		//Add a title to the GUI
		frm.setTitle("Word Length Counter");
		//Make the window visible to the user
		frm.setVisible (true);           
	}
      	
 }