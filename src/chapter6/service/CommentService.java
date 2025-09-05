package chapter6.service;

import static chapter6.utils.CloseableUtil.*;
import static chapter6.utils.DBUtil.*;

import java.sql.Connection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import chapter6.beans.Comment;
import chapter6.beans.UserComment;
import chapter6.dao.CommentDao;
import chapter6.dao.UserCommentDao;
import chapter6.logging.InitApplication;

public class CommentService {
	 Logger log = Logger.getLogger("twitter");
	 public CommentService() {
	 InitApplication application = InitApplication.getInstance();
     application.init();
	 }



	public void insert(Comment comment) {
		log.info(new Object(){}.getClass().getEnclosingClass().getName() +
		        " : " + new Object(){}.getClass().getEnclosingMethod().getName());

		Connection connection = null;
        try {
            connection = getConnection();
            new CommentDao().insert(connection, comment);
            commit(connection);
        } catch (RuntimeException e) {
            rollback(connection);
            throw e;
        } catch (Error e) {
            rollback(connection);
            throw e;
        } finally {
            close(connection);
        }


	}
	//つぶやきの返信全件取得
	public List<UserComment> select() {
		Connection connection = null;
	      try {
	        connection = getConnection();

	        List<UserComment> comments = new UserCommentDao().select(connection);
	            return comments;

	        } catch (RuntimeException e) {
	            rollback(connection);
			log.log(Level.SEVERE, new Object(){}.getClass().getEnclosingClass().getName() + " : " + e.toString(), e);
	            throw e;
	        } catch (Error e) {
	            rollback(connection);
			log.log(Level.SEVERE, new Object(){}.getClass().getEnclosingClass().getName() + " : " + e.toString(), e);
	            throw e;
	        } finally {
	            close(connection);
	        }
	    }

}