import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;
import java.io.IOException;

/**
 * Represents an iTunes library.
 * Creates the library representation based off of an iTunes Library.xml file.
 * Includes stuff to make collecting data about the songs easy and - more importantly! - fun.
 */
public class ITunesLibrary {

	//Each element is one line from the XML File.
	private ArrayList<String> xmlFile;
	
	//A HashMap of all songs. Key is the Track ID of the song, and the value is a Song object.
	private HashMap<Long, Song> allSongs;
	
	//A HashMap of all Playlists in this library. The key is the name of the Playlist, and the value is Playlist object.
	private HashMap<String, Playlist> allPlaylists;
	
	/*
	 * A list of all of the types of data stored in songs in this library. 
	 * The key is a string of the data type name, ex. "Rating", "Purchased", "Date Added"
	 * the value is a Class object that the data type holds, ex. Long, Boolean, TimeStamp.
	 */
	private HashMap<String, Class> dataNames;
	
	/*
	 * An alternate collection of all of the data in the library. The Key is the data type's name,
	 * And the value is an ArrayList<Song> containing all songs who have that dataType. 
	 */
	private HashMap<String, ArrayList<Song>> dataSongs;
	
	/*
	 * All the library's data. The key is the data type's name
	 * And the value is a hashmap whose key is a value of the data and whose
	 * value is an Arraylist of all the songs who have that value for the given data type.
	 */
	private HashMap<String, HashMap<Object, ArrayList<Song>>> dataMap;
	
	/**
	 * Sets up a representation of this iTunes library based off of the XML file.
	 * @param xmlFilePath location of the XML file
	 */
	public ITunesLibrary(String xmlFilePath)
	{
		//Loads the XML file:
		try{
			System.out.println("Loading library...");
			BufferedReader bf = new BufferedReader(new FileReader(xmlFilePath));
			xmlFile = new ArrayList<String>();
			String line;
	
			while((line = bf.readLine()) != null)
			{
				//Because sometimes a comment lasts more than one line long, this
				//makes sure a new line is entered into the array only when it is the end of a <key> thingie.
				while(!line.endsWith(">"))
				{
					line+=bf.readLine();
				}
				xmlFile.add(line);
			}
			
			bf.close();
			
		}catch(IOException e)
		{
			System.out.println("Error with XML file:");
			System.out.println(e.getMessage());
			System.out.println("Program ended.");
			System.exit(0);
		}
		
	//Loads songs:
		//System.out.print("Loading songs...");
		
		allSongs = new HashMap<Long, Song>();
		dataNames = new HashMap<String, Class>();
		int index = 0;
		
		//move to the line where the songs begin.
		while(xmlFile.get(index++).indexOf("<key>Tracks") == -1)	
		{ 	}
		
		while(xmlFile.get(++index).indexOf("<key>") != -1) //going through each song
		{
			Song temp = new Song();
			index++;
			String line, key, value, valueType;
			while(((line = xmlFile.get(++index)).indexOf("</dict")) == -1) //going through each bit of info about the song. while this line isn't <dict>
			{
				//this is the name of the data, ex. Date Modified
				key = line.substring(8, line.indexOf("</")); // the key is a String starting at index 8 and going till the <
				//if it's new, then later on it gets added to the dataNames Hashmap.
				boolean isKeyNew = !dataNames.containsKey(key);
				//this is the value type of the data, ex. date, boolean, integer
				valueType = line.substring(line.indexOf("</") + 7, line.indexOf('>',line.indexOf("</") + 7));
				//because of how booleans are stored in the XML file, if the data is a boolean, the file simply puts true/ or false/ as the data type
				if(valueType.equals("true/"))
				{
					temp.put(key, true);
					if(isKeyNew)
						dataNames.put(key, Boolean.class);
				}else if(valueType.equals("false/"))
				{
					temp.put(key, false);
					if(isKeyNew)
						dataNames.put(key, Boolean.class);
				}else{
					//this is the value of the data. Ex. date = 2014-03-17T01:27:09Z
					value = line.substring(line.lastIndexOf('>', line.length()-2) + 1, line.lastIndexOf('<'));
					if(valueType.equals("string")){	
						temp.put(key, value);	
						if(isKeyNew)
							dataNames.put(key, String.class);
					}else if(valueType.equals("integer")){
						temp.put(key, Long.parseLong(value));
						if(isKeyNew)
							dataNames.put(key, Long.class);
					}else if(valueType.equals("date")){
						//this changes the date format given in the XML file into one accepted by Timestamp.valueOf() method.
						value = value.replace('T', ' ').substring(0, value.length() - 2);
						temp.put(key, Timestamp.valueOf(value));
						if(isKeyNew)
							dataNames.put(key, Timestamp.class);
					}else{
						System.out.println("Unknown data type: " + valueType + "\nValue: " + value + "\nKey: " + key);
					}
				}
			}
		
			allSongs.put((Long)temp.get("Track ID"), temp);
		}
	//System.out.println(" Songs loaded.");	
		
	//Loads data:
	//System.out.print("Loading data...");
		dataSongs = new HashMap<String, ArrayList<Song>>();
		dataMap = new HashMap<String, HashMap<Object, ArrayList<Song>>>();
		for(String dataName: dataNames.keySet())
		{
			ArrayList<Song> songs = new ArrayList<Song>();
			HashMap<Object, ArrayList<Song>> valuesMap = new HashMap<Object, ArrayList<Song>>();
			for(Song tempSong: allSongs.values())
			{
				if(tempSong.containsKey(dataName))
				{
					songs.add(tempSong);
					Object value = tempSong.get(dataName);
					if(valuesMap.containsKey(value))
					{
						valuesMap.get(value).add(tempSong);
					}else{
						valuesMap.put(value, new ArrayList<Song>(Arrays.asList(tempSong)));
					}
				}
			}
			dataSongs.put(dataName, songs);
			dataMap.put(dataName, valuesMap);
		}
	//System.out.println(" Data loaded.");	
	//Loads Playlists:
	//System.out.print("Loading Playlists...");
		allPlaylists = new HashMap<String, Playlist>();
		
		//move to the line where Playlists begin. This is so the next while loop does't register "name" from the songs earlier.
		while(xmlFile.get(++index).indexOf("Playlist") == -1)	
		{ 	}
		
		//stop when you get to the end of the file (or dangerously near the end)
		while(xmlFile.size() - 4 > index)
		{
			while(xmlFile.get(index).indexOf("Name") == -1)
			{	index++; }//move to where the name of the Playlist is
			
			//this is the extracted name of the Playlist
			String name = xmlFile.get(index).substring(26, xmlFile.get(index).lastIndexOf('<'));
			
			//move forward till we get to the list of songs in here, or if there turns out not to be any songs in this Playlist.
			while(xmlFile.get(++index).indexOf("Playlist Items") == -1 && xmlFile.get(index).indexOf("Name") == -1)
			{	}
			
			ArrayList<Song> songs = new ArrayList<Song>();
			String line;
			if(xmlFile.get(index).indexOf("Name") == -1)
			{
				while((line = xmlFile.get(index+=3)).indexOf("<key>") != -1)
				{
					songs.add(dataMap.get("Track ID").get(Long.parseLong(line.substring(33, line.lastIndexOf('<')))).get(0));
				}
			}
			
			allPlaylists.put(name, new Playlist(name, songs));
		}
		allPlaylists.put("All Songs", new Playlist("All Songs", new ArrayList<Song>(allSongs.values())));
	//	System.out.println(" Playlists loaded.");
		System.out.println("Library loaded.");
	}
	
