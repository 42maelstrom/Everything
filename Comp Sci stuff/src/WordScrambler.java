/****************************************************
 *  Class: WordScramble
 * Author: 
 *   Date: 
 *   
 *   This program will take a single String and
 *   print out all of the permutations of that 
 *   String in the output area. It will work only 
 *   for Strings with less than 10 characters.
 *   
 *   Ex: tend
 *   tend, tedn, tned, tnde, tden, tdne
 *   etnd, etdn, entd, endt, edtn, ednt
 *   nted, ntde, netd, nedt, ndte, ndet
 *   dten, dtne, detn, dent, dnte, dnet
 *
 *****************************************************/


import java.awt.*;
import java.util.ArrayList;

import BreezyGUI.*;


public class WordScrambler extends GBFrame
{
	private Label wordLabel;
	private TextField wordField;
	private Button scrambleButton;
	private Button clearButton;
	private TextArea outputArea;
	
	public WordScrambler()
	{
		wordLabel = addLabel ("Enter Word",1,1,1,1);
		wordField = addTextField("",1,2,1,1);
		scrambleButton = addButton("Scramble",2,2,1,1);
		clearButton = addButton("Clear",2,1,1,1);
		outputArea = addTextArea("",3,1,3,8);
	}
	
	
	public void buttonClicked(Button buttonObj)
	{
		if(buttonObj == scrambleButton)
		{
			String word = wordField.getText();
			if(word.length()>9)
			{
				outputArea.setText("Word is too long\n");
				wordField.setText("");
			}
			else
			{
				outputArea.setText("Loading...");

				//list of all the permutations of the word.
				ArrayList<String> perms = iterativePerms(word);
				
				outputArea.setText("\nPermutations of " + word + ":\n");
				
				int charsPerLine = 38;
				int wordsPerLine = charsPerLine / (word.length() + 2);
				
				for(int i = 0; i < perms.size(); i++) {
					//print the permutations.
					outputArea.append(perms.get(i));
				
					if(i + 1 < perms.size()) {
						if((i+1) % wordsPerLine != 0) {
							outputArea.append(", ");
						} else {
							outputArea.append("\n");
						}
					}
				}
			}
		}
		else if(buttonObj == clearButton)
		{
			outputArea.setText("");
			wordField.setText("");
		}
	}
	
	public static ArrayList<String> perms(String word)
	{
		ArrayList<String> perms = new ArrayList<String>();
		
		//if length is one, there is one permutation: itself.
		if(word.length() == 1)
		{
			perms.add(word);
			return perms;
		}
		
		//for each character, add that character in front with all
		//of the permutations of the remaining characters appended
		for(int i = 0; i < word.length(); i++) {
			
			String removedChar = word.substring(i,i+1);
			String newString = word.substring(0, i) + word.substring(i+1);
			
			ArrayList<String> perms2 = perms(newString);
			
			for(int j = 0; j < perms2.size(); j++)
				perms.add(removedChar + perms2.get(j));
		}

		return perms;
	}
	
	public static ArrayList<String> iterativePerms(String word) {
		String reverseWord = "";
		for(int i = 0; i < word.length(); i++) {
			reverseWord = word.substring(i, i+1) + reverseWord;
		}
		
		//so that the perms come in backwards, which looks better.
		word = reverseWord;
		
		ArrayList<String> perms = new ArrayList<String>();
		//start with the base character
		perms.add(word.substring(0, 1));
		
		//for each remaining letter, make a permutation with that 
		//letter inserted into each possible position
		for(int i = 1; i < word.length(); i++) {
			//character to add in each position
			String charToAdd = word.substring(i, i+1);
			//new list of perms created from list of old perms
			ArrayList<String> newPerms = new ArrayList<String>(perms.size() * (i+1));
			for(String perm: perms) {
				//for each position, insert the letter.
				for(int pos = 0; pos < i+1; pos++)
					newPerms.add(perm.substring(0,pos) + charToAdd + perm.substring(pos)); 
			}
			perms = newPerms;
		}
		
		return perms;
	}
	
	
	public static void main(String[] args)
	{
		WordScrambler frm = new WordScrambler();
		frm.setSize(300,600);
		frm.setVisible(true);
		frm.repaint();
	}
}
