package games.omg.legacy;

public enum Direction {

	NORTH(1, 0),
	SOUTH(-1, 0),
	EAST(0, 1),
	WEST(0, -1),
	NORTHEAST(1, 1),
	SOUTHEAST(-1, 1),
	NORTHWEST(1, -1),
	SOUTHWEST(-1, -1);

	//

	final private int x;
	final private int z;

	Direction(int x, int z) {
		this.x = x;
		this.z = z;
	}

	//

	public int getX() {
		return x;
	}

	public int getZ() {
		return z;
	}
}