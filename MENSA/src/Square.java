
public class Square {
	private boolean isBurnt = false;
	private final Axis axis;
	private boolean isBoxed = false;
	private int order;
	public final int totalSize;
	public final int origOrder;
	private Direction neighborsDirection;
	
	/**
	 * Construct a square with the axis, order, and totalSize.
	 * @param axis
	 * @param order
	 * @param totalSize
	 */
	public Square(Axis axis, int order, int totalSize, Direction neighborsDirection) {
		this.axis = axis;
		this.origOrder = order;
		this.order = order;
		this.totalSize = totalSize;
		this.neighborsDirection = neighborsDirection;
	}

	public boolean isBurnt() {
		return isBurnt;
	}

	public void burn() {
		this.isBurnt = true;
	}
	
	public int getTotalSize() {
		return this.totalSize;
	}
	
	public int getOrigOrder() {
		return this.origOrder;
	}
	
	public Direction getNeighborsDirection() {
		return neighborsDirection;
	}

	public boolean isBoxed() {
		return isBoxed;
	}

	public void boxMe(){
		if(isBurnt)
			System.out.println("Warning: boxing a burnt square");
		
		this.isBoxed = true;
	}

	public int getOrder() {
		return order;
	}

	public void reduceOrder() {
		this.order--;
	}

	public Axis getAxis() {
		return axis;
	}
	
}
