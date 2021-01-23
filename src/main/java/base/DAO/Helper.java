package base.DAO;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

public class Helper {
	public static void setPossibleNullInt(PreparedStatement statement, int index, int number) throws SQLException {
		if(number == 0) {
			statement.setNull(index, java.sql.Types.NULL);
		} else {
			statement.setInt(index, number);
		}
	}
	
	public static void setPossibleNullDate(PreparedStatement statement, int index, Date date) throws SQLException {
		if(date == null) {
			statement.setNull(index, java.sql.Types.NULL);
		} else {
			statement.setDate(index, new java.sql.Date(date.getTime()));
		}
	}
}
