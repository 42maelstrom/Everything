import java.awt.AWTException;
import java.awt.Desktop;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.Random;

/**
 * A class to automate the survey-entering of the science march madness competition.
 */
public class ScienceRobot{
	
	private Robot bot;
	private int newTabDelay;
	private int choiceDelay;
	private int submitDelay;
	private int openBrowserDelay;
	private int windowCloseRate;
	private final int closeWindowDelay = 300;
	
	public ScienceRobot() {
		try{
			newTabDelay = 1500;
			choiceDelay = 0;
			submitDelay = 0;
			openBrowserDelay = 5000;
			windowCloseRate = 1;
			bot = new Robot();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Set the delay between picking choices.
	 * Use if you want to see what choices it makes.
	 * Keep as zero if you want it to go fast.
	 * Default value is 0.
	 * @param ms - delay, in milliseconds, between choices.
	 */
	public void setChoiceDelay(int ms) {
		choiceDelay = ms;
	}
	
	/**
	 * Set the delay for loading the webpage.
	 * Is called every time the bot votes.
	 * Default value is 1500.
	 * @param ms - delay, in milliseconds, given to open the page.
	 */
	public void setNewTabDelay(int ms) {
		newTabDelay = ms;
	}
	
	/**
	 * Set the delay before submitting choices.
	 * Use if you want to see choices.
	 * Keep as zero for speed.
	 * Default value is 0.
	 * @param ms - delay, in milliseconds, before clicking "submit".
	 */
	public void setSubmitDelay(int ms) {
		submitDelay = ms;
	}
		
	/**
	 * Set the delay for opening the browser.
	 * The bot closes and opens the browser every so often (determined by the "quit rate")
	 * This makes sure that the browser doesn't crash with too many open tabs.
	 * This delay is done after closing, to make sure it has adequate time to close completely,
	 * and after opening the new window, to make sure the new window loads.
	 * Default value is 5000.
	 * @param ms
	 */
	public void setOpenBrowserDelay(int ms) {
		openBrowserDelay = ms;
	}
	
	public void setWindowCloseRate(int rate) {
		windowCloseRate = rate;
	}
	
	private void closeTab() {
		bot.keyPress(KeyEvent.VK_META);
		bot.keyPress(KeyEvent.VK_W);
		bot.keyRelease(KeyEvent.VK_META);
		bot.keyRelease(KeyEvent.VK_W);
	}
	
	private void openWebPage() {
		try{
			String html = "http://kwiksurveys.com/app/rendersurvey.asp?sid=npb6nbq6zo1ig0z509674&refer=www%2Egoogle%2Ecom";
			//for round two;
			html = "http://kwiksurveys.com/app/rendersurvey.asp?sid=b7aqhc7b3t4uzwr514800&refer=";
			//for round 3:
			html = "http://kwiksurveys.com/app/rendersurvey.asp?sid=izyvu3f7og4vnrn516397&refer=";
			// open the default web browser for the HTML page
			Desktop.getDesktop().browse(new URI(html));
			
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Vote once with the given choices. True for the first pick, false for the second pick.
	 * Number of indeces = number of questions.
	 */
	public void vote(boolean[] choices, int numOfTimes) {
		
		int numOfRepeats = numOfTimes / windowCloseRate;
		int remainder = numOfTimes % windowCloseRate;
		if(remainder != 0)
			numOfRepeats++;
		
		double estimateMili = openBrowserDelay + (closeWindowDelay + 200*windowCloseRate)*numOfRepeats + numOfTimes*(newTabDelay + submitDelay + choiceDelay*choices.length);
		double estMin = estimateMili / 1000 / 60;
		
		System.out.println(numOfTimes + " votes: " + ( estimateMili / 1000 ) + " seconds");
		if(estMin > 1)
			System.out.println("=" + estMin + " minutes");
		
		long start = System.nanoTime();
		openWebPage();
		bot.delay(openBrowserDelay);
		
		for(int i = 0; i < numOfTimes; i++){
			System.out.println(i + " votes completed");
			voteOnce(choices);
			if((i + 1) % windowCloseRate == 0){
				bot.delay(closeWindowDelay);
				for(int j = 0; j < windowCloseRate + 3; j++)
					closeTab();				
				bot.delay(closeWindowDelay + 200*windowCloseRate);
				openWebPage();
				bot.delay(10000);
			}
		}
		
		bot.delay(closeWindowDelay);
		for(int i = 0; i < remainder + 1; i++)
			closeTab();
		
		double sec = (System.nanoTime() - start) / 1000000000;
		double min = sec / 60;
		
		System.out.println("Time taken: " + sec + " seconds");
		if(min > 1)
			System.out.println("=" + min + " minutes");
		
	}
	
	public void vote(boolean[] choices) {
		openWebPage();
		bot.delay(openBrowserDelay);
		voteOnce(choices);
		closeTab();
	}
	
	private void voteOnce(boolean[] choices){
	
		openWebPage();
		bot.delay(newTabDelay);
		
		try{
			
			int xPos = 167;
			int yPos = 434;
			int yGap = 146;
			int mask = InputEvent.BUTTON1_MASK;       

			for(int i = 0; i < choices.length; i++) {
				boolean pickChoiceOne = choices[i];
				
				if(pickChoiceOne == false)
					yPos+= 30;

				bot.mouseMove(xPos, yPos);
				bot.mousePress(mask);     
				bot.mouseRelease(mask);	
				
				bot.delay(choiceDelay);
				
				yPos+=yGap;
				
				if(pickChoiceOne == false)
					yPos -=30;
			}

			
			bot.mouseMove(860, yPos - 64);

			bot.delay(submitDelay);
			
			bot.mousePress(mask);     
			bot.mouseRelease(mask);		
			
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}	

	public static void main(String[] args) {
		ScienceRobot bot = new ScienceRobot();
		boolean choices[] = {true};
		bot.setNewTabDelay(5000);
		bot.setOpenBrowserDelay(10000);
		bot.setWindowCloseRate(20);
		bot.vote(choices, 300000);
	}
}
