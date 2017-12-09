package next.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.jdbc.JdbcTemplate;
import next.model.Qna;

public class QnaDao {
	private static final Logger log = LoggerFactory.getLogger(QnaDao.class);
	private JdbcTemplate jdbcTemplate;
	private ResultMap<Qna> resultMap = new ResultMap<Qna>() {
		@Override
		public Qna mappingRow(ResultSet resultSet) throws SQLException {
			Qna qna = new Qna();
			qna.setQuestionId(resultSet.getInt("questionId"));
			qna.setTitle(resultSet.getString("title"));
			qna.setWriter(resultSet.getString("writer"));
			qna.setContents(resultSet.getString("contents"));
			qna.setCreatedDate(resultSet.getString("createdDate"));
			qna.setCountOfAnswer(resultSet.getInt("countOfAnswer"));

			return qna;
		}
	};
	
	public QnaDao() {
		jdbcTemplate = new JdbcTemplate();
	}

	public List<Qna> selectQnas() {
		log.debug("QnaDao selectQnas");

		String sql = "SELECT questionId, title, writer, contents, createdDate, countOfAnswer FROM questions ORDER BY createdDate DESC";
		
		return jdbcTemplate.excuteQueryForList(sql, resultMap);
	}

	public Qna selectQna(int questionId) {
		log.debug("QnaDao selectQna questionId : {}", questionId);

		String sql = "SELECT questionId, title, writer, contents, createdDate, countOfAnswer FROM questions WHERE questionId = #{questionId}";
		
		return jdbcTemplate.excuteQueryForObject(sql, resultMap, questionId + "");
	}

	public void insertQna(Qna qna) {
		log.debug("QnaDao insertQna");

		String sql = "INSERT INTO questions(title, writer, contents, createdDate, countOfAnswer) VALUES('#{title}', '#{writer}', '#{contents}', CURRENT_TIMESTAMP(), 0)";
		
		jdbcTemplate.excuteUpdate(sql, qna);
	}

}
