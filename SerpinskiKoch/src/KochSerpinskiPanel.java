
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;

public class KochSerpinskiPanel extends JPanel {
	
	public double minScale = 1;
	public float lineThickness = 0.5f;
	public static final double SQRT_3 = Math.sqrt(3);
	
	private enum SideType {
		LEFT, RIGHT, CENTER;
	}
	
	public KochSerpinskiPanel() {
		super();
		this.setLayout(null);
	}
	
	/**
	 * In case you want to have it change colors based on scale. This would get called
	 * in drawSerpinski() to see what color to use at this depth.
	 * @param scale - the scale of the serpinski triangle
	 * @return the color to draw
	 */
	private Color getColor(double scale) {
		return Color.RED;
//		float r, g, b;
//		// value between 0 and 1 (percent)   z
//		double val = (4 + Math.log(scale) / Math.log(2)) / 15;
//		r = (float)Math.min(Math.max(0, 1.5-Math.abs(1-4*(val-0.5))),1);
//		g = (float)Math.min(Math.max(0, 1.5-Math.abs(1-4*(val-0.25))),1);
//		b = (float)Math.min(Math.max(0, 1.5-Math.abs(1-4*val)),1);
//		
//		return new Color(r, g, b);
	}
	
	/**
	 * Draw the snowflake. This calls drawSerpinskiKoch(......), which does it more generally.
	 */
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		setBackground(Color.WHITE);
		super.paintComponent(g2d);
		int scale = (int)((Math.min(this.getHeight(), this.getWidth()*2/SQRT_3)) / 4 - 10);
		Point center = new Point(this.getWidth() / 2, this.getHeight() / 2 - 10);
	