	/**
	 * Returns the XML File that created this library, stored as
	 * a parsed string with each line as one element of the ArrayList.
	 * @return The parsed XML File stored into an ArrayList<String>
	 */
	public ArrayList<String> getXmlFile() {
		return xmlFile;
	}
	
	/**
	 * Returns a HashMap<Long,Song> where each key is the unique
	 * songID of each song, and the Song is the song.
	 * @return A HashMap of each song, keyed with its ID.
	 */
	public HashMap<Long, Song> getAllSongs() {
		return allSongs;
	}

	/**
	 * Returns a HashMap of all the Playlists in this library:
	 * key: the string name of the Playlist
	 * value: A Object of class Playlist corresponding to the Playlist name.
	 * @return a HashMap of each playlist, keyed with its String name.
	 */
	public HashMap<String, Playlist> getAllPlaylists() {
		return allPlaylists;
	}
	
	/**
	 * Returns the song with the given name.
	 * this.getSong(name) is equivalent to this.getDataMap().get("Name").get(name).get(0);
	 * @param name - the name of the song
	 * @return A song object of the song
	 */
	public Song getSong(String name)
	{
		ArrayList<Song> songs = dataMap.get("Name").get(name);
		if(songs == null)
			return null;
		return songs.get(0);
	}
	
	/**
	 * Of the songs with that info for that data type, returns
	 * an ArrayList<Song> of the songs whose info  (for example, whose Plays)
	 * matches the given Object value (for example, 50 plays).
	 */
	public ArrayList<Song> getSongsWith(String dataType, Object value)
	{
		return dataMap.get(dataType).get(value);
	}
	
	/**
	 * Returns the Playlist with the name.
	 * this method is equivalent to calling getAllPlaylists.get(name);
	 * @param name - the name of the Playlist
	 * @return the Playlist
	 */
	public Playlist getPlaylist(String name)
	{
		if(!allPlaylists.containsKey(name))
			return null;
		return allPlaylists.get(name);
	}
	
	/**
	 * Returns a HashMap containing the name of any data type stored in this library.
	 * For example, if one song in this library has a DateAdded which is a TimeStamp,
	 * and an other song has a Plays which is a Long, this HashMap will contain 
	 * "DateAdded">TimeStamp and "Plays">Long
	 * @return a HashMap of all of the Classes of datatypes in this library.
	 */
	public HashMap<String, Class> getDataNames()
	{
		return dataNames;
	}
	
	/**
	 * Returns the dataSongs HashMap for this library.
	 * Data songs maps the String name of a data type (for example, "Rating")
	 * to an ArrayList<Song> of all of the songs in the library who contain
	 * a value for that data type.
	 * @return the dataSongs HashMap
	 */
	public HashMap<String, ArrayList<Song>> getDataSongs()
	{
		return dataSongs;
	}
	
