import java.util.HashMap;

/**
 * A song represents one song in the iTunes library. Uses a HashMap to store info, so that
 * generic names of data can be added, accessed, etc.
 * Because it implements a hashmap, that way all of the hashmap methods can be easily used on it.
 * @author Simon Alford
 */
public class Song extends HashMap implements Comparable<Song>{
	
	/**
	 * Returns the name of the song.
	 */
	public String toString()
	{
		return this.get("Name").toString();
	}

	public int compareTo(Song other) {
		return this.toString().compareTo(other.toString());
	}
}
