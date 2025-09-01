package chapter6.dao;

import static chapter6.utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

        String sql = "DELETE FROM messages WHERE id = ?";
        //DBに送るための箱
        PreparedStatement ps = null;

        try {
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
    	String sql = "SELECT text, id FROM messages WHERE id = ?";
    	PreparedStatement ps = null;
	    try {
			//SQL文をとりあえずDBに送る（便利なので）
			ps = connection.prepareStatement(sql);
			//sqlの1つ目の？にmessageIdを入れる
			ps.setInt(1, messageId);
			//実行した結果をResultSetに入れる  SELECTだからexecuteQuery()！！データ更新ならexecuteUpdate()！
			ResultSet rs = ps.executeQuery();
			//rs.next()) で次の行に進むもしもないならnullを返す
			if (!rs.next()) {
	            return null;
	        }
			//messageオブジェクトを作って、その中にテキストのＩＤとテキストを入れる
			Message m = new Message();
			m.setId(messageId);
            m.setText(rs.getString("text"));

			return m;

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
    	//sql文を作る コマンド＋テーブル名
    	String sql = "UPDATE messages SET text = ? WHERE id = ?";
    	//DBに送るための箱をつくる
    	PreparedStatement ps = null;
    	try {
    		//SQL文をとりあえずDBに送る（便利なので）
    		ps = connection.prepareStatement(sql);
    		//sqlの1つ目の？にtextを入れる
    		ps.setString(1, text);
    		//sqlの2つ目の？にmessageIdを入れる
        	ps.setInt(2, messageId);
        	ps.executeUpdate();
    	}catch (SQLException e) {
            log.log(Level.SEVERE, getClass().getName() + " : " + e, e);
            throw new SQLRuntimeException(e);
    	}finally {
	        close(ps); // PreparedStatementだけ閉じる（connectionは閉じない）
	    }
    }

}



