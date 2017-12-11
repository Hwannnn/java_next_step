package core.jdbc;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import next.dao.ResultMap;
import next.model.GeneratedKey;

public class JdbcTemplate {
	private static final Logger log = LoggerFactory.getLogger(JdbcTemplate.class);
	private static final String SQL_PARAMETER_PATTERN = "\\{[a-zA-Z]*\\}";
	
	public void excuteUpdate(String sql, String... parameter) {
		log.debug("JdbcTemplate excuteUpdate sql : {}", sql);

		String replacedSql = setSqlParameter(sql, parameter);

		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(replacedSql);) {

			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			throw new DataAccessException("JdbcTemplate excuteUpdate sql : " + sql, e);
		}
	}
	
	public void excuteUpdate(String sql, Object model) {
		log.debug("JdbcTemplate excuteUpdate sql : ", sql);
		
		String replacedSql = setSqlParameter(sql, model);

		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(replacedSql);) {

			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			throw new DataAccessException("JdbcTemplate excuteUpdate sql : " + sql, e);
		}
	}
	
	public void excuteUpdate(String sql, Object model, GeneratedKey generatedKey) {
		log.debug("JdbcTemplate excuteUpdate sql : ", sql);
		
		String replacedSql = setSqlParameter(sql, model);

		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(replacedSql);) {
			
			preparedStatement.executeUpdate();
			
			ResultSet resultSet = preparedStatement.getGeneratedKeys();
			if(resultSet.next()) {
				generatedKey.setId(resultSet.getInt(1));
			}
			
			resultSet.close();
			
		} catch (SQLException e) {
			throw new DataAccessException("JdbcTemplate excuteUpdate sql : " + sql, e);
		}
	}

	public <T> T excuteQueryForObject(String sql, ResultMap<T> resultMap, String... parameter) {
		log.debug("JdbcTemplate excuteQuery sql : {}", sql);

		String replacedSql = setSqlParameter(sql, parameter);

		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(replacedSql);
			 ResultSet resultSet = preparedStatement.executeQuery();) {
			
			if(resultSet.next() == false) {
				return null;
			}

			return resultMap.mappingRow(resultSet);

		} catch (SQLException e) {
			throw new DataAccessException("JdbcTemplate excuteQuery sql : " + replacedSql, e);
		}
	}
	
	public <T> List<T> excuteQueryForList(String sql, ResultMap<T> resultMap, String... parameter) {
		log.debug("JdbcTemplate excuteQuery sql : ", sql);
		
		List<T> resultModels = new ArrayList<>();
		String replacedSql = setSqlParameter(sql, parameter);
		
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(replacedSql);
			 ResultSet resultSet = preparedStatement.executeQuery();) {

			while(resultSet.next()) {
				resultModels.add(resultMap.mappingRow(resultSet));
			}
			
			return resultModels;

		} catch (SQLException e) {
			throw new DataAccessException("JdbcTemplate excuteQuery sql : " + replacedSql, e);
		}
	}
	
	@SuppressWarnings("unused")
	private String setSqlParameter(String sql, String... parameter) {
		String[] replacedSql = sql.split("#");

		for (int index = 1; index < replacedSql.length; index++) {
			if (replacedSql[index].indexOf("{") > -1 && replacedSql[index].indexOf("}") > -1) {
				replacedSql[index] = replacedSql[index].replaceFirst(SQL_PARAMETER_PATTERN, parameter[index - 1]);
			}
		}

		return String.join("", replacedSql);
	}

	private String setSqlParameter(String sql, Object model) {
		String[] replacedSql = sql.split("#");

		for (int index = 1; index < replacedSql.length; index++) {
			int preBracketIndex = replacedSql[index].indexOf("{");
			int postBracketIndex = replacedSql[index].indexOf("}");

			if (preBracketIndex > -1 && preBracketIndex > -1) {
				String sqlParameter = replacedSql[index].substring(preBracketIndex + 1, postBracketIndex);
				replacedSql[index] = replacedSql[index].replaceFirst(SQL_PARAMETER_PATTERN, excuteGetterMethod(model, sqlParameter));
			}
		}

		return String.join("", replacedSql);
	}
	
	@SuppressWarnings("unused")
	private String excuteGetterMethod(Object model, String parameter) {
		String getterMethodValue = null;
		Class<?> modelClass = model.getClass();

		try {
			for (Method method : modelClass.getMethods()) {
				String getterMethodName = combineGetterMethodName(parameter);

				if (getterMethodName.equals(method.getName())) {
					getterMethodValue = method.invoke(model) + "";
					break;
				}
			}

		} catch (Exception e) {
			log.debug("JdbcTemplate excuteGetterMethod Error model, parameter : {}, {}", model, parameter, e);
		}

		return getterMethodValue;
	}

	private String combineGetterMethodName(String variableName) {
		String firstLetterCapitalize = variableName.substring(0, 1).toUpperCase() + variableName.substring(1);

		return "get" + firstLetterCapitalize;
	}

}
