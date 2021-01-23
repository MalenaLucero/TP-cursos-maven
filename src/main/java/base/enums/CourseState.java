package base.enums;

public enum CourseState {
	cursando("cursando"), aprobado("aprobado"), desaprobado("desaprobado");

	private final String value;
	
	private CourseState(String string) {
		this.value = string;
	}

	public String getValue() {
		return value;
	}

}
