package base.enums;

public enum EnrollmentState {
	activo("activo"), cancelado("cancelado");

	private final String value;
	
	private EnrollmentState(String string) {
		this.value = string;
	}

	public String getValue() {
		return value;
	}
}
