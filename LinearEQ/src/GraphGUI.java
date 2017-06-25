import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Scanner;

import javax.swing.*;

public class GraphGUI implements ActionListener, KeyListener{
	
	private JFrame window;
	private JPanel panel;
	private JLabel l1, l2, l3;
	private JTextField aField, bField;
	private JButton button;
	private JPanel graph;

	public GraphGUI() {
		
		window = new JFrame("Linear equation grapher");
		panel = new JPanel();
		panel.setLayout(null);
		
		int initSlope = -4, initYInt = 2;
		graph = new Graph(initSlope, initYInt);
		graph.setLocation(0,0);
		
		int h = graph.getHeight() + 10;
		
		l1 = new JLabel("y = ");
		l1.setSize(100, 30);
		l1.setLocation(50, h);

		aField = new JTextField(Integer.toString(initSlope));
		aField.setSize(50,30);
		aField.setLocation(80,h);
		aField.addKeyListener(this);
		
		l2 = new JLabel("x");
		l2.setSize(50,30);
		l2.setLocation(130,h);
		
		l3 = new JLabel("+");
		l3.setSize(50,30);
		l3.setLocation(150,h);

		bField = new JTextField(Integer.toString(initYInt));
		bField.setSize(50,30);
		bField.setLocation(170,h);
		bField.addKeyListener(this);
		
		button = new JButton("Graph");
		button.setSize(70, 30);
		button.setLocation(250, h);
		button.addActionListener(this);
		button.addKeyListener(this);
		
		panel.add(button);
		panel.add(l1);
		panel.add(l2);
		panel.add(l3);
		panel.add(aField);
		panel.add(bField);
		panel.add(graph);
		
		window.addKeyListener(this);
		window.setContentPane(panel);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setSize(graph.getWidth(), graph.getHeight() + 75);
		window.setVisible(true);
		
		aField.requestFocus();
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(button)) {
			updateGraph();
		}
	}

	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			updateGraph();
		}
	}
	
	public void updateGraph() {
		try{
			double slope = Double.parseDouble(aField.getText());
			double yInt = Double.parseDouble(bField.getText());
			panel.remove(graph);
			graph = new Graph(slope, yInt);
			graph.setLocation(0,0);
			panel.add(graph);
			panel.repaint();
		} catch (Exception ex) {
			System.out.println(ex.toString());
		}
	}

	public void keyTyped(KeyEvent e) {

	}


	public void keyPressed(KeyEvent e) {
		
	}
	
	public static void main(String[] args) {
		GraphGUI g = new GraphGUI();
	}
}