	/**
	 * A HashMap of the library's data. This is the main collection of all of the library's song info.
	 * Here's how it is arranged:
	 * Key: the name of the data type, ex. "Rating"
	 * Value: a second HashMap who acts as follows:
	 * 			Key: a value of the above data type, as an object, ex. "1" (one star)
	 * 			Value: an ArrayList of all of the songs with that data type, ex: ["Boring song","I am boring"]
	 */
	public HashMap<String, HashMap<Object, ArrayList<Song>>> getDataMap()
	{
		return dataMap;
	}
	
	/**
	 * Lists the songs out on the screen.
	 */
	public void printAllSongs()
	{
		System.out.println("Songs in Library:");
		for(Song temp: allSongs.values())
		{
			System.out.println(temp.get("Name"));
		}
	}
	
	/**
	 * Lists all of the Playlists in this library out on the screen,
	 * in addition to the number of songs in each Playlist.
	 */
	public void printAllPlaylists()
	{
		System.out.println("Playlists in Library:");
		for(String temp: allPlaylists.keySet())
		{
			System.out.println(temp + ": " + allPlaylists.get(temp).size() + " songs");
		}
	}
	
	/**
	 * Lists on the screen all of the different data types, their corresponding Class
	 * of data (ex. Long, Timestamp), and the number of songs which contain the data type.
	 */
	public void printAllData()
	{
		System.out.println("Song Data in Library");
		for(String temp: dataNames.keySet())
		{
			System.out.println(temp + ": " + dataNames.get(temp).getSimpleName() + ": " + dataSongs.get(temp).size());
		}
	}
	
	/**
	 * Lists on the screen the different values for this data
	 * type, and the number of songs for each value.
	 */
	public void printDataValuesFor(String typeName)
	{
		System.out.println("Data values for " + typeName);
		for(Object temp: dataMap.get(typeName).keySet())
		{
			System.out.println(temp.toString() + ": " + temp.getClass().getSimpleName() + ": " + dataMap.get(typeName).get(temp).size() + " songs");
		}
	}
	
	/**
	 * Creates a dataMap for this Playlist. It creates exactly the same type of dataMap in any
	 * ITunesLibrary, except here with only the songs in this Playlist.
	 * @param playlist - A Playlist of songs
	 * @return a dataMap analagous to this library's dataMap.
	 */
	public HashMap<String, HashMap<Object, ArrayList<Song>>> makeDataMapFor(Playlist playlist)
	{
		HashMap<String, HashMap<Object, ArrayList<Song>>> dataMap = new HashMap<String, HashMap<Object, ArrayList<Song>>>();
		
		for(Song song: playlist)
		{
			for(Object temp: song.keySet())
			{
				String key = (String)temp; 
				Object value = song.get(key);
				if(dataMap.containsKey(key))
				{
					if(dataMap.get(key).containsKey(value))
					{
						dataMap.get(key).get(value).add(song);
					}else{
						dataMap.get(key).put(value, new ArrayList<Song>(Arrays.asList(song)));
					}
				}else{
					HashMap<Object, ArrayList<Song>> map = new HashMap<Object, ArrayList<Song>>();
					map.put(value, new ArrayList<Song>(Arrays.asList(song)));
					dataMap.put(key, map);
				}
			}
		}
		return dataMap;
	}
	
	/**
	 * Creates a dataMap with the Playlist with this name from this library.
	 * It creates exactly the same type of dataMap in any
	 * ITunesLibrary, except here with only the songs in this Playlist.
	 * @param playlist - The name of a Playlist
	 * @return a dataMap analagous to this library's dataMap.
	 */
	public HashMap<String, HashMap<Object, ArrayList<Song>>> makeDataMapFor(String playlistName)
	{
		return this.makeDataMapFor(this.getPlaylist(playlistName));
	}
	
	
	/**
	 * returns an ArrayList containing all of the different data values for each song in an ArrayList.
	 * Only uses data for songs from the given Playlist.
	 * Example: ArrayList<Long> totalTimes = myLib.collectData("Total Time",myLib.getPlaylist("iPhone 2"));
	 * 			This returns the total time for each song in the Playlist.
	 * @param dataType - The string of the data type data is being collected for.
	 * @param playlist - The Playlist from which songs' data is being collected.
	 * @return an ArrayList containing all of the data values for each song in the playlist.
	 */
	public ArrayList collectData(String dataType, Playlist playlist)
	{
		ArrayList list = new ArrayList();
		int i = 0;
		//in order to find out which data type it is.
		while(!playlist.get(i).containsKey(dataType))
		{
			i++;
		}
		if(playlist.get(i).get(dataType) instanceof java.sql.Timestamp)
		{
			for(Song temp: playlist)
			{
				if(temp.containsKey(dataType))
					list.add(((Timestamp)temp.get(dataType)).getTime());
			}
		}else{
			for(Song temp: playlist)
			{
				if(temp.containsKey(dataType))
					list.add(temp.get(dataType));
			}
		}
		return list;
	}
	
	/**
	 * returns an ArrayList containing all of the different data values for each song in an arraylist.
	 * Uses all songs in the library.
	 * Example: ArrayList<Long> totalTimes = myLib.collectData("Total Time");
	 * 			This returns the total time for each song in the library.
	 * @param dataType - The string of the data type data is being collected fo.r
	 * @return an ArrayList containing all of the data values for each song in the library.
	 */
	public ArrayList collectData(String dataType)
	{
		return this.collectData(dataType, this.getPlaylist("All Songs"));
	}

