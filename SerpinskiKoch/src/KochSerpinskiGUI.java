import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

public class KochSerpinskiGUI extends JFrame implements ChangeListener, ActionListener {
	
	private KochSerpinskiPanel ksp;
	
	private JLabel minLengthLabel;
	private JSlider minLengthSlider;
	
	private JLabel lineThicknessLabel;
	private JSlider lineThicknessSlider;
	
	private JButton savePicButton;
	
	private JLabel sizeLabel;
	private JTextField sizeField;

	private JDialog dialog;
	private JButton dialogButton;
	private JLabel dialogLabel;
	
	double minDepth = 0.125;
	double maxDepth = 256;
	float maxThickness = 2;
	
	public KochSerpinskiGUI() {
		super("Koch/Serpinski Snowflake Generator");
		getContentPane().setBackground(Color.WHITE);
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double width = screenSize.getHeight();
		double height = screenSize.getHeight();
		
		setLayout(null);
		setSize((int)width, (int)height);
		
		ksp = new KochSerpinskiPanel();
		ksp.setLocation(180, 0);
		add(ksp);

		//it's switched for max- and min- depth giving min and max on slider because 
		//as 'depth' increases on slider, the minScale decreases.
		int min = minScaleToSliderVal(maxDepth);
		int max = minScaleToSliderVal(minDepth);
		int val = minScaleToSliderVal(ksp.minScale);
		minLengthSlider = new JSlider(JSlider.VERTICAL, min, max, val);
		minLengthSlider.addChangeListener(this);
		add(minLengthSlider);
		
		minLengthLabel = new JLabel("Depth");
		minLengthLabel.setSize(500, 100);
		minLengthLabel.setFont(new Font("Arial", Font.ITALIC, 12));
		add(minLengthLabel);
		
		lineThicknessSlider = new JSlider(JSlider.VERTICAL, 0, lineThicknessToSliderVal(maxThickness), (int)(100*ksp.lineThickness));
		lineThicknessSlider.addChangeListener(this);
		add(lineThicknessSlider);
		
		lineThicknessLabel = new JLabel("Thickness");
		lineThicknessLabel.setSize(500, 100);
		lineThicknessLabel.setFont(new Font("Arial", Font.ITALIC, 12));
		add(lineThicknessLabel);
		
		savePicButton = new JButton("Save picture");
		savePicButton.setSize(140, 40);
		savePicButton.addActionListener(this);
		add(savePicButton);
		
		sizeLabel = new JLabel("Picture height in pixels");
		sizeLabel.setFont(new Font("Arial", Font.PLAIN, 12));
		sizeLabel.setSize(200, 30);
		add(sizeLabel);
		
		sizeField = new JTextField("500");
		sizeField.setSize(50, 30);
		add(sizeField);
		
		resize();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	public void resize() {
		minLengthSlider.setSize(25, getHeight() - 150);
		minLengthSlider.setLocation(35, 5);
		minLengthLabel.setLocation(29, minLengthSlider.getHeight() + (int)minLengthSlider.getLocation().getY() - 45);
		lineThicknessSlider.setSize(minLengthSlider.getWidth(), minLengthSlider.getHeight());
		lineThicknessSlider.setLocation(105, 5);
		lineThicknessLabel.setLocation(91, lineThicknessSlider.getHeight() + (int)lineThicknessSlider.getLocation().getY() - 45);
		savePicButton.setLocation(19, getHeight() - 115);
		sizeField.setLocation(130, getHeight() - 65);
		sizeLabel.setLocation(5, getHeight() - 65);
		ksp.setSize(getWidth() - ksp.getLocation().x, getHeight());
	}
	
	public double sliderValToMinScale(int val) {
		return Math.pow(2, .1*(double)(80 - val));
	}
	
	public int minScaleToSliderVal(double minScale) {
		return (int)(80 - 10*(Math.log(minScale)/Math.log(2)));
	}
	
	public float sliderValToLineThickness(int val) {
		//so that it doesn't give an error for giving a value of 0 line thickness.
		if(val == 0) {
			return Float.MIN_VALUE;
		} else {
			return (float)val / 100f;
		}
	}
	
	public int lineThicknessToSliderVal(float lineThickness) {
		return (int) lineThickness*100;
	}

	@Override
	public void stateChanged(ChangeEvent arg0) {
		JSlider source = (JSlider)arg0.getSource();
		
		if(source.equals(minLengthSlider)) {
			ksp.minScale = sliderValToMinScale(source.getValue());
		} else if(source.equals(lineThicknessSlider)) {
			ksp.lineThickness = sliderValToLineThickness(source.getValue());
		}
		
		ksp.repaint();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		JButton source = (JButton)arg0.getSource();
		
		if(source == savePicButton) {
			int newHeight = Integer.parseInt(sizeField.getText());
			int newWidth = (int)(newHeight * ksp.SQRT_3 / 2);
        
      JFileChooser fc = new JFileChooser();
      String[] extensions = {".png", ".wbmp", ".bmp", ".gif", ".jpeg"};
      
      //to remove the default "all files" filter
      fc.removeChoosableFileFilter(fc.getAcceptAllFileFilter());
      FileNameExtensionFilter fnef = null;
      for(String extension: extensions) {
      	fnef = new FileNameExtensionFilter(extension.substring(1).toUpperCase(), extension);
      	fc.addChoosableFileFilter(fnef);
      }
      
      //to make sure JPEG is the default filter.
      fc.setFileFilter(fnef);
      
      int result = fc.showSaveDialog(this);

      dialog = new JDialog(this);
      dialog.setLayout(new BoxLayout(dialog.getContentPane(), BoxLayout.PAGE_AXIS));
      dialog.getRootPane().setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
      dialogLabel = new JLabel();
      dialogLabel.setPreferredSize(new Dimension(100, 40));
      dialogLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
      dialogButton = new JButton("OK");
      dialogButton.setAlignmentX(Component.CENTER_ALIGNMENT);
      dialogButton.setPreferredSize(new Dimension(100, 40));
      dialog.add(dialogLabel);
      dialog.setResizable(false);
      dialogButton.addActionListener(this);
      
      if(result == JFileChooser.APPROVE_OPTION) {
      	try {
      		dialog.setTitle("Loading");
      		dialogLabel.setText("Saving Photo...");
      		dialog.setSize(200, 80);
      		dialog.setLocation(this.getWidth() / 2 - dialog.getWidth() / 2, this.getHeight() / 2 - dialog.getHeight() / 2);
      		dialog.setLocationRelativeTo(this);
      		dialog.setVisible(true);
      		
        	File file  = fc.getSelectedFile();
        	KochSerpinskiPanel newKSP = new KochSerpinskiPanel();
        	//these have to be changed because the new picture might be bigger/smaller
        	//bigger would make line thickness appear smaller and minscale be deeper.
        	//colors will also be different but I don't care about that.
        	newKSP.minScale = ksp.minScale / ksp.getHeight() * newHeight;
        	newKSP.lineThickness = ksp.lineThickness / ksp.getHeight() * newHeight;
        	newKSP.setSize(newWidth, newHeight);
        	BufferedImage image = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
        	Graphics2D graphics2D = image.createGraphics();
        	newKSP.paint(graphics2D);
        	
      	  ImageIO.write(image, ((FileNameExtensionFilter)fc.getFileFilter()).getExtensions()[0].substring(1), file);
        	
        	dialog.setTitle("Success");
        	dialog.add(dialogButton);
        	dialogLabel.setText("Image saved successfully");
        	dialog.setSize(200, 100);
      	} catch(Throwable e) {
      		dialog.setTitle("Error");
      		dialog.add(dialogButton);
      		dialogLabel.setText("<html>Error creating photo.<br>Picture was probably too large.<br>Try giving the JVM more memory.</html>");
      		dialog.setSize(239, 117);
      		//because this isn't working out so well.
      		System.exit(0);
      	} finally {
      		dialog.setLocation(this.getWidth() / 2 - dialog.getWidth() / 2, this.getHeight() / 2 - dialog.getHeight() / 2);
      		dialog.setLocationRelativeTo(this);
      		dialog.setVisible(true);
      	}
      }
		} else if( source == dialogButton) {
			dialog.setVisible(false);
		}
	}
	
	//to make sure that when this jframe window is resized, the koch-serpinski panel 
	//is also resized.
	@Override
	public void validate() {
		resize();
		super.validate();
	}
}
