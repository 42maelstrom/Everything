import java.util.HashMap;


public final class Tile {
	boolean isBlank;
	char c;
	int value;
	
	private static final HashMap<Character, Integer> TILE_VALUES;
	
	static {
		TILE_VALUES = new HashMap<Character, Integer>();
		int[] intvals = {1,3,3,2,1,4,2,4,1,8,5,1,3,1,1,3,10,1,1,1,1,4,4,8,4,10,0};
		String chars =  "ABCDEFGHIJKLMNOPQRSTUVWXYZ*";
		for(int i = 0; i < chars.length(); i++) {
			TILE_VALUES.put(chars.charAt(i), intvals[i]);
		}
	}
	
	public static int getValue(char c) {
		if(Character.isLowerCase(c))
			return 0;
		return TILE_VALUES.get(c);
	}
	
	public Tile(char c) {
		this(Character.toUpperCase(c), Character.isLowerCase(c) || c == '*');
	}
	
	public Tile(char c, boolean isBlank) {
		this.c = c;
		this.isBlank = isBlank;
		if(isBlank) {
			this.value = 0;
		} else {
			this.value = TILE_VALUES.get(c);
		}
	}
	
	public Tile copy() {
		return new Tile(c, isBlank);
	}

	public boolean isBlank() {
		return isBlank;
	}
	
	/**
	 * You can only change the character if it was a blank tile.
	 * @param c - the character to change it to
	 * @return - whether or not the character was changed, i.e. isBlank()
	 */
	public boolean setChar(char c) {
		if(isBlank)
			this.c = c;
		return isBlank;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tile other = (Tile) obj;
		
		if(isBlank) {
			if(other.isBlank) {
				return true;
			} else {
				return false;
			}
		} else if (c == other.c) {
			return true;
		} else {
			return false;
		}
	}
	
	public String toString() {
		if(isBlank && c != '*')
			return Character.toLowerCase(c) + "";
		return c + "";
	}
}