	public static void main(String[] args)
	{
		
		/** See radiohead song number of plays sorted **/
		ITunesLibrary myLib = new ITunesLibrary("Library 5-7-2017.xml");
		ArrayList<Song> list = myLib.getSongsWith("Artist", "Radiohead");
		
		for(int i = 0; i < list.size(); i++) {
			Song s = list.get(i);
			if (!s.containsKey("Play Count")) {
				list.remove(s);
				i--;
			}
		}
		
		System.out.println(list.get(34).toString());
		
		for(int i = 0; i < list.size(); i++) {
			for(int j = i + 1; j < list.size(); j++) {
				Long l = Long.valueOf(list.get(i).get("Play Count").toString());
				String s = list.get(j).get("Play Count").toString();
				Long l2 = Long.valueOf(s);
				
				if (l < l2) {
					Song temp = list.get(i);
					list.set(i, list.get(j));
					list.set(j, temp);
				}
			}
		}
		
		for(Song s: list) {
			System.out.println(s.toString() + " " + s.get("Play Count"));
		}
			
//		
//	/**What songs were in the RadioheadCD?**/
//		ITunesLibrary myLib = new ITunesLibrary("OldLibrary.xml");
//		ArrayList<Song> list = myLib.getPlaylist("RadioheadCD");
//		Helper.sort(list);
//		for(Song temp: list) {
//			System.out.println(temp.toString());
//		}
			
	/** What songs had incorrectly transferred play counts?**/	
//		ITunesLibrary newLib = new ITunesLibrary("/Users/simonalford42/Desktop/iTunes Music Library.xml");
//		
//		for(Song newSong: myLib.allSongs.values()) {
//			long oldPlays = 0, newPlays = 0;
//			ArrayList<Song> songs;
//			ArrayList<Long> playCounts;
//			
//			songs = myLib.dataMap.get("Name").get(newSong.toString());
//			if(songs != null) {
//				playCounts = new ArrayList<Long>();
//				for(Song temp: songs) {
//					if(temp.containsKey("Play Count")) {
//						if((Long)temp.get("Play Count") > newPlays)
//							oldPlays = (Long)temp.get("Play Count");
//					}
//				}
//			}
//			
//			songs = newLib.dataMap.get("Name").get(newSong.toString());
//			if(songs != null) {
//				playCounts = new ArrayList<Long>();
//				for(Song temp: songs) {
//					if(temp.containsKey("Play Count")) {
//						if((Long)temp.get("Play Count") > newPlays)
//							newPlays = (Long)temp.get("Play Count");
//					}
//				}
//				
//				if(oldPlays - newPlays > 0 ) {
//					System.out.println(newSong.toString() + ": " + oldPlays + ", " + newPlays);
//				}
//			}
//		}
//		
	/** How many songs per letter of the alphabet for songs in iPhone 2?**/
//	String alphabet = "abcdefghijklmnopqrstuvwxyz#";
//	int[] list = new int[27];
//	for(Song song: myLib.getPlaylist("iPhone 2")) {
//		String firstLetter = song.toString().substring(0,1).toLowerCase();
//		int index = alphabet.indexOf(firstLetter);
//		if(index == -1){
//			list[26]++;
//		} else {
//			list[index]++;
//		}
//	}
//	
//	for(int i = 0; i < list.length; i++) {
//		System.out.println(alphabet.substring(i,i+1) + ": " + list[i] + " songs");
//	}
//	
	/**Let's see artist rankings based off of total plays**/
	
//	HashMap<String, Long> artistsTotalPlays = new HashMap<String, Long>();
//	ArrayList<Long> list = new ArrayList<Long>();
//	
//	for(Object temp: myLib.getDataMap().get("Artist").keySet()) {
//		String artist = (String)temp;
//		long totalPlays = 0;
//		for(Song song: myLib.getDataMap().get("Artist").get(temp)) {
//			if(song.containsKey("Play Count"))
//				totalPlays += (Long)song.get("Play Count");
//		}
//		artistsTotalPlays.put(artist, totalPlays);
//		list.add(totalPlays);
//	}
	
//	//I apologize for doing this the lazy way
//	Helper.sort(list);
//	outer:
//	while(list.get(0).equals(1L) == false) {
//		for(String artist: artistsTotalPlays.keySet()) {
//			if(artistsTotalPlays.get(artist).equals(list.get(0))) {
//				System.out.println(artist + ": " + list.remove(0) + " total plays");
//				continue outer;
//			}
//		}
//	}
	
	/** Let's see how many total plays I have**/
//		long totalPlays = 0;
//		for(Object temp: myLib.getDataMap().get("Play Count").keySet())
//			totalPlays += (Long)temp * myLib.getDataMap().get("Play Count").get(temp).size();
//		System.out.println("Total plays = " + totalPlays);
//		
//		totalPlays = 0;
//		for(Song temp: myLib.getAllSongs().values()) {
//			if(temp.containsKey("Play Count"))
//				totalPlays+= (Long) temp.get("Play Count");
//		}
//		System.out.println("Total plays = " + totalPlays);
	
	/** Songs per time length segment with spread interval**/
//	//how often the number of songs within that spread length will be counted.
//	final int intervalLength = 1; //in seconds
//	
//	//the range of seconds that a song's length has to be in to be counted.
//	//Ex. spread length of 5, interval length of 1, then it will graph number of songs with 0-5, 1-6, 2-7, 3-8, etc. seconds.
//	//final int spreadLength = 150; //in seconds. Is for the second amount and greater, not centered on the second amount.
//	int[] spreadLengths = {1,2,5,10,30,50,100,150,200,250,300};
//	for(int spreadLength: spreadLengths)
//	{
//		//The maximum length, in seconds, of a song for it to be considered.
//		//This is to make the graph more centered and not as focused on outliers.
//		final int maxLength = 650;
//		int[] songsPerTime = new int[maxLength / intervalLength];
//		int maxIndexUsed = 0;
//		for(Song temp: myLib.getPlaylist("Music"))
//		{
//			if(temp.containsKey("Total Time"))
//			{
//				double secondsLength = (Long)temp.get("Total Time") / 1000;
//				if(secondsLength < maxLength)
//				{
//					int endIndex = (int)secondsLength / intervalLength; // if interval length is 2 seconds, and seconds length is 5, will go in the second index (0-2, 2-4, 4-6, etc.)
//					// interval 2, spread 5: 0-5, 2-7, 4-9, 6-11, 8-13, 10-15, 12-17, 14-19
//					//song length = 12 seconds, index = 6
//					//song length = 13 seconds, index = 6
//					//song length = 14 seconds, index = 7
//					int startIndex = endIndex - spreadLength/intervalLength;
//					if(startIndex < 0)
//						startIndex = 0;
//					int i;
//					for(i = startIndex; i <= endIndex; i++)
//					{
//						if(i > songsPerTime.length - 1) //if you're past the max, stop.
//							break;
//						songsPerTime[i]++;
//					}
//					if(i - 1 > maxIndexUsed)
//						maxIndexUsed = i - 1;
//				}	
//			}
//		}
//		songsPerTime = Arrays.copyOf(songsPerTime, maxIndexUsed + 1);
//		System.out.println(Arrays.toString(songsPerTime));
//		Helper.storeData(songsPerTime, "Songs_Per_Time_" + intervalLength + "_interval_" + spreadLength + "_spread.csv");
//		
//	}	
	/**Average Plays per second letter of the alphabet**/
//	ArrayList<ArrayList<Integer>> list = new ArrayList<ArrayList<Integer>>(27);
//	for(int i = 0; i < 27; i++)
//	{
//		list.add(new ArrayList<Integer>());
//	}
//	for(Song temp: myLib.getPlaylist("iPhone 2"))
//	{
//		if(temp.containsKey("Play Count") && ((String)temp.get("Name")).length() > 2)
//		{
//			String secondLetter = temp.toString().substring(1,2).toLowerCase();
//			int value = "abcdefghijklmnopqrstuvwxyz".indexOf(secondLetter);
//			if(value == -1)
//				value = 26;
//			list.get(value).add((int)(long)(Long)temp.get("Play Count"));
//		}
//	}
//	int[] array = new int[27];
//	for(int i = 0; i < 27; i++)
//	{
//		int sum = 0;
//		if(list.get(i).size() > 0)
//		{
//			for(Integer temp: list.get(i))
//			{
//				sum+=temp;
//			}
//			sum/= list.get(i).size();
//		}
//		array[i] = sum;
//	}
//	Helper.storeData(array, "Avg_Plays_Per_Second_Letter.csv");
	/**Songs per letter of the alphabet*/
//	int[] songsPerLetter = new int[27];
//	for(Song temp: myLib.allSongs.values())
//	{
//		String firstLetter = temp.toString().substring(0,1).toLowerCase();
//		int value = "abcdefghijklmnopqrstuvwxyz".indexOf(firstLetter);
//		if(value == -1)
//			value = 26;
//		songsPerLetter[value] = songsPerLetter[value] + 1;
//	}
//	Helper.storeData(songsPerLetter, "Songs_Per_Letter.csv");
	/**average length for each track number**/
//		//track number is the number for that song
//		//track count is the number of songs in that album;
//		HashMap<String, HashMap<Object, ArrayList<Song>>> data = myLib.makeDataMapFor(myLib.getPlaylist("Music"));
//		HashMap<Object, ArrayList<Song>> trackNum = new HashMap<Object, ArrayList<Song>>();
//		for(Object temp: data.get("Track Number").keySet())
//		{
//			ArrayList<Song> list = new ArrayList<Song>();
//			for(Song song: data.get("Track Number").get(temp))
//			{
//				if(song.containsKey("Total Time") && song.containsKey("Track Count") && (Long)song.get("Track Count") > 7)
//				{
//					list.add(song);
//				}
//			}
//			trackNum.put(temp, list);
//		}
//		long[] avgLength = new long[trackNum.keySet().size()];
//		int i = 0;
//		for(Object temp: trackNum.keySet())
//		{
//			long avg = 0;
//			for(Song song: trackNum.get(temp))
//			{
//				avg += (Long)song.get("Total Time");
//			}
//			avg/=trackNum.get(temp).size();
//			avgLength[i++] = avg;
//		}
//		Helper.storeData(avgLength,"AvgLength_Per_Track_Number.csv");
	/**date added per artist (scatter)**/	
//		ArrayList<Song> validSongs = new ArrayList<Song>();
//		for(Song temp: myLib.getPlaylist("Music"))
//		{
//			if(temp.containsKey("Artist") && temp.containsKey("Date Added") && (Long)((Timestamp)temp.get("Date Added")).getTime() > 1389005341000L)
//				validSongs.add(temp);
//		}
//		//maps artists to a list of the dates added for each artist;
//		HashMap<String, ArrayList<Long>> artistMap = new HashMap<String, ArrayList<Long>>();
//		for(Song temp: validSongs)
//		{
//			String artist = (String)temp.get("Artist");
//			if(artistMap.containsKey(artist))
//			{
//				artistMap.get(artist).add((Long)((Timestamp)temp.get("Date Added")).getTime());
//			}else{
//				ArrayList<Long> arraylist = new ArrayList<Long>();
//				arraylist.add((Long)((Timestamp)temp.get("Date Added")).getTime());
//				artistMap.put(artist, arraylist);
//			}	
//		}
//		//Calculate average date added for each artist.
//		ArrayList<String> artistNames = new ArrayList<String>(artistMap.keySet());
//		ArrayList<Long> avgDatesAdded = new ArrayList<Long>();
//		for(String name: artistNames)
//		{
//			long avgDateAdded = 0;
//			for(long dateAdded: artistMap.get(name))
//			{
//				avgDateAdded+=dateAdded;
//			}
//			avgDateAdded/=artistMap.get(name).size();
//			avgDatesAdded.add(avgDateAdded);
//		}
//		//sort the artists based off of average date added
//		for(int i = 0; i < artistNames.size() - 1; i++)
//		{
//			for(int j = i + 1; j < artistNames.size(); j++)
//			{
//				if(avgDatesAdded.get(i) > avgDatesAdded.get(j))
//				{
//					Long dateTemp = avgDatesAdded.get(i);
//					avgDatesAdded.set(i, avgDatesAdded.get(j));
//					avgDatesAdded.set(j, dateTemp);
//					
//					String artistTemp = artistNames.get(i);
//					artistNames.set(i, artistNames.get(j));
//					artistNames.set(j, artistTemp);
//				}
//			}
//		}
//		long[][] array = new long[2][validSongs.size()];
//		int i = 0;
//		for(int j = 0; j < artistNames.size(); j++)
//		{
//			for(long dateAdded: artistMap.get(artistNames.get(j)))
//			{
//				array[0][i] = j;
//				array[1][i++] = dateAdded;
//			}
//		}
//		//Helper.print(array);
//		for(i = 0; i < artistNames.size(); i++)
//		{
//			System.out.println(i + ": " + artistNames.get(i));
//		}
//		Helper.storeData(array, "DateAdded_Per_Artist.csv");		
	/** size per length (not average)**/
//	ArrayList<Song> songs = new ArrayList<Song>();
//	for(Song temp: myLib.getPlaylist("Music"))
//	{
//		if(temp.containsKey("Size") && temp.containsKey("Total Time"))
//			songs.add(temp);
//	}
//	System.out.println("Sorting...");
//	//sort the songs according to length
//	for(int i = 0; i < songs.size(); i++)
//	{
//		for(int j = i + 1; j < songs.size(); j++)
//		{
//			if((Long)songs.get(i).get("Total Time") > (Long)songs.get(j).get("Total Time"))
//			{
//				Song temp = songs.get(i);
//				songs.set(i, songs.get(j));
//				songs.set(j, temp);
//			}
//		}
//	}
//	ArrayList<Long> sizePerLength = new ArrayList<Long>();
//	for(Song temp: songs)
//	{
//		sizePerLength.add((Long)temp.get("Size") / (Long)temp.get("Total Time"));
//	}
//	Helper.storeData(sizePerLength, "Size_Div_Length.csv");
	/**Avg size per time length**/
//		int intervalLength = 1;
//		double[][] playsPerTime = new double[2][3750 / intervalLength];
//		int maxIndexUsed = 0;
//		for(Song temp: myLib.getPlaylist("Music"))
//		{
//			if(temp.containsKey("Total Time") && temp.containsKey("Size"))
//			{
//				double secondsLength = (Long)temp.get("Total Time") / 1000;
//				int index = (int)(secondsLength / intervalLength);
//				double size = (double)(Long)temp.get("Size");
//				if(index < playsPerTime[0].length)
//				{
//					playsPerTime[0][index] = playsPerTime[0][index] + size;
//					playsPerTime[1][index] = playsPerTime[1][index] + 1;
//					if(index > maxIndexUsed && size != 0)
//					{
//						maxIndexUsed = index;
//					}
//				}	
//			}
//		}
//		//System.out.println(maxIndexUsed + ": " + playsPerTime[1][maxIndexUsed]);
//		double[] finalPlaysPerTime = new double[maxIndexUsed + 1];
//		for(int i = 0; i < maxIndexUsed + 1; i++)
//		{
//			if(playsPerTime[1][i] == 0)
//			{
//				finalPlaysPerTime[i] = 0;
//			}else{
//				finalPlaysPerTime[i] = playsPerTime[0][i] / playsPerTime[1][i];
//			}
//		}
//		System.out.println(Arrays.toString(finalPlaysPerTime));
//		Helper.storeData(finalPlaysPerTime, "Size_Per_Length" + intervalLength + ".csv");			
	/**Avg skips per time length**/
//		int intervalLength = 2;
//		double[][] playsPerTime = new double[2][3750 / intervalLength];
//		int maxIndexUsed = 0;
//		for(Song temp: myLib.getPlaylist("Music"))
//		{
//			if(temp.containsKey("Total Time") && temp.containsKey("Skip Count"))
//			{
//				double secondsLength = (Long)temp.get("Total Time") / 1000;
//				int index = (int)(secondsLength / intervalLength);
//				double playCount = (double)(Long)temp.get("Skip Count");
//				if(index < playsPerTime[0].length)
//				{
//					playsPerTime[0][index] = playsPerTime[0][index] + playCount;
//					playsPerTime[1][index] = playsPerTime[1][index] + 1;
//					if(index > maxIndexUsed && playCount != 0)
//					{
//						maxIndexUsed = index;
//					}
//				}	
//			}
//		}
//		System.out.println(maxIndexUsed + ": " + playsPerTime[1][maxIndexUsed]);
//		double[] finalPlaysPerTime = new double[maxIndexUsed + 1];
//		for(int i = 0; i < maxIndexUsed + 1; i++)
//		{
//			if(playsPerTime[1][i] == 0)
//			{
//				finalPlaysPerTime[i] = 0;
//			}else{
//				finalPlaysPerTime[i] = playsPerTime[0][i] / playsPerTime[1][i];
//			}
//		}
//		System.out.println(Arrays.toString(finalPlaysPerTime));
//		Helper.storeData(finalPlaysPerTime, "Skips_Per_Time" + intervalLength + ".csv");	
	/**Avg Plays per time length**/
//		int intervalLength = 15;
//		double[][] playsPerTime = new double[2][3750 / intervalLength];
//		int maxIndexUsed = 0;
//		for(Song temp: myLib.getPlaylist("Music"))
//		{
//			if(temp.containsKey("Total Time") && temp.containsKey("Play Count"))
//			{
//				double secondsLength = (Long)temp.get("Total Time") / 1000;
//				int index = (int)(secondsLength / intervalLength);
//				double playCount = (double)(Long)temp.get("Play Count");
//				if(index < playsPerTime[0].length)
//				{
//					playsPerTime[0][index] = playsPerTime[0][index] + playCount;
//					playsPerTime[1][index] = playsPerTime[1][index] + 1;
//					if(index > maxIndexUsed && playCount != 0)
//					{
//						maxIndexUsed = index;
//					}
//				}	
//			}
//		}
//		System.out.println(maxIndexUsed + ": " + playsPerTime[1][maxIndexUsed]);
//		double[] finalPlaysPerTime = new double[maxIndexUsed + 1];
//		for(int i = 0; i < maxIndexUsed + 1; i++)
//		{
//			if(playsPerTime[1][i] == 0)
//			{
//				finalPlaysPerTime[i] = 0;
//			}else{
//				finalPlaysPerTime[i] = playsPerTime[0][i] / playsPerTime[1][i];
//			}
//		}
//		System.out.println(Arrays.toString(finalPlaysPerTime));
//		Helper.storeData(finalPlaysPerTime, "Plays_Per_Time" + intervalLength + ".csv");
	/** Plays per song per artist**/
//		ArrayList<Double> avgPlaysPerArtist = new ArrayList<Double>();
//		HashMap<String, Double> avgMap = new HashMap<String, Double>();
//		HashMap<String, HashMap<Object, ArrayList<Song>>> data = myLib.dataMap;
//		for(Object key: data.get("Artist").keySet())
//		{
//			ArrayList<Song> list = data.get("Artist").get(key);
//			if(list.size() > 5)
//			{
//				double sum = 0;
//				for(Song song: list)
//				{
//					if(song.containsKey("Play Count"))
//					{
//						sum+=(Long)song.get("Play Count");
//					}else{
//						
//					}
//				}
//				if(sum != 0)
//				{
//					avgPlaysPerArtist.add(sum / list.size());
//					avgMap.put(key.toString(), sum / list.size());
//				}
//			}
//		}
//		Helper.print(avgMap);
//		Helper.sort(avgPlaysPerArtist);
//		Helper.storeData(avgPlaysPerArtist, "Avg_Plays_Per_Artist5.csv");
	/**Length of time per song**/
//		ArrayList<Long> lengthPerSong = myLib.collectData("Total Time",myLib.getPlaylist("Music"));
//		Helper.sort(lengthPerSong);
//		Helper.print(lengthPerSong);
//		Helper.storeData(lengthPerSong, "Length_Per_Song.csv");
//		HashMap<Song, Long> map = new HashMap<Song, Long>();
//		for(Song temp: myLib.getPlaylist("Music"))
//		{
//			if(temp.containsKey("Total Time"))
//			{
//				if((Long)temp.get("Total Time") / 1000 / 60 > 10)
//				{
//					map.put(temp, (Long)temp.get("Total Time") / 1000 / 60);
//					System.out.println(temp.toString() + ": " + map.get(temp).toString());
//				}
//			}
//		}
	/**Songs per genre**/
//		ArrayList<Integer> songsPerGenre = new ArrayList<Integer>();
//		for(ArrayList<Song> temp: myLib.dataMap.get("Genre").values())
//		{
//			songsPerGenre.add(temp.size());
//		}
//		Helper.sort(songsPerGenre);
//		Helper.storeData(songsPerGenre, "Songs_Per_Genre.csv");
	/**Plays per Skips stuff**/	
//		int[][] playsPerSkips = new int[2][myLib.dataSongs.get("Skip Count").size()];
//		int i = 0;
//		for(Song temp: myLib.dataSongs.get("Skip Count"))
//		{
//			if(temp.containsKey("Play Count") && (Long)temp.get("Play Count") < 500)
//			{
//				playsPerSkips[0][i] = (int)(long)(Long)temp.get("Skip Count");
//				playsPerSkips[1][i] = (int)(long)(Long)temp.get("Play Count");
//				i++;
//			}
//		}
//		playsPerSkips[0] = Arrays.copyOf(playsPerSkips[0], i);
//		playsPerSkips[1] = Arrays.copyOf(playsPerSkips[1], i);
//		Helper.sort(playsPerSkips, 0);
//		Helper.print(playsPerSkips);
//		Helper.storeData(playsPerSkips, "Plays_Per_Skips.csv");
//		int j = 0, index = 0;
//		int[][] playsPerSkipsAvg = new int[2][20];
//		for(i = 0 ; i < 21; i++)
//		{
//			int sum = 0, count = 0;
//			while(j < playsPerSkips[0].length && playsPerSkips[0][j] == i)
//			{
//				sum+=playsPerSkips[1][j++];
//				count++;
//			}
//			if(count > 0)
//			{
//				playsPerSkipsAvg[0][index] = i;
//				playsPerSkipsAvg[1][index] = Math.round(sum / count);
//				index++;
//			}
//		}
//		playsPerSkipsAvg[0] = Arrays.copyOf(playsPerSkipsAvg[0], index);
//		playsPerSkipsAvg[1] = Arrays.copyOf(playsPerSkipsAvg[1], index);
//		Helper.storeData(playsPerSkipsAvg, "Plays_Per_Skips_Avg.csv");		
	/**Songs per artist**/
//		HashMap<String, Integer> songsPerArtistMap = new HashMap<String, Integer>();
//		ArrayList<Integer> songsPerArtist = new ArrayList<Integer>();
//		for(Object temp: myLib.dataMap.get("Artist").keySet())
//		{
//			songsPerArtistMap.put(temp.toString(), myLib.dataMap.get("Artist").get(temp).size());
//			songsPerArtist.add(myLib.dataMap.get("Artist").get(temp).size());
//		}
//		Helper.sort(songsPerArtist);
//		Helper.storeData(songsPerArtist, "Songs_Per_Artist.csv");
	/**Plays per song**/
//		ArrayList<Long> playsPerSong = new ArrayList<Long>();
//		for(Song temp: myLib.dataSongs.get("Play Count"))
//		{
//			playsPerSong.add((Long)temp.get("Play Count"));
//		}
//		Helper.sort(playsPerSong);
//		Helper.storeData(playsPerSong, "Plays_Per_Song.csv");
	/**Songs per play**/
//		int[][] songsPerPlay2 = new int[2][myLib.getDataMap().get("Play Count").values().size()];
//		ArrayList<Integer> songsPerPlay = new ArrayList<Integer>();
//		int i = 0;
//		for(Object temp: myLib.getDataMap().get("Play Count").keySet())
//		{
//			songsPerPlay2[0][i] = (int)(long)(Long)temp;
//			songsPerPlay2[1][i++] = myLib.getDataMap().get("Play Count").get(temp).size();
//		}
//		Helper.sort(songsPerPlay2, 0);
//		Helper.storeData(songsPerPlay2, "Songs_Per_Play2.csv");
//		for(ArrayList<Song> temp: myLib.getDataMap().get("Play Count").values())
//		{
//			songsPerPlay.add(temp.size());
//		}
//		Helper.sort(songsPerPlay);
//		Helper.storeData(songsPerPlay, "Songs_Per_Play.csv");
	/**Date added per song**/
//		ArrayList<Long> dateAdded = (ArrayList<Long>)myLib.collectData("Date Added",myLib.getPlaylist("Music"));
//		Helper.sort(dateAdded);
//		Helper.storeData(dateAdded, "DateAdded_Per_Song2.csv");
	/**Skip date per song**/
//		ArrayList<Long> skipDate = (ArrayList<Long>)myLib.collectData("Skip Date",myLib.getPlaylist("Music"));
//		Helper.sort(skipDate);
//		Helper.storeData(skipDate, "SkipDate_Per_Song.csv");
	/**Play date per song**/
//		ArrayList<Long> playDate = (ArrayList<Long>)myLib.collectData("Play Date",myLib.getPlaylist("Music"));
//		Helper.sort(playDate);
//		Helper.storeData(playDate, "PlayDate_Per_Song.csv");
	/**Skip count per song**/
//		ArrayList<Long> skipCount = (ArrayList<Long>)myLib.collectData("Skip Count",myLib.getPlaylist("Music"));
//		Helper.sort(skipCount);
//		Helper.storeData(skipCount, "SkipCount_Per_Song.csv");
	/** Looking at skipped songs when there were a ton of songs being skipped around August 1, 2014.**/	
//		ArrayList<Song> test = new ArrayList<Song>();
//		System.out.println(new Timestamp(1406838605000L).toString());
//		System.out.println(new Timestamp(1407742441000L).toString());
//		for(Song temp: myLib.dataSongs.get("Skip Date"))
//		{
//			long time = ((Timestamp)temp.get("Skip Date")).getTime();
//			if(time > 1406838605000L && time < 1407742441000L)
//			{
//				test.add(temp);
//				System.out.println(temp.toString());
//			}
//		}
		
	}
}
