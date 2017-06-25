import java.awt.*;
import java.util.ArrayList;

import BreezyGUI.*;

public class Strings extends GBFrame 
{
   	//The labels: these display the text strings that guide the user.
 
   	private Label firstLabel; 
   	
   	//The integer fields: these are used for the input and output of 
   	//integer values.
  	private IntegerField firstField;

   	//The command buttons: these trigger the buttonClicked method when
   	//clicked.

   	private Button magicButton;
 	
 	// A text area to output the answers.  You can put text into a TextArea 
 	// either by using area.setText(somestring) which replaces all of the text
 	// with the new text, or area.append(somestring) which adds the new string
 	// to the text already present in the text area
 	 
 	private TextArea outputArea;
 	

    /**
     * This is the constructor for the GCF_LCM class. It constructs the needed
     * buttons, labels, fields and text area. Those two labels, two fields, 
     * three buttons and the text area are the instance variables for the class.
     * The purpose of the contructor is to construct each of those instance
     * variables so that the GUI frame works.
     */
   	public Strings( )
   	{
   		firstLabel = addLabel("Please input a string",1,1,2,2);
   		
   		firstField = addIntegerField(0,2,3,2,2);
   		outputArea = addTextArea("",4,1,5,5);
   		magicButton = addButton("Do your magic",3,1,1,1);
   	}

   
   	/**
   	 * This method is called every time one of the buttons in our 
   	 * GUI frame is clicked. The specific button being clicked is 
   	 * passed to the method as the object "buttonObj."
   	 */
   	public void buttonClicked (Button buttonObj)
   	{
      	// get the values in each of the fields
      	String str = firstField.getText();

     	// now figure out which button was clicked
    
      	if (buttonObj == magicButton)
      	{  
      		
      		outputArea.append("Length: " + str.length() + " characters \nFirst Letter: " + str.charAt(0) + "\nNumber of Vowels: " + numVowels(str) + "\nWithout first char: " + str.substring(1) + "\n");
      	}
   	}
   	
   	public int numVowels(String str)
   	{
   		int num = 0;
   		for(int i = 0; i < str.length(); i++)
   		{
   			if(str.charAt(i) == 'a' || str.charAt(i) == 'e' || str.charAt(i) == 'i' || str.charAt(i) == 'o' || str.charAt(i) == 'u')
   			{
   				num++;
   			}
   		}
   		return num;
   	
   	}
   	
   	public static void main (String[] args)
	{
		//Instantiate the GUI part
		Frame frm = new Strings();    
		//Set the application's window width and height in pixels
		frm.setSize (300, 300);  
		//Make the window visible to the user
		frm.setVisible (true);           
	}
      	
 }