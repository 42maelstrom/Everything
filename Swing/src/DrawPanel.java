import java.awt.*;
import javax.swing.*;
import java.util.Random;

public class DrawPanel extends JPanel {

	private void doDrawing(Graphics g) {
			
		Graphics2D g2d = (Graphics2D) g;
		
		g2d.setColor(Color.RED);
		
		for(int i = 0; i < 100; i++) {
			Dimension size = getSize();
			Insets insets = getInsets();
			
			int w = size.width - insets.left - insets.right;
			int h = size.height - insets.top - insets.bottom;
	
			Random r = new Random();
			int x = Math.abs(r.nextInt()) % w;
			int y = Math.abs(r.nextInt()) % h;
			
			g2d.drawLine(x,y,x,y);
		}
		
		Font f = new Font("Times New Roman", Font.PLAIN, 16);
		Font f2 = new Font("Monaco", Font.PLAIN, 56);
		
		FontMetrics fm = g.getFontMetrics(f);
		FontMetrics fm2 = g.getFontMetrics(f2);
		int cx = 75;
		int cy = 100;
		g.setFont(f);
		g.drawString("Hello, ", cx, cy);
		cx += fm.stringWidth("Hello, ");
		g.setFont(f2);
		g.drawString("World!", cx, cy);
		
	}
		
	@Override
	public void paintComponent(Graphics g) {
		System.out.println("Hi");
		super.paintComponent(g);
		doDrawing(g);
	}
}
