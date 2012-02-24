package util;

public enum MailInterval {
	m10(10, "m"),
	h12(12, "h"),
	h24(24, "h"),
	d7(7, "d"),
	d30(30, "d");
	
	private final int val;
	private final String type;

	private MailInterval(int val, String type) {
		this.val = val;
		this.type = type;
	}
	
	public long getIntervalInMillis() {
		switch (this) {
		case m10:
			return this.val * 60 * 1000;
		case h12:
			return this.val * 60 * 60 * 1000;
		case h24:
			return this.val * 60 * 60 * 1000;
		case d7:
			return this.val * 60 * 60 * 7 * 1000;
		case d30:
			return this.val * 60 * 60 * 30 * 1000;
		default:
//			Failure
			return Long.MIN_VALUE;
		}
	}

	public String getType() {
		return type;
	}
}
