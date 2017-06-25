import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class BirthdayAnniversary {
	
	public static void main(String[] args) {
		GregorianCalendar gc = new GregorianCalendar(new SimpleTimeZone(-8 * 60 * 60 * 1000, TimeZone.getAvailableIDs(-8 * 60 * 60 * 1000)[0]));
		
		SimpleDateFormat myFormat = new SimpleDateFormat("dd MM yyyy");
		long mPd = 24 * 60 * 60 * 1000;
		
		try {
			Date simonBDay = myFormat.parse("19 09 1997");
			Date momBDay   = myFormat.parse("13 06 1966");
			Date simonAnn  = myFormat.parse("28 05 2010");
			Date momAnn    = myFormat.parse("31 08 1991");
			
			Date today = gc.getTime();
			double simonRatio, momRatio;
			
			do {
				today = gc.getTime();
				System.out.println("Today: " + today);
				System.out.println((today.getTime() - simonBDay.getTime()) / mPd);
		    long simonTogetherTime = (today.getTime() - simonAnn.getTime()) / mPd;
		    System.out.println("Simon togetherTime = " + simonTogetherTime);
		    long momTogetherTime = (today.getTime() - momAnn.getTime()) / mPd;
		    System.out.println("Mom togetherTime = " + momTogetherTime);
		    long simonAge = (today.getTime() - simonBDay.getTime()) / mPd;
		    System.out.println("Simon age = " + simonAge);
		    long momAge = (today.getTime() - momBDay.getTime()) / mPd;
		    System.out.println("Mom age = " + momAge);
		    
		    simonRatio = (double)simonTogetherTime / (double)simonAge;
		    System.out.println("Simon ratio = " + simonRatio);
		    momRatio = (double)momTogetherTime / (double)momAge;
		    System.out.println("Mom ratio = " + momRatio);
		    
		   // String s = new Scanner(System.in).nextLine();
		    gc.add(Calendar.DATE, 1);
			} while(momRatio > simonRatio);
		} catch (ParseException e) {
		    e.printStackTrace();
		}
	}
}
