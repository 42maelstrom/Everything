import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.RenderingHints;

import javax.swing.JPanel;
import javax.swing.JLabel;

/*
 * Things to do for this program
 * -Fix display issues with the axis labels and rounding
 * -Finish GUI so that you can input xMin, xMax, etc.
 * -Make it auto-resizable with the window
 * -Make it more perfectly like how Wolfram Alpha graphs
 */

public class Graph extends JPanel {
	//as of now, note that the following must be true:
	//	width - 2*xPadding = 600, height - 2*yPadding = 400
	private final int width = 660, height = 480;			// width and height of the panel
	private final double slope, yInt, xInt; 				// slope, x-intercept, and y-intercept of the panel
	private final int xPadding = 30, yPadding = 40; 		// amount of white space on either side of the graph
	private final int graphWidth = width - 2*xPadding;		// width of the graph, in pixels
	private final int graphHeight = height - 2*yPadding;	// height of the graph, in pixels 
	private final int xPixelMin = xPadding;					// the left-most point on the graph, in pixels
	private final int xPixelMax = width - xPadding;			// the right-most point on the graph, in pixels
	private final int yPixelMin = yPadding;					// the highest point on the graph, in pixels (lowest pixel number)
	private final int yPixelMax = height - yPadding;		// the lowest point on the graph, in pixels (highest pixel number)
	private final int originX = width / 2, originY;			// pixel numbers for the point (0, 0) on the graph

	private final double xMin, xMax, yMin, yMax; 			// min and max's for x and y, point values (not pixel)
  
	private final double xScale, yScale;
	private final int xMajTickRate = 5;						// every so many minor ticks, there is a major tick, which is
	private final int yMajTickRate = 5;						// larger and has a label on it. This says how often the minor ticks are labeled.
	private final int xPixelsPerTick = 17;					// how many pixels for each minor tick
	private final int yPixelsPerTick = 13;
	private final double xTickScale, yTickScale;			// the distance between major ticks.
	
	private final JLabel label; 
	
	public Graph(double slope, double yInt) {
		
		setBackground(Color.WHITE);
		setSize(width, height);
		setLayout(null);
		
		label = new JLabel("Created by Simon Alford, modelled after "
				+ "Wolfram|Alpha's graphing of linear equations");
		label.setFont(new Font("Arial", Font.PLAIN, 11));
		label.setForeground(Color.GRAY);
		label.setSize(600,30);
		label.setLocation(xPixelMin, height - 30);
		
		add(label);
		
		this.slope = slope;
		this.yInt = yInt;
		xInt = - yInt / slope;

		if(yInt == 0) {
			if(slope == 0) {
				xTickScale = 1;
				yTickScale = 1;
			} else {
				xTickScale = 1;
				yTickScale = axis(Math.abs(slope));
			}
		} else {
			yTickScale = axis(Math.abs(yInt));
			
			if(slope == 0) {
				xTickScale = 1;
			} else {
				xTickScale = axis(Math.abs(xInt));
			}
		}
		
		xScale = xPixelsPerTick*xMajTickRate / xTickScale;
		yScale = yPixelsPerTick*yMajTickRate / yTickScale;
		
		xMax = graphWidth / 2 / xScale;
		xMin = -xMax;
		
		if(yInt < 0) {
								 //move up one major tick
			originY = height / 2 - yPixelsPerTick*yMajTickRate;
			yMax = graphHeight / 2 / yScale - yPixelsPerTick*yMajTickRate / yScale;
			yMin = -(graphHeight / 2 / yScale + yPixelsPerTick*yMajTickRate / yScale);
		} else if(yInt > 0) {
						  		 //move down one major tick
			originY = height / 2 + yPixelsPerTick*yMajTickRate;
			yMax = graphHeight / 2 / yScale + yPixelsPerTick*yMajTickRate / yScale;
			yMin = -(graphHeight / 2 / yScale - yPixelsPerTick*yMajTickRate / yScale);
		} else {
			//smack dab in the middle.
			originY = height / 2;
			yMax = graphHeight / 2 / yScale;
			yMin = - yMax;
		}
	}
	
