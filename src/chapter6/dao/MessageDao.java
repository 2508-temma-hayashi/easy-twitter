package chapter6.dao;

import static chapter6.utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import chapter6.beans.Message;
import chapter6.exception.SQLRuntimeException;
import chapter6.logging.InitApplication;

public class MessageDao {


    /**
    * ロガーインスタンスの生成
    */
    Logger log = Logger.getLogger("twitter");

    /**
    * デフォルトコンストラクタ
    * アプリケーションの初期化を実施する。
    */
    public MessageDao() {
        InitApplication application = InitApplication.getInstance();
        application.init();

    }

    public void insert(Connection connection, Message message) {

	  log.info(new Object(){}.getClass().getEnclosingClass().getName() +
        " : " + new Object(){}.getClass().getEnclosingMethod().getName());

        PreparedStatement ps = null;
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("INSERT INTO messages ( ");
            sql.append("    user_id, ");
            sql.append("    text, ");
            sql.append("    created_date, ");
            sql.append("    updated_date ");
            sql.append(") VALUES ( ");
            sql.append("    ?, ");                  // user_id
            sql.append("    ?, ");                  // text
            sql.append("    CURRENT_TIMESTAMP, ");  // created_date
            sql.append("    CURRENT_TIMESTAMP ");   // updated_date
            sql.append(")");

            ps = connection.prepareStatement(sql.toString());

            ps.setInt(1, message.getUserId());
            ps.setString(2, message.getText());

            ps.executeUpdate();
        } catch (SQLException e) {
		log.log(Level.SEVERE, new Object(){}.getClass().getEnclosingClass().getName() + " : " + e.toString(), e);
            throw new SQLRuntimeException(e);
        } finally {
            close(ps);
        }
    }

    public void delete(Connection connection, int messageId) {
    	log.info(getClass().getName() + " : " + new Object(){}.getClass().getEnclosingMethod().getName());
        //DBに送るための箱
        PreparedStatement ps = null;

        try {
        	String sql = "DELETE FROM messages WHERE id = ?";
        	//文を準備してIDをセットして削除を実行
        	ps = connection.prepareStatement(sql);
        	ps.setInt(1, messageId);
        	ps.executeUpdate();
        }catch (SQLException e) {
	        log.log(Level.SEVERE,new Object(){}.getClass().getEnclosingClass().getName() + " : " + e.toString(), e);
	        throw new SQLRuntimeException(e);
	    } finally {
	        close(ps);                   // PreparedStatementだけ閉じる（connectionは閉じない）
	    }


    }

    //messageIdからメッセージを取得する
    public Message select(Connection connection, int messageId) {
    	//ログを付ける
    	log.info(getClass().getName() + " : " + new Object(){}.getClass().getEnclosingMethod().getName());
    	PreparedStatement ps = null;
    	try {
	    	String sql = "SELECT * FROM messages WHERE id = ?";
			//SQL文をとりあえずDBに送る（便利なので）
			ps = connection.prepareStatement(sql);
			//sqlの1つ目の？にmessageIdを入れる
			ps.setInt(1, messageId);
			//実行した結果をResultSetに入れる  SELECTだからexecuteQuery()！！データ更新ならexecuteUpdate()！
			ResultSet rs = ps.executeQuery();
			//rs.next()) で次の行に進むもしもないならnullを返す
            List<Message> messages = toMessages(rs);
            if (messages.isEmpty()) {
                return null;
            } else {
                return messages.get(0);
            }


	    }catch (SQLException e) {
	        log.log(Level.SEVERE, getClass().getName() + " : " + e, e);
	        throw new SQLRuntimeException(e);
		}finally {
	        close(ps); // PreparedStatementだけ閉じる（connectionは閉じない）
	    }
    }

    //textを更新するメソッド
    public void updateText(Connection connection, int messageId, String text) {
    	//ログを付ける
    	log.info(getClass().getName() + " : " + new Object(){}.getClass().getEnclosingMethod().getName());
    	//DBに送るための箱をつくる
    	PreparedStatement ps = null;
    	try {
    		//sql文を作る コマンド＋テーブル名
        	String sql = "UPDATE messages SET text = ? , updated_date = ? WHERE id = ? ";
    		//SQL文をとりあえずDBに送る（便利なので）
    		ps = connection.prepareStatement(sql);
    		//sqlの1つ目の？にtextを入れる
    		ps.setString(1, text);
    		//sqlの2つ目の？にtimestampを入れる
    		ps.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
    		//三つ目にいれる
        	ps.setInt(3, messageId);
        	ps.executeUpdate();
    	}catch (SQLException e) {
            log.log(Level.SEVERE, getClass().getName() + " : " + e, e);
            throw new SQLRuntimeException(e);
    	}finally {
	        close(ps); // PreparedStatementだけ閉じる（connectionは閉じない）
	    }
    }

    private List<Message> toMessages(ResultSet rs) throws SQLException {

            List<Message> messages = new ArrayList<Message>();
            try {
                while (rs.next()) {
                    Message message = new Message();
                    message.setId(rs.getInt("id"));
                    message.setUserId(rs.getInt("user_Id"));
                    message.setText(rs.getString("text"));
                    message.setCreatedDate(rs.getTimestamp("created_date"));
                    message.setUpdatedDate(rs.getTimestamp("updated_date"));

                    messages.add(message);
                }
                return messages;
            } finally {
                close(rs);
           }
        }
}



