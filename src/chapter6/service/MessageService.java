package chapter6.service;

import static chapter6.utils.CloseableUtil.*;
import static chapter6.utils.DBUtil.*;

import java.sql.Connection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang.StringUtils;

import chapter6.beans.Message;
import chapter6.beans.UserMessage;
import chapter6.dao.MessageDao;
import chapter6.dao.UserMessageDao;
import chapter6.logging.InitApplication;

public class MessageService {


    /**
    * ロガーインスタンスの生成
    */
    Logger log = Logger.getLogger("twitter");

    /**
    * デフォルトコンストラクタ
    * アプリケーションの初期化を実施する。
    */
    public MessageService() {
        InitApplication application = InitApplication.getInstance();
        application.init();

    }

    public void insert(Message message) {

	  log.info(new Object(){}.getClass().getEnclosingClass().getName() +
        " : " + new Object(){}.getClass().getEnclosingMethod().getName());

        Connection connection = null;
        try {
            connection = getConnection();
            new MessageDao().insert(connection, message);
            commit(connection);
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

    /*
     * selectの引数にString型のuserIdを追加
     */
    public List<UserMessage> select(String userId) {
      final int LIMIT_NUM = 1000;

      Connection connection = null;
      try {
        connection = getConnection();
        /*
        * idをnullで初期化
        * ServletからuserIdの値が渡ってきていたら
        * 整数型に型変換し、idに代入
        */
        Integer id = null;
        if(!StringUtils.isEmpty(userId)) {
            id = Integer.parseInt(userId);
        }

        /*
        * messageDao.selectに引数としてInteger型のidを追加
        * idがnullだったら全件取得する
        * idがnull以外だったら、その値に対応するユーザーIDの投稿を取得する
        */
        List<UserMessage> messages = new UserMessageDao().select(connection, id, LIMIT_NUM);
            return messages;

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

    //つぶやきの編集編集画面の表示のための取得
    public Message select(int messageId) {
    	Connection connection = null;
        try {
        	//つなげる
          connection = getConnection();
          //DBからtextを1件抽出するDAOのメソッドを使う
          Message message = new MessageDao().select(connection, messageId);
          return message;

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

    //つぶやきの編集
    public void update(Message message) {
    	Connection connection = null;
        try {
        	//DBにつなげる
          connection = getConnection();
          //DAOのupdateメソッドで
          new MessageDao().update(connection, message);
          commit(connection);


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


    public void delete(int messageId){
    	//ログに記録させるためのもの
        log.info(getClass().getName() + " : " + new Object(){}.getClass().getEnclosingMethod().getName());
        //DBに接続
        Connection connection = null;
        try {
            connection = getConnection();

            // 削除
            new MessageDao().delete(connection, messageId);

            commit(connection);
        } catch (RuntimeException e) {
            rollback(connection);
            log.log(Level.SEVERE,new Object(){}.getClass().getName() + " : " + e.toString(), e);
            throw e;
        } finally {
            close(connection);
        }
    }
}


