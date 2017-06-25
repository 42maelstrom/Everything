import java.awt.AWTException;
import java.awt.Desktop;
import java.awt.Robot;
import java.io.File;
import java.io.IOException;

public class RobotFun{
	
	Robot bot = new Robot();
	
	public static void main(String[] args) throws AWTException, IOException{
		
		RobotFun test = new RobotFun();
	}
	
	public RobotFun() throws AWTException, IOException{
		// using this in real life, you'd probably want to check that the desktop
		// methods are supported using isDesktopSupported()...

//		String htmlFilePath = "http://www.youtube.com"; // path to your new file
//		File htmlFile = new File(htmlFilePath);
//
//		// open the default web browser for the HTML page
//		Desktop.getDesktop().browse(htmlFile.toURI());
//
//		// if a web browser is the default HTML handler, this might work too
//		Desktop.getDesktop().open(htmlFile);
		
		bot.setAutoDelay(40);
		for(int xPos = 0; xPos < 1700; xPos+=100)
		{
			for(int yPos = 0; yPos<1200; yPos+=100){
				System.out.println(xPos + ", " + yPos);
				bot.mouseMove(xPos, yPos);
				bot.delay(20);
			}
		}
	}	
}
