package base.util;

public class ValidationUtil {
	public static boolean isGradeValid(int grade) {
		if(grade > 0 && grade < 11) {
			return true;
		} else {
			return false;
		}
	}
}
