import java.io.File;
import java.util.ArrayList;


public class Songs {
	
	File folder;
	ArrayList<String> songNames;
	
	public Songs() {
		folder = new File("/Users/16alford_simon/Desktop/Jukebox Songs");
		
		File[] listOfFiles = folder.listFiles();
		songNames = new ArrayList<String>();
		for(int i = 0; i < listOfFiles.length; i++) {
			if(listOfFiles[i].toString().endsWith(".mp3")){
				String fileName = listOfFiles[i].getName();
				songNames.add(fileName.substring(0,fileName.length() - 4));
			}
		}
		
	}
	public ArrayList<String> getSongs() {
		return songNames;
	}
	
	public boolean isSongPlaying() {
		return true;
	}
	
	public void playSong(int index) {
		String songName = songNames.get(index);
	}
	
	public static void main(String[] args) {
		Songs s = new Songs();
		System.out.println(s.getSongs().toString());
	}
	
}
