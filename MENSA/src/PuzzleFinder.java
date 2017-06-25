import java.io.FileWriter;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.ArrayList;
import java.util.Arrays;

public class PuzzleFinder {
	static ArrayList<Class> classes = new ArrayList<Class>();
	static String[] monthAbbreviations = {"Jan", "Feb", "Mar", "Apr", "May", "Jun"
		, "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

	static {
		//add all of the possible classes to the list.
		for(int month = 0; month < 12; month++) {
			for(int day = 1; day < 31; day++) {
				try {
					Class c = Class.forName(monthAbbreviations[month] + Integer.toString(day));
					if(null != c)
						classes.add(c);
				} catch(Exception e) { 
					
				}
			}
		}
	}
	
	public static void makePuzzleListFile() {
		try {
			PrintWriter pw = new PrintWriter(new FileWriter("PuzzleList.txt", false));
			for(Class c: classes) {
				pw.println(c.getSimpleName());
				
				try {
					pw.println("Name: " + 
						(String)c.getDeclaredField("NAME").get(null));
				} catch (Exception e) {			
					pw.println("Name: none");
				}
				
				try { 
					pw.println("Description: " + 
							(String)c.getDeclaredField("DESCRIPTION").get(null));
				} catch (Exception e) {	
					pw.println("Description: none");
				}
				
				String hLine = "Helper classes: ";
				
				try {
					for(Class hClass: (ArrayList<Class>)c.getDeclaredField("HELPER_CLASSES").get(null))
						hLine += hClass.getSimpleName() + " ";
				} catch (Exception e) {
					hLine+="none";				
				}
				
				pw.println(hLine);
				pw.println();
			}
			
			pw.close();
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
	}
	
	public static void main(String[] args) {
		makePuzzleListFile();
	}
}
