package chapter6.dao;

import static chapter6.utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import chapter6.beans.UserComment;
import chapter6.exception.SQLRuntimeException;
import chapter6.logging.InitApplication;

public class UserCommentDao {
	Logger log = Logger.getLogger("twitter");

	public UserCommentDao() {
		InitApplication application = InitApplication.getInstance();
        application.init();
	}
	public List<UserComment> select(Connection connection) {

		  log.info(new Object(){}.getClass().getEnclosingClass().getName() +
	        " : " + new Object(){}.getClass().getEnclosingMethod().getName());

	        PreparedStatement ps = null;

	        try {
	        	StringBuilder sql = new StringBuilder();
	        	sql.append("SELECT ");
	        	sql.append("  comments.id            AS id, ");
	        	sql.append("  comments.text          AS text, ");
	        	sql.append("  comments.user_id       AS user_id, ");
	        	sql.append("  comments.message_id       AS message_id, ");
	            sql.append("  users.account       AS account, ");
	        	sql.append("  users.name          AS name, ");
	            sql.append("  comments.created_date  AS created_date ");
	        	sql.append("FROM comments ");
	        	sql.append("INNER JOIN users ");
	        	sql.append("ON comments.user_id = users.id ");
	        	sql.append("WHERE comments.user_id = users.id ");
	        	sql.append("ORDER BY created_date ASC");


	        //SQLをデータに送るための準備のオブジェクト
	        	ps = connection.prepareStatement(sql.toString());
	        //SQLに投げた結果を受け取るオブジェクト
	        ResultSet rs = ps.executeQuery();



	        //SQLの結果をtoComments(rs)で一行ずつ読みとってJAVAの中に入れている
	        List<UserComment> comments = toUserComments(rs);
	        return comments;

	    } catch (SQLException e) {
	    	log.log(Level.SEVERE, new Object(){}.getClass().getEnclosingClass().getName() + " : " + e.toString(), e);
	        throw new SQLRuntimeException(e);
	    } finally {
	        close(ps);
	    }



	 }

	 private List<UserComment> toUserComments(ResultSet rs) throws SQLException {


		  log.info(new Object(){}.getClass().getEnclosingClass().getName() +
	        " : " + new Object(){}.getClass().getEnclosingMethod().getName());

	        List<UserComment> comments = new ArrayList<UserComment>();
	        try {
	        	//読み込んだ情報をコメントオブジェクトに入れる
	            while (rs.next()) {
	                UserComment comment = new UserComment();
	                comment.setId(rs.getInt("id"));
	                comment.setText(rs.getString("text"));
	                comment.setUserId(rs.getInt("user_id"));
	                comment.setAccount(rs.getString("account"));
	                comment.setName(rs.getString("name"));
	                comment.setMessageId(rs.getInt("message_id"));
	                comment.setCreatedDate(rs.getTimestamp("created_date"));
	                //それをリストに入れる
	                comments.add(comment);
	            }
	            return comments;
	        } finally {
	            close(rs);
	        }
	    }

}
