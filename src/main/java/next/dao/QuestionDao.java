package next.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import core.jdbc.*;
import next.model.Question;

public class QuestionDao {
    private JdbcTemplate jdbcTemplate;

    public QuestionDao() {
        jdbcTemplate = JdbcTemplate.getInstance();
    }

    public void insert(Question question) {
        String sql = "INSERT INTO questions(writer, title, contents, createdDate) VALUES (?, ?, ?, ?)";

        PreparedStatementCreator preparedStatementCreator = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement pstmt = con.prepareStatement(sql);
                pstmt.setString(1, question.getWriter());
                pstmt.setString(2, question.getTitle());
                pstmt.setString(3, question.getContents());
                pstmt.setTimestamp(4, new Timestamp(question.getTimeFromCreateDate()));
                return pstmt;
            }
        };

        jdbcTemplate.update(preparedStatementCreator);
    }

    public void update(Question question) {
        String questionUpdateSql = "UPDATE questions SET writer = ?, title = ?, contents = ? WHERE questionId = ?";

        PreparedStatementCreator preparedStatementCreator = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement pstmt = con.prepareStatement(questionUpdateSql);
                pstmt.setString(1, question.getWriter());
                pstmt.setString(2, question.getTitle());
                pstmt.setString(3, question.getContents());
                pstmt.setLong(4, question.getQuestionId());
                return pstmt;
            }
        };

        jdbcTemplate.update(preparedStatementCreator);
    }

    public List<Question> findAll() {
        String sql = "SELECT questionId, writer, title, createdDate, countOfAnswer FROM QUESTIONS "
                + "order by questionId desc";

        RowMapper<Question> rm = new RowMapper<Question>() {
            @Override
            public Question mapRow(ResultSet rs) throws SQLException {
                return new Question(rs.getLong("questionId"), rs.getString("writer"), rs.getString("title"), null,
                        rs.getTimestamp("createdDate"), rs.getInt("countOfAnswer"));
            }

        };

        return jdbcTemplate.query(sql, rm);
    }

    public Question findById(long questionId) {
        String sql = "SELECT questionId, writer, title, contents, createdDate, countOfAnswer FROM QUESTIONS "
                + "WHERE questionId = ?";

        RowMapper<Question> rm = new RowMapper<Question>() {
            @Override
            public Question mapRow(ResultSet rs) throws SQLException {
                return new Question(rs.getLong("questionId"), rs.getString("writer"), rs.getString("title"),
                        rs.getString("contents"), rs.getTimestamp("createdDate"), rs.getInt("countOfAnswer"));
            }
        };

        return jdbcTemplate.queryForObject(sql, rm, questionId);
    }

    public void increaseCountOfAnswer(long questionId) {
        String AnswerCountUpdateSql = "UPDATE  questions SET countOfAnswer = countOfAnswer + 1 WHERE questionId = ?";

        jdbcTemplate.update(AnswerCountUpdateSql, questionId);
    }

    public void decreaseCountOfAnswer(long questionId) {
        String AnswerCountUpdateSql = "UPDATE  questions SET countOfAnswer = countOfAnswer - 1 WHERE questionId = ?";

        jdbcTemplate.update(AnswerCountUpdateSql, questionId);
    }

    public void deleteQustion(long questionId) {
        String questionDeleteSql = "DELETE FROM questions WHERE questionId = ?";

        jdbcTemplate.update(questionDeleteSql, questionId);
    }

}
