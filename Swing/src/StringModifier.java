import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.*;


public class StringModifier extends JFrame implements ActionListener{

	private JTextArea input;
	private JTextArea output;
	private JButton modify;
	private JPanel panel;
	private JButton modify2;
	private JScrollPane iP;
	private JScrollPane oP;
	
	public StringModifier() {
		this.setTitle("String Modifier 2.0");
		panel = new JPanel();
		input = new JTextArea();
		output  = new JTextArea();
		modify2 = new JButton();
		modify = new JButton();
		iP = new JScrollPane(input);
		oP = new JScrollPane(output);
		
		iP.setSize(200,50);
		modify.setSize(80,30);
		oP.setSize(200,50);
		modify2.setSize(80,30);
		
		
		input.setText("Input text here");
		modify.setText("Modify 1");
		output.setText("Here's where the output will go");
		modify2.setText("Modify 2");
		
		input.setWrapStyleWord(true);
		output.setWrapStyleWord(true);
		input.setLineWrap(true);
		output.setLineWrap(true);
		
		iP.setLocation(20,20);
		modify.setLocation(20,200);
		oP.setLocation(20,120);
		modify2.setLocation(120,200);
		
		modify.addActionListener(this);
		modify2.addActionListener(this);
		
		panel.setLayout(null);
		panel.add(iP);
		panel.add(modify);
		panel.add(oP);
		panel.add(modify2);
		
		this.setContentPane(panel);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(modify)) {
			output.setText(modify1(input.getText()));
		} else if(e.getSource().equals(modify2)) {
			output.setText(modify2(input.getText()));
		}
	}
	
	public static String modify1(String input) {
		return input.replaceAll("[^a-zA-Z]","");
	}
	
	public static String modify2(String input) {
		input = modify1(input).toLowerCase();
		String answer = "";
		String vowels = "aeiou";
		for(int i = 0; i < input.length(); i++) {
			char c = input.charAt(i);
			if(vowels.indexOf(c) != -1) {
				answer += Character.toUpperCase(c);
			} else {
				answer += c;
			}
		}
		return answer;
	}
	
	public static void runGuiForm() {
		StringModifier sm = new StringModifier();
		sm.setSize(260,300);
		sm.setVisible(true);
	}
	
	public static ArrayList<String> openTxtFile(String path) {
		
		ArrayList<String> lines = new ArrayList<String>();
		try{
			
			BufferedReader bf = new BufferedReader(new FileReader(path));
			String line;
	
			while((line = bf.readLine()) != null)
				lines.add(line);
			
			bf.close();
			
		}catch(IOException e)
		{
			System.out.println(e.getMessage());
			System.exit(0);
		}

		return lines;
	}
	
	public static void runInputFileForm() {
		String path = "TestStrings.txt";
		ArrayList<String> lines = openTxtFile(path);
		//lines.add("All 12 wagons should be red, right?");
		//lines.add("33Bobcatz!Rule");
		for(String line: lines) {
			System.out.println("Original: " + line);
			System.out.println("Modified 1: " + modify1(line));
			System.out.println("Modified 2: " + modify2(line) + "\n");
		}
	}
	
	public static void main(String[] args) {
		//runGuiForm();
		//runInputFileForm();
	}
}
