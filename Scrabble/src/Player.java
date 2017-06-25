public abstract class Player {
	String name;
	
	public Player(String name) {
		this.name = name;
	}
	
	public abstract Move makeMove(BoardAndRack bar);
	
	public String getName() {
		return name;
	}
}
