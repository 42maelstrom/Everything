import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.swing.*;


public class TempConv extends JFrame implements ActionListener{
	
	JPanel panel;
	JButton toC;
	JButton toF;
	JTextField fField;
	JTextField cField;
	JLabel fLabel;
	JLabel cLabel;
	
	public TempConv() {
		
		panel = new JPanel();
		panel.setLayout(null);
		
		fLabel = new JLabel("Fahrenheit: ");
		cLabel = new JLabel("Celcius: ");
		fLabel.setSize(100,30);
		cLabel.setSize(100,30);
		fLabel.setLocation(43,10);
		cLabel.setLocation(43,50);
		
		fField = new JTextField();
		cField = new JTextField();
		fField.setLocation(126,10);
		cField.setLocation(126,50);
		fField.setSize(55,30);
		cField.setSize(55,30);
		fField.setText("");
		cField.setText("");
		fField.addActionListener(this);
		cField.addActionListener(this);
		
		toC = new JButton("Convert from F to C");
		toF = new JButton("Convert from C to F");
		toC.setSize(150,30);
		toF.setSize(150,30);
		toC.setLocation(33,100);
		toF.setLocation(33,150);
		toC.addActionListener(this);
		toF.addActionListener(this);
		toC.setFocusPainted(false);
		toF.setFocusPainted(false);
		
		panel.add(toC);
		panel.add(toF);
		panel.add(fField);
		panel.add(cField);
		panel.add(fLabel);
		panel.add(cLabel);
		
		this.setTitle("Temperature converter");
		this.setContentPane(panel);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(220,230);
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(fField) || e.getSource().equals(toC)) {
			try{
				double f = Double.parseDouble(fField.getText());
				double c = (f - 32) * 5 / 9;
				String ans = Double.toString(c);
				int endIndex = Math.max(ans.indexOf('.'),3) + 2;
				cField.setText(ans.substring(0, endIndex));
			} catch(Exception exception) {
				cField.setText("NaN");
			}
		} else if(e.getSource().equals(cField) || e.getSource().equals(toF)) {
			try{
				double c = Double.parseDouble(cField.getText());
				double f = c * 9 / 5 + 32;
				String ans = Double.toString(f);
				int endIndex = Math.max(ans.indexOf('.'),3) + 2;
				fField.setText(ans.substring(0, endIndex));
			} catch(Exception exception) {
				fField.setText("NaN");
			}
				
		}
	}
	
	public static void main(String[] args) {
		JFrame tc = new TempConv();
		tc.setVisible(true);
	}
}
