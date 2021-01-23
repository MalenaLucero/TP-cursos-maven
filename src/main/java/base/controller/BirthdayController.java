package base.controller;

import java.sql.Connection;
import java.sql.SQLException;

public class BirthdayController {
	public static void showBirthdays(Connection connection) throws SQLException {
		TeacherController.findNextBirthday(connection);
		TeacherController.findCurrentMonthBirthdays(connection);
	}
}