	public void paintComponent(Graphics g) {
		//this way it fills in the background color!
		super.paintComponent(g);

		//draw the axes
		g.setColor(Color.GRAY);
		g.drawLine(xPixelMin, originY, xPixelMax, originY);
		g.drawLine(originX, yPixelMin, originX, yPixelMax);
		 
		//label axes with x and y
		g.setFont(new Font("Georgia", Font.ITALIC, 20));
		g.setColor(Color.DARK_GRAY);
		g.drawString("y", originX - 6, yPixelMin- 13);
		g.drawString("x", xPixelMax + 13, originY + 5);

		//font for axis major tick labels
		g.setFont(new Font("Palatino", Font.PLAIN, 20));
		g.setColor(Color.GRAY);
		
		int n = 0; // the n'th major tick /label
		
		//add ticks and labels to x axis
		for(int i = 1; originX + i*xPixelsPerTick < xPixelMax; i++) {
			
			if(i % xMajTickRate == 0) {
				n++;
				g.drawLine(originX + i*xPixelsPerTick, originY, originX + i*xPixelsPerTick, originY - 5);
				g.drawLine(originX - i*xPixelsPerTick, originY, originX - i*xPixelsPerTick, originY - 5);
				
				g.setColor(Color.DARK_GRAY);

				String label = format(xTickScale*n, 7);
				int sWidth = g.getFontMetrics().stringWidth(label);
				g.drawString(label, originX + i*xPixelsPerTick - sWidth / 2 , originY + 24);

				label = format(xTickScale*-n, 7);
				sWidth = g.getFontMetrics().stringWidth(label);
				g.drawString(label, originX - i*xPixelsPerTick - sWidth / 2, originY + 24);
				
				g.setColor(Color.GRAY);
			} else {
				g.drawLine(originX + i*xPixelsPerTick, originY, originX + i*xPixelsPerTick, originY - 3);
				g.drawLine(originX - i*xPixelsPerTick, originY, originX - i*xPixelsPerTick, originY - 3);
			}
			
		}
		
		n = 0;
		//add ticks and labels to the y-axis (bottom part)
		for(int i = 1; originY + i*yPixelsPerTick < yPixelMax; i++) {
			if(i % yMajTickRate == 0) {
				n++;
				g.drawLine(originX, originY + i*yPixelsPerTick, originX + 5, originY + i*yPixelsPerTick);
				g.setColor(Color.DARK_GRAY);
				String label = format(yTickScale*-n, 7);
				int sWidth = g.getFontMetrics().stringWidth(label);
				int sHeight = 14; //using g.getFontMetrics().getHeight() was giving too large of a height...
				g.drawString(label, originX - sWidth - 7 ,  originY + i*yPixelsPerTick + sHeight / 2);
				g.setColor(Color.GRAY);
			} else {
				g.drawLine(originX, originY + i*yPixelsPerTick, originX + 3, originY + i*yPixelsPerTick);
			}
		}
		
		n = 0;
		//add ticks and labels to the y-axis (upper part)
		for(int i = 1; originY - i*yPixelsPerTick > yPixelMin; i++) {
			if(i % yMajTickRate == 0) {
				n++;
				g.drawLine(originX, originY - i*yPixelsPerTick, originX + 5, originY - i*yPixelsPerTick);
				g.setColor(Color.DARK_GRAY);
				String label = format(yTickScale*n, 7);
				int sWidth = g.getFontMetrics().stringWidth(label);
				int sHeight = 14;
				g.drawString(label, originX - sWidth - 7 ,  originY - i*yPixelsPerTick + sHeight / 2);
				g.setColor(Color.GRAY);
			} else {
				g.drawLine(originX, originY - i*yPixelsPerTick, originX + 3, originY - i*yPixelsPerTick);
			}
		}
		
		//draw the line for the linear equation (in dark blue!)
		final Color darkBlue = new Color(50, 70, 160);
		g.setColor(darkBlue);
		
		int xStart = xPixelMin, xEnd = xPixelMax; //positions of where to start and stop drawing the line
		
		//find lowest x value where f(x) is inside range
		while(false == (fPixel(xStart) <= yPixelMax && fPixel(xStart) >= yPixelMin))
			xStart += 1;
		
		//find highest x value where f(x) is inside range
		while(false == (fPixel(xEnd) <= yPixelMax && fPixel(xEnd) >= yPixelMin))
			xEnd -= 1;
		
		g.drawLine(xStart, fPixel(xStart), xEnd, fPixel(xEnd));
	
		if(slope == 0) {			
			g.drawLine(xStart, fPixel(xStart) + 1, xEnd, fPixel(xEnd) + 1);
		} else {
			g.drawLine(xStart - 1,  fPixel(xStart), xEnd - 1,  fPixel(xEnd));
			g.drawLine(xStart + 1, fPixel(xStart), xEnd + 1, fPixel(xEnd));
		}
		
		//mark y-intercept
		g.setColor(Color.RED);
		int radius = 3; //radius of the oval being drawn over the point, so it's centered
		g.fillOval(originX - radius, yPointToPixel(yInt) - radius, 2*radius, 2*radius);
		
		//label y-intercept
		g.setColor(Color.DARK_GRAY);
		String y = format(yInt, 2);
		String label = "(0, " + y + ")";
		int sHeight = 14;
		if(slope < 0) {
			g.drawString(label, originX + 8, yPointToPixel(yInt) + sHeight / 2 - 6);
		} else if(slope > 0) {
			g.drawString(label, originX + 8, yPointToPixel(yInt) + sHeight / 2 + 6);
		} else {
			g.drawString(label, originX + 8, yPointToPixel(yInt) + sHeight / 2 - 12);
		}
		
		//label and mark x-intercept as long as it's not the same as the y-intercept
		if(yInt != 0) {
			g.setColor(Color.RED);
			g.fillOval(xPointToPixel(xInt) - radius, originY - radius, 2*radius, 2*radius);
			
			g.setColor(Color.DARK_GRAY);
			String x = format(xInt, 2);
			label = "(" + x + ", 0)";
			int sWidth = g.getFontMetrics().stringWidth(label);
		if(slope > 0) {
			g.drawString(label, xPointToPixel(xInt) - sWidth, originY - 10);
		} else {
			g.drawString(label, xPointToPixel(xInt) , originY - 10);
		}
		}
	}
	
