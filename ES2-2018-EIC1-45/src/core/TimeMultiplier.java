package core;

/**
 * @author Markiyan Pyekh
 *
 */
public enum TimeMultiplier {

	HOUR(3600, "hour(s)"), MINUTE(60, "minute(s)"), SECOND(1, "second(s)");

	private int multiplier;
	private String name;

	/**
	 * The constructor
	 * 
	 * @param value
	 * @param name
	 */
	private TimeMultiplier(int value, String name) {
		this.multiplier = value;
		this.name = name;
	}

	/**
	 * Returns the multiplier
	 * 
	 * @return multiplier
	 */
	public int getMultiplier() {
		return multiplier;
	}

	/**
	 * Returns the time multiplier name
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}

}
