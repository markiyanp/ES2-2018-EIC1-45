package core;

public enum TimeMultiplier {

	HOUR(3600, "hour(s)"), MINUTE(60, "minute(s)"), SECOND(1, "second(s)");

	private int multiplier;
	private String name;

	private TimeMultiplier(int value, String name) {
		this.multiplier = value;
		this.name = name;
	}

	public int getMultiplier() {
		return multiplier;
	}

	public String getName() {
		return name;
	}

}
