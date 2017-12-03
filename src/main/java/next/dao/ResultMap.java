package next.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ResultMap<T> {
	T mappingRow(ResultSet resultSet) throws SQLException;
}