		//g2d.setColor(Color.RED);
		g2d.setStroke(new BasicStroke(lineThickness));
		drawSerpinskiKoch(g2d, center, scale, true);
	}
	
	/**
	 * @param center - the center of the snowflake.
	 * @param scale - the scale of the center Serpinski triangle = distance from
	 *  center of triangle to the midpoint of an edge of the Serpinski triangle 
	 * @param isRightSideUp - whether or not to draw it right-side up.
	 */
	private void drawSerpinskiKoch(Graphics2D g, Point center, double scale, boolean isRightSideUp) {
		double up;
		if(isRightSideUp) {
			up = 1;
		} else {
			up = -1;
		}
		
		//draw a serpinski triangle in the middle, then draw kochserpinski sides on each side
		
		drawSerpinski(g, center, scale, isRightSideUp, true, true, true);
		
		Point leftPoint = new Point(center.x - .5*SQRT_3*scale, center.y - up*.5*scale); 
		Point rightPoint = new Point(center.x + .5*SQRT_3*scale, center.y - up*.5*scale);
		Point centerPoint = new Point(center.x, center.y + up*scale);
		
		drawKochSerpinskiSide(g, centerPoint, scale*SQRT_3*2/3, !isRightSideUp, SideType.CENTER);

		if(isRightSideUp) {
			drawKochSerpinskiSide(g, leftPoint, scale*SQRT_3*2/3, isRightSideUp, SideType.LEFT);
			drawKochSerpinskiSide(g, rightPoint, scale*SQRT_3*2/3, isRightSideUp, SideType.RIGHT);
		} else {
			drawKochSerpinskiSide(g, rightPoint, scale*SQRT_3*2/3, isRightSideUp, SideType.LEFT);
			drawKochSerpinskiSide(g, leftPoint, scale*SQRT_3*2/3, isRightSideUp, SideType.RIGHT);
		}
	}
	
	/**
	 * @param center - the midpoint of the side
	 * @param scale - the length of the side / 3. 
	 * @param isRightSideUp - if the spiky side is facing more upwards than downwards.
	 */
	private void drawKochSerpinskiSide(Graphics2D g, Point center, double scale, boolean isRightSideUp, SideType sideType) {
		//scale of the serpinski triangle that will be drawn.
		if((scale / (2*SQRT_3)) < minScale)
			return;
		
		double up;
		if(isRightSideUp) {
			up = 1;
		} else {
			up = -1;
		}
		
		switch(sideType) {
		case LEFT: 
			Point newSerpinskiCenter = new Point(center.x - up*.25*scale, center.y - up*.25*scale / SQRT_3);
			drawSerpinski(g, newSerpinskiCenter, scale / (2*SQRT_3), !isRightSideUp, isRightSideUp, !isRightSideUp, true);
			
			Point leftSmallerSideCenter = new Point(center.x - .5*scale, center.y + .5*SQRT_3*scale);
			Point rightSmallerSideCenter = new Point(center.x + .5*scale, center.y - .5*SQRT_3*scale);
			
			drawKochSerpinskiSide(g, leftSmallerSideCenter, scale / 3, isRightSideUp, SideType.LEFT);
			drawKochSerpinskiSide(g, rightSmallerSideCenter, scale / 3, isRightSideUp, SideType.LEFT);
			
			//center for the new fractal side on the center serpinski triangle just drawn 6 lines ago.
			Point centerCenter = new Point(center.x - up*.25*scale, center.y - up*.25*SQRT_3*scale);
			Point rightCenter = new Point(center.x - up*.5*scale, center.y);
			
			drawKochSerpinskiSide(g, centerCenter, scale / 3, isRightSideUp, SideType.CENTER);
			drawKochSerpinskiSide(g, rightCenter, scale / 3, !isRightSideUp, SideType.RIGHT);
			
			break;
		case RIGHT:
			newSerpinskiCenter = new Point(center.x + up*.25*scale, center.y - up*.25*scale / SQRT_3);
			drawSerpinski(g, newSerpinskiCenter, scale / (2*SQRT_3), !isRightSideUp, !isRightSideUp, isRightSideUp, true);
			
			leftSmallerSideCenter = new Point(center.x + .5*scale, center.y + .5*SQRT_3*scale);
			rightSmallerSideCenter = new Point(center.x - .5*scale, center.y - .5*SQRT_3*scale);
			
			drawKochSerpinskiSide(g, leftSmallerSideCenter, scale / 3, isRightSideUp, SideType.RIGHT);
			drawKochSerpinskiSide(g, rightSmallerSideCenter, scale / 3, isRightSideUp, SideType.RIGHT);
			
			//center for the new fractal side on the center serpinski triangle just drawn 6 lines ago.
			centerCenter = new Point(center.x + up*.25*scale, center.y - up*.25*SQRT_3*scale);
			Point leftCenter = new Point(center.x + up*.5*scale, center.y);
			
			drawKochSerpinskiSide(g, centerCenter, scale / 3, isRightSideUp, SideType.CENTER);
			drawKochSerpinskiSide(g, leftCenter, scale / 3, !isRightSideUp, SideType.LEFT);
			
			break;
		case CENTER:
			newSerpinskiCenter = new Point(center.x, center.y - up*(.5/SQRT_3)*scale);
			drawSerpinski(g, newSerpinskiCenter, scale / (2*SQRT_3), isRightSideUp, true, true, false);
			
			leftSmallerSideCenter = new Point(center.x - scale, center.y);
			rightSmallerSideCenter = new Point(center.x + scale, center.y);
			
			drawKochSerpinskiSide(g, leftSmallerSideCenter, scale / 3, isRightSideUp, SideType.CENTER);
			drawKochSerpinskiSide(g, rightSmallerSideCenter, scale / 3, isRightSideUp, SideType.CENTER);
			
			rightCenter = new Point(center.x + up*.25*scale, center.y - up*.25*SQRT_3*scale);
			leftCenter = new Point(center.x - up*.25*scale, center.y - up*.25*SQRT_3*scale);
			
			drawKochSerpinskiSide(g, rightCenter, scale / 3, isRightSideUp, SideType.RIGHT);
			drawKochSerpinskiSide(g, leftCenter, scale / 3, isRightSideUp, SideType.LEFT);
			
			break;
		}
	}
	
	/**
	 * Draw a Serpinski triangle.
	 * @param g
	 * @param center - the center of the triangle
	 * @param scale - the distance in pixels from the center of the triangle to the midpoint of a side of the triangle.
	 * @param isRightSideUp - if true, the triangle is pointing upwards.
	 */
	private void drawSerpinski(Graphics2D g, Point center, double scale, boolean isRightSideUp,
			boolean drawLeftSide, boolean drawRightSide, boolean drawCenterSide) {
		
		if(scale < minScale)
			return;
		
		g.setColor(getColor(scale));
		double up;
		if(isRightSideUp) {
			up = 1;
		} else {
			up = -1;
		}
		
		Point leftCorner = new Point(center.x - SQRT_3*scale, center.y + up*scale);
		Point rightCorner = new Point(center.x + SQRT_3*scale, center.y + up*scale);
		Point centerCorner = new Point(center.x, center.y - up*2*scale);

		if(drawCenterSide)
			g.drawLine(leftCorner.getIntX(), leftCorner.getIntY(), rightCorner.getIntX(), rightCorner.getIntY());
		if(drawLeftSide)
			g.drawLine(leftCorner.getIntX(), leftCorner.getIntY(), centerCorner.getIntX(), centerCorner.getIntY());
		if(drawRightSide)
			g.drawLine(rightCorner.getIntX(), rightCorner.getIntY(), centerCorner.getIntX(), centerCorner.getIntY());
		
		if(scale < minScale*2)
			return;
		
		Point leftPoint = new Point(center.x - .5*SQRT_3*scale, center.y - up*.5*scale); 
		Point rightPoint = new Point(center.x + .5*SQRT_3*scale, center.y - up*.5*scale);
		Point centerPoint = new Point(center.x, center.y + up*scale);
		
		g.drawLine(leftPoint.getIntX(), leftPoint.getIntY(), rightPoint.getIntX(), rightPoint.getIntY());
		g.drawLine(leftPoint.getIntX(), leftPoint.getIntY(), centerPoint.getIntX(), centerPoint.getIntY());
		g.drawLine(rightPoint.getIntX(), rightPoint.getIntY(), centerPoint.getIntX(), centerPoint.getIntY());
		
		Point leftStart = new Point(center.x - .5*SQRT_3*scale, center.y + up*.5*scale);
		Point rightStart = new Point(center.x + .5*SQRT_3*scale, center.y + up*.5*scale);
		Point centerStart = new Point(center.x, center.y - up*scale);
		
		drawSerpinski(g, leftStart, scale / 2, isRightSideUp, false, false, false);
		drawSerpinski(g, rightStart, scale / 2, isRightSideUp, false, false, false);
		drawSerpinski(g, centerStart, scale / 2, isRightSideUp, false, false, false);
	}
}
