import java.util.ArrayList;
import java.util.HashMap;


/**
 * This class represents one playlist in the itunes library.
 * Extends an ArrayList<Song> Ñthe only addition is that a playlist also has a name.
 * @author Simon
 */
public class Playlist extends ArrayList<Song>{
	
	private String name;
	
	public Playlist()
	{	
		
	}
	
	public Playlist(String name)
	{
		this.name = name;
	}
	
	public Playlist(String name, ArrayList<Song> songs)
	{
		this.name = name;
		this.addAll(songs);
	}
	
	public Playlist(ArrayList<Song> songs)
	{
		this.name = "Untitled";
		this.addAll(songs);
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String newName)
	{
		name = newName;
	}
	
	/**
	 * Returns a Playlist whose songs contain all of the keys.
	 * @param keys - Data keys the songs must have, ex. Rating, Plays
	 * @return A Playlist whose songs all contain the keys
	 */
	public Playlist songsWith(String... keys)
	{
		Playlist songsWith = new Playlist();
		
		outer:
		for(Song temp: this)
		{
			for(String key: keys)
			{
				if(temp.containsKey(key))
					continue outer;
			}
			songsWith.add(temp);
		}
		return songsWith;
	}
	
	/**
	 * Returns a Playlist containing all songs who contain that value given that key
	 * @param key
	 * @param value
	 * @return a Playlist containing all songs who contain that value given that key
	 */
	public Playlist songsWith(String key, Object value)
	{
		Playlist songsWith = new Playlist();
		for(Song temp: this)
		{
			if(temp.get(key).equals(value))
				songsWith.add(temp);
		}
		return songsWith;
	}
	
	/**
	 * Basically an AND for Playlists
	 * @param a Playlist
	 * @return a Playlist of songs that are found in THIS AND THAT
	 */
	public Playlist songsSharedWith(Playlist playlist)
	{
		Playlist songs = new Playlist();
		for(Song temp: playlist)
		{
			if(this.contains(temp))
				songs.add(temp);
		}
		return songs;
	}
	
	/**
	 * Returns songs in this Playlist which aren't in another
	 * @param a Playlist
	 * @return a Playlist of songs that are found in THIS AND NOT THAT
	 */
	public Playlist songsNotIn(Playlist playlist)
	{
		Playlist songs = new Playlist();
		for(Song temp: this)
		{
			if(!playlist.contains(temp))
				songs.add(temp);
		}
		return songs;
	}
	
	/**
	 * Basically an OR for playlists
	 * @param a Playlist
	 * @return a Playlist of songs that are found in THIS OR THAT Playlist
	 */
	public Playlist addNoRepeats(Playlist playlist)
	{
		Playlist both = new Playlist();
		both.addAll(playlist);
		both.addAll(this.songsNotIn(playlist));
		return both;
	}

}
