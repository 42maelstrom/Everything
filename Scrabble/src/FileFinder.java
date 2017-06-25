import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Date;
import java.util.ArrayList;
import java.util.Calendar;

import javax.imageio.ImageIO;


public class FileFinder {
	public static File mostRecentlyModifiedIn(String directoryPath) {
		File directory = new File(directoryPath);
		if(directory.isDirectory() == false) {
			throw new IllegalArgumentException("File path given is not a directory");
		}
		
		File mrm = mostRecentlyModifiedIn(directory);
		System.out.println(mrm.getAbsolutePath());
		return mrm; 
	}
	
	private static File mostRecentlyModifiedIn(File directory) {
		File mrm = null;

		for (File file: directory.listFiles()) {
			System.out.println(file.getName());
			if (mrm == null || file.lastModified() > mrm.lastModified()) {
				if (file.isDirectory()) {
					mrm = mostRecentlyModifiedIn(file);
				} else {
					mrm = file;
				}
			}
		}
		
		return mrm;
	}
	
	public static File newestPhotosPicture() {
		String path = "/Users/alfordsimon/Pictures/Photos Library.photoslibrary/Masters/";
		Calendar cal = Calendar.getInstance();
		String month = String.valueOf(1 + cal.get(Calendar.MONTH));
		if(month.length() == 1)
			month = '0' + month;
		
		String year = String.valueOf(cal.get(Calendar.YEAR));
		
		path = path + year + '/' + month;
		System.out.println(path);
		return mostRecentlyModifiedIn(path);
	}
	
	public static void main(String[] args) {
		File newest = newestPhotosPicture();
		PicPanel.makePanel(newest);
	}
}
