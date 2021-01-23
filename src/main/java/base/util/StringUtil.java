package base.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class StringUtil {
	public static boolean isBlank(String string) {
		if(string != null && string.length() > 0) {
			return false;
		} else {
			return true;
		}
	}
	
	public static String dateToString(Date date) {
		if(date == null) {
			return null;
		} else {
			SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");
			return DATE_FORMAT.format(date);
		}
	}
}
