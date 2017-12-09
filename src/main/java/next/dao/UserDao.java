package next.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.jdbc.JdbcTemplate;
import next.model.User;

public class UserDao {
	private static final Logger log = LoggerFactory.getLogger(UserDao.class);
	private JdbcTemplate jdbcTemplate;
	private ResultMap<User> resultMap = new ResultMap<User>() {
		@Override
		public User mappingRow(ResultSet resultSet) throws SQLException {
			return new User(resultSet.getString("userId"), resultSet.getString("password"), resultSet.getString("name"),
					resultSet.getString("email"));
		}
	};

	public UserDao() {
		this.jdbcTemplate = new JdbcTemplate();
	}

	public void insertUser(User user) throws SQLException {
		log.debug("UserDao insertUser user : {}", user);
		
		String insertQuery = "INSERT INTO users VALUES ('#{userId}', '#{password}', '#{name}', '#{email}')";

		jdbcTemplate.excuteUpdate(insertQuery, user);
	}

	public void updateUser(User user) throws SQLException {
		log.debug("UserDao updateUser {}" + user);

		String updateQuery = "UPDATE users SET password = '#{password}', name = '#{name}', email = '#{email}' WHERE userId = '#{userId}'";

		jdbcTemplate.excuteUpdate(updateQuery, user);
	}

	public List<User> selectUsers() throws SQLException {
		log.debug("UserDao selectUsers");

		String selectQuery = "SELECT userId, password, name, email FROM users";

		return jdbcTemplate.excuteQueryForList(selectQuery, resultMap);
	}

	public User selectUser(String userId) {
		log.debug("UserDao selectUser userId : {}", userId);

		String selectQuery = "SELECT userId, password, name, email FROM users WHERE userId = '#{userId}'";

		return jdbcTemplate.excuteQueryForObject(selectQuery, resultMap, userId);
	}

}
