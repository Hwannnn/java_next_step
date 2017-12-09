package next.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.jdbc.JdbcTemplate;
import next.model.Answer;
import next.model.GeneratedKey;

public class AnswerDao {
	private static final Logger log = LoggerFactory.getLogger(AnswerDao.class);
	private JdbcTemplate jdbcTemplate;
	private ResultMap<Answer> resultMap = new ResultMap<Answer>() {
		@Override
		public Answer mappingRow(ResultSet resultSet) throws SQLException {
			Answer answer = new Answer();
			answer.setAnswerId(resultSet.getInt("answerId"));
			answer.setContents(resultSet.getString("contents"));
			answer.setWriter(resultSet.getString("writer"));
			answer.setCreatedDate(resultSet.getString("createdDate"));
			answer.setQuestionId(resultSet.getInt("questionId"));
			
			return answer;
		}
	};
	
	public AnswerDao() {
		jdbcTemplate = new JdbcTemplate();
	}

	public List<Answer> selectAnswers(int questionId) {
		log.debug("AnswerDao selectAnswers");

		String sql = "SELECT answerId, writer, contents, createdDate, questionId FROM answers WHERE questionId = #{questionId} ORDER BY createdDate DESC";

		return jdbcTemplate.excuteQueryForList(sql, resultMap, questionId + "");
	}
	
	public Answer selectAnswer(int answerId) {
		log.debug("AnswerDao selectAnswer");

		String sql = "SELECT answerId, writer, contents, createdDate, questionId FROM answers WHERE answerId = #{answerId}";
		
		return jdbcTemplate.excuteQueryForObject(sql, resultMap, answerId + "");
	}
	
	public int insertAnswer(Answer answer) {
		log.debug("AnswerDao insertAnswer");

		GeneratedKey generatedKey = new GeneratedKey();
		String sql = "INSERT INTO answers(writer, contents, createdDate, questionId) VALUES('#{writer}', '#{contents}', CURRENT_TIMESTAMP(), #{questionId})";
		
		jdbcTemplate.excuteUpdate(sql, answer, generatedKey);
		
		return generatedKey.getId();
	}

	public void deleteAnswer(int answerId) {
		log.debug("AnswerDao deleteAnswer");

		String sql = "DELETE FROM answers WHERE answerId = #{answerId}";
		
		jdbcTemplate.excuteUpdateById(sql, answerId + "");
	}
	
}
