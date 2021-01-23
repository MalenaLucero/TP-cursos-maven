package base.enums;

public enum Division {
	A("A"), B("B"), C("C"), D("D");

	private final String value;
	
	private Division(String string) {
		this.value = string;
	}

	public String getValue() {
		return value;
	}
}