	/**
	 * Returns a string that is the value rounded to sigFig number of sig Figs.
	 * It also attempts to make rounding errors not as messy.
	 */
	private String format(double value, int sigFigs) {
		int decPlaces = sigFigs - 1 - (int)Math.floor(Math.log10(Math.abs(value)));
		double m = Math.pow(10, decPlaces);
		if(Math.abs(value) > 1 / m)
			value = Math.floor(m*value) / m;
		String s = Double.toString(value);
		if(Math.floor(value) == value)
			s = s.substring(0, s.length() - 2);
		while(s.indexOf('.') != -1 && s.charAt(s.length() - 1) == '0')
			s = s.substring(0, s.length() - 1);
		return s;
	}

	/**
	 * Given the x-intercept or y-intercept of the graph,
	 * this decides what the xTickScale or yTickScale should be
	 * The scale will always be 1, 2, or 5, or one of those times 10^n.
	 * @param intercept - the intercept of the graph
	 * @return - the value for the xTickScale.
	 */
	private double axis(double intercept) {
		int l = (int)Math.floor(Math.log10(intercept));
		int i = (int)(intercept * Math.pow(10, -l));
		
		if(i >= 8) {
			return 10*Math.pow(10, l);
		} else if(i >= 4) {
			return 5*Math.pow(10, l);
		} else {
			return 2*Math.pow(10, l);
		}
	}
	
	/**
	 * Returns the y-pixel from the linear equation for the pixel x.
	 */
	private int fPixel(int x) {
		return (int)yPointToPixel(f(xPixelToPoint(x)));
	}

	/**
	 * Returns the y-value of the linear equation for the point x.
	 */
	private double f(double x) {
		return x*slope + yInt;
	}
	
	/**
	 * Converts the x-pixel into its corresponding x-value on the graph.
	 */
	private double xPixelToPoint(int pixel) {
		return (pixel - originX) / xScale;
	}
	
	/**
	 * Converts the y-pixel into its corresponding y-value on the graph.
	 * This was never used so it might not actually work.
	 */
	private double yPixelToPoint(int pixel) {
		return -(pixel - originY) / yScale;
	}
	
	/**
	 * Converts the x-value on the graph to its x-pixel value.
	 */
	private int xPointToPixel(double point) {
		return (int)(point*xScale + originX);
	}
	
	/**
	 * Converts the y-value on the graph to its y-pixel value.
	 */
	private int yPointToPixel(double point) {
		return (int)(-point*yScale + originY);
	}
	
}
